package eu.ase.net.tcp;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {

        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

		if (args.length < 2) {
			System.out.println("Clientul trebuie sa aiba 2 parametrii: IPMasina port");
			System.exit(1);
		}

        try {
            clientSocket = new Socket(args[0], Integer.parseInt(args[1]));					//SOCKET
            out = new PrintWriter(clientSocket.getOutputStream(), true);					//OUT2SERVER
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	//INfromSERVER
	    //CONNECT = OUT2SERVER + INfromSERVER
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host"+args[0]);
            System.exit(2);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+args[0]);
            System.exit(3);
        }

	try {
	/*BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	String userInput;

	while ((userInput = stdIn.readLine()) != null) {
	    out.println(userInput);
		String lin = in.readLine();
	    System.out.println("Sever: " + lin);
		if (lin.compareTo("La revedere!") == 0) break;
	}*/

	String lin = "";
	out.println("As vrea sa ma conectez.");					//SEND
	lin = in.readLine();									//RECV
	System.out.println("Sever: " + lin);
	
	out.println("Ce data este?");								//SEND
	lin = in.readLine();									//RECV
	System.out.println("Sever: " + lin);
	
	out.println("Ce timp este?");
	lin = in.readLine();
	System.out.println("Sever: " + lin);
	
	//Thread t = Thread.currentThread();
	Thread.sleep(20000);

	//out.println("La revedere!");
	//lin = in.readLine();
	//System.out.println("Sever: " + lin);
			
	out.close();											//CLOSEOUT
	in.close();												//CLOSEIN
	//stdIn.close();
	clientSocket.close();									//CLOSESOCKET
	    //CLOSE = CLOSESOCKET + CLOSEOUT + CLOSEIN
	} catch (IOException ioee) {
		ioee.printStackTrace();
	}  catch (InterruptedException intre) {
		intre.printStackTrace();
	}
    } //end main
} //end class
