package com.gg.tablerownumbers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.UIResource;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;



public class TableUtilities 
{
	
	/**
	 * Adds a number column in the row header of the scrollpane, to match rows
	 * in the table. Assumes that table has already been added to a scollpane. If the table
	 * is not in a scrollpane nothing will happen.
	 * 
	 * @param userTable Table to have column added to (if it is in a scrollpane)
	 * @param startingNumber. Number to start number column with, typically 0 or 1.
	 */
	
	public static void addNumberColumn(final JTable userTable, int startingNumber, boolean isRowSelectable)
	{
	    Container p = userTable.getParent();
	    
	    if (p instanceof JViewport)
	    {
	      Container gp = p.getParent();
	      	      
	      if (gp instanceof JScrollPane)
	      {  	  
	        final JScrollPane scrollPane = (JScrollPane)gp;
	        
	        // Make certain we are the viewPort's view and not, for
	        // example, the rowHeaderView of the scrollPane -
	        // an implementor of fixed columns might do this.
	        JViewport viewport = scrollPane.getViewport();
	        
	        if (viewport == null || viewport.getView() != userTable)
	        {
	          return;
	        }
	        
	        JTableHeader tableHeader = (JTableHeader)userTable.getTableHeader();
	        scrollPane.setColumnHeaderView(tableHeader);
      
	        final JTable rowHeadersTable = new JTable(new TableUtilities().new RowHeadersTableModel(userTable.getModel().getRowCount(), startingNumber));
//	        rowHeadersTable.getModel().addTableModelListener()
	        userTable.getModel().addTableModelListener(new TableModelListener(){

				public void tableChanged(TableModelEvent e) 
				{
					RowHeadersTableModel m = (RowHeadersTableModel)rowHeadersTable.getModel();
					
					if (userTable.getRowCount() != m.getRowCount())
					{
						if (userTable.getRowCount() > m.getRowCount())
						{
							int rowDiff = userTable.getRowCount() - m.getRowCount();
							for (int i = 0; i < rowDiff; i++) 
							{ 							 
								m.addNumber();
							}
						}
						else if(userTable.getRowCount() < m.getRowCount())
						{
							int rowDiff = m.getRowCount() - userTable.getRowCount();
							for (int i = 0; i < rowDiff; i++) 
							{
								m.removeNumber();														
							}							
						}
						m.fireTableDataChanged();
					}					
				}	        	
	        });
    
	        //label used for rendering and for 
	        final JLabel label = new JLabel();
	        
	        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
	        {
				public void adjustmentValueChanged(AdjustmentEvent e) 
				{
					int scrollBarValue = e.getValue();
						
					adjustColumnWidth(rowHeadersTable, label, scrollBarValue);					
				}	        	
	        });
	        
	        //this is where you set the width of the row headers
	        rowHeadersTable.createDefaultColumnsFromModel();
	        
	        // make the rows look like headers
	        rowHeadersTable.setBackground(rowHeadersTable.getTableHeader().getBackground());
	        rowHeadersTable.setForeground(rowHeadersTable.getTableHeader().getForeground());
	        rowHeadersTable.setFont(rowHeadersTable.getTableHeader().getFont());
	        
	        rowHeadersTable.setRowHeight(userTable.getRowHeight());
	        
	        rowHeadersTable.getTableHeader().setReorderingAllowed(false);
	        /**
	         * If selectable then change the colouring in the renderer
	         */
	        if (isRowSelectable)
	        {
	        	//adding a renderer
		        rowHeadersTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer()
		        {	
					public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
					{
						label.setText("" + value);
						
						if (isSelected)
						{
							label.setForeground(rowHeadersTable.getSelectionForeground());
							label.setBackground(rowHeadersTable.getSelectionBackground());
						}
						else
						{
							label.setBackground(rowHeadersTable.getBackground());
							label.setForeground(rowHeadersTable.getForeground());						
						}
						return label;
					}
		        });
		        
		        rowHeadersTable.setRowSelectionAllowed(true);
		        rowHeadersTable.setCellSelectionEnabled(true);
		        rowHeadersTable.setFocusable(true);
		        rowHeadersTable.setDragEnabled(true);
		               
//				ListSelectionModel lsm = rowHeadersTable.getSelectionModel();				
//				userTable.setSelectionModel(lsm);

	        }
	        scrollPane.setRowHeaderView(rowHeadersTable);

	        // set the row header name into the top left corner
	        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeadersTable.getTableHeader());

	        Border border = scrollPane.getBorder();
	        if (border == null || border instanceof UIResource)
	        {
	          scrollPane.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
	        }

	        scrollPane.addComponentListener(new ComponentListener(){

				public void componentHidden(ComponentEvent e) {}
				public void componentShown(ComponentEvent e){}
				public void componentMoved(ComponentEvent e) {}

				/**
				 * Whenever the component is resized need to re-adjust the column width
				 * if necessary. This method is also called when the screen is first 
				 * layed out.
				 */
				public void componentResized(ComponentEvent e) 
				{
					adjustColumnWidth(rowHeadersTable, label, scrollPane.getVerticalScrollBar().getValue());
				}
	        });
	        
	        
	        rowHeadersTable.setSelectionMode(userTable.getSelectionModel().getSelectionMode());
	        
	        TableUtilities.TableListener tableListener = new TableUtilities().new TableListener(rowHeadersTable, userTable);
	        
	        
	      }
	    }
	}


    class RowHeadersTableModel extends AbstractTableModel 
    {
    	private ArrayList numbersList = new ArrayList();
    	private int startNumber;
    	
    	public RowHeadersTableModel(int maxNumber, int startingNumber)
    	{
	        //start at starting number and then go to row count (plus starting number amount)
    		this.startNumber = startingNumber;
	        int j = 0;
	        for (int i = startingNumber; i < maxNumber + startNumber; i++) 
	        {
	        	numbersList.add(new Integer(j + startNumber));
		        j++;
			}
	        
//	        this.addTableModelListener(this);
    	}

		public int getRowCount() 
		{
			if (numbersList != null)
				return numbersList.size();
			else
				return 0;
		}
		
		public int getMaxIntValue()
		{
			if (numbersList != null && numbersList.size() != 0)
			{
				Integer integer = (Integer)getValueAt(numbersList.size()-1, 0);
				return integer.intValue();
			}
			else
				return 0;
			
		}

		public int getColumnCount() 
		{
			return 1;
		}

		public String getColumnName(int columnIndex) 
		{
			return "";
		}

		public Class getColumnClass(int columnIndex) 
		{
			return Integer.class;
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) 
		{
			return false;
		}

		public Object getValueAt(int rowIndex, int columnIndex) 
		{
			return numbersList.get(rowIndex);
		}
		public void addNumber() 
		{
			if (numbersList.size() != 0)
			{
				Integer maxNum = (Integer)numbersList.get(numbersList.size()-1);

				numbersList.add(numbersList.size(), new Integer(maxNum.intValue()+1));
			}
			else
			{
				numbersList.add(numbersList.size(), new Integer(startNumber));				
			}
			this.fireTableDataChanged();
		}
		public void removeNumber() 
		{
			numbersList.remove(numbersList.size() - 1);
		}


		public void setValueAt(Object aValue, int rowIndex, int columnIndex)
		{
			
		}
		public void addTableModelListener(TableModelListener l)
		{
			super.addTableModelListener(l);
		}
		public void removeTableModelListener(TableModelListener l)
		{
			super.removeTableModelListener(l);
		}

    	
    };
    
    /**
     * Adjusts the column width of the row headers table containg the number column. The font
     * metrics are extracted from the label of the row at the bottom of the viewport
     * and used to determing the appropriate width.
     * 
     * @param rowHeadersTable single column table in the row header
     * @param label label used to get font metrics
     * @param scrollBarValue int value for determing point of lowest row
     */
	private static void adjustColumnWidth(final JTable rowHeadersTable, final JLabel label, int scrollBarValue) 
	{
		label.setFont(rowHeadersTable.getFont());
		label.setOpaque(true);								
		label.setHorizontalAlignment(JLabel.CENTER);

		int v = rowHeadersTable.getVisibleRect().height;
	
	    int row = rowHeadersTable.rowAtPoint(new Point(0,v  + scrollBarValue));
		
		Integer modelValue = null;
		if (row != -1)
		{
			modelValue = (Integer)rowHeadersTable.getModel().getValueAt(row, 0);						
		}
		else
		{
			RowHeadersTableModel tm = (RowHeadersTableModel)rowHeadersTable.getModel();
			modelValue = new Integer(tm.getMaxIntValue());
		}
		
		label.setText("" + modelValue);
		FontMetrics fontMetrics = label.getFontMetrics(label.getFont());
		
		int widthFactor = 0;
		
		if (fontMetrics != null && label.getText() != null)
		{
			widthFactor = fontMetrics.stringWidth(label.getText());
			
		    rowHeadersTable.setPreferredScrollableViewportSize(new Dimension(widthFactor + 8, 100)); // height is ignored
//		    rowHeadersTable.updateUI();	
			rowHeadersTable.repaint();

		}
	}

	
	
	
	class TableListener implements ListSelectionListener
	{
		private JTable rowHeadersTable;
		private JTable userTable;
	    private JViewport userTableViewPort;
	    private JViewport rowHeadersViewPort;
		
		
		public TableListener(JTable rowHeadersTable, JTable userTable)
		{
			this.userTable = userTable;
			this.rowHeadersTable = rowHeadersTable;
			
		    Container p = userTable.getParent();				    
		    userTableViewPort = (JViewport)p;

		    Container p2 = rowHeadersTable.getParent();
		    rowHeadersViewPort = (JViewport)p2;
			
			Point newPosition = userTableViewPort.getViewPosition();
			rowHeadersViewPort.setViewPosition(newPosition);

//			userTableViewPort.setViewPosition(newPosition);		    
		    
			rowHeadersTable.getSelectionModel().addListSelectionListener(this);
			userTable.getSelectionModel().addListSelectionListener(this);
			
			
		}

		public void valueChanged(ListSelectionEvent e) 
		{
			System.out.println("ListSelectionEvent");
			
			if (e.getSource() == userTable.getSelectionModel())
			{
				rowHeadersTable.getSelectionModel().removeListSelectionListener(this);
				rowHeadersTable.getSelectionModel().clearSelection();
				
				int[] rows = userTable.getSelectedRows();
				
				for (int i = 0; i < rows.length; i++) 
				{
					System.out.println("adding row selection to rowHeaders table : " + rows[i]);
					rowHeadersTable.getSelectionModel().addSelectionInterval(rows[i], rows[i]);
					
				}
				int[] iarr = rowHeadersTable.getSelectedRows();
				
				rowHeadersTable.getSelectionModel().addListSelectionListener(this);
			}
			else if (e.getSource() == rowHeadersTable.getSelectionModel())
			{
				System.out.println("in rowHeadersTable");
				System.out.println("e.getValueIsAdjusting() " + e.getValueIsAdjusting());
				

				boolean isColumnSelectionAllowed = userTable.getColumnSelectionAllowed();
				boolean isRowSelectionAllowed = userTable.getRowSelectionAllowed();
				boolean isCellSelectionAllowed = userTable.getCellSelectionEnabled();
				
				userTable.getSelectionModel().removeListSelectionListener(this);
				userTable.getSelectionModel().clearSelection();
				
				int[] rows = rowHeadersTable.getSelectedRows();
				
				if (isRowSelectionAllowed && !isCellSelectionAllowed && !isColumnSelectionAllowed)
				{
					for (int i = 0; i < rows.length; i++) 
					{
						userTable.addRowSelectionInterval(rows[i], rows[i]);									
					    System.out.println("NI? - viewPort1 y = " + userTableViewPort.getViewPosition().y + " viewPort2 y = " + rowHeadersViewPort.getViewPosition().y);
						userTableViewPort.setViewPosition(rowHeadersViewPort.getViewPosition());

					}
				}
				else
				{				    
				    //looks cleaner
				    userTableViewPort.setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);					

					System.out.println("");
					
					for (int i = 0; i < rows.length; i++) 
					{
						if (i == 0)
						{
							//need to create row first with change selection
							userTable.changeSelection(rows[i],0, false, false);						
							userTable.changeSelection(rows[i],userTable.getColumnCount(), false, true);

						}
						else
						{
							userTable.addRowSelectionInterval(rows[i], rows[i]);						
						}				
					}					
				}
				//re-adding the listener to the user table
				userTable.getSelectionModel().addListSelectionListener(this);					
			}
			else
			{
				System.out.println("no table selestion model");
			}
			
		}
		
	}
	
	
	
	
	
	
	/**
	 * Test classes below :
	 */
	
	

	/** 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame();
		
		JPanel panel = new JPanel();
		
		frame.setVisible(true);

		final TableUtilities t = new TableUtilities();

		ArrayList al = new ArrayList();
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
							
		
		frame.getContentPane().add(panel);	
		frame.setSize(200,200);
		
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		
	}
	
	
	public class TestModel extends AbstractTableModel
	{
		private ArrayList list = new ArrayList();
		protected String[] columnNames = {"one","two","three","four","five","six","seven","eight","nine"};

		
		
		public TestModel(ArrayList list)
		{
			this.list = list;
		}
		
		public void removeRows(int[] rowsSelected) 
		{
			ArrayList collectionToDelete = new ArrayList();
			for (int i = 0; i < rowsSelected.length; i++) 
			{
				Object o = list.get(rowsSelected[i]);
				collectionToDelete.add(o);
			}
			
			list.removeAll(collectionToDelete);
			
			
		}

		public void addRow(TestObject object) 
		{
			list.add(object);			
		}

		public int getRowCount() 
		{
			return list.size();
		}

		public int getColumnCount() 
		{
			return columnNames.length;
		}
		
		public String getColumnName(int i)
		{
			return columnNames[i];
		}

		public Object getValueAt(int rowIndex, int columnIndex) 
		{			
			Object value = null;
			TestObject to = (TestObject)list.get(rowIndex);
			
			switch(columnIndex)
			{
				case 0:
					value = to.getOne();
					break;
				case 1:
					value = to.getTwo();
					break;
				case 2:
					value = to.getThree();
					break;
				case 3:
					value = to.getFour();
					break;
			}
			
			return value;
		}

		public TestObject getTestObject(int row) 
		{
			return (TestObject)list.get(row);
		}
	};
	
	class TestRenderer extends DefaultTableCellRenderer
	{

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
		{
			JLabel component = (JLabel)super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
			
			TableColumn tc = table.getColumnModel().getColumn(column);
			
			int i = table.getColumnModel().getColumnIndex(tc.getIdentifier());

			Object o = table.getModel().getValueAt(row, i);

			if (isSelected)
			{
				component.setBackground(Color.BLACK);
				component.setForeground(Color.WHITE);				
			}
			else
			{
				if (o instanceof Date)
				{
					component.setBackground(Color.BLUE);
					component.setForeground(Color.BLACK);
				}
//				else if (o instanceof String)
//				{
//					component.setBackground(Color.RED);
//					component.setForeground(Color.BLACK);
//				}
				else if (o instanceof Integer)
				{
					component.setBackground(Color.ORANGE);				
					component.setForeground(Color.BLACK);
				}
				else
				{
					component.setBackground(Color.WHITE);
					component.setForeground(Color.BLACK);
				}
			}
			return component;
			
		}
		
	}
	
	class TestObject 
	{
		private String one;
		private Date two;
		private Integer three;
		private String four;
		
		public TestObject(String s, Date d, Integer i, String s2)
		{
			one = s;
			two = d;
			three = i;
			four = s2;
		}
		
		public String getFour() {
			return four;
		}
		public void setFour(String four) {
			this.four = four;
		}
		public String getOne() {
			return one;
		}
		public void setOne(String one) {
			this.one = one;
		}
		public Integer getThree() {
			return three;
		}
		public void setThree(Integer three) {
			this.three = three;
		}
		public Date getTwo() {
			return two;
		}
		public void setTwo(Date two) {
			this.two = two;
		}
	}
}
