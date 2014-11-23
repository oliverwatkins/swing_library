package com.gg.calculation;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;

import com.gg.calculation.model.FunctionInput;
import com.gg.calculation.model.MultiEmptyNode;
import com.gg.calculation.model.ValueNode;
import com.gg.calculation.model.operators.BinaryOperator;

public class CalculationTreeEditor implements TreeCellEditor {
	private EditorTextField emptyNodeEditor;
	private DefaultMutableTreeNode editedNode;
	private JTree tree;

	// just a reference
	private CalculationPanel calculationPanel;

	public CalculationTreeEditor(CalculationPanel panel) {
		this.calculationPanel = panel;
		emptyNodeEditor = new EditorTextField();
	}

	/**
	 * 
	 * 
	 * @see javax.swing.tree.TreeCellEditor#getTreeCellEditorComponent(javax.swing.JTree,
	 *      java.lang.Object, boolean, boolean, boolean, int)
	 */
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {

		this.tree = tree;

		editedNode = (DefaultMutableTreeNode) value;

		Object userObject = editedNode.getUserObject();

		System.out.println("user object class is = " + userObject.getClass());

		emptyNodeEditor.setText("");

		return emptyNodeEditor;
	}

	public Object getCellEditorValue() {
		
		System.out.println("getCellEditorValue");

		Double i = (Double) emptyNodeEditor.getCellEditorValue();

		ValueNode vn = new ValueNode(i);

		editedNode.setUserObject(new ValueNode(i));

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) editedNode
				.getParent();

		int index = parent.getIndex(editedNode);

		if (editedNode.getUserObject() instanceof MultiEmptyNode) {
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) editedNode
					.getParent();

			DefaultMutableTreeNode newMultiEmpty = new DefaultMutableTreeNode();

			newMultiEmpty.setUserObject(new MultiEmptyNode());

			parentNode.add(newMultiEmpty);
		} else if (parent.getUserObject() instanceof BinaryOperator) {
			System.out.println("parent user object is BinaryOperator");
			// inserting into the actual object model
			BinaryOperator bo = (BinaryOperator) parent.getUserObject();

			if (index == 0) {
				System.out.println("setting first operand type = "
						+ vn.getClass());
				bo.setFirstOperand(vn);
			} else if (index == 1) {
				System.out.println("setting second operand type = "
						+ vn.getClass());
				bo.setSecondOperand(vn);
			}
		} else if (parent.getUserObject() instanceof FunctionInput) {
			System.out.println("parent user object is FunctionInput");

			FunctionInput fi = (FunctionInput) parent.getUserObject();
			fi.setValue(vn);
		}
		// this
		System.out.println("<<< get cell editor value >>>>");
		System.out.println("editedNode.getUserObject() = "
				+ editedNode.getUserObject());

		calculationPanel.updateCalculationText();

		return editedNode.getUserObject();
	}

	/**
	 * CalculationTree checks to see whether node is editable or not. Not here.
	 */
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	public boolean shouldSelectCell(EventObject event) {
		System.out.println("--------- should select cell");

		return emptyNodeEditor.shouldSelectCell(event);
	}

	public boolean stopCellEditing() {
		System.out.println("<<<<< cell editing has stopped >>>>>>");

		return emptyNodeEditor.stopCellEditing();
	}

	public void cancelCellEditing() {
		emptyNodeEditor.cancelCellEditing();
	}

	public void addCellEditorListener(CellEditorListener l) {
		emptyNodeEditor.addCellEditorListener(l);
	}

	public void removeCellEditorListener(CellEditorListener l) {
		emptyNodeEditor.removeCellEditorListener(l);
	}

}
