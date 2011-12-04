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
public class CaptchaSimplePainter implements CaptchaPainter {
	private static CaptchaSimplePainter instance;

	private CaptchaSimplePainter() {

	}
	/**
	 * Singleton-pattern as suggested by
	 * http://en.wikipedia.org/wiki/Singleton_pattern#The_solution_of_Bill_Pugh
	 * 
	 */
	private static class SingletonHolder {
		public static final CaptchaSimplePainter instance = new CaptchaSimplePainter();
	}

	/**
	 * Returns the singleton instance of CaptchaSimplePainterSingleton
	 * 
	 * @return
	 */
	public static CaptchaSimplePainter getInstance() {
		return SingletonHolder.instance;
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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.GREEN);
		g.drawString(text, 0, (height / 2) + g.getFontMetrics().getDescent());
	}

	/**
	 * Calculates the width required to paint the text
	 */
	@Override
	public int calculateWidth(Graphics g, String captchaText) {
		System.out.println(g.getFontMetrics().stringWidth(captchaText));
		return 10 + g.getFontMetrics().stringWidth(captchaText);
	}
}
