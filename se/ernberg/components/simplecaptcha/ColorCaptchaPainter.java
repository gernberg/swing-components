package se.ernberg.components.simplecaptcha;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import se.ernberg.math.RandomFunctions;

/**
 * Paints a string on a graphics instance. Applies some random colors and lines
 * to the image in order to make it harder for automated robots to decode the
 * text-string.
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class ColorCaptchaPainter extends CaptchaPainterAdapter {
	/**
	 * The maximum number of spacing between letters (the acutal number used for
	 * spacing will be in the range from 0 to this value)
	 */
	private static final int maximumLetterSpacing = 5;

	private Image image = null;

	@Override
	protected void generateImage(Graphics g, String text, Dimension d) {

		image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		FontMetrics fm = g.getFontMetrics();

		// Draws a background with a random color
		g2d.setColor(RandomFunctions.randomColor(150, 255));
		g2d.fillRect(0, 0, d.width, d.height);

		// Adds some lines
		for (int i = 0; i < RandomFunctions.randomRange(5, 10); i++) {
			g2d.setColor(RandomFunctions.randomColor(150, 255));

			g2d.drawLine(RandomFunctions.randomRange(0, d.width),
					RandomFunctions.randomRange(0, d.height),
					RandomFunctions.randomRange(0, d.width),
					RandomFunctions.randomRange(0, d.height));

		}

		// Makes the string to appear to be "almost" centered
		int left = (int) ((d.width - fm.stringWidth(text) - RandomFunctions
				.randomRange(0, maximumLetterSpacing) * text.length()) / 2);

		// Adds the characters one by one and with some random patterns applied
		for (char c : text.toCharArray()) {
			g2d.setColor(RandomFunctions.randomColor(55, 128));
			g2d.drawString(String.valueOf(c), left, (int) ((d.height + fm
					.getAscent()) / 2 + RandomFunctions.randomRange(-d.height,
					d.height) / 8));
			left += fm.charWidth(c) + maximumLetterSpacing * Math.random();
		}
	}


	/**
	 * Calculates the width required to fit the captchaText
	 * 
	 * @param graphics
	 * @param captchaText
	 */
	@Override
	public Dimension calculateDimension(Graphics g, String captchaText) {
		int x = g.getFontMetrics().stringWidth(captchaText)
				+ maximumLetterSpacing * captchaText.length();
		int y = g.getFontMetrics().getHeight() * 2;
		return new Dimension(x, y);
	}


	@Override
	protected Image getImage() {
		return image;
	}


}
