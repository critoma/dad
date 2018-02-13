
package eu.ase.ftp;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.net.Socket;

/**
 * This class converts data streams to and from IMAGE representation.
 */
public class ImageRepresentation
	extends Representation
{
	ImageRepresentation()
		{
		super("binary", 'I');
		}

	/**
	 * @return an input stream to read data from the socket. Data will be
	 * translated from IMAGE representation to local representation.
	 */
	public InputStream getInputStream(Socket socket)
		throws IOException
		{
		return socket.getInputStream();
		}

	/**
	 * @return an output stream to write data to the socket. Data will be
	 * translated from local representation to IMAGE representation.
	 */
	public OutputStream getOutputStream(Socket socket)
		throws IOException
		{
		return socket.getOutputStream();
		}

	/**
	 * @return the size that the specified file would have in this
	 * representation.
	 */
	public long sizeOf(File file)
		throws IOException
		{
		return file.length();
		}
}
