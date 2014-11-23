package com.gg.calculation.model.operators;

import com.gg.calculation.model.BinaryEmptyNode;
import com.gg.calculation.model.CalculationObject;

public abstract class BinaryOperator extends Operator {

	private CalculationObject firstOperand;
	private CalculationObject secondOperand;

	public BinaryOperator() {
		setFirstOperand(new BinaryEmptyNode(1));
		setSecondOperand(new BinaryEmptyNode(1));
	}

	/**
	 * @return
	 */
	public CalculationObject getFirstOperand() {
		return firstOperand;
	}

	/**
	 * @return
	 */
	public CalculationObject getSecondOperand() {
		return secondOperand;
	}

	/**
	 * @param object
	 */
	public void setFirstOperand(CalculationObject object) {
		firstOperand = object;
	}

	/**
	 * @param object
	 */
	public void setSecondOperand(CalculationObject object) {
		secondOperand = object;
	}

	public BinaryOperator clone() {
		CalculationObject co1 = null;
		CalculationObject co2 = null;

		if (getFirstOperand() != null)
			co1 = (CalculationObject) getFirstOperand().clone();
		if (getSecondOperand() != null)
			co2 = (CalculationObject) getSecondOperand().clone();

		BinaryOperator bo = null;

		bo = (BinaryOperator) super.clone();

		bo.setFirstOperand(co1);
		bo.setSecondOperand(co2);

		return bo;
	}
}
