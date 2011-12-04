package se.ernberg.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.omg.CORBA.RepositoryIdHelper;

/**
 * A simple refresh button that has a nice update icon. It's possible to add
 * Actions listener that listens to when the button is clicked.
 * 
 * Icon from: http://www.fatcow.com/free-icons
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class RefreshButton extends JComponent implements MouseListener,
		FocusListener, KeyListener {
	private ArrayList<ActionListener> actionListeners = new ArrayList<ActionListener>();
	private Image image;
	private boolean hasFocus = false;

	/**
	 * Creates the button and loads the icon, fallbacks to a 10x10 yellow square
	 * if the icon can't be loaded for any reason
	 */
	public RefreshButton() {
		super();
		try {
			URL imgURL = getClass().getResource("icons/refsresh.png");
			image = ImageIO.read(imgURL);
		} catch (Exception e) {
			System.err.println("Couldn't find file");
			image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			g.setColor(Color.YELLOW);
			g.fillRect(0, 0, image.getWidth(this), image.getHeight(this));
		}
		setPreferredSize(new Dimension(image.getWidth(this) + 2, getBaseline(0,
				0)));
		addMouseListener(this);
		setFocusable(true);
		addFocusListener(this);
		addKeyListener(this);
	}

	/**
	 * Adds an action listener that will be called when the button is clicked
	 * 
	 * @param actionListener
	 */
	public void addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}

	/**
	 * Paints the button and if focused - a border
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int y = (getHeight() - image.getHeight(this)) / 2;
		if (hasFocus) {
			g.setColor(Color.GRAY);
			g.drawRect(0, y, image.getHeight(this) + 1,
					image.getWidth(this) + 1);
		}
		g.drawImage(image, 1, y + 1, this);
	}
	/**
	 * If someone clicks directly on the element, then its considered an action
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		notifyActionListeners();
	}

	/**
	 * Notifies all action listeners
	 */
	private void notifyActionListeners() {
		for (int i = (actionListeners.size() - 1); i >= 0; i--) {
			actionListeners.get(i).actionPerformed(
					new ActionEvent(this, 0, "Click"));
		}
	}

	/**
	 * If the element is focused and someone presses ENTER or SPACE, that is
	 * considered as an action.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER
				|| e.getKeyCode() == KeyEvent.VK_SPACE) {
			notifyActionListeners();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * Handles focus
	 */
	@Override
	public void focusGained(FocusEvent e) {
		hasFocus = true;
		repaint();
	}
	/**
	 * Handles focus
	 */
	@Override
	public void focusLost(FocusEvent e) {
		hasFocus = false;
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}