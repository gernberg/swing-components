package se.ernberg.components.captcha;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import se.ernberg.math.BetterMath;

public class CaptchaColourPainter implements CaptchaPainter {
	/**
	 * Standard fontSize is 24, it's a good value for readability
	 */
	private float fontSize = 24;
	private int maximumLetterSpacing = 5;

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
		
		g2d.setFont(g2d.getFont().deriveFont((float) (height/2)));

		AffineTransform t = new AffineTransform();
		FontMetrics fm = g.getFontMetrics();

		int left = (int) ((g.getClipBounds().width - fm.stringWidth(text) - Math
				.random() * maximumLetterSpacing * text.length()) / 2);

		g2d.setColor(new Color(
				BetterMath.randomRange(200,255),
				BetterMath.randomRange(200,255),
				BetterMath.randomRange(200,255)
				));
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < BetterMath.randomRange(5, 10); i++) {

			g2d.setColor(new Color(
					BetterMath.randomRange(0, 128),
					BetterMath.randomRange(0, 128),
					BetterMath.randomRange(0, 128)
					));
			g2d.transform(t);
			g2d.drawLine(BetterMath.randomRange(0, width),
					BetterMath.randomRange(0, height),
					BetterMath.randomRange(0, width),
					BetterMath.randomRange(0, height));
		}

		for (char c : text.toCharArray()) {
			g2d.setColor(new Color(
					BetterMath.randomRange(55, 128),
					BetterMath.randomRange(55, 128),
					BetterMath.randomRange(55, 128)
					));
			g2d.drawString(String.valueOf(c), left,
					(int) ((height + fm.getAscent()) / 2 + BetterMath.randomRange(-height, height)/8));
			left += fm.charWidth(c) + maximumLetterSpacing * Math.random();
		}
	}
}
