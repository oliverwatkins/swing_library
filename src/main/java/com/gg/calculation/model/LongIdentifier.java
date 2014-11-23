package com.gg.calculation.model;

import java.io.Serializable;

public class LongIdentifier implements Serializable{
	
	public Long keyValue;

	public LongIdentifier() {
		keyValue = null;
	}

	public LongIdentifier(long value) {
		this.keyValue = new Long(value);
	}

	public LongIdentifier(Long keyValue) {
		this.keyValue = keyValue;
	}

	public Long getLongValue() {
		return keyValue;
	}

	public int intValue() {
		return keyValue.intValue();
	}

	public boolean equals(Object object) {
		if (object instanceof LongIdentifier)
			return equals((LongIdentifier) object);
		return false;
	}

	public int hashCode() {
		return keyValue.hashCode();
	}

	public boolean equals(LongIdentifier identifier) {
		return (keyValue.equals(identifier.keyValue));
	}

	public String toString() {
		return keyValue.toString();
	}

}
