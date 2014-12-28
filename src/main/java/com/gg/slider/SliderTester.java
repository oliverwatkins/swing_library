package com.gg.slider;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
		
		SideBar sideBar = new SideBar(SideBar.SideBarMode.MAXIMISE_CONTENT, false, 300, false, false);

		JTree tree = new JTree();
		
		Icon i1 = new ImageIcon("src/main/resource/img/Calendar/Calendar_24x24.png");
		
//		SidebarSectionModel m1 = new SidebarSectionModel("Colours", tree, "JTree");
		SidebarSection ss1 = new SidebarSection(sideBar, "Calendars", "JTree", tree, i1);
		sideBar.addSection(ss1);

		
		DefaultTableModel tModel = new DefaultTableModel(4,5);
		tModel.setValueAt("Einstein", 0, 0);
		tModel.setValueAt("Plato", 1, 0);
		tModel.setValueAt("Nietsche", 2, 0);
		
		JTable table = new JTable(tModel);

//		SideBar innerSideBar = new SideBar(SideBarMode.MAXIMISE_CONTENT, false);
		SideBar innerSideBar = new SideBar(SideBarMode.MAXIMISE_CONTENT, true, -1, false, true);
		
		
		
		innerSideBar.add(new SidebarSection(innerSideBar, "Calendars", "JTree", new JLabel("XX"), i1));
		innerSideBar.add(new SidebarSection(innerSideBar, "Calendars", "JTree", new JLabel("XXX"), i1));
		innerSideBar.add(new SidebarSection(innerSideBar, "Calendars", "JTree", new JTree(), i1));
		
		innerSideBar.setBorder(BorderFactory.createLineBorder(Color.RED));
		
//		innerSideBar.add(new JLabel("asdf"));
		

		Icon i2 = new ImageIcon("src/main/resource/img/Mail/Mail_24x24.png");

//		SidebarSectionModel m2 = new SidebarSectionModel("Thinkers", table, "JTable");
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

		
//		SidebarSectionModel m3 = new SidebarSectionModel("Dealers", list, "JList");
		SidebarSection ss3 = new SidebarSection(sideBar, "Logistics Partners", "JList", list, i3);
		sideBar.addSection(ss3);
		
		listPanel.add(sideBar, BorderLayout.WEST);
		listPanel.add(new JLabel("<html><body><h1>central panel</html>", JLabel.CENTER));
		
		
		tabbedPane.add("Slider Bar", listPanel);
		
		p.add(tabbedPane);
		return p;
	}
}
