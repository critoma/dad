package eu.ase.net.snmp;

import java.net.*;
import java.io.*;

public class TestSnmp {
	public static void main(String[] args) {
		try {
			// get a datagram socket
	        DatagramSocket socket = new DatagramSocket();
			//DatagramSocket socket = new DatagramSocket(161);//it is not correct because this constructor executes bind

	        // send request
	        byte[] kk = new byte[39];
	        
			kk[0]=0x30; //universal sequence in BER/DER encoding
			kk[1]=0x25; //length of the sequence excluding the first 2 bytes
			kk[2]=0x02; //information that follows is an Integer Number
			kk[3]=0x01;	// length of data
			kk[4]=0x00; // zero is the actual value of data => version number 0
			kk[5]=0x04;	// follows an octet string
			kk[6]=0x06;	// with 6 bytes length
			kk[7]=(byte)'p';	
			kk[8]=(byte)'u';
			kk[9]=(byte)'b';
			kk[10]=(byte)'l';
			kk[11]=(byte)'i';
			kk[12]=(byte)'c';
			kk[13]=(byte)0xA1; //1010 0001 => When the first three bytes are 101,it means that the data to follow is Context Specific 
						 //i.e. you have to look up the documentation and look for it as it's context changes with every protocol. 
						 //The last four bits hold the number, which is 1 in this case. So it's Context Specific 1. This stands for Get Next Request in the documentation.
			kk[14]=0x18; //the length is 24 bytes
			kk[15]=2;	 //The next three bytes hold the Request ID, which we've set to one. Kk[15] is 0x02 which means that the data is an Integer. The next byte is the length and the data itself is 0x01.
			kk[16]=1;
			kk[17]=1;
			kk[18]=2;	 //The next three bytes contain the error status of the present connection. Since we've just begun talking, it is set to zero.
			kk[19]=1;
			kk[20]=0;
			kk[21]=2;	 //Right after that we have another three bytes which hold the error index. It too is set to zero.
			kk[22]=1;
			kk[23]=0;
			kk[24]=0x30; //Now, at kk[24] we have another structure (or sequence if you prefer) starting up. The byte after that is the length of the data that follows.
						 //Immediatly after the first structure, we have another one. 
						 //This format is maintained so that if you want to ask two questions at the same time, you can. Simply add another 0x30 after the end of this one and more data.
						 //After the length byte we have the byte 0x06 which stands for the Object data type. The byte after that is the length. 
			kk[25]=0x0D; //length
			kk[26]=0x30; // Sequence Structure
			kk[27]=0x0B; // length 11 bytes
			kk[28]=0x06; // Object Data Type - OID
			kk[29]=0x07; // the length of Object data type is 7 bytes
						 //Right at the top is the root, then comes the ISO (1), then org under ISO (1.3), then the Department of Defense or the dod under org (1.3.6). Right after that we have the Internet (1.3.6.1) under the Internet we have mgmt (1.3.6.1.2) and under mgmt we have MIB or the Management Information Database (1.3.6.1.2.1). 
						 //Since we're using SNMP which is used to manage a network system, we have system (1.3.6.1.2.1). 
						 //So the hierarchy path we're on is 1.3.6.1.2.1.1
			kk[30]=0x2B; //So the bytes from kk[30] to kk[36] hold the hierarchy of our query. What you may find a little confusing is that the first byte, kk[30], is 0x2b instead of 1.3 as it should be. Actually, this is the ISO being little clever (for once!). To save transmitting an extra byte, they decided to multiply the first 1 in 1.3.6.1.2.1.3 with 40 and add it to the second number i.e. 3. So we get 1*40+3=43 or 0x2b!! Confusing, but neat.
			kk[31]=0x06;
			kk[32]=0x01;
			kk[33]=0x02;
			kk[34]=0x01;
			kk[35]=0x01;
			kk[36]=0x03; //IMPORTANT => Value 3, in kk[36], is Sysuptime under System.
			kk[37]=0x05; //The last two bytes in the packet are 0x05 and 0x00 which collectively stand for a NULL value. This is because we can't have an answer in a query!
			kk[38]=0x00;
			
			InetAddress address = InetAddress.getByName("127.0.0.1");
	        	DatagramPacket packet = new DatagramPacket(kk, kk.length, address, 161);
		        socket.send(packet);
	    
	        	// get response
			byte[] bufResp = new byte[256];
		        packet = new DatagramPacket(bufResp, bufResp.length);
	        	socket.receive(packet);

			// display response
	        	String received = new String(packet.getData());
	        	System.out.print("Client de la server: " + received);
	    		
			for(int j = 0; j < bufResp.length; j++)
				System.out.printf("%02X ", bufResp[j]);

			// close socket
	        	socket.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
