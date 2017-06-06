package com.gg.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import com.gg.filter.ComponentFilter.NoComponentFilter;

/**
 * {@link ListModel} that allows for filtering of elements. When changing the filter, the whole list will get updated according to the filtering. Accessing data
 * from the model will access the filtered version of the list, but an unfiltered copy is held locally and can be accessed by calling
 * {@link #getUnfilteredData()}.
 */
public class FilterListModel<T> extends AbstractListModel<T> implements FilterableComponentModel {

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

	@Override
	public void setFilter(ComponentFilter filter) {
		if (filter == null)
			filter = NoComponentFilter.INSTANCE;
		this.filter = filter;
		updateList();
	}

	@Override
	public ComponentFilter getFilter() {
		return filter;
	}

	@Override
	public void setFilterText(String filterText) {
		this.filterText = filterText;
		updateList();
	}

	@Override
	public String getFilterText() {
		return filterText;
	}

	public T[] getUnfilteredData() {
		return data;
	}

	/**
	 * Has to be called after the list changed or could change due to changes in the filtering. Will be called automatically except for if some of the elements
	 * of the data array were changed internally. Just make them immutable, please.
	 */
	public void updateList() {
		filtered = Arrays.asList(data).stream().filter(f -> filter.accepts(filterText, f.toString())).collect(Collectors.toList());
		fireContentsChanged(this, 0, filtered.size());
	}
}