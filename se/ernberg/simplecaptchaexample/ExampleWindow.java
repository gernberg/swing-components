package se.ernberg.simplecaptchaexample;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import se.ernberg.components.simplecaptcha.CaptchaStatusListener;
import se.ernberg.components.simplecaptcha.CaptchaTextGenerator;
import se.ernberg.components.simplecaptcha.SimpleCaptcha;
import se.ernberg.components.simplecaptcha.SimpleCaptchaPainter;
import se.ernberg.components.simplecaptcha.SimpleCaptchaTextGenerator;
import se.ernberg.components.simplecaptcha.SwedishCaptchaTextGenerator;

public class ExampleWindow {
	final static JPanel mainPanel = new JPanel();
	public ExampleWindow() {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Super Captcha Test");
		SimpleCaptcha captcha;
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		mainPanel.add(example1());
		mainPanel.add(example2());
		mainPanel.add(example3());
		mainPanel.add(example4());
		mainPanel.add(example5());
		mainPanel.add(example6());

		// Sets the generated string length to 10 chars
		captcha = new SimpleCaptcha(new SimpleCaptchaPainter(), new SimpleCaptchaTextGenerator(10));
		mainPanel.add(captcha);

		// Sets the generated string length to 10 chars and use only a,b,c
		captcha = new SimpleCaptcha(new SimpleCaptchaTextGenerator(1, "abc"));
		mainPanel.add(captcha);
		
		// Use only a,b,c and mix upper / lowercase
		SimpleCaptchaTextGenerator textGenerator = new SimpleCaptchaTextGenerator("abc");
		textGenerator.mixUpperAndLowerCase(true);
		captcha = new SimpleCaptcha(textGenerator);
		mainPanel.add(captcha);
		
		
		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}


	/**
	 * It's really this easy to add a SuperCaptcha component
	 * 
	 * @return
	 */
	private static SimpleCaptcha example1() {
		return new SimpleCaptcha();
	}

	/**
	 * This simple code make a button enabled / disabled depending on what the
	 * user types.
	 * 
	 * @return
	 */
	private static JPanel example2() {
		JPanel panel = new JPanel();
		SimpleCaptcha captcha = new SimpleCaptcha();
		final JButton button = new JButton("Ok");
		button.setEnabled(false);
		captcha.addCaptchaStatusUpdatedListener(new CaptchaStatusListener() {
			@Override
			public void captchaStatusUpdated(boolean isCorrect) {
				// Focus the button 
				button.setEnabled(isCorrect);
				button.requestFocusInWindow();
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
		final SimpleCaptcha captcha = new SimpleCaptcha();
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
	 * An example on how to configure the SimpleCaptcha
	 * 
	 * @return
	 */
	private static JPanel example4() {
		final JPanel panel = new JPanel();
		
		SimpleCaptcha captcha = new SimpleCaptcha(new SimpleCaptchaPainter(),
				new SwedishCaptchaTextGenerator());
		
		captcha.showRefreshButton(false);
		panel.add(captcha);

		return panel;
	}

	/**
	 * An example on how to configure the SimpleCaptcha during runtime and
	 * showing the possibility to change configuration at any time (in this
	 * example - on a Status Update);
	 * 
	 * @return
	 */
	private static JPanel example5() {
		JPanel panel = new JPanel();
		
		final SimpleCaptcha captcha = new SimpleCaptcha();
		captcha.addCaptchaStatusUpdatedListener(new CaptchaStatusListener() {
			@Override
			public void captchaStatusUpdated(boolean isCorrect) {
				captcha.showRefreshButton(!isCorrect);
				if (isCorrect) {
					captcha.setPainter(new SimpleCaptchaPainter());
				}
			}
		});
		
		panel.add(captcha);
		return panel;
	}

	/**
	 * Example on how to create a custom Text Generator on the fly
	 * @return
	 */
	private static Component example6() {
		return new SimpleCaptcha(new CaptchaTextGenerator() {
			
			@Override
			public String generateString() {
				return "foo";
			}
		});
	}
	
	public static void main(String[] args) {
		new ExampleWindow();
	}
}