package com.lottery.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static void scaleImage(File source, File dest, int newSize) throws IOException {
		if (!source.isFile()) {
			return;
		}
		if (newSize == 0) {
			return;
		}

		Image srcImage = null;
		srcImage = javax.imageio.ImageIO.read(source);
		if (srcImage == null) {
			return;
		}
		int oldWidth = srcImage.getWidth(null);
		int oldHeight = srcImage.getHeight(null);

		if(oldWidth < newSize && oldHeight < newSize) {
			Files.move(source.toPath(), dest.toPath());
		} else {
			if (oldWidth > oldHeight && oldWidth > newSize) {
				int newWidth = newSize;
				float scale = newWidth / (float) oldWidth;
				int newHeight = Math.round(scale * oldHeight);
				createImage(srcImage, dest, newWidth, newHeight);
				
			} else {
				int newHeight = newSize;
				float scale = newHeight / (float) oldHeight;
				int newWidth = Math.round(scale * oldWidth);
				createImage(srcImage, dest, newWidth, newHeight);
			}
		}
	}

	private static void createImage(Image srcImage, File dest, Integer newWidth, int newHeight) throws IOException {
		try (OutputStream out = new FileOutputStream(dest);){
			Image image = srcImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = tag.createGraphics();

			g.drawImage(image, 0, 0, null); // 
			g.dispose();

			
			String name = dest.getName();
			String ext = name.substring(name.lastIndexOf('.') + 1);
			ImageIO.write(tag, ext, out);//
		} 
	}

	public static void main(String[] args) throws IOException {
		File src = new File("C:\\tmp\\test_lib\\202006\\index.jpg");
		File dst = new File("C:\\\\tmp\\\\test_lib\\\\202006\\index2.jpg");
		//scaleImage(src, dst, 500, 0);
	}
}

