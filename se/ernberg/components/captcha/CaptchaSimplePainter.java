package se.ernberg.components.captcha;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class CaptchaSimplePainter implements CaptchaPainter{
	private static CaptchaSimplePainter instance;
	private CaptchaSimplePainter(){
		
	}
	public static CaptchaSimplePainter getInstance(){
		if(instance==null)
			instance = new CaptchaSimplePainter();
		return instance;
	}
	public void paint(Graphics g, String text){
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.GREEN);
		FontMetrics fm = g.getFontMetrics();
		g.drawString(text, 0, (height/2)+fm.getDescent());
	}
	@Override
	public int calculateWidth(Graphics g, String captchaText) {
		System.out.println(g.getFontMetrics().stringWidth(captchaText));
		return 10+g.getFontMetrics().stringWidth(captchaText);
	}
}
