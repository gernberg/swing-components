package se.ernberg.components.captcha;

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
	 */
	public void paint(Graphics g, String text);

	/**
	 * Calculates the size of the image
	 * 
	 * @param g
	 * @param captchaText
	 * @return
	 */
	public Dimension calculateDimension(Graphics g, String captchaText);

}
