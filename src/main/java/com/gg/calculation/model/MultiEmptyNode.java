package com.gg.calculation.model;


public class MultiEmptyNode extends EmptyNode 
{
    public static final String SYMBOL = "*";

    
	public MultiEmptyNode()
	{
	    super(MultiEmptyNode.SYMBOL);
	}
	
    public Object clone() 
    {
        return super.clone();
    }

}
