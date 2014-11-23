
package com.gg.calculation;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.gg.calculation.model.Output;

public class OutputTree extends JTree implements DragGestureListener,
		DragSourceListener {

	/**
	 * 
	 * @param treeModel
	 * @param cPanel
	 */
	public OutputTree(OutputTreeModel treeModel) {
		this.setModel(treeModel);

		DragSource dragSource = DragSource.getDefaultDragSource();
		dragSource.createDefaultDragGestureRecognizer(this,
				DnDConstants.ACTION_COPY, this);
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

	public void dragGestureRecognized(DragGestureEvent dge) {
		
		Point location = dge.getDragOrigin();

		TreePath path = this.getPathForLocation(location.x, location.y);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
				.getLastPathComponent();

		System.out
				.println("The class of user object node is node.getUserObject() "
						+ node.getUserObject().getClass());

		Output o = (Output) node.getUserObject();

		if (o != null) {
			Transferable transferable = new SimpleTransferable(o);
			dge.startDrag(null, transferable, this);
		}
	}

	public void dragEnter(DragSourceDragEvent dsde) {
	}

	public void dragOver(DragSourceDragEvent dsde) {
	}

	public void dropActionChanged(DragSourceDragEvent dsde) {
	}

	public void dragExit(DragSourceEvent dse) {
	}

	public void dragDropEnd(DragSourceDropEvent dsde) {
	}

}
