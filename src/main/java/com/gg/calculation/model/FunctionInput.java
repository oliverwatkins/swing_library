package com.gg.calculation.model;


public class FunctionInput implements CalculationObject {
	private String name;
	private CalculationObject value;

	public FunctionInput(String name, CalculationObject co) {
		this.name = name;
		value = co;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CalculationObject getValue() {
		return value;
	}

	public void setValue(CalculationObject value) {
		this.value = value;
	}

	public String toString() {
		return name;
	}

	public Object clone() {
		try {
			FunctionInput fi = (FunctionInput) super.clone();
			fi.setValue((CalculationObject) value.clone());
			return fi;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
