package com.gg.slider;

import java.awt.Dimension;
import java.awt.Rectangle;

public class SidebarAnimation extends Animation{

	private SidebarSection sideBarSection;
	
	public SidebarAnimation(SidebarSection sidebarSection, int durationMs) {
		
		super(durationMs);
		
//		super(0, sidebarSection.contentPane.getHeight(), durationMs);
		
		
		
//		System.out.println("sidebarSection.getContentPane().getSize().height =" + sidebarSection.contentPane.getSize().height);
		
		this.sideBarSection = sidebarSection;
	}

	public void starting () {
		sideBarSection.contentPane.setVisible(true);
	}
	
	protected void render(int value) {
		
//		value= value+400;
		
		System.out.println("render with value : " + value);
		
		sideBarSection.setPreferredSize(new Dimension(Integer.MAX_VALUE, value));
		sideBarSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, value));
		sideBarSection.setSize(new Dimension(Integer.MAX_VALUE, value));
		
		sideBarSection.contentPane.setVisible(true);
//		sideBarSection.contentPane.setSize(3000,300);
		
		
//		sideBarSection.contentPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, value));

//		sideBarSection.validate();

		sideBarSection.revalidate();
		sideBarSection.getParent().revalidate();
		sideBarSection.getParent().getParent().revalidate();
		sideBarSection.repaint();
		
	}


	public void stopped () {
		
		sideBarSection.contentPane.setVisible(true);
		sideBarSection.revalidate();
		
		System.out.println("sideBarSection height " + sideBarSection.getSize().height);
		System.out.println("sideBarSection titlePanel height " + sideBarSection.titlePanel.getSize().height);
		System.out.println("sideBarSection.contentPane height " + sideBarSection.contentPane.getSize().height);
		
	}
	

}
