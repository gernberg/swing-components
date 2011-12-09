package se.ernberg.components.simplecaptcha;

import java.util.Random;

/**
 * This is an all purpose text generator that generates a randomized text string
 * that lacks characters that are ambiguous (eg. o, O, 0, I, l, 1)
 * 
 * This class is possible to use as a Singleton (by using getInstance()), the
 * singleton instance is predefined to always generate a 5 character string. If
 * another number of characters are required it is possible to use the standard
 * Constructor and pass the wanter number of characters as an argument.
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class SimpleCaptchaTextGenerator implements CaptchaTextGenerator {
	/**
	 * The standard length that is used if no arguments are passed to the
	 * Constructor
	 */
	private static final int defaultLength = 5;

	private static final String defaultChars = "23456789abdefghjmnpqrst";
	private static final boolean defaultMixUpperAndLowerPolicy = false;

	/**
	 * This instance's stringLength
	 */
	private int stringLength;

	private String useChars;
	private boolean mixUpperAndLowerCase = true;

	/**
	 * If no options are provided - the standardLength is used
	 */
	public SimpleCaptchaTextGenerator() {
		this(defaultLength);
	}

	/**
	 * Creates a new instance of {@link SimpleCaptchaTextGenerator} and gives
	 * the possibility to specify how many characters the generated string
	 * should be.
	 * 
	 * @param stringLength
	 */
	public SimpleCaptchaTextGenerator(int stringLength) {
		this(stringLength, defaultChars);
	}

	/**
	 * Creates a new instance of {@link SimpleCaptchaTextGenerator} and gives
	 * the possibility to specify which characters to use when generating the
	 * captcha string.
	 * 
	 * @param stringLength
	 */
	public SimpleCaptchaTextGenerator(String useChars) {
		this(defaultLength, useChars);
	}

	/**
	 * Creates a new instance of {@link SimpleCaptchaTextGenerator} and gives
	 * the possibilty to specify length of the generated string and which
	 * characters to use when generating the captcha string.
	 * 
	 * @param stringLength
	 * @param useChars
	 */
	public SimpleCaptchaTextGenerator(int stringLength, String useChars) {
		super();
		this.stringLength = stringLength;
		this.useChars = useChars;
	}

	/**
	 * Generates a string to the length specified in this specifik instance.
	 */
	public String generateString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < stringLength; i++) {
			char randomChar = useChars.charAt((int) (Math.random() * useChars
					.length()));
			if (mixUpperAndLowerCase && Math.random() > 0.5) {
				randomChar = Character.toUpperCase(randomChar);
			}
			sb.append(randomChar);
		}
		return sb.toString();
	}
	
	/**
	 * Tells if the generator should mix upper and lowercase chars
	 * @param mixUpperAndLowerCase
	 */
	public void mixUpperAndLowerCase(boolean mixUpperAndLowerCase) {
		this.mixUpperAndLowerCase = mixUpperAndLowerCase;
	}
}
