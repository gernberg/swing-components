package se.ernberg.components.simplecaptcha;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import se.ernberg.components.RefreshButton;

/**
 * SuperCaptcha is a captcha component for use together with Java Swing. There
 * are a number of constructors available for customizing the compontent
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class SimpleCaptcha extends JPanel implements DocumentListener,
		FocusListener, CaptchaStatusListener {
	/**
	 * Generated by Eclipse
	 */
	private static final long serialVersionUID = -6690646366168806898L;
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
	 * This is the refreshbutton
	 */
	private final RefreshButton refreshButton = new RefreshButton();
	/**
	 * A list of listeners to any changes in the status
	 */
	private final ArrayList<CaptchaStatusListener> captchaStatusListeners = new ArrayList<CaptchaStatusListener>();

	/**
	 * A list of observers that recieves information about when all threads are
	 * done
	 */
	private final ArrayList<CaptchaObserver> captchaObservers = new ArrayList<CaptchaObserver>();
	/**
	 * Sets the latestStatus (used to not flood the listeners with events);
	 */
	private boolean latestStatus;
	/**
	 * The painter instance that is responsible for painting the captchaText
	 */
	private CaptchaPainter captchaPainter;
	/**
	 * The generator that is used for generating captcha text strings
	 */
	private CaptchaTextGenerator captchaTextGenerator;
	/**
	 * The first captcha string-generation sends -1 to observers
	 */
	public static final long CAPTCHA_GENERATED = -1;
	/**
	 * Is used when regenerating strings - may be used by Observers for various
	 * reasons
	 */
	private long lastGenerationID = 0;

	/**
	 * Creating a new SimpleCaptcha, if no TextGenerator and/or CaptchaPainter
	 * is specified the getDefaultPainter() and/or getDefaultTextGenerator() are
	 * used.
	 * 
	 * @see CaptchaPainter
	 * @see CaptchaTextGenerator
	 * @param captchaPainter
	 *            A painter that should paint the captcha text in a somewhat
	 *            readable fashion and present it to the user
	 * @param captchaTextGenerator
	 *            A generator that is responsible for generating the
	 *            text-strings used as captcha
	 */
	public SimpleCaptcha() {
		this(getDefaultPainter(), getDefaultTextGenerator());
	}

	public SimpleCaptcha(CaptchaPainter captchaPainter) {
		this(captchaPainter, getDefaultTextGenerator());
	}

	public SimpleCaptcha(CaptchaTextGenerator captchaTextGenerator) {
		this(getDefaultPainter(), captchaTextGenerator);
	}

	public SimpleCaptcha(CaptchaPainter captchaPainter,
			CaptchaTextGenerator captchaTextGenerator) {
		this.captchaPainter = captchaPainter;
		this.captchaTextGenerator = captchaTextGenerator;

		captchaImage = new CaptchaImage();

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

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

		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textfield.setText("");
				generateCaptchaText(lastGenerationID++);
			}
		});

		add(captchaImage);
		add(textfield);
		add(refreshButton);
		addCaptchaStatusUpdatedListener(this);
		generateCaptchaText(CAPTCHA_GENERATED);
	}

	/**
	 * Returns the default CaptchaPainter
	 * 
	 * @return
	 */
	private static CaptchaPainter getDefaultPainter() {
		return new ColorCaptchaPainter();
	}

	/**
	 * Returns the default CaptchaTextGenerator
	 * 
	 * @return
	 */
	private static CaptchaTextGenerator getDefaultTextGenerator() {
		return new BasicCaptchaTextGenerator();
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
	 * Removes a listener that recieves an boolean everytime the users types in
	 * the textfield
	 * 
	 * @param listener
	 */
	public void removeCaptchaStatusUpdatedListener(
			CaptchaStatusListener listener) {
		captchaStatusListeners.remove(listener);
	}

	/**
	 * Adds an observer that recieves notifications when a new string is
	 * generated.
	 * 
	 * @param observer
	 */
	public void addCaptchaObserver(CaptchaObserver observer) {
		captchaObservers.add(observer);

	}

	/**
	 * Removes an observer
	 * 
	 * @param observer
	 */
	public void removeCaptchObserver(CaptchaObserver observer) {
		captchaObservers.remove(observer);
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

	/**
	 * Notifies status listeners that the captcha status is updated
	 * 
	 * @param isCorrect
	 *            if the entered text is correct.
	 */
	private void notifyStatusListeners(boolean isCorrect) {
		for (int i = captchaStatusListeners.size() - 1; i >= 0; i--) {
			captchaStatusListeners.get(i).statusUpdated(isCorrect);
		}
	}

	/**
	 * Notifies the 
	 * @param textGenerationID
	 */
	private void notifyTextGenerationObservers(long textGenerationID) {
		for (int i = captchaObservers.size() - 1; i >= 0; i--) {
			captchaObservers.get(i).textGenerationComplete(textGenerationID);
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
	 * Checks if any options has changed, if so then do repaint and revalidate
	 * 
	 * @param oldObject
	 * @param newObject
	 */
	private void repaintIfNeeded(Object oldObject, Object newObject) {
		if (!oldObject.equals(newObject)) {
			revalidate();
			repaint();
		}
	}

	/**
	 * Is called when something has changed - repaints and revalidates the
	 * component
	 */
	public void somethingChanged() {
		captchaImage.forceRegeneratedImage();
		revalidate();
		repaint();
	}

	/**
	 * Returns the captcha text, or an empty string if no captcha exists
	 * 
	 * @return captchaText
	 */
	protected String getCaptchaText() {
		if (captchaText == null)
			return "";
		return captchaText;
	}

	/**
	 * This is used to change if the refreshButton should be focusable or not
	 */
	@Override
	public void statusUpdated(boolean isCorrect) {
		refreshButton.setFocusable(!isCorrect);
	}

	/**
	 * @return {@link CaptchaTextGenerator}
	 */
	public CaptchaTextGenerator getCaptchaTextGenerator() {
		return captchaTextGenerator;
	}

	/**
	 * @return {@link CaptchaPainter}
	 */
	public CaptchaPainter getCaptchaPainter() {
		return captchaPainter;
	}

	/**
	 * Returns the last generation id, used by Observers (increase by one every
	 * time refresh-button is clicked.)
	 * 
	 * @return
	 */
	public long getLastGenerationID() {
		return lastGenerationID;
	}

	/**
	 * Sets the text generator to be used. Please note that if SimpleCaptcha has
	 * generated a text string it will not regenerate one when this method is
	 * called (compare to setCaptchaPainter). If you want to generate a new text
	 * - please use the function reGenerate() in SimpleCaptcha.
	 * 
	 * @param captchaTextGenerator
	 */
	public void setCaptchaTextGenerator(
			CaptchaTextGenerator captchaTextGenerator) {
		repaintIfNeeded(this.captchaTextGenerator, captchaTextGenerator);
		this.captchaTextGenerator = captchaTextGenerator;
	}

	/**
	 * The default behaviour of SimpleCaptcha is to repaint the graphics when
	 * this method is called (and the new CaptchaPainter differs from the old
	 * one).
	 * 
	 * @param captchaPainter
	 */
	public void setPainter(CaptchaPainter captchaPainter) {
		repaintIfNeeded(this.captchaPainter, captchaPainter);
		this.captchaPainter = captchaPainter;
	}

	/**
	 * Tells if the default refresh button should be shown
	 * 
	 * @return showRefreshButton
	 */
	public void showRefreshButton(boolean showRefreshButton) {
		refreshButton.setVisible(showRefreshButton);
	}
	/**
	 * Generates the captchaText, useful if the CaptchaTextGenerator or
	 * CaptchaPainter renders unreadable.
	 */
	public void generateCaptchaText(long textGenerationID) {
		(new CaptchaTextGenerationThread(textGenerationID)).start();
	}
	/**
	 * 
	 *
	 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
	 */
	private class CaptchaTextGenerationThread extends Thread {
		private final long textGenerationID;

		private CaptchaTextGenerationThread(long textGenerationID) {
			this.textGenerationID = textGenerationID;
		}

		@Override
		public void run() {
			captchaText = captchaTextGenerator.generateString();
			textfield.setColumns(captchaText.length());
			captchaImage.validate();
			textChanged();
			somethingChanged();
			notifyTextGenerationObservers(textGenerationID);
		}

	}
	/**
	 * Tells the CaptchaPainter what to paint and helps Captcha to check the
	 * current size of the captchaImage component
	 * 
	 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
	 */
	private class CaptchaImage extends JPanel implements
			HierarchyBoundsListener {
		/**
		 * Used to keep track of "real"-changes in height / width of this
		 * component (in order to avoid unneccesary regeneration of
		 * captcha-images)
		 */
		int oldWidth, oldHeight;
		/**
		 * Generated by Eclipse
		 */
		private static final long serialVersionUID = 5463117666297420859L;

		/**
		 * Creates a new Captcha Image with a painter associated
		 * 
		 * @param text
		 * @param painter
		 */
		public CaptchaImage() {
			addHierarchyBoundsListener(this);
		}

		/**
		 * Paints the captchaText using the CaptchaPainter on the graphics
		 * 
		 * @param g
		 */
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			getCaptchaPainter().paint(g, getCaptchaText(),
					new Dimension(getWidth(), getHeight()));
		}

		/**
		 * Let the Captcha regenerate image and calculate size when neccessary
		 * (when width or height has changed)
		 */
		@Override
		public void ancestorResized(HierarchyEvent e) {
			if (oldWidth != getWidth() || oldHeight != getHeight()) {
				oldWidth = getWidth();
				oldHeight = getHeight();
				forceRegeneratedImage();
			}
		}

		/**
		 * Updates the size
		 */
		private void updateSize() {
			revalidate();
			repaint();
		}

		/**
		 * Calculates the preferred size of the component
		 * 
		 * @return preferred dimensions of captcha image
		 */
		@Override
		public Dimension getPreferredSize() {
			return getCaptchaPainter().calculateDimension(getGraphics(),
					getCaptchaText());
		}

		/**
		 * Calculates the minimum size of the component in order to make it work
		 * (if it's resized too small it's possible that text will render
		 * unreadable)
		 * 
		 * @return minimum size
		 */
		@Override
		public Dimension getMinimumSize() {
			return getPreferredSize();
		}

		/**
		 * Forces the image to be regenerated and updates the size.
		 */
		public void forceRegeneratedImage() {
			getCaptchaPainter().forceRegeneratedImage();
			updateSize();
		}

		@Override
		public void ancestorMoved(HierarchyEvent e) {

		}
	}
}
