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

	/**
	 * This method is called before "paint" and used in order to show graphics 
	 * while waiting for {@link CaptchaTextGenerator} to finish. 
	 * 
	 * @param g Graphics objects where to paint
	 * @param text Loading text to display. 
	 * @param d Dimensions to paint
	 */
	public void paintLoadingGraphics(Graphics g, String text, Dimension d);
	
	/**
	 * Should calculate the dimensions needed for loading graphics.
	 * 
	 * @param graphics
	 * @param loadingText
	 * @return Dimension
	 */
	public Dimension calculateLoadingDimension(Graphics graphics,
			String loadingText);
}
