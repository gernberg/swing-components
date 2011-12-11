package se.ernberg.components.simplecaptcha;

import java.util.EventListener;

/**
 * A listener that recieves updates when the user types anything in the
 * {@link SimpleCaptcha} text-input field that changes the status of the
 * SimpleCaptcha
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public interface CaptchaStatusListener extends EventListener{
	/**
	 * Is called upon when the status changes
	 * 
	 * @param isCorrect
	 *            if the user input in textfield corresponds to the captchaImage
	 */
	public void statusUpdated(boolean isCorrect);
	
}
