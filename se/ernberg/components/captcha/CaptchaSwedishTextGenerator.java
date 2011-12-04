package se.ernberg.components.captcha;

import se.ernberg.math.RandomFunctions;
/**
 * This is an example class of how to create an own text generator.
 * This generates one of the following Swedish words:
 * 	katt, hund, fisk, korv, bröd
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class CaptchaSwedishTextGenerator implements CaptchaTextGenerator {
	private static CaptchaSwedishTextGenerator instance;
	private static String[] words = {"katt","hund", "fisk", "korv", "bröd"};
	/**
	 * Returns the current Singleton instance (if any, otherwise instantiates one)
	 * @return
	 */
	public static CaptchaSwedishTextGenerator getInstance() {
		
		if (instance == null)
			instance = new CaptchaSwedishTextGenerator();
		return instance;
	}
	/**
	 * Generates a Swedish word
	 */
	public String generateString() {
		return words[RandomFunctions.randomRange(0, words.length-1)];
	}
}
