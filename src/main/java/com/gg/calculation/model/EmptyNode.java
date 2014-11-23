package com.gg.calculation.model;


public abstract class EmptyNode implements CalculationObject {

	private String symbol;

	public EmptyNode(String stringRep) {
		this.symbol = stringRep;
	}

	public String toString() {
		return symbol;
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
