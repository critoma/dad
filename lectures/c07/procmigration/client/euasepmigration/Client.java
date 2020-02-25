package euasepmigration;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

public class Client {

	public static void main(String[] args) {
		Socket clientSocket = null;
		//PrintWriter out = null;
		ObjectOutputStream out = null;
		BufferedReader in = null;

		if (args.length < 2) {
			System.out.println("Clientul must have 2 parameters: IP and port");
			System.exit(1);
		}

		try (SocketChannel sc = SocketChannel.open(new InetSocketAddress(args[0], Integer.parseInt(args[1])-1));
            		FileChannel fc = new FileInputStream("tasks.jar").getChannel()) {
        			fc.transferTo(0, fc.size(), sc);
    		} catch(Exception ioeg) {
			ioeg.printStackTrace();
		}
    		System.out.println("Files Transfered...");

		try {
			Thread.sleep(3500);

			clientSocket = new Socket(args[0], Integer.parseInt(args[1])); // SOCKET
			//out = new PrintWriter(clientSocket.getOutputStream(), true); // OUT2SERVER
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // INfromSERVER

			// CONNECT = OUT2SERVER + INfromSERVER

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host" + args[0]);
			System.exit(2);
		} catch (Exception e) {
			System.err.println("Couldn't get I/O for the connection to: " + args[0]);
			System.exit(3);
		}

		try {
			String lin = "{"
				+"\"jar\":\"tasks.jar\","
				+" \"class\":\"euasepmigration.TaskImpl\","
				+" \"method\":\"runTask\","
				+" \"methodResult\":\"getTaskResult\","
				+" \"arrayValues\":[1,3,5,7,9]"
				+"}";
			
			out.writeObject(lin);

			lin = in.readLine();

			System.out.println("Server response is: " + lin);

			Thread.sleep(10000);


			out.close(); // CLOSEOUT

			in.close(); // CLOSEIN

			// stdIn.close();

			clientSocket.close(); // CLOSESOCKET

			// CLOSE = CLOSESOCKET + CLOSEOUT + CLOSEIN

		} catch (IOException ioee) {

			ioee.printStackTrace();

		} catch (InterruptedException intre) {

			intre.printStackTrace();

		}

	} // end main

} // end class

