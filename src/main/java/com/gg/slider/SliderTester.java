package com.gg.slider;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;


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
		
		SideBar sideBar = new SideBar(SideBar.SideBarMode.MAXIMISE_CONTENT);

		JTree tree = new JTree();
		
//		SidebarSectionModel m1 = new SidebarSectionModel("Colours", tree, "JTree");
		SidebarSection ss1 = new SidebarSection(sideBar, "Colours", "JTree", tree);
		sideBar.addSection(ss1);

		
		DefaultTableModel tModel = new DefaultTableModel(4,5);
		tModel.setValueAt("Einstein", 0, 0);
		tModel.setValueAt("Plato", 1, 0);
		tModel.setValueAt("Nietsche", 2, 0);
		
		JTable table = new JTable(tModel);
		
		
//		SidebarSectionModel m2 = new SidebarSectionModel("Thinkers", table, "JTable");
		SidebarSection ss2 = new SidebarSection(sideBar, "Thinkers", "JTable", table);
		sideBar.addSection(ss2);

		
		//test

		DefaultListModel<String> model = new DefaultListModel<String>();
		model.add(0, "Bill Gates");
		model.add(1, "Steven Spielberg");
		model.add(2, "Donald Trump");
		model.add(3, "Steve Jobs");		
		 
		JList<String> list = new JList<String>();
		
		list.setModel(model);
		
//		SidebarSectionModel m3 = new SidebarSectionModel("Dealers", list, "JList");
		SidebarSection ss3 = new SidebarSection(sideBar, "Dealers", "JList", list);
		sideBar.addSection(ss3);
		
		listPanel.add(sideBar, BorderLayout.WEST);
		listPanel.add(new JLabel("<html><body><h1>central panel</html>", JLabel.CENTER));
		
		
		tabbedPane.add("Slider Bar", listPanel);
		
		p.add(tabbedPane);
		return p;
	}
}
