package com.gg.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.AbstractListModel;
import com.gg.filter.ComponentFilter.NoComponentFilter;

public class FilterListModel<T> extends AbstractListModel<T> {

	private static final long	serialVersionUID	= 6865110265691571982L;

	private T[]					data;
	private List<T>				filtered;

	protected ComponentFilter	filter;
	protected String			filterText;

	public FilterListModel(ComponentFilter filter, T[] data) {
		this.data = Objects.requireNonNull(data);
		filtered = new ArrayList<>();
		setFilter(filter);
	}

	@Override
	public int getSize() {
		return filtered.size();
	}

	@Override
	public T getElementAt(int index) {
		if (index < 0)
			return null;
		return filtered.get(index);
	}

	public void setFilter(ComponentFilter filter) {
		if (filter == null)
			filter = new NoComponentFilter();
		this.filter = filter;
		updateList();
	}

	public ComponentFilter getFilter() {
		return filter;
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText;
		updateList();
	}

	public String getFilterText() {
		return filterText;
	}

	public void updateList() {
		filtered = Arrays.asList(data).stream().filter(f -> filter.accepts(filterText, f.toString())).collect(Collectors.toList());
		fireContentsChanged(this, 0, filtered.size());
	}
}