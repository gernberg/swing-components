package se.ernberg.components.zoomableimage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
		MouseMotionListener, MouseListener {
	Image image, resizedImage;

	public ZoomableImage(Image image) {
		this.image = image;
		this.resizedImage = image;
	}

	public ZoomableImage(String filename) {
		try {
			image = ImageIO.read(new File(filename));
			setPreferredSize(new Dimension(200, 600));
			addMouseWheelListener(this);
			addMouseMotionListener(this);
			addMouseListener(this);
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Kunde inte ladda filen");
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

	double zoom = 1;
	double x, y = 0;
	double scrollSpeed = 0.005;

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

		double centerx = Math.ceil((e.getX() - x) / zoom);// image.getWidth(this)/2;
		double centery = Math.ceil((e.getY() - y) / zoom);// image.getHeight(this)/2;
		zoom -= e.getUnitsToScroll() * scrollSpeed;
		if (zoom * image.getHeight(this) < getHeight()) {
			zoom = (getHeight() / ((double) image.getHeight(this)));
		}

		System.out.println("centerx: " + centerx);
		System.out.println("centery: " + centery);
		System.out.println("zoom: " + zoom);

		int centerPointX = e.getX();
		int centerPointY = e.getY();

		int newx = (int) (centerPointX - centerx * zoom);
		int newy = (int) (centerPointY - centery * zoom);
		x = newx;
		y = newy;
		revalidate();
		repaint();
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
		if (e.getButton() == MouseEvent.BUTTON1) {
			if ((lastClicked + 1000) > System.currentTimeMillis()) {
				reset();
			}
			lastClicked = System.currentTimeMillis();
		} else {
			long centerx = (int) ((e.getX() - x) / zoom);// image.getWidth(this)/2;
			long centery = (int) ((e.getY() - y) / zoom);// image.getHeight(this)/2;

			int centerPointX = e.getX();
			int centerPointY = e.getY();

			int newx = (int) (centerPointX - centerx);
			int newy = (int) (centerPointY - centery);
			x = newx;
			y = newy;
			revalidate();
			repaint();
		}
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

}
