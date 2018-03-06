
package eu.ase.ftp;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.net.Socket;

/**
 * This class handles transmissions using STREAM mode. The data is sent as a
 * continuous stream of data.
 */
public class StreamTransmissionMode
	extends TransmissionMode
{
	/* size of data chunks sent at a time. */
	private static final int BUFSIZ = 1024;

	StreamTransmissionMode()
	{
		super('S');
	}

	/**
	 * Reads the contents of the file from "in", and writes the data to
	 * the given socket using the specified representation.
	 */
	public void sendFile(InputStream in, Socket s, Representation representation)
		throws IOException
		{
		OutputStream out = representation.getOutputStream(s);
		byte buf[] = new byte[BUFSIZ];
		int nread;
		while ((nread = in.read(buf)) > 0)
			{
			out.write(buf, 0, nread);
			}
		out.close();
		}

	/**
	 * Reads data from the given socket and converts it from the
	 * specified representation to local representation, writing the
	 * result to a file via "out".
	 */
	public void receiveFile(Socket s, OutputStream out, Representation representation)
		throws IOException
		{
		InputStream in = representation.getInputStream(s);
		byte buf[] = new byte[BUFSIZ];
		int nread;
		while ((nread = in.read(buf, 0, BUFSIZ)) > 0)
			{
			out.write(buf, 0, nread);
			}
		in.close();
		}
}
