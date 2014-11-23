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


public class ExpanderTester extends JFrame{
	
	public ExpanderTester(){
	}

	
	public static void main (String[] args) {

		try {
			String plafClassName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			UIManager.setLookAndFeel(plafClassName);
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		catch (Error e) {
			e.printStackTrace();
		}

		ExpanderTester frame = new ExpanderTester();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 1024, 708);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel listPanel = new JPanel(new BorderLayout());
		
		SideBar sideBar = new SideBar(SideBar.SideBarMode.MINIMISE_CONTENT);

		JTree tree = new JTree();
		
		SidebarSectionModel m1 = new SidebarSectionModel("Colours", tree, "JTree");
		SidebarSection ss1 = new SidebarSection(sideBar, m1);
		sideBar.addSection(ss1);

		
		DefaultTableModel tModel = new DefaultTableModel(4,5);
		tModel.setValueAt("Einstein", 0, 0);
		tModel.setValueAt("Plato", 1, 0);
		tModel.setValueAt("Nietsche", 2, 0);
		
		JTable table = new JTable(tModel);
		
		
		SidebarSectionModel m2 = new SidebarSectionModel("Thinkers", table, "JTable");
		SidebarSection ss2 = new SidebarSection(sideBar, m2);
		sideBar.addSection(ss2);


		DefaultListModel model = new DefaultListModel();
		model.add(0, "Bill Gates");
		model.add(1, "Steven Spielberg");
		model.add(2, "Donald Trump");
		model.add(2, "Steve Jobs");		
		
		JList list = new JList();
		
		list.setModel(model);
		
		SidebarSectionModel m3 = new SidebarSectionModel("Dealers", list, "JList");
		SidebarSection ss3 = new SidebarSection(sideBar, m3);
		sideBar.addSection(ss3);
		
		listPanel.add(sideBar, BorderLayout.WEST);
		listPanel.add(new JLabel("<html><body><h1>central panel</html>", JLabel.CENTER));
		
		
		JPanel treePanel = new JPanel();
		JPanel tablePanel = new JPanel();
		
		tabbedPane.add("Slider Bar", listPanel);
//		tabbedPane.add("Tree", treePanel);
//		tabbedPane.add("Table", tablePanel);
		
		
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(tabbedPane);
		frame.setVisible(true);
	}
}
