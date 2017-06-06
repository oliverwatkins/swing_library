package com.gg.filter;

import java.util.BitSet;

/**
 * Objects of this class represent a filter that can be used to show only certain entries in a Component. "Data entry" refers to list or tree elements, but with
 * proper models it can also be applied to other components like tables and more.
 * 
 * There are four pre-made filters declared as static subclasses of this class allowing to filter text that starts with, contains, or ends with the filter
 * phrase. But this interface could also be used to filter by date or by more complex patterns if needed.
 */
public interface ComponentFilter {

	/**
	 * Tests if a certain String can be shown with the current filtering. It is up to the model of the component how to handle the result of calling this
	 * method. By default, it should hide all data entries failing this method, but it could also be that those are only greyed out or so.
	 * 
	 * @param filterText the text to filter the data with
	 * @param value the text value of the data entry to test
	 * @return true if the text of the data entry can be shown, false otherwise.
	 */
	public boolean accepts(String filterText, String value);

	/**
	 * Tells which characters of the current data entry should be highlighted according to the current filtering. For each character in <code>value</code> the
	 * returned BitSet will contain a bit at the same position telling if highlighting is turned on (bit set) for that character or off (bit not set). How
	 * highlighting is handled depends on the renderer used to render the component but typing the font in bolt is default.
	 * 
	 * @param filterText the text to filter the data with
	 * @param value the text value of the data entry to render
	 * @return a {@link BitSet} where each bit tells if the character of <code>value</code> at the same position should be highlighted. If
	 *         {@link #accepts(String, String)} would return {@code false} because {@code value} does not math the filter, no bit will be set.
	 */
	public BitSet highlight(String filterText, String value);

	/**
	 * Objects of this class will accept all strings regardless of the input and always return an empty BitSet meaning no text highlighting. Use this instead of
	 * {@code null}.
	 */
	public static class NoComponentFilter implements ComponentFilter {

		public static NoComponentFilter INSTANCE = new NoComponentFilter();

		/**
		 * @deprecated Use {@link NoComponentFilter#INSTANCE} instead.
		 */
		@Deprecated
		private NoComponentFilter() {
		}

		@Override
		public boolean accepts(String filterText, String value) {
			return true;
		}

		@Override
		public BitSet highlight(String filterText, String value) {
			return new BitSet(value.length());
		}

	}

	/** Filters out all values that don't contain the filter text at least once, optionally case sensitive, highlighting all occurrences of the filter text. */
	public static class ContainsComponentFilter implements ComponentFilter {

		public boolean caseSensitive;

		public ContainsComponentFilter() {
			this(false);
		}

		public ContainsComponentFilter(boolean caseSensitive) {
			this.caseSensitive = caseSensitive;
		}

		@Override
		public boolean accepts(String filterText, String value) {
			return value == null || filterText == null || (caseSensitive ? value.contains(filterText) : value.toLowerCase().contains(filterText.toLowerCase()));
		}

		@Override
		public BitSet highlight(String filterText, String value) {
			BitSet ret = new BitSet(value.length());
			if (filterText != null && !filterText.isEmpty()) {
				if (!caseSensitive) {
					filterText = filterText.toLowerCase();
					value = value.toLowerCase();
				}
				int pos = 0;
				while (pos <= value.length()) {
					pos = value.indexOf(filterText, pos);
					if (pos == -1)
						break;
					ret.set(pos, pos + filterText.length());
					pos += filterText.length() + 1;
				}
			}
			return ret;
		}
	}

	/**
	 * Filters out all values that don't start with the filter text at least once, optionally case sensitive, highlighting the first bit containing the filter
	 * text.
	 */
	public static class StartsWithComponentFilter implements ComponentFilter {

		public boolean caseSensitive;

		public StartsWithComponentFilter() {
			this(false);
		}

		public StartsWithComponentFilter(boolean caseSensitive) {
			this.caseSensitive = caseSensitive;
		}

		@Override
		public boolean accepts(String filterText, String value) {
			return value == null || filterText == null || (caseSensitive ? value.startsWith(filterText) : value.toLowerCase().startsWith(filterText.toLowerCase()));
		}

		@Override
		public BitSet highlight(String filterText, String value) {
			BitSet ret = new BitSet(value.length());
			if (filterText != null && !filterText.isEmpty()) {
				if (!caseSensitive) {
					filterText = filterText.toLowerCase();
					value = value.toLowerCase();
				}
				if (accepts(filterText, value))
					ret.set(0, filterText.length());
			}
			return ret;
		}
	}

	/**
	 * Filters out all values that don't end with the filter text at least once, optionally case sensitive, highlighting the last bit containing the filter
	 * text.
	 */
	public static class EndsWithComponentFilter implements ComponentFilter {

		public boolean caseSensitive;

		public EndsWithComponentFilter() {
			this(false);
		}

		public EndsWithComponentFilter(boolean caseSensitive) {
			this.caseSensitive = caseSensitive;
		}

		@Override
		public boolean accepts(String filterText, String value) {
			return value == null || filterText == null || (caseSensitive ? value.endsWith(filterText) : value.toLowerCase().endsWith(filterText.toLowerCase()));
		}

		@Override
		public BitSet highlight(String filterText, String value) {
			BitSet ret = new BitSet(value.length());
			if (filterText != null && !filterText.isEmpty()) {
				if (!caseSensitive) {
					filterText = filterText.toLowerCase();
					value = value.toLowerCase();
				}
				if (accepts(filterText, value))
					ret.set(value.length() - filterText.length(), value.length());
			}
			return ret;
		}
	}
}