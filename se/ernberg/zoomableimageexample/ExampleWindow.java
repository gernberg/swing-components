package se.ernberg.zoomableimageexample;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import se.ernberg.components.zoomableimage.ZoomableImage;
import se.ernberg.components.zoomableimage.ZoomableImageChangedListener;
import se.ernberg.components.zoomableimage.ZoomableImageEvent;

/**
 * An usage example on how to use the ZoomableImage component
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class ExampleWindow {
	public ExampleWindow() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {
		JFrame frame = new JFrame("Zoomable Image Test Window");
		final Container panel = frame.getContentPane();

		// Use any image, in this example - a screenshot
		Image image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		try {
			image = (new Robot()).createScreenCapture(new Rectangle(0, 0, 1000,
					1000));
		} catch (Exception e) {
			e.printStackTrace();
		}

		final ZoomableImage zoomableImage = new ZoomableImage(image,
				ZoomableImage.FIT_PANE);

		final JLabel zoomLevel = new JLabel("Zoom:");
		
		// It's possible to set a preferred size, if none is specified. The size
		// of the image is the the preferredSize by default
		zoomableImage.setPreferredSize(new Dimension(100, 100));

		// Add buttons for the different actions provided by ZoomableImage
		JPanel optionsPanel = new JPanel();
		optionsPanel.add(zoomLevel);
		
		optionsPanel.add(new JButton(zoomableImage.ACTION_RESET));
		optionsPanel.add(new JButton(zoomableImage.ACTION_ZOOM_IN));
		optionsPanel.add(new JButton(zoomableImage.ACTION_ZOOM_OUT));
		optionsPanel.add(new JButton(zoomableImage.ACTION_FIT_PANE));
		optionsPanel.add(new JButton(zoomableImage.ACTION_FILL_PANE));
		panel.setLayout(new BorderLayout(5, 5));
		panel.add(optionsPanel, BorderLayout.NORTH);
		panel.add(zoomableImage, BorderLayout.CENTER);
		
		zoomableImage.addZoomableChangeListener(new ZoomableImageChangedListener() {
			
			@Override
			public void viewUpdated(ZoomableImageEvent e) {
				zoomLevel.setText("Zoom: " + Math.floor(e.getZoom()*1000.0)/1000.0);
			}
		});
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new ExampleWindow();
	}
}