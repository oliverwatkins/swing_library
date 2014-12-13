package com.gg.slider;

import java.awt.Dimension;
import java.awt.Rectangle;

public class SidebarAnimation extends Animation{

	private SidebarSection sideBarSection;
	
	public SidebarAnimation(SidebarSection sidebarSection, int durationMs) {
		super(0, sidebarSection.contentPane.getHeight(), durationMs);
		
//		System.out.println("sidebarSection.getContentPane().getSize().height =" + sidebarSection.contentPane.getSize().height);
		
		this.sideBarSection = sidebarSection;
	}

	public void starting () {
		sideBarSection.contentPane.setVisible(true);
	}
	
	protected void render(int value) {
		
		sideBarSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, value));
		
		sideBarSection.contentPane.setVisible(true);
		
		sideBarSection.revalidate();
	}


	public void stopped () {
		
		sideBarSection.contentPane.setVisible(true);
		sideBarSection.revalidate();
	}

}
