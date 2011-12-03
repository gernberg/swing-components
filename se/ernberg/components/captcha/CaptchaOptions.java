package se.ernberg.components.captcha;

import java.awt.Graphics;

/**
 * En klass som är möjlig att använda som singleton 
 * @author gustav
 */
public class CaptchaOptions {
	private static CaptchaOptions instance;
	private CaptchaPainter captchaPainter = CaptchaColourPainter.getInstance();
	private CaptchaTextGenerator captchaTextGenerator = CaptchaSimpleTextGenerator.getInstance();
	/**
	 * Hämtar en singleton-instans
	 * @return
	 */
	public static CaptchaOptions getInstance(){
		if(instance==null)
			instance = new CaptchaOptions();
		return instance;
	}
	public CaptchaTextGenerator getTextMaker() {
		return captchaTextGenerator;
	}
	public CaptchaPainter getCaptchaPainter() {
		return captchaPainter;
	}
	
}
