package com.gg.calculation.model;


public class BinaryEmptyNode extends EmptyNode {

	public static final String SYMBOL = "?";
	private int position = 0;

	public BinaryEmptyNode(int position) {
		super(BinaryEmptyNode.SYMBOL);
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Object clone() {
		return super.clone();
	}
}
