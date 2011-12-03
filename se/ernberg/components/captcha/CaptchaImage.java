package se.ernberg.components.captcha;

import java.awt.Graphics;

import javax.swing.JPanel;

public class CaptchaImage extends JPanel {
	private CaptchaPainter painter;
	private String text;

	public CaptchaImage(String text, CaptchaPainter painter) {
		this.painter = painter;
		this.text = text;
		setPreferredSize(painter.getPreferredSize());
	}

	@Override
	public void paint(Graphics g) {
		painter.paint(g, getText());
	}

	public String getText() {
		return text;
	}
}
