package com.gg.calculation.model.operators;

import com.gg.calculation.model.CalculationObject;

public abstract class Operator implements CalculationObject {
	// level this operator is embedded in another calculation
	protected int embeddedLevel;

	public abstract String getSymbol();

	public abstract String getName();

	public abstract Long getIdentifier();

	protected boolean ignoreNulls = true;

	public String toString() {
		return getSymbol();
	}

	public Operator clone() {
		try {
			return (Operator)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
