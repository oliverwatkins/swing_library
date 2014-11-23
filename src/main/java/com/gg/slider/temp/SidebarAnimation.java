package com.gg.slider.temp;

import java.awt.Rectangle;

import com.gg.slider.SidebarSection;

public class SidebarAnimation extends Animation{

	private SidebarSection sideBarSection;
	
	public SidebarAnimation(SidebarSection sidebarSection) {
		super(0, sidebarSection.getContentPane().getPreferredSize().height, 300);
		
		System.out.println("sidebarSection.getContentPane().getSize().height =" + sidebarSection.getContentPane().getSize().height);
		
		this.sideBarSection = sidebarSection;
	}

	protected void render(int value) {
		
//		,0+value
		
		Rectangle r = new Rectangle(0,0,sideBarSection.getContentPane().getPreferredSize().width, 60+value);
		
		sideBarSection.getContentPane().setBounds(r);//setSize(new Dimension(sideBarSection.getContentPane().getPreferredSize().width,0+value));
		
//		sideBarSection.getContentPane().getParent().validate();
		
//		sideBarSection.getContentPane().setSize(new Dimension(sideBarSection.getContentPane().getPreferredSize().width,0+value));
		
		
		// TODO Auto-generated method stub
	}
	
	public void starting () {
		
		sideBarSection.getContentPane().setVisible(true);
//		searchRequestPanel.setVisible(true);
	}

	public void stopped () {
		
		sideBarSection.getContentPane().setVisible(true);
//		
		sideBarSection.revalidate();
		
//		searchRequestPanel.termsField.requestFocus();
	}

}
