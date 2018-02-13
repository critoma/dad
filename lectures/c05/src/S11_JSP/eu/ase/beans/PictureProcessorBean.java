package eu.ase.beans;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class PictureProcessorBean
{
	public PictureProcessorBean() {
	}

	public int processPicture(String fileName, String newFileName, String xpoz, String ypoz, String w, String h) {
	   int result = 0;
	   try{
		//java TestZoom test1.jpg test2.jpg 1500 1500 100 100
		BufferedImage img = ImageIO.read(new File(fileName));
		BufferedImage imgRes = img.getSubimage(Integer.parseInt(xpoz), Integer.parseInt(ypoz), Integer.parseInt(w), Integer.parseInt(h));
		ImageIO.write(imgRes, "jpg", new File(newFileName));
	   } catch(Exception e) {
		e.printStackTrace();
		result = 1;
	   }
	   return result;
	}

	public int processPictureTime(String fileName, String newFileName, String xpoz, String ypoz, String w, String h, int counts) {
	   int result = 0;
	   for(int i = 0; i < counts; i++) {
	   try{
		//java TestZoom test1.jpg test2.jpg 1500 1500 100 100
		BufferedImage img = ImageIO.read(new File(fileName));
		BufferedImage imgRes = img.getSubimage(Integer.parseInt(xpoz), Integer.parseInt(ypoz), Integer.parseInt(w), Integer.parseInt(h));
		ImageIO.write(imgRes, "jpg", new File(newFileName));
	   } catch(Exception e) {
		e.printStackTrace();
		result = 1;
	   }
	   }
	   return result;
	}
}
