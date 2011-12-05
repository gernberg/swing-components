package se.ernberg.components.zoomableimage;

public class ZoomableImageEvent {
	private int x,y,width,height;
	private double zoom;
	private EventType type;

	static enum EventType{
		POSITION_CHANGED, ZOOM_CHANGED, ZOOM_IN, IMAGE_CHANGED, ZOOM_OUT, RESET;
	}
	
	public ZoomableImageEvent(int x, int y, int width, int height, double zoom,
			EventType type) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.zoom = zoom;
		this.type = type;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public double getZoom() {
		return zoom;
	}
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	
}
