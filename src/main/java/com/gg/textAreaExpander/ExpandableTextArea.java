//package com.gg.textAreaExpander;
//
//import java.awt.Cursor;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Point;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//
//
//public class ExpandableTextArea extends JTextArea implements MouseMotionListener, MouseListener{
//	
//
//	private boolean mousePressedInCorner = false;
//	private Point mousePressedPoint = null;
//	private Dimension sizeAtPress = null;
//	private Dimension newSize = null;
//	
//	public ExpandableTextArea(int i, int j) {
//		
//		super(i,j);
//		
//		this.addMouseListener(this);
//		this.addMouseMotionListener(this);
//	}
//
//	public static void main(String[] args){
//		JFrame frame = new JFrame();
//		
//		frame.setSize(500, 300);
//		JPanel p = new JPanel();
//		
//		frame.getContentPane().add(p);
//		
//		
//		p.setLayout(new GridBagLayout());
//		
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.anchor = GridBagConstraints.WEST;
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		p.add(new JLabel("Name : "), gbc);
//
//		gbc.gridx = 1;
//		gbc.gridy = 0;
//		p.add(new JTextField(10), gbc);
//
//
//		gbc.gridx = 0;
//		gbc.gridy = 1;
//		p.add(new JLabel("Date of Birth : "), gbc);
//
//		gbc.gridx = 1;
//		gbc.gridy = 1;
//		p.add(new JTextField(10), gbc);
//
//		gbc.gridx = 0;
//		gbc.gridy = 2;
//		p.add(new JLabel("Experience : "), gbc);
//		
//		gbc.gridx = 0;
//		gbc.gridy = 3;
//		gbc.gridwidth = 2;
//		p.add(new ExpandableTextArea(10,10), gbc);
//
//		gbc.gridx = 0;
//		gbc.gridy = 4;
//		gbc.gridwidth = 1;
//		p.add(new JLabel("Phone Number : "), gbc);
//
//		gbc.gridx = 1;
//		gbc.gridy = 4;
//		p.add(new JTextField(10), gbc);
//		
//		
////		p.add(new JButton("hi"));
////		p.add(new JButton("there"));
////		p.add(new ExpandableTextArea(10,10));
////		p.add(new JButton("hi"));
////		p.add(new JButton("there"));
////		p.add(new JScrollPane(new ExpandableTextArea(10,10)));
//		
//		frame.setVisible(true);
//	}
//	
//	@Override
//	protected void paintComponent(Graphics arg0) {
//
//		super.paintComponent(arg0);
//		
//		Graphics2D g = (Graphics2D)arg0;
//		
//		int h = this.getSize().height;
//		int w = this.getSize().width;
//		
//		int[] xs = {w,w,w-10};
//		int[] ys = {h-10,h,h};
//		
//		g.fillPolygon(xs, ys, 3);
//	}
//
//	
//	@Override
//	public void mouseDragged(MouseEvent e) {
//		Point p = e.getPoint();
//		System.out.println("mousePressedInCorner = " + mousePressedInCorner);
//		if (mousePressedInCorner){
//			
//			int xDiff = (mousePressedPoint.x - p.x);
//			int yDiff = (mousePressedPoint.y - p.y);
//			
//			System.out.println("X need to resize by : " + xDiff);
//			System.out.println("Y need to resize by : " + yDiff);
//			
//			newSize = new Dimension(this.sizeAtPress.width - xDiff, this.sizeAtPress.height - yDiff);
//			
//			this.setSize(newSize);
//			this.setPreferredSize(newSize);
//			this.setMinimumSize(newSize);
//			this.setMaximumSize(newSize);
//			this.repaint();
//			this.getParent().validate();
//		}
//	}
//
//	@Override
//	public void mouseMoved(MouseEvent e) {
//		Point p = e.getPoint();
//		System.out.println("mousePressedInCorner = " + mousePressedInCorner);
//		
//		if ((p.x > getSize().width - 10) && ((p.y > getSize().height - 10))){
//			System.out.println("in the zone");
//			this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
//		}else{
//			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//		}
//	}
//	
//	public void mouseClicked(MouseEvent e) {
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//	}
//	
//	@Override
//	public void mousePressed(MouseEvent e) {
//		Point p = e.getPoint();
//		System.out.println("mouse clicked");
//		if ((p.x > getSize().width - 10) && ((p.y > getSize().height - 10))){
//			System.out.println("in the zone");
//			mousePressedInCorner = true;
//			mousePressedPoint = p;
//			sizeAtPress = this.getSize();
//		}else{
//		}
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		mousePressedInCorner = false;
//		mousePressedPoint = null;
//	}
//}
