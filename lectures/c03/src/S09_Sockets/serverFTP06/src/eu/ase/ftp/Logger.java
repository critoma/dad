
package eu.ase.ftp;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.Date;

/**
 * This class provides a logging facility. Each message is logged with a
 * priority allowing the log to be configured to filter out log messages that
 * are below a certain priority.
 */
public class Logger
{
	// Priority levels.

	public static final int LOG_EMERG = 0;		/* system is unusable */
	public static final int LOG_ALERT = 1;		/* action must be taken immediately */
	public static final int LOG_CRIT = 2;		/* critical conditions */
	public static final int LOG_ERR = 3;		/* error conditions */
	public static final int LOG_WARNING = 4;	/* warning conditions */
	public static final int LOG_NOTICE = 5;		/* normal but significant condition */
	public static final int LOG_INFO = 6;		/* informational */
	public static final int LOG_DEBUG = 7;		/* debug-level messages */

	public static final int LOG_OFF = -1;		/* Turns logging off */

	/**
	 * An array of priority names.
	 */
	public static final String priorityNames[] =
		{
		"EMERG",
		"ALERT",
		"CRIT",
		"ERR",
		"WARNING",
		"NOTICE",
		"INFO",
		"DEBUG"
		};

	/**
	 * The Writer that logs will be written to.
	 */
	private static PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.err), true);

	/**
	 * Log entries above this priority will be logged. Note that lower
	 * priority numbers represent higher priorities. Initially this value
	 * is set to LOG_OFF.
	 */
	private static int priority = LOG_OFF;

	/**
	 * This class can't be instanciated.
	 */
	private Logger()
		{
		}

	public static void setWriter(PrintWriter writer)
		{
		Logger.writer = writer;
		}

	public static void setPriority(int priority)
		{
		Logger.priority = priority;
		}

	/**
	 * Logs a message to the log with the specified priority.
	 *
	 * @param priority the priority of this log message.
	 * @param message a message string that does not end with a new line.
	 */
	public static void log(int priority, String message)
		{
		if (priority <= Logger.priority)
			writer.println(new Date() + "\t" + priorityNames[priority] + "\t" + message);
		}
}
