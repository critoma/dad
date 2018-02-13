import java.net.*;
import java.io.*;

public class URLReader {
    public static void main(String[] args) throws Exception {
	URL weburl = new URL(args[0]);
	BufferedReader in = new BufferedReader(new InputStreamReader(weburl.openStream()));

	String inputLine;

	while ((inputLine = in.readLine()) != null)
	    System.out.println(inputLine);

	in.close();
    }
}