package com.gg.tablerownumbers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.gg.tablerownumbers.TableUtilities.TestModel;
import com.gg.tablerownumbers.TableUtilities.TestObject;
import com.gg.tablerownumbers.TableUtilities.TestRenderer;

public class TableRowNumberTester extends JFrame{
	
	public static void main (String[] args) {
		
		TableRowNumberTester t = new TableRowNumberTester();
		
		
		t.setSize(500, 600);
		t.add(t.getPanel());
		t.setVisible(true);
		
	}
	
	
	public static JPanel getPanel() {
		
		JPanel panel = new JPanel();
		
		final TableUtilities t = new TableUtilities();

		ArrayList<TestObject> al = new ArrayList<TestObject>();
		al.add(t.new TestObject("asdf", new Date(), new Integer(1), "qwert"));
		al.add(t.new TestObject("zxcv", new Date(), new Integer(2), "fsddd"));
		al.add(t.new TestObject("mnvb", new Date(), new Integer(3), "fasd"));
		al.add(t.new TestObject("ce sara ", new Date(), new Integer(4), "fasd"));
		al.add(t.new TestObject("sara", new Date(), new Integer(5), "fasd"));
		al.add(t.new TestObject("whatever", new Date(), new Integer(6), "fasd"));
		al.add(t.new TestObject("may", new Date(), new Integer(7), "fasd"));
		al.add(t.new TestObject("be", new Date(), new Integer(8), "fasd"));
		al.add(t.new TestObject("may ", new Date(), new Integer(9), "fasd"));
		al.add(t.new TestObject("be", new Date(), new Integer(10), "fasd"));
		al.add(t.new TestObject("asdf", new Date(), new Integer(11), "qwert"));
		al.add(t.new TestObject("zxcv", new Date(), new Integer(12), "fsddd"));
		al.add(t.new TestObject("mnvb", new Date(), new Integer(13), "fasd"));
		al.add(t.new TestObject("ce sara ", new Date(), new Integer(14), "fasd"));
		al.add(t.new TestObject("sara", new Date(), new Integer(15), "fasd"));
		al.add(t.new TestObject("whatever", new Date(), new Integer(16), "fasd"));
		al.add(t.new TestObject("may", new Date(), new Integer(17), "fasd"));
		al.add(t.new TestObject("be", new Date(), new Integer(18), "fasd"));
		al.add(t.new TestObject("may ", new Date(), new Integer(19), "fasd"));
		al.add(t.new TestObject("be", new Date(), new Integer(20), "fasd"));
		al.add(t.new TestObject("asdf", new Date(), new Integer(21), "qwert"));
		al.add(t.new TestObject("zxcv", new Date(), new Integer(22), "fsddd"));
		al.add(t.new TestObject("mnvb", new Date(), new Integer(23), "fasd"));
		al.add(t.new TestObject("ce sara ", new Date(), new Integer(24), "fasd"));
		al.add(t.new TestObject("sara", new Date(), new Integer(25), "fasd"));
		al.add(t.new TestObject("whatever", new Date(), new Integer(26), "fasd"));
		al.add(t.new TestObject("may", new Date(), new Integer(27), "fasd"));
		al.add(t.new TestObject("be", new Date(), new Integer(28), "fasd"));
		al.add(t.new TestObject("may ", new Date(), new Integer(29), "fasd"));
		al.add(t.new TestObject("be", new Date(), new Integer(30), "fasd"));
		al.add(t.new TestObject("asdf", new Date(), new Integer(31), "qwert"));
		al.add(t.new TestObject("zxcv", new Date(), new Integer(32), "fsddd"));
		al.add(t.new TestObject("mnvb", new Date(), new Integer(33), "fasd"));
		al.add(t.new TestObject("ce sara ", new Date(), new Integer(34), "fasd"));
		al.add(t.new TestObject("sara", new Date(), new Integer(35), "fasd"));
		al.add(t.new TestObject("whatever", new Date(), new Integer(36), "fasd"));
		al.add(t.new TestObject("may", new Date(), new Integer(37), "fasd"));
		al.add(t.new TestObject("be", new Date(), new Integer(38), "fasd"));
		al.add(t.new TestObject("may ", new Date(), new Integer(39), "fasd"));
		al.add(t.new TestObject("be", new Date(), new Integer(40), "fasd"));
		al.add(t.new TestObject("be", new Date(), new Integer(41), "fasd"));
		   
		 
		TestModel model2 = t.new TestModel(al);
		
		final JTable userTable = new JTable(model2);
		
		TestRenderer renderer1 = t.new TestRenderer();		
		for (int i = 0; i < userTable.getColumnCount(); i++) 
		{
			userTable.getColumnModel().getColumn(i).setCellRenderer(renderer1);
		}		
		userTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		userTable.setColumnSelectionAllowed(true);
		userTable.setRowSelectionAllowed(true);
//		userTable.setCellSelectionEnabled(true);

		
		
		final JScrollPane pane = new JScrollPane(userTable);
		TableUtilities.addNumberColumn(userTable, 1, true);
		
		
		JButton addButton = new JButton("add");
		panel.add(pane);
		panel.add(addButton);
		
		
		
		addButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) 
			{
				TestModel testModel = (TestModel)userTable.getModel();
				testModel.addRow(t.new TestObject("new row", new Date(), new Integer(122), "fsd"));
				testModel.fireTableDataChanged();
			}
		});

		JButton removeButton = new JButton("remove");
		panel.add(removeButton);
		removeButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) 
			{
				int[] rowsSelected = userTable.getSelectedRows();
				
				TestModel testModel = (TestModel)userTable.getModel();
				testModel.removeRows(rowsSelected);
				testModel.fireTableDataChanged();
			}
		});


		JTable table2 = new JTable(model2);
		
		
		TestRenderer renderer2 = t.new TestRenderer();		
		for (int i = 0; i < table2.getColumnCount(); i++) 
		{
			table2.getColumnModel().getColumn(i).setCellRenderer(renderer2);
		}
		
		panel.add(new JScrollPane(table2));
		panel.add(new JButton("test2"));
							
		
		return panel;
		
	}
}
