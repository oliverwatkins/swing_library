package com.gg.calculation.model.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gg.calculation.model.CalculationObject;

/**
 * @author WatkinsO
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class MultiOperator extends Operator {

	/**
	 * Comment for <code>operandList</code>
	 */
	private List<CalculationObject> operandList;

	public MultiOperator() {
		operandList = new ArrayList<CalculationObject>();
	}
	
	public List<CalculationObject> getOperandList() {
		// TODO Auto-generated method stub
		return operandList;
	}

	/**
	 * @param list
	 */
	public void setOperandList(List<CalculationObject> list) {
		operandList = list;
	}

	/**
	 * @param co
	 */
	public void addOperand(CalculationObject co) {
		operandList.add(co);
	}

	/**
	 * @param selectedNodeObject
	 */
	public void removeOperand(CalculationObject selectedNodeObject) {
		operandList.remove(selectedNodeObject);
	}

	public MultiOperator clone() {
		Collection<CalculationObject> operands = getOperandList();

		MultiOperator mo = (MultiOperator) super.clone();

		ArrayList<CalculationObject> newOperandList = new ArrayList<CalculationObject>();

		for (CalculationObject element : operands) {
			CalculationObject co = element;
			newOperandList.add((CalculationObject)co.clone());

		}
		mo.setOperandList(newOperandList);

		return mo;

	}
}
