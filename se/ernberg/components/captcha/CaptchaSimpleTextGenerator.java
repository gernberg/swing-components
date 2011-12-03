package se.ernberg.components.captcha;

public class CaptchaSimpleTextGenerator implements CaptchaTextGenerator {
	private static CaptchaSimpleTextGenerator instance;
	private static int standardLength = 5;
	private int stringLength;

	public CaptchaSimpleTextGenerator(int stringLength) {
		this.stringLength = stringLength;
	}

	public static CaptchaSimpleTextGenerator getInstance() {
		if (instance == null)
			instance = new CaptchaSimpleTextGenerator(standardLength);
		return instance;
	}

	public String generateString() {
		StringBuilder sb = new StringBuilder();
		String createfrom = "abcdefghjklmnpqrstwxyz";
		for (int i = 0; i < stringLength; i++) {
			sb.append(createfrom.charAt((int) (Math.random() * createfrom
					.length())));
		}
		return sb.toString();
	}
}
