package com.gg;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.gg.calculation.CalculationTester;
import com.gg.expander.ExpanderTester;
import com.gg.filtertree.FilterTreeTester;
import com.gg.slider.SliderTester;

/**
 * THe frame contains all components visible in application such as search panel
 * and the table.
 */

public class Showcase extends JFrame {

	public Showcase() throws Exception {

		
		
		

		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, fall back to cross-platform
		    try {
		        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		    } catch (Exception ex) {
		        // not worth my time
		    }
		}
		
		
//		
//		try {
//			
////			UIManager.setLookAndFeel(
////		            UIManager.getSystemLookAndFeelClassName());
//			// Set cross-platform Java L&F (also called "Metal")
//			UIManager.setLookAndFeel(UIManager
//					.getCrossPlatformLookAndFeelClassName());
//		} catch (Exception e) {
//			throw e;
//			// handle exception
//		}

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});

		setTitle("Showcase");

		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.add("Expander", ExpanderTester.getPanel());

		tabbedPane.add("Filter Tree", FilterTreeTester.getPanel());

		tabbedPane.add("Slider", SliderTester.getPanel());

		tabbedPane.add("Calculation", CalculationTester.getPanel());

		this.add(tabbedPane);

		//
		// JMenu menu = new JMenu("Booker");
		//
		// JMenuBar menuBar = new JMenuBar();
		// menuBar.add(menu);
		// this.setJMenuBar(menuBar);
		//
		//
		// //setting up menu items
		// JMenuItem sItem = new JMenuItem(searchAction);
		// menu.add(sItem);
		// JMenuItem bItem = new JMenuItem(bookAction);
		// menu.add(bItem);
		// menu.addSeparator();
		// JMenuItem settingsItem = new JMenuItem(settingsAction);
		// menu.add(settingsItem);
		//
		//
		//
		// JPanel panel = new JPanel(new BorderLayout());
		//
		//
		//
		// table = new BookingTable();
		// table.setAutoCreateRowSorter(true); // auto sorting enabled
		// table.setModel(model);
		//
		// BookingTableRenderer renderer = new BookingTableRenderer();
		// for (int i = 0; i < table.getColumnCount(); i++) {
		// table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		// }
		// table.setDefaultRenderer(Object.class, renderer);
		//
		// JPanel bottomPanel = new JPanel();
		//
		// final JButton bookButton = new JButton("book");
		// bookButton.setEnabled(false);
		//
		// final JButton deleteButton = new JButton("delete");
		// deleteButton.setEnabled(false);
		// deleteButton.setToolTipText("for testing purposes only");
		//
		// final JButton createButton = new JButton("create");
		// createButton.setToolTipText("for testing purposes only");
		//
		// table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//
		// table.getSelectionModel().addListSelectionListener(new
		// ListSelectionListener(){
		//
		// public void valueChanged(ListSelectionEvent e) {
		//
		// if (table.getSelectionModel().isSelectionEmpty()){
		// bookButton.setEnabled(false);
		// deleteButton.setEnabled(false);
		// }else{
		// bookButton.setEnabled(true);
		// deleteButton.setEnabled(true);
		// }
		// }
		// });
		//
		// deleteButton.addActionListener(new DeleteAction(this));
		// bookButton.addActionListener(bookAction);
		// createButton.addActionListener(new CreateAction(this));
		// searchPanel.getSearchButton().addActionListener(new
		// SearchAction(this));
		//
		// bottomPanel.add(createButton);
		// bottomPanel.add(bookButton);
		// bottomPanel.add(deleteButton);
		//
		// JScrollPane scroll = new JScrollPane(table);
		// scroll.setPreferredSize(new Dimension(800, 300));
		//
		// panel.add(searchPanel, BorderLayout.NORTH);
		// panel.add(scroll, BorderLayout.CENTER);
		// panel.add(bottomPanel, BorderLayout.SOUTH);
		//
		// this.getContentPane().add(panel);
		//

		//
		// this.pack();
		// Utils.center(this);
		//
		// this.setVisible(true);
	}

	/**
	 * Set up of the main frame.
	 * 
	 * @param mode
	 *            networked or non networked
	 * @throws Exception
	 */
	public static void main(String[] s2) throws Exception {

		Showcase s = new Showcase();
		s.setSize(400, 400);

		s.setVisible(true);

		// MainFrame frame = new MainFrame();
		//
		// //initialise UIDelgate
		// UIDelegate.getInstance().initialise(frame, mode);
		//
		// //call a search all (ie get all bookings) when starting up.
		// ArrayList<Booking> list = UIDelegate.getInstance().getAllBookings();
		// frame.getModel().setBookings(list);
		//
		// frame.pack();
		// frame.setVisible(true);
	}
}
