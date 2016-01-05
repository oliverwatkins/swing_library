package com.gg.slider;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicArrowButton;

import com.gg.slider.SideBar.SideBarMode;

/**
 * Panel that contains both the title/header part and the content part.
 * 
 * @author oliver
 *
 */

public class SidebarSection extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public int minComponentHeight = 40;
	public int minComponentWidth = 350;
	
	public JComponent titlePanel;
	
	private SideBar sideBarOwner;
	
	public JComponent contentPane; //sidebar section's content
	
	private ArrowPanel arrowPanel;
	
	private int calculatedHeight;

	public SidebarSection(SideBar owner, 
			String text, 
			JComponent component, Icon icon) {
		this(owner, new JLabel(text), component, icon);
	}
	
	/**
	 * Construct a new sidebar section with the specified owner and model. 
	 * 
	 * @param owner - SideBar
	 * @param model
	 */
	public SidebarSection(SideBar owner, 
			JComponent titleComponent, 
			JComponent component, Icon icon) {
		
		if (owner.thisMode == SideBar.SideBarMode.INNER_LEVEL)
			minComponentHeight = 30;
		else
			minComponentHeight = 40;
		
		
		this.contentPane = component;
		
		sideBarOwner = owner;
		
		titlePanel = new JPanel();
		titlePanel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				
				if (SidebarSection.this != sideBarOwner.getCurrentSection()) {
					if (sideBarOwner.getCurrentSection() != null)
						sideBarOwner.getCurrentSection().collapse(true);
					
					expand(); //expand this!
				}
				else {
					collapse(true);
				}
			}
		});

		//absolute layout
//		setLayout(null);
		setLayout(new BorderLayout());
		
		add(titlePanel, BorderLayout.NORTH);

		titlePanel.setLayout(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(this.getPreferredSize().width, minComponentHeight));
		titlePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		arrowPanel = new ArrowPanel(BasicArrowButton.EAST);
		arrowPanel.setPreferredSize(new Dimension(40, 40));
		
		
		if (sideBarOwner.showArrow)
			//add into tab panel the arrow and labels.
			titlePanel.add(arrowPanel, BorderLayout.EAST);
		
		
		titlePanel.add(new JLabel(icon), BorderLayout.WEST);
		
		titleComponent.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(2, 8, 2, 2), titleComponent.getBorder()));
		titlePanel.add(titleComponent);

//		this.setMinimumSize(new Dimension(minComponentWidth, minComponentHeight));
//		component.setPreferredSize(new Dimension(0,0));
		
		add(component, BorderLayout.CENTER);
		
		revalidate();
	}

	
	
	public void expand() {
		sideBarOwner.setCurrentSection(this);
		
		arrowPanel.changeDirection(BasicArrowButton.SOUTH);
		arrowPanel.updateUI();
		
		calculatedHeight = -1;
		calculatedHeight = sideBarOwner.getSize().height;
		
		System.out.println("sidebarSection.contentPane.getHeight() " + contentPane.getHeight());
		
		if (this.sideBarOwner.animate) {
			SidebarAnimation anim = new SidebarAnimation(this, 200); // ANIMATION BIT
			
			anim.setStartValue(minComponentHeight);
			anim.setEndValue(calculatedHeight);
			anim.start();
		} else {
			if (sideBarOwner.thisMode == SideBarMode.INNER_LEVEL) {
				calculatedHeight = 1000;
				Dimension d = new Dimension(Integer.MAX_VALUE, calculatedHeight);
				setMaximumSize(d);
				sideBarOwner.setPreferredSize(d);
				contentPane.setVisible(true);
				revalidate();
			} else {
				setMaximumSize(new Dimension(Integer.MAX_VALUE, calculatedHeight));
				contentPane.setVisible(true);
				revalidate();
			}
//			printDimensions();
		}
	}
	
	
	
	public void collapse(boolean animate) {
		// remove reference
		if (sideBarOwner.getCurrentSection() == SidebarSection.this)
			sideBarOwner.setCurrentSection(null);
		
		arrowPanel.changeDirection(BasicArrowButton.EAST);
		arrowPanel.updateUI();
		
		if (animate && this.sideBarOwner.animate) {
			SidebarAnimation anim = new SidebarAnimation(this, 200); // ANIMATION BIT
			anim.setStartValue(calculatedHeight);
			anim.setEndValue(minComponentHeight);
			anim.start();
		} else {
			if (sideBarOwner.thisMode == SideBarMode.INNER_LEVEL) {
				setMaximumSize(new Dimension(Integer.MAX_VALUE, titlePanel.getPreferredSize().height));
				contentPane.setVisible(false);
				revalidate();
//				printDimensions();
			} else {
				setMaximumSize(new Dimension(Integer.MAX_VALUE, titlePanel.getPreferredSize().height));
				contentPane.setVisible(false);
				revalidate();
//				printDimensions();
			}
		}
	}
	
	@Override
	public Dimension getMinimumSize(){
		return new Dimension(minComponentWidth,minComponentHeight);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(minComponentWidth,minComponentHeight);
	}
	
	public void printDimensions() {
		System.out.println("-- DIMENSIONS -- ");
		
		System.out.println("sideBar height                     " + this.sideBarOwner.getSize().height);

		System.out.println("sideBarSection height              " + getSize().height);
		System.out.println("sideBarSection titlePanel height   " + titlePanel.getSize().height);
		System.out.println("sideBarSection.contentPane height  " + contentPane.getSize().height);
	}
	
}