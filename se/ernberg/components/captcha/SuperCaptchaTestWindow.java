package se.ernberg.components.captcha;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class SuperCaptchaTestWindow {
	final static JPanel mainPanel = new JPanel();

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Super Captcha Test");

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(example1());
		mainPanel.add(example2());
		mainPanel.add(example3());
		mainPanel.add(example4());
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));
		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
		SuperCaptcha captcha = new SuperCaptcha(CaptchaSimplePainter.getInstance(), CaptchaSwedishTextGenerator
				.getInstance());
		captcha.showRefreshButton(false);
		return captcha;
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}