package com.gg.calculation;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.gg.calculation.model.Calculation;
import com.gg.calculation.model.InputDef;
import com.gg.calculation.model.LongIdentifier;

public class OutputTreePanel extends JPanel {
	private OutputTreeModel model;

	private OutputTree outputTree;

	private DefaultMutableTreeNode inputNode;
	private DefaultMutableTreeNode calculationNode;

	public OutputTreePanel() {

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Inputs and Calculations for profile YYYYY"));

		init();
	} 

	/**
	 * 
	 */
	private void init() {
		model = new OutputTreeModel(getRootNode());

		outputTree = new OutputTree(model);
		outputTree.setRootVisible(false);
		outputTree.setCellRenderer(new OutputTreeRenderer());

		TreePath p1 = new TreePath(calculationNode.getPath());
		TreePath p2 = new TreePath(inputNode.getPath());

		// expand tree for these two nodes
		outputTree.expandPath(p1);
		outputTree.expandPath(p2);

		add(new JScrollPane(outputTree));
	}

	private TreeNode getRootNode() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();

		inputNode = new DefaultMutableTreeNode("Segments and Input Definitions");
		calculationNode = new DefaultMutableTreeNode("Calculations");

		rootNode.add(inputNode);
		rootNode.add(calculationNode);
		
		setUpInputNode(inputNode);
		setUp();

		return rootNode;
	}

	/**
	 * @param calculationNode
	 */
	private void setUp() {
		
		Collection<Calculation> calcs = getCalculations();

		if (calcs != null) {
			for (Iterator<Calculation> iter = calcs.iterator(); iter.hasNext();) {
				Calculation element = iter.next();
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(
						element);
				calculationNode.add(node);
			}
		}
		
		Collection<InputDef> inputs = getInputs();

		if (inputs != null) {
			for (Iterator<InputDef> iter = inputs.iterator(); iter.hasNext();) {
				InputDef element = iter.next();
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(
						element);
				inputNode.add(node);
			}
		}
		
		
	}

	private Collection<InputDef> getInputs() {
		
		InputDef i1 = new InputDef("field1", new LongIdentifier(1));
		InputDef i2 = new InputDef("field2", new LongIdentifier(1));
		InputDef i3 = new InputDef("field3", new LongIdentifier(1));
		InputDef i4 = new InputDef("field4", new LongIdentifier(1));
		InputDef i5 = new InputDef("field5", new LongIdentifier(1));
		InputDef i6 = new InputDef("field6", new LongIdentifier(1));
		InputDef i7 = new InputDef("field7", new LongIdentifier(1));
		InputDef i8 = new InputDef("field8", new LongIdentifier(1));
		InputDef i9 = new InputDef("field9", new LongIdentifier(1));
		InputDef i10 = new InputDef("field10", new LongIdentifier(1));

		ArrayList<InputDef> al = new ArrayList<InputDef>();
		al.add(i1);
		al.add(i2);		
		al.add(i3);
		al.add(i4);
		al.add(i5);
		al.add(i6);
		al.add(i7);
		al.add(i8);
		al.add(i9);
		al.add(i10);
		
		return al;
	}

	private Collection<Calculation> getCalculations() {
		
		Calculation c1 = new Calculation(new LongIdentifier(1), "calc1", false);
		Calculation c2 = new Calculation(new LongIdentifier(1), "calc1", false);
		Calculation c3 = new Calculation(new LongIdentifier(1), "calc1", false);

		ArrayList<Calculation> al = new ArrayList<Calculation>();
		al.add(c1);
		al.add(c2);		
		al.add(c3);
		
		return al;
	}

	/**
	 * @param inputNode
	 */
	private void setUpInputNode(DefaultMutableTreeNode node) {

	}

	public void updateOutputList() {
		outputTree.updateUI();
	}

	/**
	 * @param calculation
	 */
	public void addCalculationToTreeModel(Calculation calculation) {
		calculationNode.add(new DefaultMutableTreeNode(calculation));
		getOutputTree().updateUI();
	}

	public void removeCalculationFromTreeMode(Calculation calculation) {
		DefaultMutableTreeNode nodeToDelete = null;
		Enumeration e = calculationNode.breadthFirstEnumeration();
		
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode element = (DefaultMutableTreeNode) e
					.nextElement();

			if (element.getUserObject().equals(calculation)) {
				System.out.println("found the calc");
				nodeToDelete = element;
			}
		}
		calculationNode.remove(nodeToDelete);

		getOutputTree().updateUI();

	}

	public void addInputDefToTreeModel() {

	}


	public void removeInputDefFromTreeModel(InputDef inputDef) {
		System.out.println("REMOVING INPUT DEF id = "
				+ inputDef.getIdentifier());

		DefaultMutableTreeNode nodeToDelete = null;

		Enumeration e = inputNode.breadthFirstEnumeration();

		while (e.hasMoreElements()) {
			DefaultMutableTreeNode element = (DefaultMutableTreeNode) e
					.nextElement();

			Object userObject = element.getUserObject();

			if (userObject instanceof InputDef) {
				InputDef otherInputDef = (InputDef) userObject;

				if (inputDef.equals(otherInputDef)) {
					nodeToDelete = element;
				}
			}
		}
		nodeToDelete.removeFromParent();

		getOutputTree().updateUI();

	}

	public OutputTree getOutputTree() {
		return outputTree;
	}

}
