package com.gg.filter;

/**
 * Models that provide filtering support for their respective component should implement this interface to allow for easier handling in a more abstract and
 * standardized way.
 * 
 * Every class implementing this interface should have two fields for the filter and the filter text. The filter tells how and if elements get filtered, the
 * filter text is the actual search mask. The four methods of this interface should be getters and setters for these fields.
 * 
 * @author piegames
 */
public interface FilterableComponentModel {

	public void setFilter(ComponentFilter filter);

	public ComponentFilter getFilter();

	public void setFilterText(String filterText);

	public String getFilterText();
}
