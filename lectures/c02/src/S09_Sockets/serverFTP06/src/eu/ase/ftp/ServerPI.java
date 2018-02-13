package eu.ase.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.Socket;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.StringTokenizer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This is the server protocol interpreter. The main Server object creates one
 * of these for each user connection to interpret user commands.
 *
 * This class implements the Runnable interface so that it can be run as a
 * thread or invoked directly.
 */
public class ServerPI
	implements Runnable
{
	/**
	 * The client we are talking to.
	 */
	private Socket clientSocket;

	/**
	 * A Reader for reading from the client socket.
	 */
	private BufferedReader reader;

	/**
	 * A Writer for writing to the client socket.
	 */
	private PrintWriter writer;

	/**
	 * The data transfer process responsible for transferring files to and
	 * from the user.
	 */
	private ServerDTP dtp;

	/**
	 * Reflection is used to invoke a command handler for a given string
	 * command. This variable caches the argument types for command
	 * handler methods.
	 */
	private Class commandHandlerArgTypes[] = { String.class, StringTokenizer.class };

	/**
	 * Stores the username of the user.
	 */
	private String username;
	private String inUser;

	/**
	 * Stores the password of the user.
	 */
	private String password;

	/**
	 * This is effectively the root directory as seen from outside.
	 */
	//private final String baseDir = System.getProperty("user.dir");
	private String baseDir = System.getProperty("user.dir");

	/**
	 * The current directory as seen externally.
	 */
	private String currentDir = "/";

	private int isLoggedIn = 0;

	/**
	 * Creates a server protocol interpreter for the specified client
	 * socket. Commands will be read from the socket, and replies will be
	 * written to the socket. Data transfers, however, require a separate
	 * data socket to be created via the ServerDTP.
	 */
	public ServerPI(Socket clientSocket)
		throws IOException
		{
		this.clientSocket = clientSocket;
		reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

		dtp = new ServerDTP(this);
		}


	/**
	 * Creates a server protocol interpreter for the specified client
	 * socket. Commands will be read from the socket, and replies will be
	 * written to the socket. Data transfers, however, require a separate
	 * data socket to be created via the ServerDTP.
	 */
	public ServerPI(Socket clientSocket, String user, String pass, String ftpDir)
		throws IOException
		{
		this.clientSocket = clientSocket;
		this.username = user; this.password = pass; this.baseDir = ftpDir;

		reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

		dtp = new ServerDTP(this);
		}

	/**
	 * Runs the protocol interpreter for a client.
	 */
	public void run()
		{
		try
			{
			// The actual interpreter loop.
			//
			clientLoop();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			Logger.log(Logger.LOG_EMERG, "clientLoop failed: " + e);
			}
		finally
			{
			try
				{
				clientSocket.close();
				}
			catch (Exception e)
				{
				e.printStackTrace();
				}
			}
		}

	/**
	 * The loops on input from the client socket, reading each command and
	 * using the reflection API to invoke the appropriate command handler
	 * method for that command.
	 */
	private void clientLoop()
		throws Exception
		{
		reply(220, "localhost FTP server (" + Server.VERSION + ") ready.");

		String line = null;
		while ((line = reader.readLine()) != null)
			{
			StringTokenizer st = new StringTokenizer(line);
			String command = st.nextToken().toLowerCase();

			if ("PASS".equalsIgnoreCase(command))
				Logger.log(Logger.LOG_INFO, "PASS ********");
			else
				Logger.log(Logger.LOG_INFO, line);

			Object args[] = { line, st };
			try
				{
				Method commandHandler = getClass().getMethod("handle_" + command, commandHandlerArgTypes);
				int code = ((Integer)commandHandler.invoke(this, args)).intValue();
				if (code == 221)
					return;
				}
			catch (InvocationTargetException e)
				{
				try
					{
					throw (Exception)e.getTargetException();
					}
				catch (CommandException ce)
					{
					reply(ce.getCode(), ce.getText());
					}
				catch (NoSuchElementException e1)
					{
					reply(500, "'" + line + "': command not understood.");
					}
				}
			catch (NoSuchMethodException e)
				{
				reply(500, "'" + line + "': command not understood.");
				}
			catch (Exception e)
				{
				Logger.log(Logger.LOG_ERR, "Exception invoking " + command + " command handler: " + e);
				e.printStackTrace();
				}
			}
		}

	/**
	 * Command handler for the USER command.
	 *
	 * <pre>USER %SP% %username% %CRLF%</pre>
	 */
	public int handle_user(String line, StringTokenizer st)
		throws CommandException
		{
		this.inUser = st.nextToken();
		if(this.inUser.compareTo(this.username) == 0) {
		 return reply(331, "Password required for " + username + ".");
		} else {
		 this.isLoggedIn = 0;
		 throw new CommandException(530, "Login incorrect.");
		}
	}

	/**
	 * Command handler for the PASS command. A USER command must have been
	 * read first.
	 *
	 * <pre>PASS %SP% %password% %CRLF%</pre>
	 */
	public int handle_pass(String line, StringTokenizer st)
		throws CommandException
		{
		if (username == null || this.inUser.compareTo(this.username) != 0)
			throw new CommandException(503, "Login with USER first.");

		String pass = null;
		if (st.hasMoreTokens())
			pass = st.nextToken();
		else
			pass = "";
		
		//System.out.println("p=="+pass);
		/*
		 * Support for rheise.os when it implements authentication:
		 *
		 * http://www.progsoc.uts.edu.au/~rheise/projects/rheise.os/
		 *
		try
			{
			PasswordCredentials credentials = new PasswordCredentials(username, password);
			User user = UserManager.getUser(username);
			UserToken userToken = SecuritySystem.assumeUser(user, credentials);
			SecuritySystem.setEffectiveUserToken(userToken);
			}
		catch (AuthenticationException e)
			{
			// XXX: keep track of repeated failures.
			throw new CommandException(530, "Login incorrect.");
			}
		*/
		//this.password = password;
		if(this.username.compareTo(this.inUser) == 0 && this.password.compareTo(pass) == 0) {
			this.isLoggedIn = 1;
			return reply(230, "User " + username + " logged in.");
		} else {
			this.isLoggedIn = 0;			
			throw new CommandException(530, "Login incorrect.");
			//throw new CommandException(530, "'" + line + "': Login incorrect.");
		}
	}

	/**
	 * Command handler for the ACCT command.
	 *
	 * <pre>ACCT %SP% %account-information% %CRLF%</pre>
	 */
	public int handle_acct(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the CWD command.
	 *
	 * <pre>CWD %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_cwd(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();

		String arg = st.nextToken();

		String newDir = arg;
		if (newDir.length() == 0)
			newDir = "/";

		newDir = resolvePath(newDir);

		File file = new File(createNativePath(newDir));
		if (!file.exists())
			throw new CommandException(550, arg + ": no such directory");
		if (!file.isDirectory())
			throw new CommandException(550, arg + ": not a directory");

		currentDir = newDir;
		Logger.log(Logger.LOG_DEBUG, "new cwd = " + currentDir);
		return reply(250, "CWD command successful.");
		}

	/**
	 * Command handler for the CDUP command.
	 *
	 * <pre>CDUP %CRLF%</pre>
	 */
	public int handle_cdup(String line, StringTokenizer st)
		throws CommandException
		{
		return handle_cwd(line, st);
		}

	/**
	 * Command handler for the SMNT command.
	 *
	 * <pre>SMNT %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_smnt(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the QUIT command.
	 *
	 * <pre>QUIT %CRLF%</pre>
	 */
	public int handle_quit(String line, StringTokenizer st)
		throws CommandException
		{
		username = null;
		password = null;
		return reply(221, "Goodbye.");
		}

	/**
	 * Command handler for the REIN command.
	 *
	 * <pre>REIN %CRLF%</pre>
	 */
	public int handle_rein(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();

		username = null;
		password = null;
		currentDir = "/";
		dtp = new ServerDTP(this);
		return reply(220, "Service ready for new user.");
		}

	/**
	 * Command handler for the PORT command.
	 *
	 * <pre>PORT %SP% %host-port% %CRLF%</pre>
	 */
	public int handle_port(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		String portStr = st.nextToken();
		st = new StringTokenizer(portStr, ",");
		String h1 = st.nextToken();
		String h2 = st.nextToken();
		String h3 = st.nextToken();
		String h4 = st.nextToken();
		int p1 = Integer.parseInt(st.nextToken());
		int p2 = Integer.parseInt(st.nextToken());

		String dataHost = h1 + "." + h2 + "." + h3 + "." + h4;
		int dataPort = (p1 << 8) | p2;

		dtp.setDataPort(dataHost, dataPort);

		return reply(200, "PORT command successful.");
		}

	/**
	 * Command handler for the PASV command.
	 *
	 * <pre>PASV %CRLF%</pre>
	 */
	public int handle_pasv(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the TYPE command. Supported arguments are 'A'
	 * for ASCII and 'I' for IMAGE.
	 *
	 * <pre>TYPE %SP% %type-code% %CRLF%</pre>
	 */
	public int handle_type(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		String arg = st.nextToken().toUpperCase();
		if (arg.length() != 1)
			throw new CommandException(500, "TYPE: invalid argument '" + arg + "'");
		char code = arg.charAt(0);
		Representation representation = Representation.get(code);
		if (representation == null)
			throw new CommandException(500, "TYPE: invalid argument '" + arg + "'");
		dtp.setRepresentation(representation);
		return reply(200, "Type set to " + arg);
		}

	/**
	 * Command handler for the STRU command. The only supported argument
	 * is 'F' for file structure.
	 *
	 * <pre>STRU %SP% %structure-code% %CRLF%</pre>
	 */
	public int handle_stru(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		String arg = st.nextToken().toUpperCase();
		try
			{
			if (arg.length() != 1)
				throw new Exception();
			char stru = arg.charAt(0);
			switch (stru)
				{
				case 'F':
					dtp.setDataStructure(stru);
					break;
				default:
					throw new Exception();
				}
			}
		catch (Exception e)
			{
			throw new CommandException(500, "STRU: invalid argument '" + arg + "'");
			}
		return reply(200, "STRU " + arg + " ok.");
		}

	/**
	 * Command handler for the MODE command. The only supported argument
	 * is 'S' for STREAM.
	 *
	 * <pre>MODE %SP% %mode-code% %CRLF%</pre>
	 */
	public int handle_mode(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		String arg = st.nextToken().toUpperCase();
		if (arg.length() != 1)
			throw new CommandException(500, "MODE: invalid argument '" + arg + "'");
		char code = arg.charAt(0);
		TransmissionMode mode = TransmissionMode.get(code);
		if (mode == null)
			throw new CommandException(500, "MODE: invalid argument '" + arg + "'");
		dtp.setTransmissionMode(mode);
		return reply(200, "MODE " + arg + " ok.");
		}

	/**
	 * Command handler for the RETR command. The ServerDTP is used to
	 * send the data to the user.
	 *
	 * <pre>RETR %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_retr(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		String path = null;
		try
			{
			path = line.substring(5);
			}
		catch (Exception e)
			{
			throw new NoSuchElementException(e.getMessage());
			}

		path = createNativePath(path);
		Logger.log(Logger.LOG_DEBUG, "handle_retr: " + path);

		return dtp.sendFile(path);
		}

	/**
	 * Command handler for the STOR command. The ServerDTP is used to
	 * receive the data on the data port.
	 *
	 * <pre>STOR %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_stor(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		String path = null;
		try
			{
			path = line.substring(5);
			}
		catch (Exception e)
			{
			throw new NoSuchElementException(e.getMessage());
			}
		path = createNativePath(path);
		return dtp.receiveFile(path);
		}

	/**
	 * Command handler for the STOU command.
	 *
	 * <pre>STOU %CRLF%</pre>
	 */
	public int handle_stou(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the APPE command.
	 *
	 * <pre>APPE %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_appe(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the ALLO command.
	 *
	 * <pre>ALLO %SP% %decimal-integer% [%SP% R %SP% %decimal-integer%] %CRLF%</pre>
	 */
	public int handle_allo(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the REST command.
	 *
	 * <pre>REST %SP% %marker% %CRLF%</pre>
	 */
	public int handle_rest(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the RNFR command.
	 *
	 * <pre>RNFR %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_rnfr(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the RNTO command.
	 *
	 * <pre>RNTO %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_rnto(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the ABOR command.
	 *
	 * <pre>ABOR %CRLF%</pre>
	 */
	public int handle_abor(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the DELE command.
	 *
	 * <pre>DELE %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_dele(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();

		String arg = st.nextToken();

		String filePath = resolvePath(arg);

		File file = new File(createNativePath(filePath));
		if (!file.exists())
			throw new CommandException(550, arg + ": file does not exist");
		if (!file.delete())
			throw new CommandException(550, arg + ": could not delete file");

		return reply(250, "DELE command successful.");
		}

	/**
	 * Command handler for the RMD command.
	 *
	 * <pre>RMD %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_rmd(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();

		String arg = st.nextToken();

		String dirPath = resolvePath(arg);

		File dir = new File(createNativePath(dirPath));
		if (!dir.exists())
			throw new CommandException(550, arg + ": directory does not exist");
		if (!dir.isDirectory())
			throw new CommandException(550, arg + ": not a directory");
		if (!dir.delete())
			throw new CommandException(550, arg + ": could not remove directory");

		return reply(250, "RMD command successful.");
		}

	/**
	 * Command handler for the MKD command.
	 *
	 * <pre>MKD %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_mkd(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();

		String arg = st.nextToken();

		String dirPath = resolvePath(arg);

		File dir = new File(createNativePath(dirPath));
		if (dir.exists())
			throw new CommandException(550, arg + ": file exists");
		if (!dir.mkdir())
			throw new CommandException(550, arg + ": directory could not be created");

		return reply(257, "\"" + dirPath + "\" directory created");
		}

	/**
	 * Command handler for the PWD command.
	 *
	 * <pre>PWD %CRLF%</pre>
	 */
	public int handle_pwd(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();

		return reply(257, currentDir);
		}

	/**
	 * Command handler for the LIST command. If pathname is not specified,
	 * the current durectory will be used.
	 *
	 * <pre>LIST [%SP% %pathname%] %CRLF%</pre>
	 */
	public int handle_list(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		String path = null;
		if (st.hasMoreTokens())
			path = st.nextToken();
		else
			path = currentDir;

		path = createNativePath(path);

		return dtp.sendList(path);
		}

	/**
	 * Command handler for the NLST command. If pathname is not specified,
	 * the current directory will be used.
	 *
	 * <pre>NLST [%SP% %pathname%] %CRLF%</pre>
	 */
	public int handle_nlst(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		String path = null;
		if (st.hasMoreTokens())
			path = st.nextToken();
		else
			path = currentDir;

		path = createNativePath(path);

		return dtp.sendNameList(path);
		}

	/**
	 * Command handler for the SITE command.
	 *
	 * <pre>SITE %SP% %string% %CRLF%</pre>
	 */
	public int handle_site(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the SYST command.
	 *
	 * <pre>SYST %CRLF%</pre>
	 */
	public int handle_syst(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		return reply(215, "UNIX - I mean Java.");
		}

	/**
	 * Command handler for the STAT command.
	 *
	 * <pre>STAT [%SP% %pathname%] %CRLF%</pre>
	 */
	public int handle_stat(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the HELP command.
	 *
	 * <pre>HELP [%SP% %string%] %CRLF%</pre>
	 */
	public int handle_help(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		throw new CommandException(500, "'" + line + "': command not supported.");
		}

	/**
	 * Command handler for the NOOP command.
	 *
	 * <pre>NOOP %CRLF%</pre>
	 */
	public int handle_noop(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();
		return reply(200, "NOOP command successful.");
		}

	// 
	// UNDOCUMENTED COMMANDS
	//

	/**
	 * Command handler for the SIZE command.
	 *
	 * <pre>SIZE %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_size(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();

		String arg = st.nextToken();
		String path = resolvePath(arg);
		File file = new File(createNativePath(path));

		if (!file.exists())
			throw new CommandException(550, arg + ": no such file");
		if (!file.isFile())
			throw new CommandException(550, arg + ": not a plain file");

		Representation representation = dtp.getRepresentation();
		long size;
		try
			{
			size = representation.sizeOf(file);
			}
		catch (IOException e)
			{
			throw new CommandException(550, e.getMessage());
			}

		// XXX: in ascii mode, we must count newlines properly
		return reply(213, "" + size);
		}

	/**
	 * Command handler for the MDTM command.
	 *
	 * <pre>MDTM %SP% %pathname% %CRLF%</pre>
	 */
	public int handle_mdtm(String line, StringTokenizer st)
		throws CommandException
		{
		checkLogin();

		String arg = st.nextToken();
		String path = resolvePath(arg);
		File file = new File(createNativePath(path));

		if (!file.exists())
			throw new CommandException(550, arg + ": no such file");

		Date date = new Date(file.lastModified());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String dateStr = dateFormat.format(date);

		return reply(213, dateStr);
		}

	/**
	 * Writes a reply line to the client socket.
	 *
	 * @param code the reply code.
	 * @param text the string message to include in the reply.
	 * @return the reply code for this reply.
	 */
	int reply(int code, String text)
		{
		writer.println(code + " " + text);
		return code;
		}

	/**
	 * Creates a native absolute path from a path string sent from the
	 * client. The absolute path constructed will always be prefixed with
	 * baseDir. If ftpPath does not begin with a '/', the constructed path
	 * will also be relativee to currentDir.
	 */
	String createNativePath(String ftpPath)
		{
		String path = null;
		if (ftpPath.charAt(0) == '/')
			path = baseDir + ftpPath;
		else
			path = baseDir + currentDir + "/" + ftpPath;
		Logger.log(Logger.LOG_DEBUG, "createNativePath(" + ftpPath + ") = " + path);
		return path;
		}

	/**
	 * Resolves an FTP given by the client. Relative paths will be
	 * resolved relative to currentDir. '.' path segments will be removed,
	 * and '..' path segments will pop the previous segment of the path
	 * stack (if there is a previous segment).
	 */
	String resolvePath(String path)
		{
		if (path.charAt(0) != '/')
			path = currentDir + "/" + path;

		StringTokenizer pathSt = new StringTokenizer(path, "/");
		Stack<String> segments = new Stack<String>();
		while (pathSt.hasMoreTokens())
			{
			String segment = pathSt.nextToken();
			if (segment.equals(".."))
				{
				if (!segments.empty())
					segments.pop();
				}
			else if (segment.equals("."))
				{
				// skip
				}
			else
				{
				segments.push(segment);
				}
			}

		StringBuffer pathBuf = new StringBuffer("/");
		Enumeration segmentsEn = segments.elements();
		while (segmentsEn.hasMoreElements())
			{
			pathBuf.append(segmentsEn.nextElement());
			if (segmentsEn.hasMoreElements())
				pathBuf.append("/");
			}

		return pathBuf.toString();
		}

	void checkLogin()
		throws CommandException
		{
		//System.out.println("u="+this.username+" p="+this.password);
		if (password == null || this.isLoggedIn == 0)
			throw new CommandException(530, "Please login with USER and PASS.");
		} 
}
