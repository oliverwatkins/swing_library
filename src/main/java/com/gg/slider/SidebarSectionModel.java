package com.gg.slider;

import javax.swing.JComponent;

public class SidebarSectionModel {

	private String title;
	private JComponent sectionContent;
	private String supplementaryText;;

	public SidebarSectionModel(String title, 
			JComponent sectionContent, 
			String supplementaryText) {
		this.title = title;
		this.sectionContent = sectionContent;
		this.supplementaryText = supplementaryText;
	}

	public String getText() {
		return title;
	}

	public JComponent getSectionContent(){
		return sectionContent;
	}

	public String getSupplementaryText() {
		return supplementaryText;
	}
}