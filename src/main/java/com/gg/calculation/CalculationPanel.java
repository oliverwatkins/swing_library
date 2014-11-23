package com.gg.calculation;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.gg.calculation.model.BinaryEmptyNode;
import com.gg.calculation.model.Calculation;
import com.gg.calculation.model.LongIdentifier;

public class CalculationPanel extends JPanel {

	private JEditorPane calculationEditorPane = new JEditorPane();

	private CalculationTree tree;
	private CalculationTreeModel treeModel;

	public CalculationPanel() {
		init();
		setCalculation(new Calculation(new LongIdentifier(1), "asdf", null, null));
	}

	/**
	 * 
	 */
	private void init() {
		// set editor pane to render HTML and turn editable off
		calculationEditorPane.setContentType("text/html");
		calculationEditorPane.setEditable(true);

		// TODO: not working
		calculationEditorPane.setCursor(Cursor
				.getPredefinedCursor(Cursor.TEXT_CURSOR));

		setBorder(BorderFactory.createTitledBorder("Calculation Definition"));

		treeModel = new CalculationTreeModel(getRootNode());

		tree = new CalculationTree(treeModel, this);

		// tree.getSelectionModel().setS
		// tree.addTreeExpansionListener(treeModel);
		tree.setShowsRootHandles(true);

		tree.expandAll();
		CalculationTreeRenderer renderer = new CalculationTreeRenderer();
		tree.setCellRenderer(renderer);
		tree.setCellEditor(new CalculationTreeEditor(this));
		tree.setEditable(true);
		tree.setRootVisible(true);

		setLayout(new GridBagLayout());

		add(new JScrollPane(tree), new GridBagConstraints(
		/* Grid x */0,
		/* Grid y */0,
		/* width */1,
		/* height */1,
		/* weight x */1,
		/* weight y */1,
		/* anchor */GridBagConstraints.NORTH,
		/* fill */GridBagConstraints.BOTH,
		/* Inserts */new Insets(2, 2, 2, 2),
		/* min pad x */0,
		/* min pad y */0));
		add(new JScrollPane(calculationEditorPane), new GridBagConstraints(
		/* Grid x */0,
		/* Grid y */1,
		/* width */1,
		/* height */1,
		/* weight x */1,
		/* weight y */.2,
		/* anchor */GridBagConstraints.NORTH,
		/* fill */GridBagConstraints.BOTH,
		/* Inserts */new Insets(2, 2, 2, 2),
		/* min pad x */0,
		/* min pad y */0));
		updateCalculationText();
	}

	private TreeNode getRootNode() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
				new BinaryEmptyNode(0));

		return rootNode;
	}

	public void updateCalculationText() {
		if (treeModel.getCalculation() == null)
			calculationEditorPane.setText("");
		else
			calculationEditorPane
					.setText(treeModel.getCalculation().toString());
	}

	public void setCalculation(Calculation calc) {
		treeModel.setCalculation(calc);
		tree.expandAll();
		updateCalculationText();
	}

	public CalculationTreeModel getTreeModel() {
		return treeModel;
	}

	public CalculationTree getTree() {
		return tree;
	}
}
