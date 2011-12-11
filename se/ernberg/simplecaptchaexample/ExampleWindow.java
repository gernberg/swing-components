package se.ernberg.simplecaptchaexample;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import se.ernberg.components.simplecaptcha.CaptchaObserver;
import se.ernberg.components.simplecaptcha.CaptchaStatusListener;
import se.ernberg.components.simplecaptcha.CaptchaTextGenerator;
import se.ernberg.components.simplecaptcha.ColorCaptchaPainter;
import se.ernberg.components.simplecaptcha.SimpleCaptcha;
import se.ernberg.components.simplecaptcha.BasicCaptchaPainter;
import se.ernberg.components.simplecaptcha.BasicCaptchaTextGenerator;
import se.ernberg.components.simplecaptcha.SwedishCaptchaTextGenerator;

public class ExampleWindow {
	final static JPanel mainPanel = new JPanel();
	final static JFrame frame = new JFrame("Super Captcha Test");

	public ExampleWindow() {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		SimpleCaptcha captcha;
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		mainPanel.add(example1());
		mainPanel.add(example2());
		mainPanel.add(example3());
		mainPanel.add(example4());
		mainPanel.add(example5());
		mainPanel.add(example6());

		
		// Sets the generated string length to 10 chars
		captcha = new SimpleCaptcha(new BasicCaptchaPainter(),
				new BasicCaptchaTextGenerator(10));
		mainPanel.add(captcha);

		// Sets the generated string length to 10 chars and use only a,b,c
		captcha = new SimpleCaptcha(new BasicCaptchaTextGenerator(1, "abc"));
		mainPanel.add(captcha);
		
		
		mainPanel.add(captcha);
		
		// Creates a SimpleCaptcha that has a slow textGenerator 
		captcha = new SimpleCaptcha(new CaptchaTextGenerator() {
			
			@Override
			public String generateString() {
				System.out.println("Hittar på saker...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return "good morning";
			}
		});
		// Let's pack when the slow-captcha is done generating text
		captcha.addCaptchaObserver(new CaptchaObserver() {
			
			@Override
			public void regenerationComplete(long id) {
				System.out.println(id);
				if(id == SimpleCaptcha.CAPTCHA_GENERATED)
					frame.pack();
			}
		});
		
		mainPanel.add(captcha);

		// Use only a,b,c and mix upper / lowercase
		BasicCaptchaTextGenerator textGenerator = new BasicCaptchaTextGenerator(
				"abc");
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
			public void statusUpdated(boolean isCorrect) {
				// Focus the button
				if (isCorrect) {
					button.setEnabled(isCorrect);
					button.requestFocusInWindow();
				}
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
			public void statusUpdated(boolean isCorrect) {
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

		SimpleCaptcha captcha = new SimpleCaptcha(new BasicCaptchaPainter(),
				new SwedishCaptchaTextGenerator());

		// Since the component extends JPanel, there are much room for
		// customization (eg. setting layout)
		captcha.setLayout(new BoxLayout(captcha, BoxLayout.PAGE_AXIS));

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
			public void statusUpdated(boolean isCorrect) {
				captcha.showRefreshButton(!isCorrect);
				if (isCorrect) {
					captcha.setPainter(new BasicCaptchaPainter());
				} else {
					captcha.setPainter(new ColorCaptchaPainter());
				}
			}
		});

		panel.add(captcha);
		return panel;
	}

	/**
	 * Example on how to create a custom Text Generator on the fly
	 * 
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