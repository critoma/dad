package euasepmigration;

import java.io.*;
import java.net.*;

import java.lang.reflect.*;
import java.util.regex.*;

import java.nio.*;
import java.nio.channels.*;

public class Server4Tasks {
	

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;

		boolean listening = true;

		OutputStream os = null;
		PrintWriter out = null;
		InputStream is = null;
		//BufferedReader in = null;
		ObjectInputStream in = null;
		//String inputLine = null, outputLine = null;
		String input = null;
		String outputLine = null;

		try {
			ServerSocketChannel ss = ServerSocketChannel.open();
    			ss.bind(new InetSocketAddress("127.0.0.1", 4804));
    
			try (SocketChannel sc = ss.accept();
			     FileChannel fc = new FileOutputStream("tasks.jar").getChannel()) {
        			fc.transferFrom(sc, 0, Long.MAX_VALUE);
    			} catch(Exception ioex) {
				ioex.printStackTrace();
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		

		try {
			// SEVERSOCKET = SOCKET+BIND+LISTEN
			serverSocket = new ServerSocket(4805);
			System.out.println("The Server listens in port 4805");
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4805.");
			System.exit(-1);
		}

		while (listening) {
			try {
				clientSocket = serverSocket.accept(); // ACCEPT
				System.out.println("It arrived the client (remote addr.)=" + clientSocket.getInetAddress().toString()
						+ " : (remote port)=" + clientSocket.getPort() + " in server port 4805");

				is = clientSocket.getInputStream();
				//in = new BufferedReader(new InputStreamReader(is));
				in = new ObjectInputStream(is);

				os = clientSocket.getOutputStream();
				out = new PrintWriter(os, true);

				if ((input = (String)in.readObject()) != null) {
					//input.runTask();
					//long s = input.getTaskResult();
					//outputLine = new String("OK, sum = " + s);
					System.out.println("input = "+input);

					Matcher mj = Pattern.compile(".jar\".*?\"(.*?)\"").matcher(input);
					Matcher mc = Pattern.compile(".class\".*?\"(.*?)\"").matcher(input);
					Matcher m = Pattern.compile(".method\".*?\"(.*?)\"").matcher(input);
					Matcher mr = Pattern.compile(".methodResult\".*?\"(.*?)\"").matcher(input);
					
					String theJar = "", theClass = "", theMethod = "", theMethodResult = "";
					while (mj.find()){
						theJar = mj.group(1);
						System.out.println("Captured content: " + theJar); 						
					}
					while (mc.find()){
						theClass = mc.group(1);
						System.out.println("Captured content: " + theClass); 						
					}
					while (m.find()){
						theMethod = m.group(1);
						System.out.println("Captured content: " + theMethod); 						
					}
					while (mr.find()){
						theMethodResult = mr.group(1);
						System.out.println("Captured content: " + theMethodResult); 						
					}
					String arrValues = input.substring(input.indexOf("[") + 1,input.indexOf("]")); 
					System.out.println("arrValues = " + arrValues);
					String[] arrValsStr = arrValues.split(",");
					//int[] arrayArgs = new int[]{1, 2, 3, 4, 5};
					int[] arrayArgs = new int[arrValsStr.length];
					for(int i = 0; i < arrayArgs.length; i++)
						arrayArgs[i] = Integer.parseInt(arrValsStr[i]);

					try {
					//URLClassLoader child = new URLClassLoader(
        				//	new URL[] {"./tasks.jar".toURI().toURL()},
        				//	this.getClass().getClassLoader());
					URLClassLoader child = new URLClassLoader (new URL[] {new URL("file:///home/stud/dad/lectures/c07/procmigration/server/"+theJar)}, Server4Tasks.class.getClassLoader());
					Class classToLoad = Class.forName(theClass, true, child);
					Class[] intArgsClass = new Class[] { int[].class };					
					
    					Object[] iArgs = new Object[] { arrayArgs };
    					Constructor iArgsConstructor = classToLoad.getConstructor(intArgsClass);
					Object instance = iArgsConstructor.newInstance(iArgs);
					//Object instance = classToLoad.newInstance();
					Method method = classToLoad.getDeclaredMethod(theMethod);
					method.invoke(instance);
					Method mGet = classToLoad.getDeclaredMethod(theMethodResult);
					Object result = mGet.invoke(instance);
					
					outputLine = new String("OK, result = " + result.toString());					
					} catch(Exception ee) {ee.printStackTrace();}
					out.println(outputLine);
					out.flush();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null)
						out.close();
					if (in != null)
						in.close();
					if (clientSocket != null)
						clientSocket.close();
				} catch (IOException ioec) {
					ioec.printStackTrace();
				}
			}
		} // end while
		try {
			serverSocket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}

