
package eu.ase.ftp;

/**
 * This exception is thrown if there is an error parsing a configuration file.
 */
public class ParseException
	extends Exception
{
	/**
	 * This reason is specified if the named property was undefined.
	 */
	public static final int PROPERTY_UNDEFINED = 0;

	public static final long serialVersionUID = 0123L;

	/**
	 * This reason is specified if the named property was invalid.
	 */
	public static final int PROPERTY_INVALID = 1;

	/**
	 * The reason for this exception.
	 */
	private int reason;

	/**
	 * The name of the property that could not be read.
	 */
	private String propertyName;

	/**
	 * The value of the property that could not be read. If propertyValue
	 * is null, this indicates that the property value was not specified
	 * in the configuration file.
	 */
	private String propertyValue;

	/**
	 * Creates a ParseException with the specified reason, propertyName
	 * and propertyValue. The propertyValue may be null.
	 */
	public ParseException(int reason, String propertyName, String propertyValue)
		{
		this.reason = reason;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		}

	/**
	 * @return the reason for this exception.
	 */
	public int getReason()
		{
		return reason;
		}

	/**
	 * @return the name of the property that could not be read.
	 */
	public String getPropertyName()
		{
		return propertyName;
		}

	/**
	 * @return the value of the property that could not be read.
	 */
	public String getPropertyValue()
		{
		return propertyValue;
		}

	/**
	 * @return the message associated with this ParseException.
	 */
	public String getMessage()
		{
		switch (reason)
			{
			case PROPERTY_UNDEFINED:
				return "Property '" + propertyName + "' is undefined.";
			case PROPERTY_INVALID:
				return "Property '" + propertyName + "' is invalid: " + propertyValue;
			default:
				return "Unknown reason.";
			}
		}
}
