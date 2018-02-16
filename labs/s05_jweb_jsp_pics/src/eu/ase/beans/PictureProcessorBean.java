package eu.ase.beans;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class PictureProcessorBean {
	public PictureProcessorBean() {
	    
	}
	
	public int processPicture(String fileName, String newFileName, String xpoz, String ypoz, String w, String h) {
		int result = 0;
		try {
			BufferedImage img = ImageIO.read(new File(fileName));
			BufferedImage imgRes = img.getSubimage(Integer.parseInt(xpoz), Integer.parseInt(ypoz), Integer.parseInt(w), Integer.parseInt(h));
			ImageIO.write(imgRes, "jpg", new File(newFileName));
		} catch(Exception e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
}

