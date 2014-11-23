package com.gg.slider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

public class SidebarSection extends JPanel {

	public static final int minComponentHeight = 60;
	public static final int minComponentWidth = 50;
	
	private JPanel titlePanel = new JPanel();
	private SideBar sideBarOwner;
	private JComponent contentPane; //sidebar section's content
	
	public static int EXPAND_FULLY;
	public static int EXPAND_MINIMALLY;
	
	private ArrowPanel arrowPanel;

	/**
	 * Construct a new sidebar section with the specified owner and model. 
	 * 
	 * @param owner - SideBar
	 * @param model
	 */
	public SidebarSection(SideBar owner, SidebarSectionModel model) {
		
		titlePanel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				
				if (!isExpanded())
					expand();
				else
					collapse();
			}
		});

		//absolute layout
		setLayout(null);
		
		add(titlePanel);

		JLabel l1 = new JLabel(model.getText());
		JLabel l2 = new JLabel(model.getSupplementaryText());

		l1.setText(model.getText());
		l2.setText(model.getSupplementaryText());
		l2.setFont(new Font("Ariel", Font.ITALIC, 12));

		getTitlePanel().setLayout(new FlowLayout(FlowLayout.LEADING));
		getTitlePanel().setPreferredSize(
				new Dimension(this.getPreferredSize().width, SidebarSection.minComponentHeight));
		getTitlePanel().setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		arrowPanel = new ArrowPanel(BasicArrowButton.EAST);
		
		arrowPanel.setPreferredSize(new Dimension(SidebarSection.minComponentWidth,SidebarSection.minComponentHeight-10));
		
		//add into tab panel the arrow and labels.
		getTitlePanel().add(arrowPanel);
		getTitlePanel().add(l1);
		getTitlePanel().add(l2);

		contentPane = model.getSectionContent();
		
		//centering
		if (model.getSectionContent() != null){
			add(model.getSectionContent());//, BorderLayout.CENTER);
		}
		revalidate();
		
		setOwner(owner);
	}
	
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		
		titlePanel.setBounds(0, 0, w, SidebarSection.minComponentHeight);
		
		if (contentPane.isVisible())
			contentPane.setBounds(0, SidebarSection.minComponentHeight, w, contentPane.getPreferredSize().height);
		else
			contentPane.setBounds(0, 0, 0, 0);
	}
	
	public void setOwner(SideBar newOwner) {
		if (newOwner == sideBarOwner)
			return;

		sideBarOwner = newOwner; // must be before newOwner.addSection() to avoid
							// infinite recursion
		if (newOwner != null)
			newOwner.addSection(this); // add to new Side-bar
	}

	public void expand() {
		
		arrowPanel.changeDirection(BasicArrowButton.SOUTH);
		arrowPanel.updateUI();
		
		if (this != sideBarOwner.getCurrentSection()){

			if ((sideBarOwner.getCurrentSection() != null) && (this != null))
				sideBarOwner.getCurrentSection().collapse();
			if (this != null) {
				sideBarOwner.setCurrentSection(this); // must be called before section.expand()
											// to avoid recursion
				this.expand();
			}
		}
						
		if (sideBarOwner.getMode().equals(SideBar.SideBarMode.MAXIMISE_CONTENT)){
			setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		}else if (sideBarOwner.getMode().equals(SideBar.SideBarMode.MINIMISE_CONTENT)){
			setMaximumSize(new Dimension(Integer.MAX_VALUE,
					SidebarSection.minComponentHeight+contentPane.getPreferredSize().height));//Integer.MAX_VALUE, Integer.MAX_VALUE));
		}
		getContentPane().setVisible(true);
		revalidate();
	}
	
	public void collapse() {
		arrowPanel.changeDirection(BasicArrowButton.EAST);
		arrowPanel.updateUI();

		setMaximumSize(new Dimension(Integer.MAX_VALUE, getTitlePanel().getPreferredSize().height));
		getContentPane().setVisible(false);
		revalidate();
	}

	public JComponent getContentPane() {
		return contentPane;
	}
	protected JPanel getTitlePanel() {
		return titlePanel;
	}
	public Dimension getMinimumSize(){
		return new Dimension(60,SidebarSection.minComponentHeight);
	}
	public Dimension getPreferredSize(){
		return new Dimension(60,SidebarSection.minComponentHeight);
	}
	public boolean isExpanded() {
		return getContentPane().isVisible() && sideBarOwner.isCurrentSection(this);
	}
}