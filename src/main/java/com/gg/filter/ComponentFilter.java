package com.gg.filter;

import java.util.BitSet;

public interface ComponentFilter {

	public boolean accepts(String filterText, String value);

	public BitSet highlight(String filterText, String value);

	public class NoComponentFilter implements ComponentFilter {

		@Override
		public boolean accepts(String filterText, String value) {
			return true;
		}

		@Override
		public BitSet highlight(String filterText, String value) {
			return new BitSet(value.length());
		}

	}

	public class ContainsComponentFilter implements ComponentFilter {

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

	public class StartsWithComponentFilter implements ComponentFilter {

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

	public class EndsWithComponentFilter implements ComponentFilter {

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