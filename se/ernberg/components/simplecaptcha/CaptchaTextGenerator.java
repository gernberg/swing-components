package se.ernberg.components.simplecaptcha;

/**
 * Interface CaptchaTextGenerators, they really only need to implement
 * generateString in order to be allowed to generate Strings for use as captcha
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public interface CaptchaTextGenerator {
	/**
	 * Should generate a random string 
	 * @return a random generated string
	 */
	public String generateString();
}
