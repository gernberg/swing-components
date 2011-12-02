package se.ernberg.components;

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
	Image image;

	public ZoomableImage(Image image) {
		this.image = image;
	}

	public ZoomableImage(String filename) {
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Kunde inte ladda filen");
		}
		setPreferredSize(new Dimension(200, 200));
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		x = -500;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(zoom, zoom);
		g2d.translate(x, y);
		g2d.drawImage(image, 0, 0, null);
	}

	double zoom = 1;
	double x, y = 0;

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom -= e.getUnitsToScroll() / 100.0;

		x = -zoom*e.getX();
		//-image.getWidth(this)/2;
//		y = zoome.getY()-image.getHeight(this)/2;
		revalidate();
		repaint();
	}

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
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		x = e.getX()-image.getWidth(this)/2;
		System.out.println("Hj");
		revalidate();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x -= (lastx - e.getX())/zoom;
		y -= (lasty - e.getY())/zoom;
		lastx = e.getX();
		lasty = e.getY();
		System.out.println(lastx);
		revalidate();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		x = e.getX()-image.getWidth(this)/2;
//		y = e.getY()-image.getHeight(this)/2;
//		revalidate();
//		repaint();

	}
}
