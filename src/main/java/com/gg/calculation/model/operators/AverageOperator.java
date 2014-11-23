package com.gg.calculation.model.operators;



public class AverageOperator extends MultiOperator {
	/**
	 * This matches the DB TABLE OPERATOR_ID value
	 */
	static final public Long OPERATOR_ID = new Long(7);

	static final public String SYMBOL = new String("Avg");

	public String getSymbol() {
		return SYMBOL;
	}

	public String getName() {
		return "Average";
	}

	public Long getIdentifier() {
		return OPERATOR_ID;
	}

	public AverageOperator clone() {
		return (AverageOperator) super.clone();
	}

}
