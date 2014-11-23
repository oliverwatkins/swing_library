package com.gg.calculation.model.operators;


public class DivisionOperator extends BinaryOperator {
	
	
	/**
	 * This matches the DB TABLE OPERATOR_ID value
	 */
	public static final Long OPERATOR_ID = new Long(4);

	public static final String SYMBOL = new String("/");

	public String getSymbol() {
		return SYMBOL;
	}

	public String getName() {
		return "Division";
	}

	public Long getIdentifier() {
		return OPERATOR_ID;
	}

	public DivisionOperator clone() {
		return (DivisionOperator) super.clone();// new AdditionOperator();
	}
}