package eu.ase.net.udp;

import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

        // send request
        byte[] buf = new byte[256];
	buf[0] = (byte)'S'; buf[1] = (byte)'a'; buf[2] = (byte)'l'; buf[3] = (byte)'u'; buf[4] = (byte)'t';
        //InetAddress address = InetAddress.getByName("127.0.0.1");
	InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 778);
        socket.send(packet);
    
        // get response
	byte[] bufResp = new byte[256];
        packet = new DatagramPacket(bufResp, bufResp.length);
        socket.receive(packet);

	// display response
        String received = new String(packet.getData());
        System.out.print("Client de la server: " + received);
    
	// close socket
        socket.close();
    }
}
