package com.gg.calculation;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import com.gg.calculation.model.operators.AdditionOperator;
import com.gg.calculation.model.operators.AverageOperator;
import com.gg.calculation.model.operators.DivisionOperator;
import com.gg.calculation.model.operators.MultiplicationOperator;
import com.gg.calculation.model.operators.Operator;
import com.gg.calculation.model.operators.SubtractionOperator;
import com.gg.calculation.model.operators.SumOperator;

public class OperatorTableModel extends AbstractTableModel {

	private ArrayList<Operator> operators;
	private static String[] schema = { "Symbol", "Name" };
	
	public OperatorTableModel(Collection<Operator> c) {
		operators = new ArrayList<Operator>(c);
	}

	public int getRowCount() {
		return operators.size();
	}

	public int getColumnCount() {
		return schema.length;
	}

	public Object getValueAt(int r, int c) {
		
		Operator operator = operators.get(r);
		switch (c) {
			case 0:
				return operator.getSymbol();
			case 1:
				return operator.getName();
		}
		return null;
	}

	/**
	 * create a dublicate instance, otherwise we are copying from one table to
	 * another.
	 * 
	 * @param i
	 * @return
	 */
	public Operator getOperatorAt(int i) {
		Operator op = operators.get(i);

		if (op instanceof AdditionOperator) {
			AdditionOperator ao = new AdditionOperator();
			return ao;
		}
		if (op instanceof AverageOperator) {
			AverageOperator ao = new AverageOperator();
			return ao;
		}
		if (op instanceof DivisionOperator) {
			DivisionOperator oper = new DivisionOperator();
			return oper;
		}
		if (op instanceof MultiplicationOperator) {
			MultiplicationOperator oper = new MultiplicationOperator();
			return oper;
		}
		if (op instanceof SubtractionOperator) {
			SubtractionOperator oper = new SubtractionOperator();
			return oper;
		}
		if (op instanceof SumOperator) {
			SumOperator ao = new SumOperator();
			return ao;
		} else {
			System.out.println("error in operator table model");
			return null;
		}
	}

	public String getColumnName(int c) {
		return schema[c];
	}
}
