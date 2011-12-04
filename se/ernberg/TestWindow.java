package se.ernberg;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import se.ernberg.components.captcha.SuperCaptchaTestWindow;
import se.ernberg.components.zoomableimage.ZoomableImageTestWindow;

public class TestWindow {

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("HelloWorldSwing");
		final Container panel = frame.getContentPane();
		panel.setLayout(new FlowLayout());// new BoxLayout(panel,
											// BoxLayout.Y_AXIS));
		JButton button = new JButton("SuperCaptchaTest");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SuperCaptchaTestWindow();
			}
		});
		panel.add(button);
		button = new JButton("ZoomableImageTest");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ZoomableImageTestWindow();
			}
		});
		panel.add(button);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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