package eu.ase.net.smtp;

import java.io.*;
import java.net.*;

public class TCPSMTPClient {
    public static void main(String[] args) {
	String password = "   ";
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
		
		if (args.length < 2) {
			System.out.println("Clientul trebuie sa aiba 2 parametrii (127.0.0.1 25): IPMasina port");
			System.exit(1);
		}

        try {
            clientSocket = new Socket(args[0], Integer.parseInt(args[1]));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host"+args[0]);
            System.exit(2);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+args[0]);
            System.exit(3);
        }

	try {
	
	/*
	String lin = "";
	out.println("HELO " + args[0]);
	lin = in.readLine();
	System.out.println("Sever: " + lin);
	
	out.println("MAIL FROM:<cristian.toma@ie.ase.ro>");
	lin = in.readLine();
	System.out.println("Sever: " + lin);
	
	out.println("RCPT TO:<cristian.toma@ie.ase.ro>");
	lin = in.readLine();
	System.out.println("Sever: " + lin);
	
	out.println("DATA");
	lin = in.readLine();
	System.out.println("Sever: " + lin);
	
	out.println("\r\nHello there!\r\nOK, bye!\r\n.\r\n");
	lin = in.readLine();
	System.out.println("Sever: " + lin);
	
	out.println("QUIT");
	lin = in.readLine();
	System.out.println("Sever: " + lin);
	*/
	
	String lin = "";
	out.println("EHLO " + args[0]);
	int k = 0;
	while((lin = in.readLine()) != null && (lin.length() > 1) && k < 5)
		{System.out.println("Sever: " + lin);k++;}
	
	out.println("AUTH LOGIN");
	lin = in.readLine();
	System.out.println("Server: " + lin);
	
	out.println(Base64Encoder.encodeAsString("ctoma".getBytes()));
	lin = in.readLine();
	System.out.println("Server: " + lin);
	
	out.println(Base64Encoder.encodeAsString(password.getBytes()));
	lin = in.readLine();
	System.out.println("Server: " + lin);
	
	out.println("MAIL FROM:<cristian.toma@ie.ase.ro>");
	lin = in.readLine();
	System.out.println("Server: " + lin);
	
	out.println("RCPT TO:<cristian.toma@ie.ase.ro>");
	lin = in.readLine();
	System.out.println("Server: " + lin);
	
	out.println("DATA");
	lin = in.readLine();
	System.out.println("Server: " + lin);
	
	out.println("Salut!\r\nE-mail de incercare. OK!\r\n.");
	lin = in.readLine();
	System.out.println("Server: " + lin);
	
	out.println("QUIT");
	lin = in.readLine();
	System.out.println("ServerQ: " + lin);
	
	out.close();
	in.close();
	clientSocket.close();
	} catch (IOException ioee) {ioee.printStackTrace();}
    } //end main
} //end class