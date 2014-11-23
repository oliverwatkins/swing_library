package com.gg.calculation;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.gg.calculation.model.CalculationObject;
import com.gg.calculation.model.CalculationShared;
import com.gg.calculation.model.EmptyNode;
import com.gg.calculation.model.FunctionInput;
import com.gg.calculation.model.InputDefShared;
import com.gg.calculation.model.ValueNode;
import com.gg.calculation.model.operators.Function;
import com.gg.calculation.model.operators.Operator;

public class CalculationTreeRenderer extends DefaultTreeCellRenderer {
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				leaf, row, hasFocus);

		setIcon(null);
		setDisabledIcon(null);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

		Object uObject = node.getUserObject();

		CalculationObject userObject = (CalculationObject) uObject;

		String htmlString = "<HTML><BODY>";

		if (userObject instanceof EmptyNode) {
			htmlString = htmlString + "<FONT COLOR="
					+ UIConstants.EMPTY_NODE_COLOUR + "><b>";

			this.setText(htmlString + userObject.toString()
					+ "</B></FONT></BODY></HTML>");
		} else if (userObject instanceof Operator) {
			htmlString = htmlString + "<FONT COLOR="
					+ UIConstants.OPERATOR_COLOUR + "><B>";

			this.setText(htmlString + userObject.toString()
					+ "</B></FONT></BODY></HTML>");
		} else if (userObject instanceof Function) {
			htmlString = htmlString + "<FONT COLOR="
					+ UIConstants.FUNCTION_COLOUR + "><B>";

			this.setText(htmlString + userObject.toString()
					+ "</B></FONT></BODY></HTML>");
		} else if (userObject instanceof FunctionInput) {
			htmlString = htmlString + "<FONT COLOR="
					+ UIConstants.FUNCTION_COLOUR + "><B>";

			this.setText(htmlString + userObject.toString()
					+ "</B></FONT></BODY></HTML>");
		} else if (userObject instanceof InputDefShared) {
			htmlString = htmlString + "<FONT COLOR="
					+ UIConstants.INPUT_DEF_COLOUR + ">";

			InputDefShared id = (InputDefShared) userObject;

			if (id.getRelativeReportingPeriod() != 0) {
				this.setText(htmlString + userObject.toString() + "</FONT>"
						+ "<FONT COLOR=" + UIConstants.REL_REP_PERIOD_COLOUR
						+ "> [" + id.getRelativeReportingPeriod() + "]</FONT>"
						+ "</BODY></HTML>");
			} else
				this.setText(htmlString + userObject.toString()
						+ "</FONT></BODY></HTML>");
		} else if (userObject instanceof CalculationShared) {
			
//			setIcon(IconLibrary.getInstance()
//					.getIcon(IconLibrary.CALCULATION16));
//			setDisabledIcon(IconLibrary.getInstance().getIcon(
//					IconLibrary.CALCULATION16));

			htmlString = htmlString + "<FONT COLOR="
					+ UIConstants.CALCULATION_COLOUR + ">";

			CalculationShared calc = (CalculationShared) userObject;

			String name = calc.getName();

			this.setText(htmlString + name + "</FONT></BODY></HTML>");

			// htmlString = htmlString + "<FONT COLOR=" +
			// UIConstants.CALCULATION_COLOUR + ">";
			//			
			// this.setText(htmlString + calc.getName() +
			// "</FONT></BODY></HTML>");
			this.setToolTipText(calc.toString());
		} else if (userObject instanceof ValueNode) {
			ValueNode vn = (ValueNode) userObject;

			htmlString = htmlString + "<FONT COLOR=" + UIConstants.VALUE_COLOUR
					+ ">";

			this.setText(htmlString + vn.getValue() + "</FONT></BODY></HTML>");
		} else {
			System.out.println("value is not of the right type");

			// this.setText("!!!!!!");

			// Logger.debug(msgStr + userObject.getClass());
		}

		return this;
	}
}
