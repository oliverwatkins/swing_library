package com.gg.calculation.model;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.gg.calculation.UIConstants;
import com.gg.calculation.model.operators.BinaryOperator;
import com.gg.calculation.model.operators.Function;
import com.gg.calculation.model.operators.MultiOperator;
import com.gg.calculation.model.operators.Operator;

/**
 * GUI implementation of CalculationShared
 */
public class Calculation extends CalculationShared implements Comparable {

	private static final long serialVersionUID = 1L;

	private boolean isRatio;

	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

	/**
	 * @param isRatio
	 * @param ident
	 * @param nameStr
	 */
	public Calculation(LongIdentifier identifier, String name, boolean isRatio) {
		super(identifier, name);
		this.isRatio = isRatio;
	}

	/**
	 * @param calcIdent
	 * @param calcName
	 * @param object
	 * @param profileID
	 */
	public Calculation(LongIdentifier calcIdent, String calcName,
			CalculationObject calculationRootObject, LongIdentifier profileID) {

		super(calcIdent, calcName, calculationRootObject, profileID);
	}

	/**
	 * GUI thing
	 * 
	 * 
	 * TODO
	 * 
	 * @return
	 */
	public boolean isCalculationComplete() {
		// iterate over root object
		if (getRootCalculationObject() instanceof Operator) {
			return doesCalculationObjectContainEmpty(getRootCalculationObject());
		} else
			return false;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		StringBuffer buf = new StringBuffer();
		buf.append("<html><body> = ");
 
		if (getRootCalculationObject() instanceof Operator
				|| getRootCalculationObject() instanceof Function
				|| getRootCalculationObject() instanceof InputDefShared
				|| getRootCalculationObject() instanceof CalculationShared
				|| getRootCalculationObject() instanceof ValueNode) {
			buf.append(constructCalculationString(getRootCalculationObject()));
		} else {
			// ??
			// System.out.println("this.getRootCalculationObject() " +
			// this.getRootCalculationObject());

		}
		return buf.toString();
	}

	/**
	 * we pass in both the current object from the object model, as well as the
	 * current tree node we are dealing with.
	 * 
	 * @param operator
	 * @return
	 */
	private boolean doesCalculationObjectContainEmpty(CalculationObject operator) {
		boolean empty = false;

		if (operator instanceof MultiOperator) {
			MultiOperator mo = (MultiOperator) operator;
			List<CalculationObject> list = mo.getOperandList();
			
			for (CalculationObject element : list) {
				CalculationObject co = element;
				empty = doesCalculationObjectContainEmpty(co);
			}
		} else if (operator instanceof BinaryOperator) {
			BinaryOperator bo = (BinaryOperator) operator;
			CalculationObject co1 = bo.getFirstOperand();
			CalculationObject co2 = bo.getSecondOperand();

			if (co1 instanceof EmptyNode) {
				return true;
			} else {
				empty = doesCalculationObjectContainEmpty(co1);
			}
			if (co2 instanceof EmptyNode) {
				return true;
			} else {
				empty = doesCalculationObjectContainEmpty(co1);
			}
		}

		return empty;
	}

	/**
	 * @param calculationObject
	 * @return
	 */
	private String constructCalculationString(
			CalculationObject calculationObject) {
		StringBuffer buf = new StringBuffer();

		if (calculationObject instanceof BinaryOperator) {
			BinaryOperator bo = (BinaryOperator) calculationObject;

			buf.append("(" + constructCalculationString(bo.getFirstOperand())
					+ " <font color=" + UIConstants.OPERATOR_COLOUR + "> "
					+ bo.getSymbol() + " </font> "
					+ constructCalculationString(bo.getSecondOperand()) + ")");
		}
		if (calculationObject instanceof Function) {
			StringBuffer bracketString = new StringBuffer();

			Function function = (Function) calculationObject;

			// TODO: should never be null. so remove this code.
			if (function.getFunctionInputList() != null) {

				Iterator iter = function.getFunctionInputList().iterator();
				while (iter.hasNext()) {
					CalculationObject calcObj = (CalculationObject) iter.next();

					bracketString.append(constructCalculationString(calcObj)
							+ ",");
				}
			}
			buf.append("<font color=" + UIConstants.FUNCTION_COLOUR + ">"
					+ function.getSymbol() + "</font>" + "(" + bracketString
					+ ")");

		}
		if (calculationObject instanceof FunctionInput) {
			FunctionInput functionInput = (FunctionInput) calculationObject;

			buf.append(constructCalculationString(functionInput.getValue()));
		} else if (calculationObject instanceof MultiOperator) {
			StringBuffer bracketString = new StringBuffer();

			MultiOperator mo = (MultiOperator) calculationObject;

			if (mo.getOperandList() != null) {

				Iterator iter = mo.getOperandList().iterator();
				while (iter.hasNext()) {
					CalculationObject calcObj = (CalculationObject) iter.next();

					bracketString.append(constructCalculationString(calcObj)
							+ ",");
				}
			}
			buf.append("<font color=" + UIConstants.OPERATOR_COLOUR + ">"
					+ mo.getSymbol() + "</font>" + "(" + bracketString + ")");
		} else if (calculationObject instanceof InputDefShared) {
			InputDefShared id = (InputDefShared) calculationObject;

			String relRepPeriodString = "";

			if (id.getRelativeReportingPeriod() != 0) {
				relRepPeriodString = "<font color="
						+ UIConstants.REL_REP_PERIOD_COLOUR + ">" + "["
						+ id.getRelativeReportingPeriod() + "]" + "</font>";
			}

			buf.append("<font color=" + UIConstants.INPUT_DEF_COLOUR + ">"
					+ id.getName() + relRepPeriodString + "</font>");
		} else if (calculationObject instanceof ValueNode) {
			ValueNode vn = (ValueNode) calculationObject;

			buf.append("<font color=" + UIConstants.VALUE_COLOUR + ">"
					+ vn.getValue() + "</font>");
		} else if (calculationObject instanceof CalculationShared) {
			CalculationShared ca = (CalculationShared) calculationObject;

			buf.append("<font color=" + UIConstants.CALCULATION_COLOUR + ">"
					+ ca.getName() + "</font>");

		} else if (calculationObject instanceof EmptyNode) {
			EmptyNode e = (EmptyNode) calculationObject;
			buf.append("<font color=" + UIConstants.EMPTY_NODE_COLOUR + ">"
					+ e.toString() + "</font>");
		}
		return buf.toString();
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int compareTo(Object o) {
		Calculation otherCalc = (Calculation) o;

		// return negative otherwise it sorts upside down.
		return -otherCalc.getName().compareToIgnoreCase(this.getName());
	}

	public boolean isRatio() {
		return isRatio;
	}

	public void setRatio(boolean isRatio) {
		this.isRatio = isRatio;
	}

}
