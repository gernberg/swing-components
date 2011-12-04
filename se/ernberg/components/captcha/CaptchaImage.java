package se.ernberg.components.captcha;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;

import javax.swing.JPanel;

/**
 * Tells the CaptchaPainter what to paint and helps Captcha to check the current
 * size of the captchaImage component
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class CaptchaImage extends JPanel implements HierarchyBoundsListener {
	/**
	 * Contains all the options for our Captcha object, for now only used in
	 * order to retrieve the CaptchaPainter
	 */
	private CaptchaOptions captchaOptions;
	/**
	 * The captcha text that we want our user to type
	 */
	private String captchaText;

	/**
	 * Creates a new Captcha Image with a painter associated
	 * 
	 * @param text
	 * @param painter
	 */
	public CaptchaImage(String text, CaptchaOptions captchaOptions) {
		this.captchaOptions = captchaOptions;
		this.captchaText = text;
		addHierarchyBoundsListener(this);
	}

	/**
	 * Paints the captchaText using the CaptchaPainter
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		captchaOptions.getCaptchaPainter().paint(g, getText());
	}

	/**
	 * @return captchaText
	 */
	public String getText() {
		return captchaText;
	}

	/**
	 * When using in a layout such as BoxLayout it may be useful to let the
	 * painter calculate the width when we're resizing so that the painter gets
	 * the space it needs
	 */
	@Override
	public void ancestorResized(HierarchyEvent e) {
		System.out.println("Resized");
		updateSize();
	}

	/**
	 * Calculates the size of the paint-area
	 */
	private void updateSize() {
		int width = captchaOptions.getCaptchaPainter().calculateWidth(
				getGraphics(), getText());
		int height = getGraphics().getFontMetrics().getHeight() * 2;
		setPreferredSize(new Dimension(width, height));
		revalidate();
		repaint();
	}

	@Override
	public void ancestorMoved(HierarchyEvent e) {

	}

	protected void setCaptchaText(String captchaText) {
		this.captchaText = captchaText;
		revalidate();
	}
}
