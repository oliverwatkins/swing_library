package com.gg.tabledialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class DialogTable {

	public static void main(String[] args) {

		DialogTable m = new DialogTable();
		m.doStuff();
	}

	JFrame mainFrame = new JFrame();
	
	private void doStuff() {
		JPanel panel = new JPanel();

		MyTableModel m = new MyTableModel();
		
		TableWithDialog t = new TableWithDialog(m);
		panel.add(new JScrollPane(t));
		
		t.getColumnModel().getColumn(3).setCellRenderer(new MyTableCellRenderer());

		mainFrame.getContentPane().add(panel);

		mainFrame.setVisible(true);
		mainFrame.pack();
	}

	class TableWithDialog extends JTable {

		public TableWithDialog(TableModel model) {
			
			super(model);

			this.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {

						int i = rowAtPoint(e.getPoint());

						final JDialog dialog = new JDialog();
						
//						m.setMaximumSize(new Dimension(400,200));
//						dialog.setPreferredSize(new Dimension(400,dialog.getPreferredSize().height));
						dialog.setLayout(new GridBagLayout());

						GridBagConstraints gbc = new GridBagConstraints();
						gbc.insets = new Insets(2,2,2,2);
						for (int j = 0; j < getColumnCount(); j++) {
							
							gbc.gridx = 0;
							gbc.gridy = j;
							gbc.anchor = GridBagConstraints.WEST;
							gbc.fill= GridBagConstraints.HORIZONTAL;
							
							Object valueInTable = getValueAt(i, j);
							
							TableCellRenderer renderer = getCellRenderer(i, j);

							Object valueInModel = getModel().getValueAt(i, j);
							
							Component rendererComponent = renderer.getTableCellRendererComponent(getThisTable(), valueInModel, false, false, i, j);

							dialog.add(new JLabel("" + getColumnName(j)), gbc);
							gbc.gridx = 1;
							
							//Rendering with DefaultTableCellRenderer does not seem to work outside
							//of the table. Need to create new component
							if (rendererComponent.getClass().equals(DefaultTableCellRenderer.UIResource.class)){
								System.out.println("rend 1 " + rendererComponent.getClass());
								
								JTextArea ta = new JTextArea("" + valueInTable);
								ta.setEditable(false);
								dialog.add(new JScrollPane(ta), gbc);
							}else{
								System.out.println("rend 2 " + rendererComponent.getClass());
								dialog.add(rendererComponent, gbc);
							}

						}
						gbc.gridx = 1;
						gbc.gridy = getColumnCount() + 1;
						gbc.fill = GridBagConstraints.NONE;
						
						
						JButton button = new JButton("OK");
						button.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								dialog.setVisible(false);
							}
						});
						button.addKeyListener(new KeyListener(){

							@Override
							public void keyPressed(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void keyReleased(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void keyTyped(KeyEvent e) {
								// TODO Auto-generated method stub
								
							}
							
						});
						gbc.anchor = GridBagConstraints.EAST;
						dialog.add(button, gbc);
						

						dialog.setLocationRelativeTo(mainFrame);
						dialog.setModal(true);
						dialog.pack();
						dialog.setVisible(true);
						

						System.out.println(" getValueAt(1, i); " + getValueAt(1, i));
					}
				}
			});
		}
		private JTable getThisTable(){
			return this;
		}
	}

	class MyTableModel extends AbstractTableModel {

		String[] columnNames;
		Object[][] data;

		Object o = new Object();

		public MyTableModel() {

			columnNames = new String[] {"First Name", "Last Name", "Sport", "Balance", "Vegetarian", "Date of Birth", "Date Joined", "Notes"};

			data = new Object[][] { {"Kathy", "Smith", "Snowboarding", "5", false, "16.04.1974", "", "Talented individual who possesses great skills on the slopers, and active and fun memeber"},
					{"John", "Doe", "Rowing", "3", true, "02.02.1972", "", ""}, 
					{"Sue", "Black", "Knitting", "-2", false, "16.12.1988", "", "An excellent knitter who can knit several multicoloured jumpers in about 3 hours. Is ready to take her knitting to the next competitive level"},
					{"Jane", "White", "Speed reading", "20", true,"16.04.1942", "", ""},
					{"Joe", "Brown", "Pool", "-10", false,"16.04.1984", "", ""},};
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
	
	
	class MyTableCellRenderer extends DefaultTableCellRenderer{

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row2,
				int column) {
			
	        int row = table.convertRowIndexToModel(row2); // this is needed if we use inbuilt sorting.
			
			JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			MyTableModel model = (MyTableModel)table.getModel();
			
			//if not selected colour row differently if booked.
			if (!isSelected){
				
				Object o = model.getValueAt(row, 3);
				
				Integer i = new Integer("" + o);
				if (i > 0){
					label.setBackground(Color.CYAN);
				}else{
					label.setBackground(Color.RED);
				}
			}
			return label;
		}
	}
}

