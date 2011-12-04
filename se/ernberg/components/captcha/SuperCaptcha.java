package se.ernberg.components.captcha;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import se.ernberg.components.RefreshButton;

public class SuperCaptcha extends JPanel implements DocumentListener,
		FocusListener {
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
	private String captchaText;
	/**
	 * A list of listeners to any changes in the status
	 */
	private final ArrayList<CaptchaStatusListener> captchaStatusListeners = new ArrayList<CaptchaStatusListener>();

	/**
	 * Options for this specific Captcha
	 */
	private CaptchaOptions captchaOptions;
	/**
	 * Sets the latestStatus (used to not flood the listeners with events);
	 */
	private boolean latestStatus;

	/**
	 * Calling the constructor without arguments creates an Captcha instance
	 * with options that will suit most users
	 */
	public SuperCaptcha() {
		this(CaptchaOptions.getInstance());
	}

	/**
	 * You may provide Options in order to customize your Captcha instance
	 * 
	 * @param options
	 */
	public SuperCaptcha(CaptchaOptions options) {
		captchaOptions = options;
		captchaOptions.addObserver(this);

		captchaText = captchaOptions.getCaptchaTextGenerator().generateString();
		captchaImage = new CaptchaImage(this);

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		textfield.setColumns(captchaText.length());
		textfield.getDocument().addDocumentListener(this);
		textfield.addFocusListener(this);
		captchaImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// If one clicks on the captchaImage, set focus to the
				// textfield.
				textfield.requestFocus();
			}
		});

		RefreshButton button = new RefreshButton();
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reGenerateCaptchaText();
			}
		});
		button.setVisible(captchaOptions.showRefreshButton());

		add(captchaImage);
		add(textfield);
		add(button);

	}

	/**
	 * Add a listener that recieves an boolean everytime the users types in the
	 * textfield
	 * 
	 * @param listener
	 */
	public void addCaptchaStatusUpdatedListener(CaptchaStatusListener listener) {
		captchaStatusListeners.add(listener);
	}

	/**
	 * Check whether the new textString is correct, if the status has changed
	 * since last notify all status listeners
	 */
	private void textChanged() {
		boolean isCorrect = isCorrect();
		if (latestStatus != isCorrect) {
			latestStatus = isCorrect;
			notifyStatusListeners(isCorrect);
		}
	}

	private void notifyStatusListeners(boolean isCorrect) {
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

	/**
	 * Call on textChanged() in order to find out if any listeners should be
	 * notified
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		textChanged();
	}

	/**
	 * Call on textChanged() in order to find out if any listeners should be
	 * notified
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		textChanged();
	}

	/**
	 * Call on textChanged() in order to find out if any listeners should be
	 * notified
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		textChanged();
	}

	/**
	 * Always tell listeners the state of the Captcha Component when focus
	 * changes
	 */
	@Override
	public void focusGained(FocusEvent e) {
		notifyStatusListeners(isCorrect());
	}

	/**
	 * Always tell listeners the state of the Captcha Component when focus
	 * changes
	 */
	@Override
	public void focusLost(FocusEvent e) {
		notifyStatusListeners(isCorrect());
	}

	/**
	 * 
	 * @param options
	 */
	public void setCaptchaOptions(CaptchaOptions options) {
		if (!captchaOptions.equals(options)) {
			somethingChanged();
		}
		captchaOptions = options;
	}

	public void reGenerateCaptchaText() {
		textfield.setText("");
		captchaText = captchaOptions.getCaptchaTextGenerator().generateString();
		textChanged();
		somethingChanged();
	}

	/**
	 * Is called when something has changed - repaints and revalidates the
	 * component
	 */
	public void somethingChanged() {
		revalidate();
		repaint();
	}

	protected CaptchaPainter getCaptchaPainter() {
		return captchaOptions.getCaptchaPainter();
	}

	protected String getCaptchaText() {
		return captchaText;
	}
}
