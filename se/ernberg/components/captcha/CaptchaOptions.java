package se.ernberg.components.captcha;

import java.util.ArrayList;

/**
 * A class that provides an easy-to-use interface for users who want to
 * customize SuperCaptcha
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class CaptchaOptions {
	private static CaptchaOptions instance;
	private CaptchaPainter captchaPainter = CaptchaColourPainter.getInstance();
	private CaptchaTextGenerator captchaTextGenerator = CaptchaSimpleTextGenerator
			.getInstance();
	private ArrayList<SuperCaptcha> observers = new ArrayList<SuperCaptcha>();
	private boolean showRefreshButton = true;

	/**
	 * Singleton-pattern as suggested by
	 * http://en.wikipedia.org/wiki/Singleton_pattern#The_solution_of_Bill_Pugh
	 * 
	 */
	private static class SingletonHolder {
		public static final CaptchaOptions instance = new CaptchaOptions();
	}

	/**
	 * Returns the singleton instance of CaptchaOptions, used by SuperCaptcha by
	 * default if user do not provide any options
	 * 
	 * @return
	 */
	public static CaptchaOptions getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * @return {@link CaptchaTextGenerator}
	 */
	public CaptchaTextGenerator getCaptchaTextGenerator() {
		return captchaTextGenerator;
	}

	/**
	 * @return {@link CaptchaPainter}
	 */
	public CaptchaPainter getCaptchaPainter() {
		return captchaPainter;
	}

	/**
	 * Sets the text generator to be used. Please note that if SuperCaptcha has
	 * generated a text string it will not regenerate one when this method is
	 * called (compare to setCaptchaPainter). If you want to generate a new text
	 * - please use the function reGenerate() in SuperCaptcha.
	 * 
	 * @param captchaTextGenerator
	 */
	public void setCaptchaTextGenerator(
			CaptchaTextGenerator captchaTextGenerator) {
		notifyObservers(this.captchaTextGenerator, captchaTextGenerator);
		this.captchaTextGenerator = captchaTextGenerator;
	}

	/**
	 * The default behaviour of SuperCaptcha is to repaint the graphics when
	 * this method is called (and the new CaptchaPainter differs from the old
	 * one).
	 * 
	 * @param captchaPainter
	 */
	public void setCaptchaPainter(CaptchaPainter captchaPainter) {
		notifyObservers(this.captchaPainter, captchaPainter);
		this.captchaPainter = captchaPainter;
	}

	/**
	 * Adds an observing captcha element
	 * 
	 * @param captcha
	 */
	public void addObserver(SuperCaptcha captcha) {
		observers.add(captcha);
	}

	/**
	 * removes an observing captcha element
	 * 
	 * @param captcha
	 */
	public void removeObserver(SuperCaptcha captcha) {
		observers.remove(captcha);
	}

	/**
	 * Tells observers that some option has changed
	 * 
	 * @param oldObject
	 * @param newObject
	 */
	private void notifyObservers(Object oldObject, Object newObject) {
		if (observers.size() > 0 && !oldObject.equals(newObject)) {
			for (int i = observers.size() - 1; i >= 0; i--) {
				observers.get(i).somethingChanged();
			}
		}
	}
	/**
	 * Defines if the default refresh button should be shown 
	 * @param showRefreshButton
	 */
	public void setShowDefaultRefreshButton(boolean showRefreshButton) {
		notifyObservers(this.showRefreshButton, showRefreshButton);
		this.showRefreshButton = showRefreshButton;
	}

	/**
	 * Tells if the default refresh button should be shown 
	 * @return showRefreshButton
	 */
	public boolean showRefreshButton() {
		return showRefreshButton;
	}

}
