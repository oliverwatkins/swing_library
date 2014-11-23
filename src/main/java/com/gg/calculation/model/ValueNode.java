package com.gg.calculation.model;


public class ValueNode implements CalculationObject {
	private Double value;

	public ValueNode(Double i) {
		value = i;
	}

	public String toString() {
		return value.toString();
	}

	public Double getValue() {
		return value;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
