package se.ernberg.components.captcha;


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
public class CaptchaSimpleTextGenerator implements CaptchaTextGenerator {
	private static CaptchaSimpleTextGenerator instance;
	/**
	 * The standard length that is used if no arguments are passed to the
	 * Constructor
	 */
	private static int standardLength = 5;
	/**
	 * This instance's stringLength
	 */
	private int stringLength;

	/**
	 * Creates a new instance of {@link CaptchaSimpleTextGenerator} and gives
	 * the possibility to specify how many characters the generated string
	 * should be.
	 * 
	 * @param stringLength
	 */
	public CaptchaSimpleTextGenerator(int stringLength) {
		this.stringLength = stringLength;
	}
	
	/**
	 * Singleton-pattern as suggested by
	 * http://en.wikipedia.org/wiki/Singleton_pattern#The_solution_of_Bill_Pugh
	 * 
	 */
	private static class SingletonHolder {
		public static final CaptchaSimpleTextGenerator instance = new CaptchaSimpleTextGenerator(standardLength);
	}

	/**
	 * If the standard string length is satisfactory, this method should be used
	 * in order to retrieve the Singleton instance 
	 * @return
	 */
	public static CaptchaSimpleTextGenerator getInstance() {
		return SingletonHolder.instance;
	}
	
	/**
	 * Generates a string to the length specified in this specifik instance.
	 */
	public String generateString() {
		StringBuilder sb = new StringBuilder();
		String createfrom = "23456789abcdefghjkmnpqrstwxyz";
		for (int i = 0; i < stringLength; i++) {
			sb.append(createfrom.charAt((int) (Math.random() * createfrom
					.length())));
		}
		return sb.toString();
	}
}
