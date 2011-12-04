package se.ernberg.components.captcha;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class SuperCaptchaTestWindow {
	final static JPanel mainPanel = new JPanel();

	public SuperCaptchaTestWindow() {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Super Captcha Test");

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(example1());
		mainPanel.add(example2());
		mainPanel.add(example3());
		mainPanel.add(example4());
		mainPanel.add(example5());
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));
		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * It's really this easy to add a SuperCaptcha component
	 * 
	 * @return
	 */
	private static SuperCaptcha example1() {
		return new SuperCaptcha();
	}

	/**
	 * This simple code make a button enabled / disabled depending on what the
	 * user types.
	 * 
	 * @return
	 */
	private static JPanel example2() {
		JPanel panel = new JPanel();
		SuperCaptcha captcha = new SuperCaptcha();
		final JButton button = new JButton("Ok");
		button.setEnabled(false);
		captcha.addCaptchaStatusUpdatedListener(new CaptchaStatusListener() {
			@Override
			public void captchaStatusUpdated(boolean isCorrect) {
				button.setEnabled(isCorrect);
			}
		});
		panel.add(captcha);
		panel.add(button);
		return panel;
	}

	/**
	 * An example with some more advanced actions when the captcha is correct
	 * 
	 * @return
	 */
	private static JPanel example3() {
		final JPanel panel = new JPanel();
		final SuperCaptcha captcha = new SuperCaptcha();
		final JButton button = new JButton("Ok");
		button.setEnabled(false);
		captcha.addCaptchaStatusUpdatedListener(new CaptchaStatusListener() {
			@Override
			public void captchaStatusUpdated(boolean isCorrect) {
				if (isCorrect) {
					button.setEnabled(true);
					button.requestFocusInWindow();
					panel.remove(captcha);
					panel.repaint();
				}
			}
		});
		panel.add(captcha);
		panel.add(button);
		return panel;
	}

	/**
	 * An example on how to configure the SuperCaptcha
	 * 
	 * @return
	 */
	private static SuperCaptcha example4() {
		SuperCaptcha captcha = new SuperCaptcha(new CaptchaSimplePainter(),
				new CaptchaSwedishTextGenerator());
		captcha.showRefreshButton(false);
		return captcha;
	}

	/**
	 * An example on how to configure the SuperCaptcha during runtime and
	 * showing the possibility to change configuration at any time (in this
	 * example - on a Status Update);
	 * 
	 * @return
	 */
	private static SuperCaptcha example5() {
		final SuperCaptcha captcha = new SuperCaptcha();
		captcha.addCaptchaStatusUpdatedListener(new CaptchaStatusListener() {
			@Override
			public void captchaStatusUpdated(boolean isCorrect) {
				captcha.showRefreshButton(!isCorrect);
				if (isCorrect) {
					captcha.setPainter(new CaptchaSimplePainter());
				}
			}
		});
		return captcha;
	}

	public static void main(String[] args) {
		new SuperCaptchaTestWindow();
	}
}