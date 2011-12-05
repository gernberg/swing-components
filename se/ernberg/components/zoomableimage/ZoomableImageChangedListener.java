package se.ernberg.components.zoomableimage;

import java.util.EventListener;

public interface ZoomableImageChangedListener extends EventListener{
	public void viewUpdated(ZoomableImageEvent e);
}
