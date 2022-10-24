package eu.ase.httpserverlambda;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPMultiServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		boolean listening = true;
		
		try {
			// http://127.0.0.1:10001/indextest.html
			int port = Integer.parseInt(args[0]);
			serverSocket = new ServerSocket(port);
			System.out.println("Server DICE listens in port: "
					+ port); 
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		while(listening) {
			//try (Socket client = serverSocket.accept()) {
			try {
				Socket client = serverSocket.accept();
				
				//HTTPMultiServerThread objClient 
				//	= new HTTPMultiServerThread(client);
				//objClient.start();
				
				Thread objClient = new Thread(() -> {
					OutputStream os = null; PrintWriter out = null;
					InputStream is = null; BufferedReader in = null;
					
					try {
						is = client.getInputStream();
						in = new BufferedReader(new InputStreamReader(is));
						
						os = client.getOutputStream();
						out = new PrintWriter(os, true);
						
						String inputLine = ""; String outputLine = "";
						String processLine = "";
						while( ( (inputLine = in.readLine()) != null ) && (inputLine.length() > 1)){
							processLine += inputLine;
						}
						
						HTTPSeminarProtocol objProtocol = new HTTPSeminarProtocol();
						outputLine = objProtocol.processInput(processLine);
						
						out.println(outputLine);
					} catch(IOException ioe) {
						ioe.printStackTrace();
					} finally {
						if(out != null)
							out.close();
					}
				});
				objClient.start();
				
				//client.close(); // wrong
			} catch(IOException ioex) {
				ioex.printStackTrace();
			}
		}
		
		try {
			if (serverSocket != null)
				serverSocket.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

class HTTPSeminarProtocol {
    public String processInput(String theInput) {
        String theOutput = ""; 
        byte[] buffResp = new byte[4096];
        if(theInput.indexOf("GET") != 0) {
            theOutput = "HTTP/1.1 200 OK\r\nContent-Length: 19\r\nNU STIU COMANDA\r\n\r\n";
        } else {
            String fileName = theInput.substring(theInput.indexOf("/")+1, theInput.indexOf(" HTTP/"));
            String fileExt = fileName.substring(fileName.indexOf(".")+1);
            String contentType = ""; String fileContent = "";
            if(fileExt.compareToIgnoreCase("txt") == 0) contentType = "text/html";
            if(fileExt.compareToIgnoreCase("html") == 0) contentType = "text/html";
            if(fileExt.compareToIgnoreCase("htm") == 0) contentType = "text/html";
            if(fileExt.compareToIgnoreCase("gif") == 0) contentType = "image/gif";
            if(fileExt.compareToIgnoreCase("class") == 0) {
                contentType = "text/html";
                //RUN java class in REFLECTIOn mode => "SERVLET CONTAINER"
            } else {
                try {
                    int bread = 0;
                    FileInputStream fis = new FileInputStream(fileName);
                    while((bread = fis.read(buffResp)) != -1) {
                        fileContent += new String(buffResp, 0, bread);
                    }  
                    fis.close();
                    theOutput = "HTTP/1.1 200 OK\r\nContent-Type: "+contentType+"\r\nContent-Length: "
                            +(fileContent.length()+2)+"\r\n\r\n"+fileContent+"\r\n";
                } catch(IOException ioec) {
                    ioec.printStackTrace();
                    theOutput = "HTTP/1.1 404\r\n\r\n";
                }
            }
        }
        return theOutput;
    }
}

/*

package eu.ase.httpserverlambda;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HTTPMultiServerThread extends Thread {
	private Socket socket = null;
	
	public HTTPMultiServerThread(Socket client) {
		this.socket = client;
	}
	
	@Override
	public void run() {
		OutputStream os = null; PrintWriter out = null;
		InputStream is = null; BufferedReader in = null;
		
		try {
			is = this.socket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			
			os = this.socket.getOutputStream();
			out = new PrintWriter(os, true);
			
			String inputLine = ""; String outputLine = "";
			String processLine = "";
			while( ( (inputLine = in.readLine()) != null ) && (inputLine.length() > 1)){
				processLine += inputLine;
			}
			
			HTTPSeminarProtocol objProtocol = new HTTPSeminarProtocol();
			outputLine = objProtocol.processInput(processLine);
			
			out.println(outputLine);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if(out != null)
				out.close();
		}
	}
}




 */

/*
package eu.dice.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

	public static void printNetMsgs(Socket client) {
		OutputStream os = null; PrintWriter out = null;
		InputStream is = null; BufferedReader in = null;

		try {
			is = client.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));

			os = client.getOutputStream();
			out = new PrintWriter(os, true);

			String inputLine = ""; String outputLine = "";
			StringBuffer processLine = new StringBuffer();

			while( ((inputLine = in.readLine()) !=null) && (inputLine.length() > 1) ) {
				processLine.append(inputLine);
			}
			System.out.println("Browser -> " + processLine);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public static void main(String[] args) {
		ServerSocket serverSock = null;
		boolean listening = true;

		try {
			int port = Integer.parseInt(args[0]);
			serverSock = new ServerSocket(port);
			System.out.println("Java DICE Web Server onport: " + port);
			while(listening) {
				Socket sockFromClient = serverSock.accept();
				printNetMsgs(sockFromClient);
			}

			if(serverSock !=null)
				serverSock.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	} // end main 

} // end class
*/

