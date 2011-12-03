package se.ernberg.components.captcha;

import java.awt.Dimension;
import java.awt.Graphics;
/**
 * The main task of a CaptchaPainter is to paint the string on a graphics object
 * in a somewhat readable fashion.
 * @author gernberg
 */
public interface CaptchaPainter {
	/**
	 * Should render the text string in a somewhat readable fashion.
	 * @param g
	 * @param text 
	 */
	public void paint(Graphics g, String text);
	/**
	 * Returns the preferred size for the painter.
	 * @return Dimension
	 */
	public Dimension getPreferredSize();

}
