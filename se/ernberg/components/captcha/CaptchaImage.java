package se.ernberg.components.captcha;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class CaptchaImage extends JPanel {
	private int standardHeight = 50;
	private CaptchaPainter painter;
	private String text;

	public CaptchaImage(String text) {
		this(text, new CaptchaColourPainter());
	}

	public CaptchaImage(String text, CaptchaPainter painter) {
		this.painter = painter;
		this.text = text;
		setPreferredSize(new Dimension(getWidth(), standardHeight));
	}

	@Override
	public Rectangle getBounds(Rectangle rv) {
		return super.getBounds(rv);
	}

	@Override
	public Dimension getSize(Dimension rv) {
		return super.getSize(rv);
	}

	public int calculateCharWidth(FontMetrics fm) {
		int[] widths = fm.getWidths();
		int maxWidth = 0;
		for (int width : widths) {
			if (width > maxWidth)
				maxWidth = width;
		}
		return maxWidth;
	}

	@Override
	public void paint(Graphics g) {
		painter.paint(g, getText());
	}

	public String getText() {
		return text;
	}
}
