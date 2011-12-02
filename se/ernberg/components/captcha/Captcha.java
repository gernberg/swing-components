package se.ernberg.components.captcha;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Captcha extends JPanel {
	private final CaptchaImage CaptchaImage;
	private final JTextField textfield = new JTextField();
	private final String text;
	private static int standardLength = 5;
	private final ArrayList<CaptchaStatusListener> captchaStatusListeners = new ArrayList<CaptchaStatusListener>();

	public Captcha() {
		this(standardLength);
	}

	public Captcha(int size) {
		this(size, new CaptchaColourPainter());
	}

	public Captcha(CaptchaPainter painter) {
		this(standardLength, painter);
	}

	public Captcha(int size, CaptchaPainter painter) {
		text = buildString(size);

		CaptchaImage = new CaptchaImage(text, painter);

		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);

		setLayout(box);

		textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkIfCorrect();
			}
		});

		CaptchaImage.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				textfield.requestFocus();
			}
		});

		add(CaptchaImage);
		add(textfield);
	}

	public void addCaptchaStatusUpdatedListener(CaptchaStatusListener listener) {
		captchaStatusListeners.add(listener);
	}

	public JTextField getTextField() {
		return textfield;
	}

	public CaptchaImage getCaptchaImage() {
		return CaptchaImage;
	}

	public void checkIfCorrect() {
		if (isCorrect()) {
			notifyListeners(isCorrect());
		}
	}

	private void notifyListeners(boolean isCorrect) {
		for (int i = captchaStatusListeners.size() - 1; i >= 0; i--) {
			captchaStatusListeners.get(i).captchaStatusUpdated(isCorrect);
		}
	}

	/**
	 * Tells if the entered text corresponds to the CAPTCHA
	 * 
	 * @return
	 */
	private boolean isCorrect() {
		return text.equalsIgnoreCase(textfield.getText());
	}

	private static String buildString(int length) {
		StringBuilder sb = new StringBuilder();
		String createfrom = "abcdefghjklmnpqrstwxyz";
		for (int i = 0; i < length; i++) {
			sb.append(createfrom.charAt((int) (Math.random() * createfrom
					.length())));
		}
		return sb.toString();
	}
}
