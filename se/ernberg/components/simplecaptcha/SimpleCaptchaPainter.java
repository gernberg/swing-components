package se.ernberg.components.simplecaptcha;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * A simple painter for use with {@link SimpleCaptcha}. Paints green text on
 * black background if no arguments are passed.
 * 
 * This painter is mostly used as an example of how you could write your own
 * SimpleCaptcha painter.
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class SimpleCaptchaPainter implements CaptchaPainter {
	private Color foregroundColor;
	private Color backgroundColor;

	/**
	 * If no arguments are passed, green and black are chosen as foreground /
	 * background color
	 */
	public SimpleCaptchaPainter() {
		this(Color.GREEN, Color.BLACK);
	}

	/**
	 * @param foregroundColor
	 * @param backgroundColor
	 */
	public SimpleCaptchaPainter(Color foregroundColor, Color backgroundColor) {
		super();
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Paints green text on a black background
	 * 
	 * @param graphics
	 * @param text
	 */
	public void paint(Graphics g, String text) {
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		
		g.setColor(getBackgroundColor());
		g.fillRect(0, 0, width, height);
		
		g.setColor(getForegroundColor());
		g.drawString(text, 0, (height / 2) + g.getFontMetrics().getDescent());
	}

	/**
	 * Calculates the width required to paint the text
	 */
	@Override
	public Dimension calculateDimension(Graphics g, String captchaText) {
		return new Dimension(10 + g.getFontMetrics().stringWidth(captchaText),
				g.getFontMetrics().getHeight());
	}

	/**
	 * @return the foreground color
	 */
	private Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * Sets the foreground color
	 * 
	 * @param foregroundColor
	 */
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	/**
	 * @return the background color
	 */
	private Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
