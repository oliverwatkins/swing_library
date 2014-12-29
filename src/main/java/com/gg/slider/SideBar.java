package com.gg.slider;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SideBar extends JPanel {
	
	//The preferred initial width of the side bar 
	private static int PREFERRED_WIDTH = 300;
	
	// box layout to contain side bar sections arranged vertically
	private BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
	
	// the currently expanded section 
	private SidebarSection currentSection = null;

	SideBarMode thisMode;

	boolean showArrow;

	boolean animate = false;
	
	public SideBar(SideBarMode mode, boolean showArrow, int preferredWidth, boolean animate) {

		this.showArrow = showArrow;
		this.thisMode = mode;
		this.animate = animate;
		
		
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		setLayout(boxLayout);
//		setMinimumSize(new Dimension(0, 0));
		if (preferredWidth != -1)
			setPreferredSize(new Dimension(preferredWidth, 1));
		else
			setPreferredSize(new Dimension(preferredWidth, 10));
		
		
		setFocusable(false);
		
		// collapse all sections
		// expand first section
		revalidate();
	}
	
	public void addSection(SidebarSection newSection) {
		add(newSection);
		
		newSection.collapse(false);
	}
	
	public boolean isCurrentExpandedSection(SidebarSection section) {
		return (section != null) && (currentSection != null)
				&& section.equals(currentSection);
	}

	public SideBarMode getMode() {
		return thisMode;
	}

	public SidebarSection getCurrentSection() {
		return currentSection;
	}

	public void setCurrentSection(SidebarSection section) {
		currentSection = section;
	}
	
	public enum SideBarMode {
		TOP_LEVEL, INNER_LEVEL;
	}

}
