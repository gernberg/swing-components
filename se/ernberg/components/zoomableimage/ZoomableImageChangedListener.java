package se.ernberg.components.zoomableimage;

import java.util.EventListener;

/**
 * A listener that recieves a ZoomableImageEvent whenever something regarding the Zoomable
 * Image changes.
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public interface ZoomableImageChangedListener extends EventListener {
	/**
	 * Called whenever something regarding the ZoomableImage changes.
	 * @param e ZoomableImageEvent
	 */
	public void viewUpdated(ZoomableImageEvent e);
}
