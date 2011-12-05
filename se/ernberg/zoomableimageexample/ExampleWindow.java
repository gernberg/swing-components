package se.ernberg.zoomableimageexample;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.ernberg.components.zoomableimage.ZoomableImage;
import se.ernberg.components.zoomableimage.ZoomableImageJSliderMediator;

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

		// It's possible to set a preferred size, if none is specified. The size
		// of the image is the the preferredSize by default
		zoomableImage.setPreferredSize(new Dimension(100, 100));

		// Add buttons for the different actions provided by ZoomableImage
		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JButton(zoomableImage.ACTION_RESET));
		optionsPanel.add(new JButton(zoomableImage.ACTION_ZOOM_IN));
		optionsPanel.add(new JButton(zoomableImage.ACTION_ZOOM_OUT));
		optionsPanel.add(new JButton(zoomableImage.ACTION_FIT_PANE));
		optionsPanel.add(new JButton(zoomableImage.ACTION_FILL_PANE));
		panel.setLayout(new BorderLayout(5, 5));
		panel.add(optionsPanel, BorderLayout.NORTH);
		panel.add(zoomableImage, BorderLayout.CENTER);
		JSlider slider = new JSlider(JSlider.VERTICAL);
		
		new ZoomableImageJSliderMediator(slider, zoomableImage);
		
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				zoomableImage.setZoom((((JSlider) e.getSource()).getValue())/100.0);
			}
		});
		
		panel.add(slider, BorderLayout.WEST);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new ExampleWindow();
	}
}