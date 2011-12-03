package se.ernberg.components.captcha;

import java.awt.Graphics;

import javax.swing.JPanel;
/**
 * Håller koll på vilken text vi ska rita
 * @author gernberg
 */
public class CaptchaImage extends JPanel {
	private CaptchaPainter painter;
	private String text;

	public CaptchaImage(String text, CaptchaPainter painter) {
		this.painter = painter;
		this.text = text;
		setPreferredSize(painter.getPreferredSize());
	}
	public int calculateMaxWidth(){
		return 100;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		painter.paint(g, getText());
	}

	public String getText() {
		return text;
	}
	
	public float getFontSize(){
		return (float)(getHeight()/2.0);
	}
}
