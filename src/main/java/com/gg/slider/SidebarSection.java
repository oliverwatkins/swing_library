package com.gg.slider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * Panel that contains both the title/header part and the content part.
 * 
 * @author oliver
 *
 */

public class SidebarSection extends JPanel {

	public static final int minComponentHeight = 60;
	public static final int minComponentWidth = 50;
	
	private JPanel titlePanel = new JPanel();
	
	private SideBar sideBarOwner;
	
	public JComponent contentPane; //sidebar section's content
	
	private ArrowPanel arrowPanel;
	
	private int calculatedHeight;

	/**
	 * Construct a new sidebar section with the specified owner and model. 
	 * 
	 * @param owner - SideBar
	 * @param model
	 */
	public SidebarSection(SideBar owner, String text, String supplementaryText, JComponent component) {
		
		this.contentPane = component;
		
		sideBarOwner = owner;
		
		titlePanel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				
				if (!isExpanded())
					expand();
				else
					collapse(true);
			}
		});

		//absolute layout
		setLayout(null);
		
		add(titlePanel);

		JLabel l1 = new JLabel(text);
		JLabel l2 = new JLabel(supplementaryText);
		l2.setFont(new Font("Ariel", Font.ITALIC, 12));

		titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		titlePanel.setPreferredSize(new Dimension(this.getPreferredSize().width, SidebarSection.minComponentHeight));
		titlePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		arrowPanel = new ArrowPanel(BasicArrowButton.EAST);
		arrowPanel.setPreferredSize(new Dimension(SidebarSection.minComponentWidth, SidebarSection.minComponentHeight-10));
		
		//add into tab panel the arrow and labels.
		titlePanel.add(arrowPanel);
		titlePanel.add(l1);
		titlePanel.add(l2);

//		component.setPreferredSize(new Dimension(0,0));
		
		add(component);//, BorderLayout.CENTER);
		
		revalidate();
		
		
	}
	
	/**
	 * Overrides super.
	 */
	
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		
		titlePanel.setBounds(0, 0, w, SidebarSection.minComponentHeight);
		
		if (contentPane.isVisible())
			contentPane.setBounds(0, SidebarSection.minComponentHeight, w, contentPane.getPreferredSize().height);
		else
			contentPane.setBounds(0, 0, 0, 0);
	}
	


	public void expand() {
		
		
		System.out.println("Expanding ..!");
		
		arrowPanel.changeDirection(BasicArrowButton.SOUTH);
		arrowPanel.updateUI();
		
		if (this != sideBarOwner.getCurrentSection()){

			if ((sideBarOwner.getCurrentSection() != null) && (this != null))
				sideBarOwner.getCurrentSection().collapse(true);
			if (this != null) {
				sideBarOwner.setCurrentSection(this); // must be called before section.expand()
											// to avoid recursion
				this.expand();
			}
		}
		
		calculatedHeight = -1;
		

						
		if (sideBarOwner.getMode().equals(SideBar.SideBarMode.MAXIMISE_CONTENT)){
			
			calculatedHeight = Integer.MAX_VALUE;
			
		}else if (sideBarOwner.getMode().equals(SideBar.SideBarMode.MINIMISE_CONTENT)){
			
			//component minimium height
			int minHeight = SidebarSection.minComponentHeight + contentPane.getPreferredSize().height;

			calculatedHeight = minHeight;
		}
		
		
		/**
		 * ANIMATION BIT
		 */
		
		SidebarAnimation anim = new SidebarAnimation(this, 1000);
		
		anim.setStartValue(SidebarSection.minComponentHeight);
		anim.setEndValue(calculatedHeight);
		anim.start();

		/**
		 * OLD BIT
		 */
		
//		
//		setMaximumSize(new Dimension(Integer.MAX_VALUE, calculatedHeight));
//		contentPane.setVisible(true);
//		revalidate();
		
		
	}
	
	public void collapse(boolean animate) {
		
		System.out.println("Collapsing ..!");

		
		arrowPanel.changeDirection(BasicArrowButton.EAST);
		arrowPanel.updateUI();

		
		/**
		 * ANIMATION BIT
		 */
		SidebarAnimation anim = new SidebarAnimation(this, 1000);
		
		if (animate) {
			anim.setStartValue(calculatedHeight);
			anim.setEndValue(SidebarSection.minComponentHeight);
			anim.start();
		}else {
			setMaximumSize(new Dimension(Integer.MAX_VALUE, titlePanel.getPreferredSize().height));
			contentPane.setVisible(false);
			revalidate();
		}
		
		
		/**
		 * OLD BIT
		 */
		
//		setMaximumSize(new Dimension(Integer.MAX_VALUE, getTitlePanel().getPreferredSize().height));
//		getContentPane().setVisible(false);
//		revalidate();
	}

//	public JComponent getContentPane() {
//		return contentPane;
//	}
//	protected JPanel getTitlePanel() {
//		return titlePanel;
//	}
	public Dimension getMinimumSize(){
		return new Dimension(60,SidebarSection.minComponentHeight);
	}
	public Dimension getPreferredSize(){
		return new Dimension(60,SidebarSection.minComponentHeight);
	}
	public boolean isExpanded() {
		return contentPane.isVisible() && sideBarOwner.isCurrentSection(this);
	}
}