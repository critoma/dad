package eu.ase.rmiweb;

import java.rmi.*;
import java.rmi.server.*;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.*;

public class SampleServerImageImpl extends UnicastRemoteObject implements SampleServerImageInterface
{
  SampleServerImageImpl() throws RemoteException
  {
     super();
  }

  public byte[] remoteProcessPictureTime(byte[] origPic, String xpoz, String ypoz, String w, String h, int counts) throws RemoteException
  {
	byte[] bufOut = null;
	     try{
		ImageIcon ii = new ImageIcon(origPic);
		Image img = ii.getImage();
		BufferedImage biold = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = biold.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();

		BufferedImage bi = null;
		for(int i = 0; i < counts; i++)
			bi = biold.getSubimage(Integer.parseInt(xpoz), Integer.parseInt(ypoz), Integer.parseInt(w), Integer.parseInt(h));

		String format = "jpeg";
		Iterator writers = ImageIO.getImageWritersByFormatName(format.toLowerCase());
		if(writers == null || !writers.hasNext()) {
			System.out.println("Unsupported format (" + format + ")");
			return null;
		}
		ImageWriter writer = (ImageWriter)writers.next();
		IIOImage iioImg = new IIOImage(bi, null, null);
		ImageWriteParam iwparam = writer.getDefaultWriteParam();
		// if JPEG, set image quality parameters
		if("jpeg".equalsIgnoreCase(format)) {
			iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwparam.setCompressionQuality(1.0f);
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		writer.setOutput(ImageIO.createImageOutputStream(out));
		writer.write(null, iioImg, iwparam);
		bufOut = out.toByteArray();
     } catch(Exception e) {
	e.printStackTrace();
	bufOut = null;
     }
     return bufOut;
  }
}


