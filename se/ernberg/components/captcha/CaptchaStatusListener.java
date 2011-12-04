package se.ernberg.components.captcha;

/**
 * A listener that recieves updates when the user types anything in the
 * {@link SuperCaptcha} text-input field that changes the status of the
 * SuperCaptcha
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public interface CaptchaStatusListener {
	/**
	 * Is called upon when the status changes
	 * 
	 * @param isCorrect
	 *            if the user input in textfield corresponds to the captchaImage
	 */
	public void captchaStatusUpdated(boolean isCorrect);
}
