
package eu.ase.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.net.ConnectException;
import java.net.Socket;

/**
 * This is the server data transfer process. It is responsible for
 * transferring files to and from the client. A separate data socket is
 * created to transfer the data.
 */
public class ServerDTP
{
	/**
	 * The ServerPI that uses this DTP.
	 */
	private ServerPI serverPI;

	/**
	 * The host of the data socket.
	 */
	private String dataHost;

	/**
	 * The port of the data socket.
	 */
	private int dataPort = -1;

	/**
	 * The transmission mode to be used. The initial transmission mode is
	 * STREAM mode.
	 */
	private TransmissionMode transmissionMode = TransmissionMode.STREAM;

	/**
	 * The representation being used for transmission. The initial
	 * representation type is ASCII.
	 */
	private Representation representation = Representation.ASCII;

	/**
	 * Creates a server data transfer process for the specified ServerPI.
	 */
	public ServerDTP(ServerPI serverPI)
		{
		this.serverPI = serverPI;
		}

	/**
	 * Sets the transmission mode.
	 */
	public void setTransmissionMode(TransmissionMode transmissionMode)
		{
		this.transmissionMode = transmissionMode;
		}

	/**
	 * Sets the structure.
	 */
	public void setDataStructure(char stru)
		{
		// Ignore. Java itself only supports file-structure, so there
		// is no sense adding record-structure support in the server.
		}

	/**
	 * @return the representation type used for transmission.
	 */
	public Representation getRepresentation()
	{
		return representation;
	}

	/**
	 * Sets the representation type used for transmission.
	 */
	public void setRepresentation(Representation representation)
		{
		this.representation = representation;
		}

	/**
	 * Sets the data port for an active transmission.
	 *
	 * @param host the host name to connect to.
	 * @param port the port number to connect to.
	 */
	public void setDataPort(String host, int port)
		{
		dataHost = host;
		dataPort = port;
		}

	/**
	 * Opens the data connection, reads the data according to the current
	 * transmission mode, representation type and structure, and writes it
	 * into the local file "path".
	 */
	public int receiveFile(String path)
		throws CommandException
		{
		int reply = 0;
		FileOutputStream fos = null;
		Socket dataSocket = null;
		try
			{
			File file = new File(path);
			if (file.exists())
				throw new CommandException(550, "File exists in that location.");

			fos = new FileOutputStream(file);

			// Connect to User DTP.
			//
			if (dataPort == -1)
				throw new CommandException(500, "Can't establish data connection: no PORT specified.");
			dataSocket = new Socket(dataHost, dataPort);

			// Read file contents.
			//
			serverPI.reply(150, "Opening " + representation.getName() + " mode data connection.");
			transmissionMode.receiveFile(dataSocket, fos, representation);
			reply = serverPI.reply(226, "Transfer complete.");
			}
		catch (ConnectException e)
			{
			throw new CommandException(425, "Can't open data connection.");
			}
		catch (IOException e)
			{
			throw new CommandException(550, "Can't write to file");
			}
		finally
			{
			try
				{
				if (fos != null)
					fos.close();
				if (dataSocket != null)
					dataSocket.close();
				}
			catch (IOException e)
				{
				}
			}
		return reply;
		}

	/**
	 * Opens the data connection reads the specified local file and writes
	 * it to the data socket using the current transmission mode,
	 * representation type and structure.
	 */
	public int sendFile(String path)
		throws CommandException
		{
		int reply = 0;
		FileInputStream fis = null;
		Socket dataSocket = null;
		try
			{
			File file = new File(path);
			if (!file.isFile())
				throw new CommandException(550, "Not a plain file.");

			fis = new FileInputStream(file);

			// Connect to User DTP.
			//
			if (dataPort == -1)
				throw new CommandException(500, "Can't establish data connection: no PORT specified.");
			dataSocket = new Socket(dataHost, dataPort);

			// Send file contents.
			//
			serverPI.reply(150, "Opening " + representation.getName() + " mode data connection.");
			transmissionMode.sendFile(fis, dataSocket, representation);
			reply = serverPI.reply(226, "Transfer complete.");
			}
		catch (FileNotFoundException e)
			{
			Logger.log(Logger.LOG_DEBUG, "No such file: " + e);
			throw new CommandException(550, "No such file.");
			}
		catch (ConnectException e)
			{
			throw new CommandException(425, "Can't open data connection.");
			}
		catch (IOException e)
			{
			Logger.log(Logger.LOG_DEBUG, "Not a regular file: " + e);
			throw new CommandException(553, "Not a regular file.");
			}
		finally
			{
			try
				{
				if (fis != null)
					fis.close();
				if (dataSocket != null)
					dataSocket.close();
				}
			catch (IOException e)
				{
				}
			}
		return reply;
		}

	/**
	 * Sends a list of file names to the User DTP. Each line contains only
	 * the file name, not modification dates and file sizes (see
	 * sendList).
	 *
	 * @param path the path of the directory to list.
	 */
	public int sendNameList(String path)
		throws CommandException
		{
		int reply = 0;
		Socket dataSocket = null;
		try
			{
			File dir = new File(path);
			String fileNames[] = dir.list();

			// Connect to User DTP.
			//
			dataSocket = new Socket(dataHost, dataPort);
			Representation representation = Representation.ASCII;
			PrintWriter writer = new PrintWriter(representation.getOutputStream(dataSocket));

			// Send file name list.
			//
			serverPI.reply(150, "Opening " + representation.getName() + " mode data connection.");
			for (int i = 0; i < fileNames.length; i++)
				{
				writer.print(fileNames[i]);
				writer.print('\n');
				}
			writer.flush();
			reply = serverPI.reply(226, "Transfer complete.");
			}
		catch (ConnectException e)
			{
			throw new CommandException(425, "Can't open data connection.");
			}
		catch (Exception e)
			{
			throw new CommandException(550, "No such directory.");
			}
		finally
			{
			try
				{
				if (dataSocket != null)
					dataSocket.close();
				}
			catch (IOException e)
				{
				}
			}
		return reply;
		}

	/**
	 * Sends a list of files in the specified directory to the User DTP.
	 * Each line contains the file name, modification date, file size and
	 * other information.
	 *
	 * @param path the path of the directory to list.
	 */
	public int sendList(String path)
		throws CommandException
		{
		int reply = 0;
		Socket dataSocket = null;
		try
			{
			File dir = new File(path);
			String fileNames[] = dir.list();
			int numFiles = fileNames != null ? fileNames.length : 0;

			// Connect to User DTP.
			//
			dataSocket = new Socket(dataHost, dataPort);
			Representation representation = Representation.ASCII;
			PrintWriter writer = new PrintWriter(representation.getOutputStream(dataSocket));

			// Send long file list.
			//
			serverPI.reply(150, "Opening " + representation.getName() + " mode data connection.");

			// Print the total number of files.
			//
			writer.print("total " + numFiles + "\n");

			// Loop through each file and print its name, size,
			// modification date etc.
			//
			for (int i = 0; i < numFiles; i++)
				{
				String fileName = fileNames[i];

				File file = new File(dir, fileName);
				listFile(file, writer);
				}

			writer.flush();

			reply = serverPI.reply(226, "Transfer complete.");
			}
		catch (ConnectException e)
			{
			throw new CommandException(425, "Can't open data connection.");
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new CommandException(550, "No such directory.");
			}
		finally
			{
			try
				{
				if (dataSocket != null)
					dataSocket.close();
				}
			catch (IOException e)
				{
				}
			}
		return reply;
		}

	/**
	 * Lists a single file in long format (including file sizes and
	 * modification dates etc.).
	 *
	 * @param file the file to list.
	 * @param writer the writer to print to.
	 */
	private void listFile(File file, PrintWriter writer)
		{
		Date date = new Date(file.lastModified());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd hh:mm");
		String dateStr = dateFormat.format(date);

		long size = file.length();
		String sizeStr = Long.toString(size);
		int sizePadLength = Math.max(8 - sizeStr.length(), 0);
		String sizeField = pad(sizePadLength) + sizeStr;

		writer.print(file.isDirectory() ? 'd' : '-');
		writer.print("rwxrwxrwx");
		writer.print(" ");
		writer.print("  1");
		writer.print(" ");
		writer.print("ftp     ");
		writer.print(" ");
		writer.print("ftp     ");
		writer.print(" ");
		writer.print(sizeField);
		writer.print(" ");
		writer.print(dateStr);
		writer.print(" ");
		writer.print(file.getName());

		writer.print('\n');
		}

	private static String pad(int length)
		{
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++)
			buf.append((char)' ');
		return buf.toString();
		}
}
