package com.gg.calculation.model;


/**
 * A wrapper for an input definition so that it can be used in calculations and
 * report definitions.
 * 
 */
public class InputDef extends InputDefShared {

	public InputDef(String name, LongIdentifier identifier) {
		super(name, identifier);
	}

	public String toString() {
		return getName();
	}

	public boolean equals(Object o) {
		InputDef inputDef = (InputDef) o;

		if (this.getIdentifier().equals(inputDef.getIdentifier()))
			return true;
		else {
			return false;
		}
	}

}
