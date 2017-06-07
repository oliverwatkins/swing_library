package com.gg.filter;

import java.awt.Component;
import java.util.BitSet;
import java.util.Objects;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * A default renderer for lists with a {@link FilterListModel}. It extends the {@link DefaultListCellRenderer} by marking all chars that should be highlighted
 * according to the {@link ComponentFilter} that is currently used in bold. If the flag {@code #highlightAll} is set, the whole element will be displayed bold
 * if at least one character should be highlighted.
 * 
 * @author piegames
 */
public class FilterListCellRenderer extends DefaultListCellRenderer {

	private static final long		serialVersionUID	= 4973941658469836335L;

	protected FilterListModel<?>	model;
	public boolean					highlightAll;

	public FilterListCellRenderer(FilterListModel<?> model) {
		this(model, false);
	}

	public FilterListCellRenderer(FilterListModel<?> model, boolean highlightAll) {
		this.model = Objects.requireNonNull(model);
		this.highlightAll = highlightAll;
	}

	@Override
	public Component getListCellRendererComponent(
			JList<?> list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		setFont(list.getFont().deriveFont(0));

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