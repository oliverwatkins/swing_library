package com.gg.slider;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SideBar extends JPanel {
	
	//The preferred initial width of the side bar 
	private static int PREFERRED_WIDTH = 200;
	
	// box layout to contain side bar sections arranged vertically
	private BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
	
	// the currently expanded section 
	private SidebarSection currentSection = null;

	private SideBarMode thisMode;
	
	public SideBar(SideBarMode mode) {
		
		this.thisMode = mode;
		
		setLayout(boxLayout);
		setMinimumSize(new Dimension(0, 0));
		setPreferredSize(new Dimension(PREFERRED_WIDTH, 1));
		setFocusable(false);
		
		// collapse all sections
		// expand first section
		revalidate();
	}
	
	public void addSection(SidebarSection newSection) {
		add(newSection);
		
		newSection.expand(); //why expand here?
	}
	
	public boolean isCurrentSection(SidebarSection section) {
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
}
