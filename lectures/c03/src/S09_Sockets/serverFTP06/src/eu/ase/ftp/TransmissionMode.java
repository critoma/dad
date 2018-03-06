
package eu.ase.ftp;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.net.Socket;

import java.util.Hashtable;

/**
 * Subclasses of TransmissionMode handle the sending or receiving of files in
 * different transmission modes.
 */
public abstract class TransmissionMode
{
	/**
	 * A code to transmission mode map.
	 */
	private static Hashtable<Character, TransmissionMode> transmissionModes = new Hashtable<Character, TransmissionMode>();

	/**
	 * The STREAM transmission mode.
	 */
	public static final TransmissionMode STREAM = new StreamTransmissionMode();

	/**
	 * @return the TransmissionMode indicated by the code argument.
	 */
	public static TransmissionMode get(char code)
		{
		return (TransmissionMode)transmissionModes.get(new Character(code));
		}

	/**
	 * The character code for this transmission mode.
	 */
	private char code;

	protected TransmissionMode(char code)
	{
		this.code = code;

		transmissionModes.put(new Character(code), this);
	}

	/**
	 * @return the character code for this transmission mode.
	 */
	public final char getCode()
	{
		return code;
	}

	/**
	 * Reads the contents of the file from "in", and writes the data to
	 * the given socket using the specified representation.
	 */
	public abstract void sendFile(InputStream in, Socket s, Representation representation)
		throws IOException;

	/**
	 * Reads data from the given socket and converts it from the
	 * specified representation to local representation, writing the
	 * result to a file via "out".
	 */
	public abstract void receiveFile(Socket s, OutputStream out, Representation representation)
		throws IOException;
}
