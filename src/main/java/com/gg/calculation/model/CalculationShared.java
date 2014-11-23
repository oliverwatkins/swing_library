package com.gg.calculation.model;


/**
 * A lightweight representation of a calculation that is used both on the server
 * and on the client.
 */
public class CalculationShared extends Output implements CalculationObject,
		Cloneable {

	// default relative reporting period
	private int defaultRelativeReportingPeriod;

	// name
	private String name;

	// long ID
	private LongIdentifier calculationID;

	// when performing calculations a reference to a reporting period is need to
	// allow caching.
	private LongIdentifier reportingPeriodID = new LongIdentifier(new Long(0));

	// root calculation object
	private CalculationObject calculationRootObject;

	private int embeddedLevel;

	public CalculationShared(LongIdentifier identifier, String name) {
		this.calculationID = identifier;
		this.name = name;
		this.calculationRootObject = null;
	}

	public CalculationShared(LongIdentifier identifier,
			CalculationObject calculationRootObject) {
		this.calculationID = identifier;
		this.calculationRootObject = calculationRootObject;
	}

	public CalculationShared(LongIdentifier calcIdent, String calcName,
			CalculationObject calculationRootObject, LongIdentifier profileID) {
		this.calculationID = calcIdent;
		this.name = calcName;
		this.calculationRootObject = calculationRootObject;
	}

	public String getType() {
		return "Calculation";
	}

	public void setName(String calcName) {
		this.name = calcName;
	}

	public String getName() {
		return name;
	}

	public CalculationObject getRootCalculationObject() {
		return calculationRootObject;
	}

	public void setRootCalculationObject(CalculationObject calcRootObject) {
		this.calculationRootObject = calcRootObject;
	}

	public LongIdentifier getIdentifier() {
		return calculationID;
	}

	public void setIdentifier(LongIdentifier identifier) {
		this.calculationID = identifier;
	}

	public String toString() {
		return getName();
	}

	public int getEmbeddedLevel() {
		return embeddedLevel;
	}

	public int getDefaultRelativeReportingPeriod() {
		return defaultRelativeReportingPeriod;
	}

	public void setDefaultRelativeReportingPeriod(
			int defaultRelativeReportingPeriod) {
		this.defaultRelativeReportingPeriod = defaultRelativeReportingPeriod;
	}

	public Object clone() {
		try {
			CalculationShared calc = (CalculationShared) super.clone();

			// calc.setProfileID(new
			// LongIdentifier(getProfileID().getLongValue().longValue()));
			// calc.setName(new String(getName().toCharArray()));
			calc.setReportingPeriodID(new LongIdentifier(getReportingPeriodID()
					.getLongValue().longValue()));
			calc.setCalculationID(new LongIdentifier(getCalculationID()
					.getLongValue().longValue()));

			if (calculationRootObject == null) {
				calc.setRootCalculationObject(null);
			} else {
				calc
						.setRootCalculationObject((CalculationObject) calculationRootObject
								.clone());
			}
			return calc;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

	public LongIdentifier getReportingPeriodID() {
		return reportingPeriodID;
	}

	public void setReportingPeriodID(LongIdentifier reportingPeriodID) {
		this.reportingPeriodID = reportingPeriodID;
	}

	public LongIdentifier getCalculationID() {
		return calculationID;
	}

	public void setCalculationID(LongIdentifier calculationID) {
		this.calculationID = calculationID;
	}
	// public boolean hasHadItsRRPChanged()
	// {
	// return hasHadItsRRPChanged;
	// }
	// public void setHasHadItsRRPChanged(boolean hasHadItsRRPChanged)
	// {
	// this.hasHadItsRRPChanged = hasHadItsRRPChanged;
	// }
}
