package se.ernberg;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import se.ernberg.components.ZoomableImage;
import se.ernberg.components.captcha.Captcha;

public class TestWindow {

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("HelloWorldSwing");
		Container panel = frame.getContentPane();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Captcha captcha = new Captcha();
		panel.add(captcha);

		final JButton button = new JButton("asdf");
		final JTextField textfield = new JTextField("asdf");
		

		
		
		panel.add(button);
		panel.add(textfield);
		
		
		ZoomableImage image = new ZoomableImage("/home/gustav/workspace/SwingComponents/src/images/w0tt.JPG");
		
		panel.add(image);
		
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