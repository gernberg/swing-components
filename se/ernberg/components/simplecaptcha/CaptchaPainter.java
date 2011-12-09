package se.ernberg.components.simplecaptcha;

import java.awt.Dimension;
import java.awt.Graphics;

/**
 * The main task of a CaptchaPainter is to paint a string on a graphics object
 * in a somewhat readable fashion.
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public interface CaptchaPainter {
	/**
	 * Should render the text string in a somewhat readable fashion.
	 * 
	 * @param g
	 * @param text
	 * @param d
	 */
	public void paint(Graphics g, String text, Dimension d);

	/**
	 * Calculates the size of the image
	 * 
	 * @param g
	 * @param captchaText
	 * @return
	 */
	public Dimension calculateDimension(Graphics g, String captchaText);
	
	/**
	 * Is called when the image needs to be redrawn
	 */
	public void forceRegeneratedImage();

}
