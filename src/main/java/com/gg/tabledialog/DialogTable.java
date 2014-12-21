package com.gg.tabledialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class DialogTable extends JTable {

	public DialogTable(TableModel model) {

		super(model);

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int i = rowAtPoint(e.getPoint());

					final JDialog dialog = new JDialog();

					// m.setMaximumSize(new Dimension(400,200));
					// dialog.setPreferredSize(new
					// Dimension(400,dialog.getPreferredSize().height));
					dialog.setLayout(new GridBagLayout());

					GridBagConstraints gbc = new GridBagConstraints();
					gbc.insets = new Insets(2, 2, 2, 2);
					for (int j = 0; j < getColumnCount(); j++) {

						gbc.gridx = 0;
						gbc.gridy = j;
						gbc.anchor = GridBagConstraints.WEST;
						gbc.fill = GridBagConstraints.HORIZONTAL;

						Object valueInTable = getValueAt(i, j);

						TableCellRenderer renderer = getCellRenderer(i, j);

						Object valueInModel = getModel().getValueAt(i, j);

						Component rendererComponent = renderer
								.getTableCellRendererComponent(getThisTable(),
										valueInModel, false, false, i, j);

						dialog.add(new JLabel("" + getColumnName(j)), gbc);
						gbc.gridx = 1;

						// Rendering with DefaultTableCellRenderer does not seem
						// to work outside
						// of the table. Need to create new component
						if (rendererComponent.getClass().equals(
								DefaultTableCellRenderer.UIResource.class)) {

							JTextArea ta = new JTextArea("" + valueInTable);
							ta.setEditable(false);
							dialog.add(new JScrollPane(ta), gbc);
						} else {
							
							dialog.add(rendererComponent, gbc);
						}

					}
					gbc.gridx = 1;
					gbc.gridy = getColumnCount() + 1;
					gbc.fill = GridBagConstraints.NONE;

					JButton button = new JButton("OK");
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							dialog.setVisible(false);
						}
					});
					gbc.anchor = GridBagConstraints.EAST;
					dialog.add(button, gbc);

//					dialog.setLocationRelativeTo(mainFrame);
					dialog.setModal(true);
					dialog.pack();
					dialog.setVisible(true);
				}
			}
		});
	}

	private JTable getThisTable() {
		return this;
	}
}
