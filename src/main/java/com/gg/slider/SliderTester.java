package com.gg.slider;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;

import com.gg.slider.SideBar.SideBarMode;


public class SliderTester extends JFrame{
	
	public SliderTester(){
		
		
	}

	
	public static void main (String[] args) {
		
		SliderTester t = new SliderTester();
		
		
		t.setSize(500, 600);
		t.add(t.getPanel());
		t.setVisible(true);
		
	}
	
	
	public static JPanel getPanel() {
		
		JPanel p = new JPanel(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel listPanel = new JPanel(new BorderLayout());
		
		SideBar sideBar = new SideBar(SideBar.SideBarMode.TOP_LEVEL, true, 300, false);

		JTree tree = new JTree();
		
		Icon i1 = new ImageIcon("src/main/resource/img/Calendar/Calendar_24x24.png");
		
		SidebarSection ss1 = new SidebarSection(sideBar, "Calendars", "JTree", tree, i1);
		sideBar.addSection(ss1);

		
		DefaultTableModel tModel = new DefaultTableModel(4,5);
		tModel.setValueAt("Einstein", 0, 0);
		tModel.setValueAt("Plato", 1, 0);
		tModel.setValueAt("Nietsche", 2, 0);
		
		JTable table = new JTable(tModel);

		SideBar innerSideBar = new SideBar(SideBarMode.INNER_LEVEL, true, -1, false);
		
		Icon i2 = new ImageIcon("src/main/resource/img/Mail/Mail_24x24.png");

		Icon smallMailIcon = new ImageIcon("src/main/resource/img/Mail/Mail_16x16.png");

		innerSideBar.add(new SidebarSection(innerSideBar, "American Partners", "JTree", getInner1(), smallMailIcon));
		innerSideBar.add(new SidebarSection(innerSideBar, "Internal", "JTree", getInner2(), smallMailIcon));
		innerSideBar.add(new SidebarSection(innerSideBar, "Promotions", "JTree", getInner3(), smallMailIcon));
		
		SidebarSection ss2 = new SidebarSection(sideBar, "Mail Groups", "JTable", innerSideBar, i2);
		sideBar.addSection(ss2);
		
		//test

		DefaultListModel<String> model = new DefaultListModel<String>();
		model.add(0, "Bill Gates");
		model.add(1, "Steven Spielberg");
		model.add(2, "Donald Trump");
		model.add(3, "Steve Jobs");		
		 
		JList<String> list = new JList<String>();
		
		list.setModel(model);

		Icon i3 = new ImageIcon("src/main/resource/img/Globe/Globe_24x24.png");

		SidebarSection ss3 = new SidebarSection(sideBar, "Logistics Partners", "JList", list, i3);
		sideBar.addSection(ss3);
		
		listPanel.add(sideBar, BorderLayout.WEST);
		listPanel.add(new JLabel("<html><body><h1>central panel</html>", JLabel.CENTER));
		
		tabbedPane.add("Slider Bar", listPanel);
		
		p.add(tabbedPane);
		return p;
	}


	private static JComponent getInner2() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		model.add(0, "Bill Gates");
		model.add(1, "Steven Spielberg");
		model.add(2, "Donald Trump");
		model.add(3, "Steve Jobs");		
		 
		JList<String> list = new JList<String>();
		
		list.setModel(model);
		return list;
	}
	
	private static JComponent getInner1() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		model.add(0, "Bill Gates");
		model.add(1, "Steven Spielberg");
		 
		JList<String> list = new JList<String>();
		
		list.setModel(model);
		return list;
	}

	private static JComponent getInner3() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		model.add(0, "Steve Jobs");		
		 
		JList<String> list = new JList<String>();
		
		list.setModel(model);
		return list;
	}
}
