package com.gg.calculation;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.gg.calculation.model.Calculation;
import com.gg.calculation.model.InputDef;

public class OutputTreeRenderer extends DefaultTreeCellRenderer {
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				leaf, row, hasFocus);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

		Object userObject = node.getUserObject();

		this.setIcon(null);

		String htmlString = "<HTML><BODY>";

		if (userObject instanceof Calculation) {
			Calculation calc = (Calculation) userObject;

			htmlString = htmlString + "<FONT COLOR="
					+ UIConstants.CALCULATION_COLOUR + ">";

			this.setText(htmlString + calc.getName() + "</FONT></BODY></HTML>");
			this.setToolTipText(calc.toString());
		} else if (userObject instanceof InputDef) {
			htmlString = htmlString + "<FONT COLOR="
					+ UIConstants.INPUT_DEF_COLOUR + ">";

			InputDef id = (InputDef) userObject;

			this.setText(htmlString + id.getName() + "</FONT></BODY></HTML>");
		} else {
			htmlString = htmlString + "<FONT COLOR=black><b>";

			this.setText(htmlString + userObject + "</b></FONT></BODY></HTML>");
		}
		return this;
	}

}
