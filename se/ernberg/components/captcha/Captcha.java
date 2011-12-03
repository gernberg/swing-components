package se.ernberg.components.captcha;

import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class Captcha extends JPanel {
	/**
	 * The image that we're drawing on, keeps track of current word to draw and
	 * which painter to use.
	 */
	private final CaptchaImage captchaImage;
	/**
	 * Used for user-input
	 */
	private final JTextField textfield = new JTextField();
	/**
	 * This is the captchaText that we want the user to write
	 */
	private final String captchaText;
	/**
	 * A list of listeners to any changes in the status
	 */
	private final ArrayList<CaptchaStatusListener> captchaStatusListeners = new ArrayList<CaptchaStatusListener>();

	/**
	 * Options for this specific Captcha
	 */
	private CaptchaOptions captchaOptions;

	/**
	 * Calling the constructor without arguments creates an Captcha instance
	 * with options that will suit most users
	 */
	public Captcha() {
		this(CaptchaOptions.getInstance());
	}

	/**
	 * You may provide Options in order to customize your Captcha instance
	 * 
	 * @param options
	 */
	public Captcha(CaptchaOptions options) {
		captchaOptions = options;
		captchaText = captchaOptions.getCaptchaTextGenerator().generateString();

		captchaImage = new CaptchaImage(captchaText,
				captchaOptions.getCaptchaPainter());

		setLayout(new BoxLayout(this, captchaOptions.getOrientation()));

		textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkIfCorrect();
			}
		});
		captchaImage.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// If one clicks on the captchaImage, set focus to the textfield.
				textfield.requestFocus();
			}
		});

		add(captchaImage);
		add(textfield);
		addHierarchyBoundsListener(new HierarchyBoundsListener() {
			
			@Override
			public void ancestorResized(HierarchyEvent e) {
				textfield.setFont(getFont().deriveFont((float)(textfield.getHeight()/2.0)));
			}
			
			@Override
			public void ancestorMoved(HierarchyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void addCaptchaStatusUpdatedListener(CaptchaStatusListener listener) {
		captchaStatusListeners.add(listener);
	}

	public JTextField getTextField() {
		return textfield;
	}

	public CaptchaImage getCaptchaImage() {
		return captchaImage;
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
		return captchaText.equalsIgnoreCase(textfield.getText());
	}

}
