package se.ernberg;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import se.ernberg.components.captcha.SuperCaptcha;
import se.ernberg.components.captcha.CaptchaColourPainter;
import se.ernberg.components.captcha.CaptchaOptions;
import se.ernberg.components.captcha.CaptchaSimplePainter;
import se.ernberg.components.captcha.CaptchaSimpleTextGenerator;
import se.ernberg.components.captcha.CaptchaStatusListener;
import se.ernberg.components.captcha.CaptchaSwedishTextGenerator;
import se.ernberg.components.zoomableimage.ZoomableImage;

public class TestWindow {

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("HelloWorldSwing");
		final Container panel = frame.getContentPane();
		panel.setLayout(new FlowLayout());//new BoxLayout(panel, BoxLayout.Y_AXIS));

		final CaptchaOptions options = new CaptchaOptions();
		options.setCaptchaTextGenerator(new CaptchaSimpleTextGenerator(2));
		options.setCaptchaTextGenerator(new CaptchaSwedishTextGenerator());
		options.setCaptchaPainter(CaptchaColourPainter.getInstance());
		
		final SuperCaptcha captcha = new SuperCaptcha(options);
		
		panel.add(captcha);

		final JButton button = new JButton("asdf");
		final JTextField textfield = new JTextField("asdf");
		
		panel.add(button);
		panel.add(textfield);

		ZoomableImage image = new ZoomableImage(
				"/home/gustav/workspace/SwingComponents/src/images/w0tt.JPG");
		
		captcha.addCaptchaStatusUpdatedListener(new CaptchaStatusListener() {
			
			@Override
			public void captchaStatusUpdated(boolean isCorrect) {
				button.setEnabled(isCorrect);
			}
		});

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

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