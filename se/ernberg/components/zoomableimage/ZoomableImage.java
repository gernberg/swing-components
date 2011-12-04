package se.ernberg.components.zoomableimage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;

public class ZoomableImage extends JComponent implements MouseWheelListener,
		MouseMotionListener, MouseListener, HierarchyBoundsListener {
	Image image;
	static final int FIT_PANE = 1;
	static final int FILL_PANE = 2;
	static final int FULL_SCALE = 3;
	private int tactic = FULL_SCALE;
	public final Action ACTION_RESET = new AbstractAction("Reset") {

		@Override
		public void actionPerformed(ActionEvent e) {
			reset();
		}
	};
	public final Action ACTION_ZOOM_IN = new AbstractAction("Zoom in") {

		@Override
		public void actionPerformed(ActionEvent e) {
			zoomIn();
		}
	};
	public final Action ACTION_ZOOM_OUT = new AbstractAction("Zoom out") {

		@Override
		public void actionPerformed(ActionEvent e) {
			zoomOut();
		}
	};
	public final Action ACTION_FILL_PANE = new AbstractAction("Fill pane") {

		@Override
		public void actionPerformed(ActionEvent e) {
			setZoomToTactic(FILL_PANE);
		}
	};
	public final Action ACTION_FIT_PANE = new AbstractAction("Fit pane") {

		@Override
		public void actionPerformed(ActionEvent e) {
			setZoomToTactic(FIT_PANE);
		}
	};
	private boolean userStartedInteracting = false;
	double zoom = 1;
	double x, y = 0;
	double scrollSpeed = 0.005;
	int lastx, lasty = 0;
	private double fastZoom = 100;

	public ZoomableImage(Image image, int tactic) {
		this.image = image;
		this.tactic = tactic;

		setPreferredSize(new Dimension(image.getWidth(this),
				image.getHeight(this)));

		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		addHierarchyBoundsListener(this);
		setBackground(Color.black);
	}

	protected void zoomOut() {
		zoomIn(getWidth() / 2, getHeight() / 2, -fastZoom);
	}

	protected void zoomIn() {
		zoomIn(getWidth() / 2, getHeight() / 2, fastZoom);
	}

	public ZoomableImage(Image image) {
		this(image, FULL_SCALE);
	}

	public void setImage(Image image) {
		this.image = image;
		repaint();
	}

	private void setZoomToTactic(int tactic) {
		this.tactic = tactic;
		userStartedInteracting = false;
		x = 0;
		y = 0;
		switch (tactic) {
		case FIT_PANE:
			zoom = (getHeight()) / ((float) image.getHeight(this));
			if (getWidth() < (zoom * image.getWidth(this))) {
				zoom = (getWidth()) / ((float) image.getWidth(this));
				y = (getHeight() - image.getHeight(this) * zoom) / 2;
			} else {
				x = (getWidth() - image.getWidth(this) * zoom) / 2;

			}
			break;
		case FILL_PANE:
			zoom = (getHeight()) / ((float) image.getHeight(this));
			if (getWidth() > (zoom * image.getWidth(this))) {
				zoom = (getWidth()) / ((float) image.getWidth(this));
				y = (getHeight() - image.getHeight(this) * zoom) / 2;
			} else {
				x = (getWidth() - image.getWidth(this) * zoom) / 2;
			}
			break;
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		// Försök inte rita bilder som inte finns
		if (image != null) {
			g2d.drawImage(image, (int) x, (int) y,
					(int) (image.getWidth(this) * zoom),
					(int) (image.getHeight(this) * zoom), null);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoomIn(e.getX(), e.getY(), -e.getUnitsToScroll());
	}

	long lastClicked[] = { 0, 0, 0 };

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	boolean isDragging = false;

	@Override
	public void mousePressed(MouseEvent e) {
		lastx = e.getX();
		lasty = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() <= lastClicked.length && e.getButton() > 0) {
			if ((lastClicked[e.getButton() - 1] + 1000) > System
					.currentTimeMillis()) {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					zoomIn(e.getX(), e.getY(), fastZoom);
					break;
				case MouseEvent.BUTTON2:
					setZoomToTactic(tactic);
					break;
				case MouseEvent.BUTTON3:
					reset();
					break;
				default:
					break;
				}
			}
			lastClicked[e.getButton() - 1] = System.currentTimeMillis();
		}
	}

	private void zoomIn(int x, int y, double unitsToScroll) {
		userStartedInteracting = true;
		double centerx = Math.ceil((x - this.x) / zoom);// image.getWidth(this)/2;
		double centery = Math.ceil((y - this.y) / zoom);// image.getHeight(this)/2;
		zoom += zoom * unitsToScroll * scrollSpeed;
		if (zoom < 0.01)
			zoom = 0.01;
		int centerPointX = x;
		int centerPointY = y;

		int newx = (int) (centerPointX - centerx * zoom);
		int newy = (int) (centerPointY - centery * zoom);
		this.x = newx;
		this.y = newy;
		revalidate();
		repaint();
	}

	public void reset() {
		zoom = 1;
		x = (getWidth() - image.getWidth(this)) / 2;
		y = (getHeight() - image.getHeight(this)) / 2;

		revalidate();
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		userStartedInteracting = true;
		lastClicked[0] = 0;
		lastClicked[2] = 0;
		x -= (lastx - e.getX());
		y -= (lasty - e.getY());
		lastx = e.getX();
		lasty = e.getY();
		revalidate();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void ancestorMoved(HierarchyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ancestorResized(HierarchyEvent e) {
		if (!userStartedInteracting) {
			setZoomToTactic(tactic);
		}
	}

}
