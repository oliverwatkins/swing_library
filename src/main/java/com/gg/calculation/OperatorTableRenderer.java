package com.gg.calculation;


import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class OperatorTableRenderer extends DefaultTableCellRenderer {

	public OperatorTableRenderer() {
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if (column == 0) {
			// TODO: using setText doesn't seem to work
			setForeground(Color.red);
			// this.setText("<HTML><BODY>" +
			// "<FONT COLOR=" + UIConstants.OPERATOR_COLOUR + "><b>"
			// + value + "</B></FONT></BODY></HTML>");
		} else if (column == 1) {
			setForeground(Color.black);
		}
		return super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);

	}
}