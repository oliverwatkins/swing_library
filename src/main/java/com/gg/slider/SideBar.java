package com.gg.slider;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SideBar extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	/** box layout to contain side bar sections arranged vertically */
	private BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
	
	/** the currently expanded section */
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
		
		setPreferredSize(new Dimension(preferredWidth, getPreferredSize().height));
		
		setFocusable(false);
		
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	resize();
            }
        });
		
		revalidate();
	}
	
    public void resize() {
    	if (currentSection != null) {
        	currentSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getHeight()));
        	currentSection.contentPane.setVisible(true);
        	currentSection.revalidate();
    	}
	}
	
    public void addSection(SidebarSection newSection, boolean collapse) {
 		add(newSection);
 		if(collapse){
             newSection.collapse(false);
         }else{
             setCurrentSection(newSection);
         }
 	}
    
    public void addSection(SidebarSection newSection) {
    	addSection(newSection, true);
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
