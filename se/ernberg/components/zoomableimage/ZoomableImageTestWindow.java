package se.ernberg.components.zoomableimage;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ZoomableImageTestWindow {

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("HelloWorldSwing");
		final Container panel = frame.getContentPane();
		panel.setLayout(new BoxLayout(panel,
											 BoxLayout.Y_AXIS));

		ZoomableImage image = new ZoomableImage(
				"/home/gustav/workspace/SwingComponents/src/images/w0tt.JPG");
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