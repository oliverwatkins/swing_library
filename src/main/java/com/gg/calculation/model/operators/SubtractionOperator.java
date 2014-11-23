package com.gg.calculation.model.operators;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SubtractionOperator extends BinaryOperator {
	/**
	 * This matches the DB TABLE OPERATOR_ID value
	 */
	static final public Long OPERATOR_ID = new Long(2);

	/**
	 * Comment for <code>SYMBOL</code>
	 */
	static final public String SYMBOL = new String("-");

	/**
	 * @return
	 */
	public String getSymbol() {
		return SYMBOL;
	}

	public String getName() {
		return "Subtraction";
	}

	/**
	 * @see calculation.operators.standardandpoors.beaker.shared.calculationengine.Operator#getIdentifier()
	 */
	public Long getIdentifier() {
		return OPERATOR_ID;
	}

	public SubtractionOperator clone() {
		return (SubtractionOperator) super.clone();
	}
}
