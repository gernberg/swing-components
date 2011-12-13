package se.ernberg.components.zoomableimage;

/**
 * ZoomableImageEvent contains some basic information about the ZoomableImage
 * which may be useful to a listener.
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class ZoomableImageEvent {
	private int x, y, width, height;
	private double zoom;
	ZoomableImage zoomableImage;

	/**
	 * Creates a new ZoomableImageEvent
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zoom
	 */
	public ZoomableImageEvent(int x, int y, int width, int height, double zoom,
			ZoomableImage zoomableImage) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.zoom = zoom;
		this.zoomableImage = zoomableImage;
	}
	/**
	 * @return Current x-position
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return Current y-position
	 */
	public int getY() {
		return y;
	}
	/**
	 * @return Current image width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @return Current image height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @return Current zoom level
	 */
	public double getZoom() {
		return zoom;
	}
	/**
	 * This ZoomableImage that is responsible for this event
	 * @return ZoomableImage responsible for this event
	 */
	public ZoomableImage getSource(){
		return zoomableImage;
	}

}
