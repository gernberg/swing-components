package se.ernberg.components.zoomableimage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class ZoomableImage extends JComponent implements MouseWheelListener,
		MouseMotionListener, MouseListener, HierarchyBoundsListener {
	Image image, resizedImage;
	static final int FIT_PANE = 1;
	static final int FILL_PANE = 2;
	static final int FULL_SCALE = 3;
	private int tactic = FULL_SCALE;
	private boolean userStartedInteracting = false;
	double zoom = 1;
	double x, y = 0;
	double scrollSpeed = 0.05;

	public ZoomableImage(Image image) {
		this.image = image;
		this.resizedImage = image;
	}

	public ZoomableImage(String filename, int tactic) {
		try {
			image = ImageIO.read(new File(filename));
			this.tactic = tactic;
			addMouseWheelListener(this);
			addMouseMotionListener(this);
			addMouseListener(this);
			addHierarchyBoundsListener(this);
			setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Kunde inte ladda filen");
		}
	}

	private void setZoomToTactic(int tactic) {
		x = 0;
		y = 0;
		switch (tactic) {
		case FIT_PANE:
			zoom = (getHeight()) / ((float) image.getHeight(this));
			if(getWidth()<(zoom*image.getWidth(this))){
				zoom = (getWidth()) / ((float) image.getWidth(this));
				y = (getHeight() - image.getHeight(this)*zoom)/2;
			}else{
				x = (getWidth() - image.getWidth(this)*zoom)/2;
				
			}
			break;
		case FILL_PANE:
			zoom = (getHeight()) / ((float) image.getHeight(this));
			if(getWidth()>(zoom*image.getWidth(this))){
				zoom = (getWidth()) / ((float) image.getWidth(this));
				y = (getHeight() - image.getHeight(this)*zoom)/2;
			}else{
				x = (getWidth() - image.getWidth(this)*zoom)/2;
			}
			break;
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
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

	long lastClicked = 0;

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	boolean isDragging = false;

	@Override
	public void mousePressed(MouseEvent e) {
		lastx = e.getX();
		lasty = e.getY();
	}

	int lastx, lasty = 0;

	@Override
	public void mouseReleased(MouseEvent e) {
		if ((lastClicked + 1000) > System.currentTimeMillis()) {
			int unitsToScroll = 0;
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				unitsToScroll = 1;
				break;
			case MouseEvent.BUTTON3:
				unitsToScroll = -1;
				break;
			default:
				break;
			}
			zoomIn(e.getX(), e.getY(), (int) (unitsToScroll / scrollSpeed));
		}
		lastClicked = System.currentTimeMillis();
	}

	private void zoomIn(int x, int y, int unitsToScroll) {
		userStartedInteracting = true;
		double centerx = Math.ceil((x - this.x) / zoom);// image.getWidth(this)/2;
		double centery = Math.ceil((y - this.y) / zoom);// image.getHeight(this)/2;
		zoom += zoom * unitsToScroll * scrollSpeed;
		if(zoom<0.01)
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
		x = 0;
		y = 0;
		zoom = 1;
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
		lastClicked = 0;
		x -= (lastx - e.getX());
		y -= (lasty - e.getY());
		lastx = e.getX();
		lasty = e.getY();
		System.out.println(x + ":" + y);
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
