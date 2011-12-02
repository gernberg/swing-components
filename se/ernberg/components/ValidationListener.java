package se.ernberg.components;

public interface ValidationListener {
	/**
	 * Fires when user entera a valid captcha-code
	 */
	public void validCaptchaRecieved();
	
	/**
	 * Fires when user enters an invalid captcha-code
	 */
	public void inValidCaptchaRecieved();
	
	/**
	 * Fires whenever user enter any input
	 * @param isValid wheter the input entered is a valid captcha or not.
	 */
	public void captchaRecieved(boolean isValid);
}
