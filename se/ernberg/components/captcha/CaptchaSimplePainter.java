package se.ernberg.components.captcha;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

public class CaptchaSimplePainter implements CaptchaPainter{	
	public void paint(Graphics g, String text){
		int width = g.getClipBounds().width;
		int height = g.getClipBounds().height;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawString(text, 0, height/2);
	}
}
