package se.ernberg.components;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class ZoomableImage extends JComponent{
	Image image;
	public ZoomableImage(Image image){
		image = image;
	}
	public ZoomableImage(String filename){
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println("Kunde inte ladda filen");
		}
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(image, 0, 0, null); 
	}
}
