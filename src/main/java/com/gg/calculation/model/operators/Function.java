package com.gg.calculation.model.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.gg.calculation.model.CalculationObject;
import com.gg.calculation.model.InputDefShared;

public abstract class Function extends Operator {

	protected HashMap<Long, CalculationObject> functionInputMap = new HashMap<Long, CalculationObject>();

	public abstract int getInputSize();

	public abstract ArrayList<InputDefShared> getInputDefList();

	public abstract void setInputDefList(ArrayList<InputDefShared> al);

	public ArrayList<CalculationObject> getFunctionInputList() {
		ArrayList<CalculationObject> functionList = new ArrayList<CalculationObject>();

		for (int i = 1; i <= functionInputMap.size(); i++) {

			functionList.add(functionInputMap.get(new Long(i)));

		}
		return functionList;
	}

	public void setFunctionInputMap(HashMap<Long, CalculationObject> hm) {
		functionInputMap = hm;
	}

	public String toString() {
		return getSymbol();
	}

	public abstract void adjustRelativeRP(int relativeReportingPeriod);

	public abstract void resetRRP();

	public Function clone() {
		
		Function npv = (Function) super.clone();

		HashMap<Long, CalculationObject> newHashMap = new HashMap<Long, CalculationObject>();

		// hashMaps do shallow cloning... so need to get in deep.
		for (Iterator<Long> iter = functionInputMap.keySet().iterator(); iter
				.hasNext();) {
			Long key = iter.next();

			CalculationObject co = functionInputMap
					.get(key);

			newHashMap.put(key, (CalculationObject) co.clone());
		}

		ArrayList<InputDefShared> clonedList = new ArrayList<InputDefShared>();

		Collection<InputDefShared> inputDefList = getInputDefList();
		
		for (InputDefShared inputDefShared : inputDefList) {
			clonedList.add((InputDefShared)inputDefShared.clone());
		}
		
		npv.setFunctionInputMap(newHashMap);
		npv.setInputDefList(clonedList);

		return npv;
	}
}
