package se.ernberg.components.captcha;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import se.ernberg.math.RandomFunctions;

public class CaptchaColourPainter implements CaptchaPainter {

	private static CaptchaColourPainter instance;

	private CaptchaColourPainter() {

	}

	public static CaptchaColourPainter getInstance() {
		if (instance == null)
			instance = new CaptchaColourPainter();
		return instance;
	}

	private int maximumLetterSpacing = 5;
	
	public void paint(Graphics g, String text) {
		FontMetrics fm = g.getFontMetrics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;

		g2d.setFont(g2d.getFont().deriveFont((float) (height / 2)));


		int left = (int) ((g.getClipBounds().width - fm.stringWidth(text) - Math
				.random() * maximumLetterSpacing * text.length()) / 2);

		g2d.setColor(RandomFunctions.randomColor(200, 255));
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < RandomFunctions.randomRange(5, 10); i++) {
			g2d.setColor(RandomFunctions.randomColor(200, 255));

			g2d.drawLine(RandomFunctions.randomRange(0, width),
					RandomFunctions.randomRange(0, height),
					RandomFunctions.randomRange(0, width),
					RandomFunctions.randomRange(0, height));

		}

		for (char c : text.toCharArray()) {
			g2d.setColor(new Color(RandomFunctions.randomRange(55, 128),
					RandomFunctions.randomRange(55, 128), RandomFunctions
							.randomRange(55, 128)));
			g2d.drawString(String.valueOf(c), left, (int) ((height + fm
					.getAscent()) / 2 + RandomFunctions.randomRange(-height,
					height) / 8));
			left += fm.charWidth(c) + maximumLetterSpacing * Math.random();
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(100,50);
	}
}
