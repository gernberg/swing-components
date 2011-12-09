package se.ernberg.components.simplecaptcha;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * A simple painter for use with {@link SimpleCaptcha}. Paints green text on
 * black background if no arguments are passed.
 * 
 * This painter is mostly used as an example of how you could write your own
 * SimpleCaptcha painter.
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class SimpleCaptchaPainter extends CaptchaPainterAdapter implements CaptchaPainter {
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
	

	Image image;
	@Override
	protected Image getImage() {
		return image;
	}

	@Override
	protected void generateImage(Graphics g, String text, Dimension d) {
		System.out.println(d.height);
		image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
		Graphics imgGraphics = image.getGraphics();
		
		
		imgGraphics.setColor(getBackgroundColor());
		imgGraphics.fillRect(0, 0, d.width, d.height);
		
		imgGraphics.setColor(getForegroundColor());
		imgGraphics.drawString(text, 0, (d.height / 2) + imgGraphics.getFontMetrics().getDescent());
		
	}
	
	
	/**
	 * Calculates the width required to paint the text
	 */
	@Override
	public Dimension calculateDimension(Graphics g, String captchaText) {
		return new Dimension(g.getFontMetrics().stringWidth(captchaText),
				g.getFontMetrics().getHeight()+10);
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
