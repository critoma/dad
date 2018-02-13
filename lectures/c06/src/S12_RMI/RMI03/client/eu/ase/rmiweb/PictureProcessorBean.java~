package eu.ase.rmiweb;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

import java.rmi.*;
import java.rmi.server.*;


public class PictureProcessorBean
{
	private static int currServer = 0;
	private static int noRmiServers = 3;
	private static String[] rmiServers = new String[]{"127.0.0.1", "127.0.0.1", "127.0.0.1"};
	//private static String[] rmiServers = new String[]{"10.2.65.86", "10.2.65.44", "10.2.65.66"};
	
	public PictureProcessorBean() {
	}

	public static void setRmiServers(String[] rmis) {
		rmiServers[0] = rmis[0]; rmiServers[1] = rmis[1]; rmiServers[2] = rmis[2]; 
	}

	public int processPictureTimeLocal(String fileName, String newFileName, String xpoz, String ypoz, String w, String h, int counts) {
	   int result = 0;
	   
	   try{
		//java TestZoom test1.jpg test2.jpg 1500 1500 100 100
		BufferedImage img = ImageIO.read(new File(fileName));

		BufferedImage imgRes = null;
	        for(int i = 0; i < counts; i++) {
		   imgRes = img.getSubimage(Integer.parseInt(xpoz), Integer.parseInt(ypoz), Integer.parseInt(w), Integer.parseInt(h));
	        }

		ImageIO.write(imgRes, "jpg", new File(newFileName));
	   } catch(Exception e) {
		e.printStackTrace();
		result = 1;
	   }
	   
	   return result;
	}

	public int processPictureTimeRemote(String fileName, String newFileName, String xpoz, String ypoz, String w, String h, int counts) {
	   int result = 1;

	   String url = "rmi://";
	   if(currServer == 0 || currServer == 1) {url+=rmiServers[currServer];currServer++;}
	   else if(currServer == 2) {url+=rmiServers[currServer]; currServer = 0;}
	   url+=":1099/SAMPLE-SERVER-IMGPROC";
	   
	   try{
		//java TestZoom test1.jpg test2.jpg 1500 1500 100 100
		//ImageIcon img = new ImageIcon(fileName, "jpg");
		File f = new File(fileName);
		FileInputStream fin = new FileInputStream(f);
		byte[] imgBufIn = new byte[(int)f.length()];
		fin.read(imgBufIn);
		fin.close();

		byte[] imgRes = null;
		SampleServerImageInterface remoteObject = (SampleServerImageInterface)Naming.lookup(url);

		imgRes = remoteObject.remoteProcessPictureTime(imgBufIn, xpoz, ypoz, w, h, counts);

		if(imgRes == null) return 1;

		FileOutputStream fout = new FileOutputStream(newFileName);
		fout.write(imgRes);
		fout.close();

		result = 0;
	   }
      catch (RemoteException exc)
        {
          System.out.println("Error in lookup: " + exc.toString());
        }
      catch (java.net.MalformedURLException exc)
        {
          System.out.println("Malformed URL: " + exc.toString());
        }
      catch (java.rmi.NotBoundException exc)
        {
          System.out.println("NotBound: " + exc.toString());
        }
      catch(Exception e) 
        {
	   e.printStackTrace();
	}
	   
	   return result;
	}
}
