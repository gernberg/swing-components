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
	private static String[] words = {"katt","hund", "fisk", "korv", "bröd"};
	/**
	 * Generates a Swedish word
	 */
	public String generateString() {
		return words[RandomFunctions.randomRange(0, words.length-1)];
	}
}
