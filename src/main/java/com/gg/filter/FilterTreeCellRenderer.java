package com.gg.filter;

import java.awt.Component;
import java.util.BitSet;
import java.util.Objects;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class FilterTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long	serialVersionUID	= 5742895132682024835L;
	protected FilterTreeModel	model;
	public boolean				highlightAll;

	public FilterTreeCellRenderer(FilterTreeModel model) {
		this(model, false);
	}

	public FilterTreeCellRenderer(FilterTreeModel model, boolean highlightAll) {
		this.model = Objects.requireNonNull(model);
		this.highlightAll = highlightAll;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel,
			boolean expanded,
			boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		setFont(tree.getFont().deriveFont(0));

		String text = getText();
		ComponentFilter filter = model.getFilter();
		String filterText = model.getFilterText();

		BitSet highlight = filter.highlight(filterText, text);
		String newText = "<html>";
		if (highlightAll)
			newText += (filter.accepts(filterText, text) && !highlight.isEmpty()) ? "<b>" + text + "</b>" : text;
		else {
			int pos = 0;
			boolean set = highlight.get(0);
			while (pos < text.length()) {
				int nextPos = set ? highlight.nextClearBit(pos) : highlight.nextSetBit(pos);
				if (nextPos == -1)
					nextPos = text.length();
				if (set)
					newText += "<b>" + text.substring(pos, nextPos) + "</b>";
				else
					newText += text.substring(pos, nextPos);
				pos = nextPos;
				set = highlight.get(pos);
			}
		}
		newText += "</html>";
		setText(newText);
		return this;
	}
}
