package com.gg.calculation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.gg.calculation.model.BinaryEmptyNode;
import com.gg.calculation.model.Calculation;
import com.gg.calculation.model.CalculationObject;
import com.gg.calculation.model.CalculationShared;
import com.gg.calculation.model.FunctionInput;
import com.gg.calculation.model.InputDefShared;
import com.gg.calculation.model.MultiEmptyNode;
import com.gg.calculation.model.ValueNode;
import com.gg.calculation.model.operators.BinaryOperator;
import com.gg.calculation.model.operators.Function;
import com.gg.calculation.model.operators.MultiOperator;
import com.gg.calculation.model.operators.Operator;

/**
 * @author WatkinsO
 * 
 */
public class CalculationTreeModel extends DefaultTreeModel{
	// reference to current calculation
	private Calculation calculation;

	// root node
	private DefaultMutableTreeNode root;

	/**
	 * @param root
	 */
	public CalculationTreeModel(TreeNode root) {
		super(root);
	}

	/**
	 * Constructs a TreeModel from CalculationModel
	 * 
	 * Converts calculation model ----- > Tree model
	 * 
	 * @param calculation
	 *            Calculation
	 */
	public void setCalculation(Calculation calculation) {
		this.calculation = calculation;
		root = null;
		// calculation will have root node

		CalculationObject rootCalculationObject = calculation
				.getRootCalculationObject();

		if (rootCalculationObject != null)
			System.out.println("cccalcObj is "
					+ rootCalculationObject.getClass());

		if (rootCalculationObject instanceof InputDefShared) {
			System.out.println("cccalcObj is input def");
			root = new DefaultMutableTreeNode(rootCalculationObject);
		} else if (rootCalculationObject instanceof CalculationShared) {
			System.out.println("cccalcObj is input def");
			root = new DefaultMutableTreeNode(rootCalculationObject);
		} else if (rootCalculationObject instanceof Operator) {
			root = new DefaultMutableTreeNode(rootCalculationObject);
			processNodeForSet((Operator) rootCalculationObject, root);
		} else if (rootCalculationObject instanceof Function) {
			root = new DefaultMutableTreeNode(rootCalculationObject);
			processNodeForSet((Function) rootCalculationObject, root);
		} else if (rootCalculationObject instanceof ValueNode) {
			root = new DefaultMutableTreeNode(rootCalculationObject);
		} else if (rootCalculationObject == null) {
			root = new DefaultMutableTreeNode(new BinaryEmptyNode(1));
		} else {
			System.out.println("shouldn't ever get here...");
		}

		this.setRoot(root);
		System.out.println("set a calculation");
	}

	/**
	 * We are working with the model and view in tandem. we pass in both the
	 * current object from the object model, as well as the current tree node we
	 * are dealing with, and process together.
	 * 
	 * @param operatorOrFunction
	 */
	private void processNodeForSet(CalculationObject operatorOrFunction,
			DefaultMutableTreeNode parentNode) {
		if (operatorOrFunction instanceof MultiOperator) {
			MultiOperator mo = (MultiOperator) operatorOrFunction;
			List<CalculationObject> list = mo.getOperandList();
			
			for (CalculationObject o : list) {
				if (o != null) {
					CalculationObject co = o;
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(co);
					parentNode.add(node);

					processNodeForSet(co, node);
				}
			}

			// at the end of the list we chuck in an empty node
			MultiEmptyNode en = new MultiEmptyNode();
			DefaultMutableTreeNode emptyNode = new DefaultMutableTreeNode(en);
			parentNode.add(emptyNode);

		} else if (operatorOrFunction instanceof BinaryOperator) {
			BinaryOperator bo = (BinaryOperator) operatorOrFunction;
			CalculationObject co1 = bo.getFirstOperand();
			CalculationObject co2 = bo.getSecondOperand();

			if (co1 == null)
				co1 = new BinaryEmptyNode(1);
			if (co2 == null)
				co2 = new BinaryEmptyNode(2);

			DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(co1);
			DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(co2);

			parentNode.add(node1);
			parentNode.add(node2);

			processNodeForSet(co1, node1);
			processNodeForSet(co2, node2);
		} else if (operatorOrFunction instanceof Function) {

			Function mo = (Function) operatorOrFunction;
			ArrayList<CalculationObject> list = mo.getFunctionInputList();

			for (CalculationObject calcObj : list) {
				if (calcObj != null) {
					FunctionInput fi = (FunctionInput) calcObj;
					DefaultMutableTreeNode functionInputNode = new DefaultMutableTreeNode(
							fi);

					DefaultMutableTreeNode functionInputValueNode;
					if (fi.getValue() == null)
						functionInputValueNode = new DefaultMutableTreeNode(
								new BinaryEmptyNode(1));
					else
						functionInputValueNode = new DefaultMutableTreeNode(fi
								.getValue());

					parentNode.add(functionInputNode);
					functionInputNode.add(functionInputValueNode);
					processNodeForSet(fi.getValue(), functionInputValueNode);
				}
			}
		} else // leaf
		{
			// DefaultMutableTreeNode node = new
			// DefaultMutableTreeNode(operator);
			// parentNode.add(node);
		}

	}

	/**
	 * @return
	 */
	public Calculation getCalculation() {
		return calculation;
	}

}