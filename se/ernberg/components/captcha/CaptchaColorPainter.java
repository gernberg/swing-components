package se.ernberg.components.captcha;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import se.ernberg.math.RandomFunctions;

/**
 * Paints a string on a graphics instance. Applies some random colors and lines
 * to the image in order to make it harder for automated robots to decode the
 * text-string.
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class CaptchaColorPainter implements CaptchaPainter {
	/**
	 * The maximum number of spacing between letters (the acutal number used for
	 * spacing will be in the range from 0 to this value)
	 */
	private int maximumLetterSpacing = 5;

	/**
	 * Private constructor since this is a Singleton
	 */
	public CaptchaColorPainter() {
	}

	/**
	 * Paints the text string on the graphics instance (with some obfuscation as
	 * described in the class definition)
	 * 
	 * @param graphics
	 * @param text
	 */
	public void paint(Graphics g, String text) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;

		FontMetrics fm = g.getFontMetrics();

		// Draws a background with a random color
		g2d.setColor(RandomFunctions.randomColor(150, 255));
		g.fillRect(0, 0, width, height);

		// Adds some lines
		for (int i = 0; i < RandomFunctions.randomRange(5, 10); i++) {
			g2d.setColor(RandomFunctions.randomColor(150, 255));

			g2d.drawLine(RandomFunctions.randomRange(0, width),
					RandomFunctions.randomRange(0, height),
					RandomFunctions.randomRange(0, width),
					RandomFunctions.randomRange(0, height));

		}

		// Makes the string to appear to be "almost" centered
		int left = (int) ((g.getClipBounds().width - fm.stringWidth(text) - RandomFunctions
				.randomRange(0, maximumLetterSpacing) * text.length()) / 2);

		// Adds the characters one by one and with some random patterns applied
		for (char c : text.toCharArray()) {
			g2d.setColor(RandomFunctions.randomColor(55, 128));
			g2d.drawString(String.valueOf(c), left, (int) ((height + fm
					.getAscent()) / 2 + RandomFunctions.randomRange(-height,
					height) / 8));
			left += fm.charWidth(c) + maximumLetterSpacing * Math.random();
		}
	}

	/**
	 * Calculates the width required to fit the captchaText
	 * @param graphics
	 * @param captchaText
	 */
	@Override
	public int calculateWidth(Graphics g, String captchaText) {
		return g.getFontMetrics().stringWidth(captchaText)
				+ maximumLetterSpacing * captchaText.length();
	}
}
