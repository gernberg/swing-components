package se.ernberg.components.simplecaptcha;


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
	private static final int standardLength = 5;
	/**
	 * This instance's stringLength
	 */
	private int stringLength;
	
	/**
	 * If no options are provided - the standardLength is used
	 */
	public SimpleCaptchaTextGenerator() {
		this(standardLength);
	}

	/**
	 * Creates a new instance of {@link SimpleCaptchaTextGenerator} and gives
	 * the possibility to specify how many characters the generated string
	 * should be.
	 * 
	 * @param stringLength
	 */
	public SimpleCaptchaTextGenerator(int stringLength) {
		this.stringLength = stringLength;
	}
	
		
	/**
	 * Generates a string to the length specified in this specifik instance.
	 */
	public String generateString() {
		StringBuilder sb = new StringBuilder();
		String createfrom = "23456789abcdefghjkmnpqrstwxz";
		for (int i = 0; i < stringLength; i++) {
			sb.append(createfrom.charAt((int) (Math.random() * createfrom
					.length())));
		}
		return sb.toString();
	}
}