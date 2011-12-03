package se.ernberg.components.captcha;

import java.awt.LayoutManager;
import javax.swing.BoxLayout;

/**
 * En klass som är möjlig att använda som singleton
 * 
 * @author gustav
 */
public class CaptchaOptions {
	private static CaptchaOptions instance;
	private CaptchaPainter captchaPainter = CaptchaColourPainter.getInstance();
	private CaptchaTextGenerator captchaTextGenerator = CaptchaSimpleTextGenerator
			.getInstance();
	private int orientation = BoxLayout.LINE_AXIS;

	/**
	 * Hämtar en singleton-instans
	 * 
	 * @return
	 */
	public static CaptchaOptions getInstance() {
		if (instance == null)
			instance = new CaptchaOptions();
		return instance;
	}

	public CaptchaTextGenerator getCaptchaTextGenerator() {
		return captchaTextGenerator;
	}

	public CaptchaPainter getCaptchaPainter() {
		return captchaPainter;
	}

	public void setCaptchaTextGenerator(CaptchaTextGenerator captchaTextGenerator) {
		this.captchaTextGenerator = captchaTextGenerator;
	}

	public void setCaptchaPainter(CaptchaPainter captchaPainter) {
		this.captchaPainter = captchaPainter;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getOrientation() {
		return orientation;
	}

}
