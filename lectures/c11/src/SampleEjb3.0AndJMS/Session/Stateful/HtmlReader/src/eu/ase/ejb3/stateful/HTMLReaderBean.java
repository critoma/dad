package eu.ase.ejb3.stateful;

import javax.naming.*;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import java.net.*;
import java.io.*;
import java.io.Serializable;

import java.util.Hashtable;

import java.rmi.RemoteException;

@Stateful
@Remote(HTMLReader.class)
public class HTMLReaderBean implements HTMLReader, Serializable {
    private Hashtable<String,StringBuffer> linksAndContent = new Hashtable<String,StringBuffer>();

    public StringBuffer getContents(String urlLink) throws HTTPResponseException {
        Context context;
        URL url;
        StringBuffer buffer;
        String line;
        int responseCode;
        HttpURLConnection connection;
        InputStream input;
        BufferedReader dataInput;

	buffer = linksAndContent.get(urlLink);
	if(buffer != null)	
		return buffer;

        try {
            url = new URL(urlLink);
            connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();
        } catch (Exception ex) {
            throw new HTTPResponseException(ex.getMessage());
        }

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new HTTPResponseException("HTTP response code: " + String.valueOf(responseCode));
        }

        try {
            buffer = new StringBuffer();
            input = connection.getInputStream();
            dataInput = new BufferedReader(new InputStreamReader(input));

            while ((line = dataInput.readLine()) != null) {
                buffer.append(line);
                buffer.append('\n');
            }

	    linksAndContent.put(urlLink, buffer);
        } catch (Exception ex) {
            throw new HTTPResponseException(ex.getMessage());
        }

        return buffer;
    } // getContents()

   @Remove
   public void checkout()
   {
      System.out.println("To be implemented");
   }
} // HTMLReaderBean
