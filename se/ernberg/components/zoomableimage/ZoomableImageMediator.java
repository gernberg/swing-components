package se.ernberg.components.zoomableimage;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ZoomableImageMediator {

	public ZoomableImageMediator(JSlider s, ZoomableImage z) {
		final JSlider slider = s;
		final ZoomableImage zoomableImage = z;
		
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				zoomableImage.setZoom((((JSlider) e.getSource()).getValue())/100.0);
			}
		});
		
	}

}
