package se.ernberg.components.zoomableimage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;

public class ZoomableImage extends JComponent implements MouseWheelListener,
		MouseMotionListener, MouseListener, HierarchyBoundsListener {
	/**
	 * Generated by Eclipse
	 */
	private static final long serialVersionUID = -6605226564837331976L;
	/**
	 * The image that should be zoomed
	 */
	private Image image;
	/**
	 * FIT_PANE means that the image should be resized to fit inside the
	 * designated pane so that the whole image is visible. The image is centered
	 * to the middle of the pane
	 */
	public static final int FIT_PANE = 1;
	/**
	 * FILL_PANE means that the image should be resized so that it precisely
	 * covers the entire designated pane. Some parts of the image may not be
	 * visible. The image is centered to the designated pane.
	 */
	public static final int FILL_PANE = 2;
	/**
	 * ORIGINAL_SCALE means that the image should not be resized at all, only
	 * centered to the middle of the designated pane.
	 */
	public static final int ORIGINAL_SIZE = 3;
	/**
	 * By Default the strategy is to draw images in their original scale
	 */
	private int displayStrategy = ORIGINAL_SIZE;
	/**
	 * Used to detect double-clicking
	 */
	long lastClicked = 0;

	/**
	 * This action resets the image in the way specified by displayStrategy.
	 */
	public final Action ACTION_RESET = new AbstractAction("Reset") {
		/**
		 * Generated by Eclipse
		 */
		private static final long serialVersionUID = -4104192164959042958L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setDisplayStrategy(displayStrategy);
		}
	};
	/**
	 * This action zooms in an image with and centers it in the pane
	 */
	public final Action ACTION_ZOOM_IN = new AbstractAction("Zoom in") {
		/**
		 * Generated by Eclipse
		 */
		private static final long serialVersionUID = -8203146974277962168L;

		@Override
		public void actionPerformed(ActionEvent e) {
			zoomIn();
		}
	};
	/**
	 * This action zooms out an image
	 */
	public final Action ACTION_ZOOM_OUT = new AbstractAction("Zoom out") {
		/**
		 * Generated by Eclipse
		 */
		private static final long serialVersionUID = 7213669613104803783L;

		@Override
		public void actionPerformed(ActionEvent e) {
			zoomOut();
		}
	};
	/**
	 * This action sets the displayStrategy to FILL_PANE
	 */
	public final Action ACTION_FILL_PANE = new AbstractAction("Fill pane") {
		/**
		 * Generated by Eclipse
		 */
		private static final long serialVersionUID = -1228809038909125370L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setDisplayStrategy(FILL_PANE);
		}
	};
	/**
	 * This action sets the displayStrategy to FIT_PANE
	 */
	public final Action ACTION_FIT_PANE = new AbstractAction("Fit pane") {
		/**
		 * Generated by Eclipse
		 */
		private static final long serialVersionUID = 1530103639268808210L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setDisplayStrategy(FIT_PANE);
		}
	};

	/**
	 * 
	 */
	public final Action ACTION_ORIGINAL_SIZE = new AbstractAction(
			"Original size") {
		/**
		 * Generated by Eclipse
		 */
		private static final long serialVersionUID = 7575628878973889612L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setDisplayStrategy(ORIGINAL_SIZE);
		}
	};
	/**
	 * A list of listeners to any changes in the status
	 */
	private final ArrayList<ZoomableImageChangedListener> zoomableChangeListeners = new ArrayList<ZoomableImageChangedListener>();
	/**
	 * Used to detect if user has started interacting with the image, if not -
	 * then updateDisplayStrategy is called.
	 */
	private boolean userStartedInteracting = false;
	/**
	 * Sets the initial zoom value to 1
	 */
	private double zoom = 1;
	/**
	 * Sets the initial position of the image to 0,0
	 */
	double x, y = 0;
	/**
	 * Sets the initail mouse wheel scroll speed to 0.005
	 */
	double mouseWheelZoomSpeed = 0.005;
	/**
	 * Used to remember "where were we last", used by the drag-n-drop functions
	 * in order to give meaningful outputs
	 */
	int lastx, lasty = 0;
	/**
	 * The speed for the "fastZoom", used when double-clicking or calling the
	 * ZoomIn() action.
	 */
	private double fastZoomSpeed = 100;

	/**
	 * ZoomableImage is basically an image component with zoom and pan functions
	 * enabled
	 * 
	 * @param image
	 *            Image to be made zoomeable
	 */
	public ZoomableImage(Image image) {
		this(image, ORIGINAL_SIZE);
	}

	/**
	 * ZoomableImage is basically an image component with zoom and pan functions
	 * enabled
	 * 
	 * @param image
	 *            Image to be made zoomeable
	 * @param displayStrategy
	 *            the displayStrategy to be used
	 */
	public ZoomableImage(Image image, int displayStrategy) {
		this.image = image;
		this.displayStrategy = displayStrategy;
		setPreferredSize(new Dimension(image.getWidth(this),
				image.getHeight(this)));

		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		addHierarchyBoundsListener(this);
		setBackground(Color.black);
	}

	/**
	 * Zooms out the image at center position (for the pane) with amount defined
	 * by fastZoom variable
	 */
	protected void zoomOut() {
		zoomIn(getWidth() / 2, getHeight() / 2, -fastZoomSpeed);
	}

	/**
	 * Zooms in the image at center position (for the pane) with amount defined
	 * by fastZoom variable
	 */
	protected void zoomIn() {
		zoomIn(getWidth() / 2, getHeight() / 2, fastZoomSpeed);
	}

	/**
	 * Replaces the image
	 * 
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
		fireUpdateActions();
	}

	/**
	 * Returns the current zoom
	 * 
	 * @return
	 */
	public double getZoom() {
		return zoom;
	}

	/**
	 * Sets zoom
	 * 
	 * @param zoom
	 */
	public void setZoom(double zoom) {
		this.zoom = zoom;
		fireUpdateActions();
	}

	/**
	 * Set zoomSpeed for mousewheel
	 * 
	 * @param scrollSpeed
	 */
	public void setMouseWheelZoomSpeed(double mouseWheelZoomSpeed) {
		this.mouseWheelZoomSpeed = mouseWheelZoomSpeed;
	}

	/**
	 * Set zoomSpeed for mousewheel
	 * 
	 * @param fastZoomSpeed
	 */
	public void setFastZoomSpeed(double fastZoomSpeed) {
		this.fastZoomSpeed = fastZoomSpeed;
	}

	/**
	 * Sets (and updates) the displayStrategy
	 * 
	 * @param displayStrategy
	 */
	private void setDisplayStrategy(int displayStrategy) {
		this.displayStrategy = displayStrategy;
		userStartedInteracting = false;
		doDisplayStrategy();
	}

	/**
	 * Repositionates/resizes the image, according to the current
	 * displayStrategy
	 */
	private void doDisplayStrategy() {
		switch (displayStrategy) {
		case FIT_PANE:
			// Set the zoom so that the height of the image should fit
			zoom = (getHeight()) / ((float) image.getHeight(this));
			if (getZoomedImageWidth() > getWidth()) {
				// If the width of the image is still to big
				zoom = (getWidth()) / ((float) image.getWidth(this));
			} else {
				// If the height-resizing was sufficient in order to fit
			}
			break;
		case FILL_PANE:
			// Set the zoom so that the height of the image should fill
			zoom = (getHeight()) / ((float) image.getHeight(this));
			if (getZoomedImageHeight() < getWidth()) {
				// If the image width is too small (not filling)
				zoom = (getWidth()) / ((float) image.getWidth(this));
			} else {
				// If the image width is filling the area
			}
			break;
		case ORIGINAL_SIZE:
			zoom = 1;
			break;
		}
		centerImage();
		System.out.println(getCenterX() + "|" + getCenterY());
		fireUpdateActions();
	}

	/**
	 * Centers the image according to current zoom-level
	 */
	private void centerImage() {
		y = getCenterY();
		x = getCenterX();
	}

	/**
	 * Calculates and returns the center x position for the image (after applied
	 * zoom)
	 * 
	 * @return int center x position for the image (after applied zoom)
	 */
	private double getCenterX() {
		return (getWidth() - image.getWidth(this) * zoom) / 2;
	}

	/**
	 * Calculates and returns the center y position for the image (after applied
	 * zoom)
	 * 
	 * @return int center y position for the image (after applied zoom)
	 */
	private double getCenterY() {
		return (getHeight() - image.getHeight(this) * zoom) / 2;
	}

	/**
	 * Paints the image
	 * 
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		// Don't try to draw images that doesn't exist.
		if (image != null) {
			g2d.drawImage(image, (int) x, (int) y, getZoomedImageWidth(),
					getZoomedImageHeight(), null);
		}
	}

	/**
	 * Gets the height of the image in its current zoomed state
	 * 
	 * @return
	 */
	private int getZoomedImageHeight() {
		return (int) (image.getWidth(this) * zoom);
	}

	/**
	 * Gets the height of the image in its current zoomed state
	 * 
	 * @return
	 */
	private int getZoomedImageWidth() {
		return (int) (image.getWidth(this) * zoom);
	}

	/**
	 * Zooms in the image at given x,y coordinate
	 * 
	 * @param x
	 *            coordinate in pane (not image)
	 * @param y
	 *            coordinate in pane (not image)
	 * @param unitsToScroll
	 *            how
	 */
	private void zoomIn(int x, int y, double unitsToScroll) {
		userStartedInteracting = true;
		double centerx = Math.ceil((x - this.x) / zoom);
		double centery = Math.ceil((y - this.y) / zoom);
		zoom += zoom * unitsToScroll * mouseWheelZoomSpeed;
		if (zoom < 0.01)
			zoom = 0.01;
		int centerPointX = x;
		int centerPointY = y;

		int newx = (int) (centerPointX - centerx * zoom);
		int newy = (int) (centerPointY - centery * zoom);
		this.x = newx;
		this.y = newy;
		fireUpdateActions();
	}

	/**
	 * Adds a listener that recieves events when anything regarding the image
	 * changes
	 * 
	 * @param listener
	 */
	public void addZoomableChangeListener(ZoomableImageChangedListener listener) {
		zoomableChangeListeners.add(listener);
	}

	/**
	 * Removes listener
	 * 
	 * @param listener
	 */
	public void removeZoomableChangeListener(
			ZoomableImageChangedListener listener) {
		zoomableChangeListeners.remove(listener);
	}

	/**
	 * Notifies listeners that an update has occurred
	 */
	private void fireUpdateActions() {
		for (int i = zoomableChangeListeners.size() - 1; i >= 0; i--) {
			zoomableChangeListeners.get(i).viewUpdated(
					new ZoomableImageEvent((int) x, (int) y, getWidth(),
							getHeight(), zoom));
		}
		// If the image moved, we are most likely needed to repaint the
		// component
		repaint();
	}

	/**
	 * Moves the image when mouse is dragged, relies on correct implementation
	 * of lastx,lasty in mousePressed.
	 * 
	 * @param e
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		userStartedInteracting = true;
		lastClicked = 0;
		x -= (lastx - e.getX());
		y -= (lasty - e.getY());
		lastx = e.getX();
		lasty = e.getY();
		fireUpdateActions();
	}

	/**
	 * Whenever the pane is resized - make sure our displayStrategy is updated,
	 * if the user has changed anything regarding the image (zoom, pan) then
	 * don't undo their changes - just ignore the window resize.
	 * 
	 * @param e
	 */
	@Override
	public void ancestorResized(HierarchyEvent e) {
		if (!userStartedInteracting) {
			setDisplayStrategy(displayStrategy);
		}
	}

	/**
	 * Zoom
	 * 
	 * @param e
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoomIn(e.getX(), e.getY(), -e.getUnitsToScroll());
	}

	/**
	 * Used in combination together with mouseDragged, used to set where a
	 * drag-movement started (in order to accurately calculate how to move the
	 * image)
	 * 
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		lastx = e.getX();
		lasty = e.getY();
	}

	/**
	 * Used to detect double-clicks (and react by zooming in)
	 * 
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if ((lastClicked + 1000) > System.currentTimeMillis()) {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					zoomIn(e.getX(), e.getY(), fastZoomSpeed);
					break;
				default:
					break;
				}
			}
			lastClicked = System.currentTimeMillis();
		}
	}

	/**
	 * Below are some unimplemented methods from interfaces, separated from the
	 * rest for readability
	 */

	/**
	 * Not implemented
	 * 
	 * @param e
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Not implemented
	 * 
	 * @param e
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * Not implemented
	 * 
	 * @param e
	 */
	@Override
	public void mouseMoved(MouseEvent e) {

	}

	/**
	 * Not implemented
	 * 
	 * @param e
	 */
	@Override
	public void ancestorMoved(HierarchyEvent e) {

	}

	/**
	 * Not implemented
	 * 
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}
}
