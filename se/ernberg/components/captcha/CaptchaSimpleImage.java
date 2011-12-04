package se.ernberg.components.captcha;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A simple painter for use with {@link SuperCaptcha}. Paints green text on
 * black background
 * 
 * This painter is mostly used as an example of how you could write your own
 * SuperCaptcha painter.
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class CaptchaSimpleImage extends CaptchaImage{
	
	public CaptchaSimpleImage(SuperCaptcha parent) {
		super(parent);
	}

	/**
	 * Paints green text on a black background
	 * 
	 * @param graphics
	 * @param text
	 */
	@Override
	public void paint(Graphics g) {
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.GREEN);
		g.drawString(getCaptchaText(), 0, (height / 2) + g.getFontMetrics().getDescent());
	}

	/**
	 * Calculates the width required to paint the text
	 */
	@Override
	public int calculateWidth() {
		return 10 + getGraphics().getFontMetrics().stringWidth(getCaptchaText());
	}
}
