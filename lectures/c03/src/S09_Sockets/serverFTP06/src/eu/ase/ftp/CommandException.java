
package eu.ase.ftp;

/**
 * This exception is thrown if there is an error handling any user command.
 */
public class CommandException
	extends Exception
{
	public static final long serialVersionUID = 0124L;

	/**
	 * The reply code to send to the user.
	 */
	private int code;

	/**
	 * The text message to send with the reply code.
	 */
	private String text;

	/**
	 * Creates a new CommandException.
	 *
	 * @param code the reply code.
	 * @param text the associated error message.
	 */
	public CommandException(int code, String text)
		{
		super(code + " " + text);
		this.code = code;
		this.text = text;
		}

	/**
	 * @return the reply code.
	 */
	public int getCode()
		{
		return code;
		}

	/**
	 * @return the reply text.
	 */
	public String getText()
		{
		return text;
		}

	/**
	 * Throwable.getMessage() returns the message passed into the
	 * constructor. CommandException does not use this argument so
	 * getMessage() is overridden to return a string constructed from the
	 * exception code and text.
	 */
	public String getMessage()
		{
		return code + " " + text;
		}
}
