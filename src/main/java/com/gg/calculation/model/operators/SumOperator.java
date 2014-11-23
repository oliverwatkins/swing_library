package com.gg.calculation.model.operators;

public class SumOperator extends MultiOperator {

	static final public String SYMBOL = new String("SUM");

	public String getSymbol() {
		return SYMBOL;
	}

	public String getName() {
		return "Sum";
	}

	public Long getIdentifier() {
		return 6l;
	}

	public SumOperator clone() {
		return (SumOperator) super.clone();
	}
}