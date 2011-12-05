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
	/**
	 * Creates a new ZoomableImageEvent
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zoom
	 */
	public ZoomableImageEvent(int x, int y, int width, int height, double zoom) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.zoom = zoom;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getZoom() {
		return zoom;
	}

}
