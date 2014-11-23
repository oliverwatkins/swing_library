package com.gg.calculation;

import java.awt.Point;
import java.awt.SystemColor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.gg.calculation.model.BinaryEmptyNode;
import com.gg.calculation.model.Calculation;
import com.gg.calculation.model.CalculationObject;
import com.gg.calculation.model.EmptyNode;
import com.gg.calculation.model.FunctionInput;
import com.gg.calculation.model.InputDefShared;
import com.gg.calculation.model.MultiEmptyNode;
import com.gg.calculation.model.operators.BinaryOperator;
import com.gg.calculation.model.operators.Function;
import com.gg.calculation.model.operators.MultiOperator;
import com.gg.calculation.model.operators.Operator;

public class CalculationTree extends JTree {

	// reference
	private CalculationPanel calculationPanel;

	private boolean modifiable;
	private DefaultMutableTreeNode selectedNode; // the selected node

	private DefaultTreeModel model;

	// list of DataFlavors supported
	public static final DataFlavor[] supportedFlavors = new DataFlavor[] {
			new DataFlavor(Calculation.class, "Calculation"),
			new DataFlavor(InputDefShared.class, "InputDefShared"),
			new DataFlavor(Operator.class, "Operator"),
			new DataFlavor(Operator.class, "Function") };

	/**
	 * 
	 * @param treeModel
	 * @param cPanel
	 */
	public CalculationTree(CalculationTreeModel treeModel,
			CalculationPanel cPanel) {
		// want reference to panel so we can update text field representation
		calculationPanel = cPanel;

		modifiable = true;

		this.setModel(treeModel);

		model = (DefaultTreeModel) this.getModel();

		// setting things up
		final DropTargetListener dropTargetListener = new DropTargetListener() {

			public void dragOver(DropTargetDragEvent dtde) {
				doDragOver(dtde);
			}

			public void drop(DropTargetDropEvent dtde) {
				doDrop(dtde);
			}

			// ignore these
			public void dropActionChanged(DropTargetDragEvent dtde) {
			}

			public void dragExit(DropTargetEvent dte) {
			}

			public void dragEnter(DropTargetDragEvent dtde) {
			}
		};

		final TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				selectedNode = (DefaultMutableTreeNode) e.getPath()
						.getLastPathComponent();
			}
		};
		addTreeSelectionListener(treeSelectionListener);

		DragSource dragSource = DragSource.getDefaultDragSource();
		DropTarget dropTarget = new DropTarget(this, dropTargetListener);

		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				handleDeleteEvent(e);
			}
		};
		registerKeyboardAction(action, KeyStroke.getKeyStroke(
				KeyEvent.VK_DELETE, 0), WHEN_FOCUSED);
	}


	public boolean isModifiable() {
		return modifiable;
	}

	public void setModifiable(boolean value) {
		this.modifiable = value;
		if (modifiable) {
			setBackground(SystemColor.window);
		} else {
			setBackground(SystemColor.menu);
		}
	}

	// DropTargetListener support
	private void doDragOver(DropTargetDragEvent dtde) {

		if (acceptDrag(dtde))
			dtde.acceptDrag(dtde.getDropAction());
		else
			dtde.rejectDrag();
	}

	/**
	 * Perform the drop
	 * 
	 * @param dtde
	 */
	private void doDrop(DropTargetDropEvent dtde) {

		dtde.acceptDrop(DnDConstants.ACTION_COPY);

		// get node where mouse is over
		Point location = dtde.getLocation();
		TreePath targetPath = this.getPathForLocation(location.x, location.y);
		DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) targetPath
				.getLastPathComponent();

		// get supported flavours
		Transferable transferable = dtde.getTransferable();
		DataFlavor[] flavor = transferable.getTransferDataFlavors();

		CalculationObject calculationObject = null;
		try {
			calculationObject = (CalculationObject) transferable
					.getTransferData(flavor[0]);
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * If the target drop node is empty, and the calculation object is not
		 * null then we can proceed to drop. Depending on what sort of a
		 * calculation object we have, we assemble tree nodes and attach.
		 */
		if (calculationObject != null
				&& targetNode.getUserObject() instanceof EmptyNode) {
			if (calculationObject instanceof BinaryOperator) {
				BinaryOperator bonO = (BinaryOperator) calculationObject;

				// attach two empty nodes
				targetNode.add(new DefaultMutableTreeNode(bonO
						.getFirstOperand()));
				targetNode.add(new DefaultMutableTreeNode(bonO
						.getSecondOperand()));

				attachCalculationObject(dtde, targetNode, calculationObject);
			} else if (calculationObject instanceof MultiOperator) {
				// targetNode.getChildCount();
				// attach one empty node
				targetNode
						.add(new DefaultMutableTreeNode(new MultiEmptyNode()));

				attachCalculationObject(dtde, targetNode, calculationObject);
			} else if (calculationObject instanceof Function) {
				System.out.println("calculationObject instanceof Function");
				Function function = (Function) calculationObject;

				ArrayList<CalculationObject> operands = function.getFunctionInputList();
				for (Iterator iter = operands.iterator(); iter.hasNext();) {
					System.out.println("we have a operand for function");
					FunctionInput element = (FunctionInput) iter.next();

					DefaultMutableTreeNode functionInputNode = new DefaultMutableTreeNode(
							element);
					functionInputNode.add(new DefaultMutableTreeNode(
							new BinaryEmptyNode(1)));
					targetNode.add(functionInputNode);
				}
				attachCalculationObject(dtde, targetNode, calculationObject);
			} else if (calculationObject instanceof Calculation) {
				attachCalculationObject(dtde, targetNode, calculationObject);
			} else if (calculationObject instanceof InputDefShared) {
				attachCalculationObject(dtde, targetNode, calculationObject);
			} else {
				System.out.println("<><> -- <><> rejecting drop");
				dtde.rejectDrop();
				return;
			}
			calculationPanel.updateCalculationText();
		} else {
			System.out.println("<><> -- <><> calculationObject is "
					+ calculationObject);
		}
	}

	/**
	 * Attach the calculation object
	 * 
	 * @param dtde
	 * @param targetNode
	 * @param object
	 */
	private void attachCalculationObject(DropTargetDropEvent dtde,
			DefaultMutableTreeNode targetNode,
			CalculationObject calculationObject) {
		System.out
				.println("we have CalculationObject to be placed on EmptyNode");

		// if target isn't root then get parent and check if multi operator
		if (!targetNode.isRoot()) {
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) targetNode
					.getParent();

			if (parent.getUserObject() instanceof MultiOperator) {
				// inserting into the tree model an empty tree node which
				// displays '*'
				model.insertNodeInto(new DefaultMutableTreeNode(
						new MultiEmptyNode()), parent, parent.getChildCount());

				// inserting into the actual object model
				MultiOperator mo = (MultiOperator) parent.getUserObject();

				mo.addOperand(calculationObject);

				// get location and path for location
				Point location = dtde.getLocation();
				TreePath path = this.getPathForLocation(location.x, location.y);

				// value at this location has changed.
				this.getModel().valueForPathChanged(path, calculationObject);

			} else if (parent.getUserObject() instanceof BinaryOperator) {
				// inserting into the actual object model
				BinaryOperator bo = (BinaryOperator) parent.getUserObject();

				// get location and path for location
				Point location = dtde.getLocation();

				TreePath path = this.getPathForLocation(location.x, location.y);

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
						.getLastPathComponent();

				int index = this.getModel().getIndexOfChild(parent, node);

				System.out.println("index of child = " + index);

				if (index == 0) {
					System.out.println("setting first operand type = "
							+ calculationObject.getClass());
					bo.setFirstOperand(calculationObject);
				} else if (index == 1) {
					System.out.println("setting second operand type = "
							+ calculationObject.getClass());
					bo.setSecondOperand(calculationObject);
				}

				// value at this location has changed.
				this.getModel().valueForPathChanged(path, calculationObject);
			} else if (parent.getUserObject() instanceof FunctionInput) {
				// inserting into the actual object model
				FunctionInput fi = (FunctionInput) parent.getUserObject();
				fi.setValue(calculationObject);

				// get location and path for location
				Point location = dtde.getLocation();

				TreePath path = this.getPathForLocation(location.x, location.y);

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
						.getLastPathComponent();

				int index = this.getModel().getIndexOfChild(parent, node);

				System.out.println("index of child = " + index);

				// value at this location has changed.
				this.getModel().valueForPathChanged(path, calculationObject);
			}
		} else if (targetNode.isRoot()) {
			System.out.println("targetNode.isRoot()");
			// get location and path for location
			Point location = dtde.getLocation();
			TreePath path = this.getPathForLocation(location.x, location.y);

			CalculationTreeModel model = (CalculationTreeModel) this.getModel();

			// model.setRoot(null);
			model.getCalculation().setRootCalculationObject(calculationObject);

			// not updating model

			// value at this location has changed.
			this.getModel().valueForPathChanged(path, calculationObject);
		}

		dtde.getDropTargetContext().dropComplete(true);

		this.expandAll();
	}

	/**
	 * acceptDrag. Can only be an empty node at this stage. In the future we may
	 * want to be able to drag and drop to replace nodes rather than just drag
	 * onto empty nodes.
	 * 
	 * @param dtde
	 * @return is acceptable
	 */
	private boolean acceptDrag(DropTargetDragEvent dtde) {
		Point location = dtde.getLocation();

		TreePath path = this.getPathForLocation(location.x, location.y);

		if (path != null) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();

			if ((CalculationObject) node.getUserObject() instanceof EmptyNode
					&& node.isLeaf()) {
				return true;
			}
		}
		return false;
		// DataFlavor[] flavors = dtde.getCurrentDataFlavors();
	}

	/**
	 * Deletes the selected tree node from the tree model, and within the
	 * calculation model.
	 * 
	 * @param e
	 */
	private void handleDeleteEvent(ActionEvent e) {
		System.out.println("delete event. selected node is " + selectedNode);

		CalculationTreeModel model = (CalculationTreeModel) getModel();
		TreePath path = new TreePath(model.getPathToRoot(selectedNode));

		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode
				.getParent();

		if (parentNode == null) {
			// we are at the root. replace the node with an empty node
			BinaryEmptyNode emptyNode = new BinaryEmptyNode(1);

			model.getCalculation().setRootCalculationObject(emptyNode);
			this.getModel().valueForPathChanged(path, emptyNode);

			// now remove all its children
			selectedNode.removeAllChildren();

		} else {
			CalculationObject selectedNodeObject = (CalculationObject) selectedNode
					.getUserObject();
			CalculationObject parentNodeObject = (CalculationObject) parentNode
					.getUserObject();

			if (!(selectedNodeObject instanceof EmptyNode)) {
				if (parentNodeObject instanceof MultiOperator) {
					// remove operand from multi operator operand list
					MultiOperator mo = (MultiOperator) parentNodeObject;
					mo.removeOperand(selectedNodeObject);

					// remove from selected node.
					selectedNode.removeFromParent();
				} else if (parentNodeObject instanceof BinaryOperator) {
					BinaryOperator bo = (BinaryOperator) parentNodeObject;
					int index = parentNode.getIndex(selectedNode);

					// remove from operand in model, and in tree model
					if (index == 0) {
						bo.setFirstOperand(new BinaryEmptyNode(1));
						selectedNode.setUserObject(new BinaryEmptyNode(1));
						selectedNode.removeAllChildren();

					} else if (index == 1) {
						bo.setSecondOperand(new BinaryEmptyNode(2));
						selectedNode.setUserObject(new BinaryEmptyNode(2));
						selectedNode.removeAllChildren();
					}
				} else if (parentNodeObject instanceof FunctionInput) {
					// remove value and add empty node to value
					FunctionInput mo = (FunctionInput) parentNodeObject;

					parentNode.add(new DefaultMutableTreeNode(
							new BinaryEmptyNode(1)));

					mo.setValue(null);

					// remove from selected node.
					selectedNode.removeFromParent();
				}
			}
		}

		// update UI, text panel and expand all
		updateUI();
		calculationPanel.updateCalculationText();
		expandAll();
	}


	/**
	 * Expand the entire tree
	 */
	public void expandAll() {
		int row = 0;
		while (row < getRowCount()) {
			expandRow(row);
			row++;
		}
	}

	public boolean isPathEditable(TreePath path) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
				.getLastPathComponent();
		if (node.getUserObject() instanceof EmptyNode)
			return true;
		else
			return false;
	}

}