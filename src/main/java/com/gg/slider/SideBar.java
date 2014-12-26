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

	private SideBarMode thisMode;

	boolean showArrow;

	public SideBar() {
		setPreferredSize(new Dimension(PREFERRED_WIDTH, 1));
		revalidate();
		
	}

	
	public SideBar(SideBarMode mode, boolean showArrow, int preferredWidth) {

		this.showArrow = showArrow;
		this.thisMode = mode;
		
		
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		setLayout(boxLayout);
//		setMinimumSize(new Dimension(0, 0));
		if (preferredWidth != -1)
			setPreferredSize(new Dimension(preferredWidth, 1));
		
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
		MAXIMISE_CONTENT, MINIMISE_CONTENT;
	}

	public void removeExpanded() {
		currentSection.collapse(true);
	}
}
