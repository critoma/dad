import java.net.*;
import java.io.*;

public class URLReaderFileSave {
    public static void main(String[] args) throws Exception {
	//mai trebuie un for care sa ajusteze prin concatenare URL-ul de unde trebuie sa se preia resursa imagine
	//String urladdr = "http://127.0.0.1/poze/t1.gif";
	String urladdr = "http://www.google.ro";
	RandomAccessFile raf = new RandomAccessFile("testgooglero.html","rw");
	URL weburl = new URL(urladdr);
	//BufferedReader in = new BufferedReader(new InputStreamReader(weburl.openStream()));
	InputStream in = weburl.openStream();

	byte[] myBuffer = new byte[4096];
	int readBytes = 0;

	while ((readBytes = in.read(myBuffer)) != -1)
		raf.write(myBuffer, 0, readBytes);
	    //System.out.println(inputLine);

	raf.close();
	in.close();
    }
}