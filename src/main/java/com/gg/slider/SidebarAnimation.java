package com.gg.slider;

import java.awt.Dimension;

public class SidebarAnimation extends Animation {
	
	private SidebarSection sideBarSection;
	
	public SidebarAnimation(SidebarSection sidebarSection, int durationMs) {
		super(durationMs);
		this.sideBarSection = sidebarSection;
	}

	@Override
	public void starting () {
		sideBarSection.contentPane.setVisible(true);
	}
	
	@Override
	protected void render(int value) {
//		System.out.println("render with value : " + value);
		sideBarSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, value));
		sideBarSection.contentPane.setVisible(true);
		sideBarSection.revalidate();
	}

	@Override
	public void stopped () {
		sideBarSection.contentPane.setVisible(true);
		sideBarSection.revalidate();
//		sideBarSection.printDimensions();
	}
	
}
