package com.gg.slider;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;


/**
 * Code is from BasicArrowButton written by David Kloba.
 * 
 * Ripped out of BasicArrowButton from PLAF. Chopped out the button stuff. Now inside a Panel
 * 
 * @author David Kloba
 */

public class ArrowPanel extends JPanel implements SwingConstants{
	
	private static final long serialVersionUID = 1L;
	
	protected int direction;
	
	private Color shadow;
	private Color darkShadow;
	private Color highlight;
	
	public ArrowPanel(int direction) {
		this(direction, UIManager.getColor("control"), 
				UIManager.getColor("controlShadow"),
				UIManager.getColor("controlDkShadow"),
				UIManager.getColor("controlLtHighlight"));
	}
	
	
	
	public ArrowPanel(int direction, Color background, Color shadow,
			Color darkShadow, Color highlight) {
		super();
		setRequestFocusEnabled(false);
		setDirection(direction);
		setBackground(background);
		this.shadow = shadow;
		this.darkShadow = darkShadow;
		this.highlight = highlight;
	}



	/**
	 * Returns the direction of the arrow.
	 * 
	 * @param direction
	 *            the direction of the arrow; one of {@code
	 *            SwingConstants.NORTH}, {@code SwingConstants.SOUTH}, {@code
	 *            SwingConstants.EAST} or {@code SwingConstants.WEST}
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Sets the direction of the arrow.
	 * 
	 * @param direction
	 *            the direction of the arrow; one of of {@code
	 *            SwingConstants.NORTH}, {@code SwingConstants.SOUTH}, {@code
	 *            SwingConstants.EAST} or {@code SwingConstants.WEST}
	 */
	public void setDirection(int dir) {
		direction = dir;
	}
	
	@Override
	public void paint(Graphics g) {
		Color origColor;
		int w, h, size;

		w = getSize().width;
		h = getSize().height;
		origColor = g.getColor();

		g.setColor(getBackground());
		g.fillRect(1, 1, w - 2, h - 2);

		// If there's no room to draw arrow, bail
		if (h < 5 || w < 5) {
			g.setColor(origColor);
			return;
		}

		// Draw the arrow
		size = Math.min((h - 4) / 3, (w - 4) / 3);
		size = Math.max(size, 2);
		paintTriangle(g, (w - size) / 2, (h - size) / 2, size, direction, false);

		g.setColor(origColor);
	}

	/**
	 * Paints a triangle.
	 */
	public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) {
		Color oldColor = g.getColor();
		int mid, i, j;

		j = 0;
		size = Math.max(size, 2);
		mid = (size / 2) - 1;

		g.translate(x, y);
		if (isEnabled)
			g.setColor(darkShadow);
		else
			g.setColor(shadow);

		switch (direction) {
		case NORTH:
			for (i = 0; i < size; i++) {
				g.drawLine(mid - i, i, mid + i, i);
			}
			if (!isEnabled) {
				g.setColor(highlight);
				g.drawLine(mid - i + 2, i, mid + i, i);
			}
			break;
		case SOUTH:
			if (!isEnabled) {
				g.translate(1, 1);
				g.setColor(highlight);
				for (i = size - 1; i >= 0; i--) {
					g.drawLine(mid - i, j, mid + i, j);
					j++;
				}
				g.translate(-1, -1);
				g.setColor(shadow);
			}

			j = 0;
			for (i = size - 1; i >= 0; i--) {
				g.drawLine(mid - i, j, mid + i, j);
				j++;
			}
			break;
		case WEST:
			for (i = 0; i < size; i++) {
				g.drawLine(i, mid - i, i, mid + i);
			}
			if (!isEnabled) {
				g.setColor(highlight);
				g.drawLine(i, mid - i + 2, i, mid + i);
			}
			break;
		case EAST:
			if (!isEnabled) {
				g.translate(1, 1);
				g.setColor(highlight);
				for (i = size - 1; i >= 0; i--) {
					g.drawLine(j, mid - i, j, mid + i);
					j++;
				}
				g.translate(-1, -1);
				g.setColor(shadow);
			}

			j = 0;
			for (i = size - 1; i >= 0; i--) {
				g.drawLine(j, mid - i, j, mid + i);
				j++;
			}
			break;
		}
		g.translate(-x, -y);
		g.setColor(oldColor);
	}

	public void changeDirection(int d) {
		setDirection(d);
	}

}
