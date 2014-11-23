package com.gg.calculation.model.operators;





public class MultiplicationOperator extends BinaryOperator {
	/**
	 * This matches the DB TABLE OPERATOR_ID value
	 */
	static final public Long OPERATOR_ID = new Long(3);

	/**
	 * Comment for <code>SYMBOL</code>
	 */
	static final public String SYMBOL = new String("*");

	/**
	 * @return
	 */
	public String getSymbol() {
		return SYMBOL;
	}

	/**
	 * @return
	 */
	public String getName() {
		return "Multiplication";
	}

	/**
	 * @see calculation.operators.standardandpoors.beaker.shared.calculationengine.Operator#getIdentifier()
	 */
	public Long getIdentifier() {
		return OPERATOR_ID;
	}

	public MultiplicationOperator clone() {
		return (MultiplicationOperator) super.clone();// new AdditionOperator();
	}
}