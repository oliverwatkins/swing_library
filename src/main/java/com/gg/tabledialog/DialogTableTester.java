package com.gg.tabledialog;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class DialogTableTester extends JFrame {

	public DialogTableTester() {

	}

	public static void main(String[] args) {

		DialogTableTester t = new DialogTableTester();

		t.setSize(500, 600);
		t.add(t.getPanel());
		t.setVisible(true);

	}

	public static JPanel getPanel() {
		JPanel panel = new JPanel();

		MyTableModel m = new MyTableModel();

		DialogTable t = new DialogTable(m);
		panel.add(new JScrollPane(t));

		t.getColumnModel().getColumn(3)
				.setCellRenderer(new MyTableCellRenderer());

		// mainFrame.getContentPane().add(panel);
		//
		// mainFrame.setVisible(true);
		// mainFrame.pack();

		return panel;

	}

	static class MyTableModel extends AbstractTableModel {

		String[] columnNames;
		Object[][] data;

		Object o = new Object();

		public MyTableModel() {

			columnNames = new String[] { "First Name", "Last Name", "Sport",
					"Balance", "Vegetarian", "Date of Birth", "Date Joined",
					"Notes" };

			data = new Object[][] {
					{
							"Kathy",
							"Smith",
							"Snowboarding",
							"5",
							false,
							"16.04.1974",
							"",
							"Talented individual who possesses great skills on the slopers, and active and fun memeber" },
					{ "John", "Doe", "Rowing", "3", true, "02.02.1972", "", "" },
					{
							"Sue",
							"Black",
							"Knitting",
							"-2",
							false,
							"16.12.1988",
							"",
							"An excellent knitter who can knit several multicoloured jumpers in about 3 hours. Is ready to take her knitting to the next competitive level" },
					{ "Jane", "White", "Speed reading", "20", true,
							"16.04.1942", "", "" },
					{ "Joe", "Brown", "Pool", "-10", false, "16.04.1984", "",
							"" }, };
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}

		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

	}

	static class MyTableCellRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row2,
				int column) {

			int row = table.convertRowIndexToModel(row2); // this is needed if
															// we use inbuilt
															// sorting.

			JLabel label = (JLabel) super.getTableCellRendererComponent(table,
					value, isSelected, hasFocus, row, column);

			MyTableModel model = (MyTableModel) table.getModel();

			// if not selected colour row differently if booked.
			if (!isSelected) {

				Object o = model.getValueAt(row, 3);

				Integer i = new Integer("" + o);
				if (i > 0) {
					label.setBackground(Color.CYAN);
				} else {
					label.setBackground(Color.RED);
				}
			}
			return label;
		}
	}
}
