package eu.ase.ftp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Properties;

/**
 * This is the main class for jftpd. It creates the server socket and loops
 * forever accepting user connections and creating ServerPI threads to handle
 * them.
 * <p>
 * To run under unix, type:
 *
 * <pre>java ro.radcom.ftp.Server</pre>
 */
public class Server
	//implements org.jos.services.common.Server
{
	/**
	 * The version number of jftpd.
	 */
	public static final String VERSION = "Version 0.3";

	/**
	 * The port this server connects to.
	 */
	public static final int SERVER_PORT = 21;

	/**
	 * The default data port.
	 */
	public static final int SERVER_DATA_PORT = 20;

	/**
	 * The port this server is listening on.
	 */
	private int port;

	private String user;
	private String pass;
	private String ftpDir;

	public static void main(String[] args)
		throws Exception
		{
		if (args.length > 1)
			{
			System.err.println("Usage: Server [ port ]");
			System.exit(1);
			}

		InputStream propertiesIn = Server.class.getResourceAsStream("jftpd.properties");
		if (propertiesIn == null)
			throw new FileNotFoundException("${archive}/eu/ase/ftp/jftpd.properties");
		Properties properties = new Properties();
		properties.load(propertiesIn);
		
		int port = SERVER_PORT;
		if (args.length == 1)
			port = Integer.parseInt(args[0]);
		Server server = new Server(properties, port);

		// Start the FTP server as a standalone server.
		//
		server.start();
		}

	/**
	 * Create an FTP server to run on port 21.
	 */
	public Server(Properties properties)
		throws ParseException
		{
		this(properties, SERVER_PORT);
		}

	/**
	 * Create an FTP server to run on the specified port.
	 */
	public Server(Properties properties, int port)
		throws ParseException
		{
		configure(properties);
		this.port = port;
		}
	
	/**
	 * Starts the FTP server. This method listens for connections on the
	 * FTP server port (usually port 21), and spawns a new thread to
	 * handle each connection.
	 */
	private void start()
		throws Exception
		{
		ServerSocket serverSocket = new ServerSocket(port);
		while (true)
			{
			Socket clientSocket = serverSocket.accept();
			ServerPI pi = new ServerPI(clientSocket, this.user, this.pass, this.ftpDir);
			new Thread(pi).start();
			}
		}

	/**
	 * Handle a client connection. This may be called directly by a
	 * superserver instead of start().
	 *
	 * @param socket the client socket.
	 */
	public void service(Socket socket)
		throws Exception
		{
		ServerPI pi = new ServerPI(socket);
		pi.run();
		}

	/**
	 * Configures the ftp server from the configuration properties.
	 */
	private void configure(Properties properties)
		throws ParseException
		{
		String priorityStr = properties.getProperty("log.priority");
		if (priorityStr == null)
			throw new ParseException(ParseException.PROPERTY_UNDEFINED, "log.priority", null);
		String priorityNames[] = Logger.priorityNames;
		int priority = Logger.LOG_OFF;
		for (int i = 0; i < priorityNames.length; i++)
			{
			if (priorityNames[i].equals(priorityStr))
				{
				priority = i;
				break;
				}
			}

		Logger.setPriority(priority);

		this.user = properties.getProperty("log.user");
		this.pass = properties.getProperty("log.pass");
		this.ftpDir = properties.getProperty("log.ftpdir");

		String output = properties.getProperty("log.output");
		OutputStream outputStream = null;
		if (output == null || output.equals("stderr"))
			{
			outputStream = System.err;
			}
		else
			{
			try
				{
				outputStream = new FileOutputStream(output, true);
				}
			catch (IOException e)
				{
				throw new ParseException(ParseException.PROPERTY_INVALID, "log.output", output);
				}
			}
		Logger.setWriter(new PrintWriter(new OutputStreamWriter(outputStream), true));
		}
}
