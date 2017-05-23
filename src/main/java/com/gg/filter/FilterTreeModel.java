package com.gg.filter;

import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import com.gg.filter.ComponentFilter.NoComponentFilter;

public class FilterTreeModel extends DefaultTreeModel {

	private static final long			serialVersionUID	= -8714629473939341376L;

	protected DefaultMutableTreeNode	unfilteredRoot;
	protected ComponentFilter			filter;
	protected String					filterText;

	public FilterTreeModel(ComponentFilter filter, TreeNode root) {
		super(root);
		setAsksAllowsChildren(true);
		setFilter(filter);
		setRoot(root);
		updateTree();
	}

	public FilterTreeModel(ComponentFilter filter, TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		setFilter(filter);
	}

	public void setFilter(ComponentFilter filter) {
		if (filter == null)
			filter = new NoComponentFilter();
		this.filter = filter;
		updateTree();
	}

	public ComponentFilter getFilter() {
		return filter;
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText;
		updateTree();
	}

	public String getFilterText() {
		return filterText;
	}

	@Override
	public void setRoot(TreeNode root) {
		this.unfilteredRoot = (DefaultMutableTreeNode) root;
		updateTree();
	}

	public void updateTree() {
		super.setRoot(prune(copyNode(unfilteredRoot)));
	}

	/**
	 * Clone/Copy a tree node. TreeNodes in Swing don't support deep cloning.
	 * 
	 * @param orig to be cloned
	 * @return cloned copy
	 */
	private DefaultMutableTreeNode copyNode(final DefaultMutableTreeNode orig) {
		if (orig == null)
			return null;
		DefaultMutableTreeNode newOne = new DefaultMutableTreeNode();
		newOne.setUserObject(orig.getUserObject());
		newOne.setAllowsChildren(!orig.isLeaf());

		Enumeration<DefaultMutableTreeNode> enm = orig.children();
		while (enm.hasMoreElements()) {
			DefaultMutableTreeNode child = enm.nextElement();
			newOne.add(copyNode(child));
		}
		return newOne;
	}

	public DefaultMutableTreeNode prune(DefaultMutableTreeNode root) {
		if (root == null)
			return null;
		boolean badLeaves = true;
		// keep looping through until tree contains only leaves that match
		while (badLeaves) {
			badLeaves = removeBadLeaves(root);
		}
		return root;
	}

	/**
	 * 
	 * @param root
	 * @return boolean bad leaves were returned
	 */
	private boolean removeBadLeaves(DefaultMutableTreeNode root) {

		// no bad leaves yet
		boolean badLeaves = false;

		// reference first leaf
		DefaultMutableTreeNode leaf = root.getFirstLeaf();

		// if leaf is root then its the only node
		if (leaf.isRoot())
			return false;

		int leafCount = root.getLeafCount(); // this get method changes if in for loop so have to define outside of it
		for (int i = 0; i < leafCount; i++) {

			DefaultMutableTreeNode nextLeaf = leaf.getNextLeaf();

			// if it does not start with the text then snip it off its parent
			if (!filter.accepts(filterText, leaf.getUserObject().toString())) {
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) leaf.getParent();

				if (parent != null)
					parent.remove(leaf);

				badLeaves = true;
			}
			leaf = nextLeaf;
		}
		return badLeaves;
	}
}