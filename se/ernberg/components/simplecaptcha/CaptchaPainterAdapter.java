package se.ernberg.components.simplecaptcha;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public abstract class CaptchaPainterAdapter implements CaptchaPainter{
	boolean needsRegeneratedImage = true;
	Image image;
	/**
	 * Paints the text string on the graphics instance (with some obfuscation as
	 * described in the class definition)
	 * 
	 * @param graphics
	 * @param text
	 */
	@Override
	public void paint(Graphics g, String text, Dimension d) {
		if (needsRegeneratedImage){
			generateImage(g, text, d);
			// Now we don't  need to regenerate for a while
			needsRegeneratedImage=false;
		}
		g.drawImage(getImage(), 0, 0, null);
	}


	protected abstract void generateImage(Graphics g, String text, Dimension d);

	@Override
	public void forceRegeneratedImage() {
		needsRegeneratedImage = true;
	}
	
	protected Image getImage() {
		return image;
	}
}
