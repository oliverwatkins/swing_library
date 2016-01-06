package com.gg.slider;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ArrowPanel2 extends JPanel implements SwingConstants{
	
	private static final long serialVersionUID = 1L;
	
	protected int direction;
	
	public ArrowPanel2(int direction) {
		this.direction = direction;
	}

	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		
		Color origColor;
		int w, h;

		w = getSize().width;
		h = getSize().height;
		origColor = g2.getColor();

		g2.fillRect(1, 1, w - 2, h - 2);
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(10.0f)); 		
		g2.drawLine(1, h-2, w-2, h-2);
		g2.drawLine(w-2, 1, w-2, h-2);

		
		
		
//		// If there's no room to draw arrow, bail
//		if (h < 5 || w < 5) {
//			g.setColor(origColor);
//			return;
//		}
//
//		// Draw the arrow
//		size = Math.min((h - 4) / 3, (w - 4) / 3);
//		size = Math.max(size, 2);
//		paintTriangle(g, (w - size) / 2, (h - size) / 2, size, direction,
//				false);
//		
		

		g2.setColor(origColor);
	}


	public void changeDirection(int direction) {
		this.direction = direction;
	}

}
