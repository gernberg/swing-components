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
		if (needsRegeneratedImage || image==null){
			image = generateImage(g, text, d);
			// Now we don't  need to regenerate for a while
			needsRegeneratedImage=false;
		}
		g.drawImage(image, 0, 0, null);
	}

	/**
	 * Should return an image that will be used as captcha image
	 * @param g
	 * @param text
	 * @param d
	 * @return Image 
	 */
	protected abstract Image generateImage(Graphics g, String text, Dimension d);

	@Override
	public void forceRegeneratedImage() {
		needsRegeneratedImage = true;
	}
	/**
	 * Method used for drawing loading graphics. This method ust calls the 
	 * paint-method with a loading message provided. 
	 * 
	 * Probably you want to override this in you Painter and eg. use a different 
	 * appearance.
	 * 
	 * @param g
	 * @param text
	 * @param d
	 */
	public void paintLoadingGraphics(Graphics g, String text, Dimension d){
		paint(g, text, d);
	}
}
