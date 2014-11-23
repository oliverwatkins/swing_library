package com.gg.calculation.model.operators;

public class AdditionOperator extends BinaryOperator {
	/**
	 * This matches the DB TABLE OPERATOR_ID value
	 */
	public static final Long OPERATOR_ID = new Long(1);

	/**
	 * Comment for <code>SYMBOL</code>
	 */
	public static final String SYMBOL = new String("+");

	public String getSymbol() {
		return SYMBOL;
	}
	
	public String getName() {
		return "Addition";
	}
	
	public Long getIdentifier() {
		return OPERATOR_ID;
	}

	public AdditionOperator clone() {
		return (AdditionOperator) super.clone();
	}
}
