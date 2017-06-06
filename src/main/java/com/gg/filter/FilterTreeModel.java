package com.gg.filter;

import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import com.gg.filter.ComponentFilter.NoComponentFilter;

/**
 * {@link TreeModel} that allows for filtering of elements. When changing the filter, the whole tree will get updated according to the filtering. Accessing data
 * from the model will access the filtered version of the tree and changes will be reset with the next filter update, but an unfiltered copy is held locally and
 * can be accessed by calling {@link #getUnfilteredRoot()}.
 */
public class FilterTreeModel extends DefaultTreeModel implements FilterableComponentModel {

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

	@Override
	public void setFilter(ComponentFilter filter) {
		if (filter == null)
			filter = NoComponentFilter.INSTANCE;
		this.filter = filter;
		updateTree();
	}

	@Override
	public ComponentFilter getFilter() {
		return filter;
	}

	@Override
	public void setFilterText(String filterText) {
		this.filterText = filterText;
		updateTree();
	}

	@Override
	public String getFilterText() {
		return filterText;
	}

	/**
	 * Will set the new root node for the tree. This object will be stored as {@code #unfilteredRoot} that can be requested through {@link #getUnfilteredRoot()}
	 * . The node will be pruned according to the filter and updated. {@link #getRoot()} will return the filtered root from now.
	 */
	@Override
	public void setRoot(TreeNode root) {
		this.unfilteredRoot = (DefaultMutableTreeNode) root;
		updateTree();
	}

	/**
	 * Has to be called after the tree changed, or the displayed tree could change due to changes in the filtering. Will be called automatically except for if
	 * the tree object was changed internally.
	 */
	public void updateTree() {
		super.setRoot(prune(copyNode(unfilteredRoot)));
	}

	public DefaultMutableTreeNode getUnfilteredRoot() {
		return unfilteredRoot;
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