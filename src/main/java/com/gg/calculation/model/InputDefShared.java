package com.gg.calculation.model;



/**
 * A lightweight representation of an input definition that is used 
 * both on the server and on the client. 
 */
public class InputDefShared extends Output implements CalculationObject 
{

    //initialise to 0.0
    private Double value = new Double(0.0);
    private LongIdentifier identifier;
    private String name;
    private int position;
    
    private boolean hasHadItsRRPChanged = false;
    
    
    //can change
	private int relativeReportingPeriod = 0;
	
	//default relative reporting period
	private int defaultRelativeReportingPeriod;
	
    /**
     * @param inputDefName
     * @param repPerInputDefIdent
     */
    public InputDefShared(String name, LongIdentifier identifier) 
    {        
        this.identifier = identifier;
        this.name = name;        
    }
    /**
     * second constructor can pass in position as well. Used for sorting in the Input
     * Spanning Tool.
     * 
     * @param name
     * @param identifier
     * @param position
     */
    public InputDefShared(String name, LongIdentifier identifier, int position) 
    {        
        this.identifier = identifier;
        this.name = name; 
        this.position = position;
    }
    /**
     * @param value
     */

    public LongIdentifier getIdentifier() 
    {
        return identifier;
    }
    public void setIdentifier(LongIdentifier identifier) 
    {
        this.identifier = identifier;
    }
    public void setValue(Double value) 
    {
        this.value = value;        
    }    
    public Double getValue() 
    {
        return value;
    }
    public void setName(String s) 
    {
        name = s;
    }
    public String getName() 
    {
        return name;
    }

    public String getType() 
    {
        return "input definition";
    }
    public String toString()
    {
        return name;
    }
    public int getPosition() 
    {
        return position;
    }
    public void setPosition(int position) 
    {
        this.position = position;
    }
    
//    public boolean hasHadItsRRPChanged() 
//    {
//        return hasHadItsRRPChanged;
//    }
//    public void setHasHadItsRRPChanged(boolean hasHadItsRRPChanged) 
//    {
//        this.hasHadItsRRPChanged = hasHadItsRRPChanged;
//    }
    
    public int getRelativeReportingPeriod() 
    {
        return relativeReportingPeriod;
    }
    public void setRelativeReportingPeriod(int relativeReportingPeriod) 
    {
        this.relativeReportingPeriod = relativeReportingPeriod;
    }
    public int getDefaultRelativeReportingPeriod() 
    {
        return defaultRelativeReportingPeriod;
    }
    public void setDefaultRelativeReportingPeriod(int defaultRelativeReportingPeriod) 
    {
        this.defaultRelativeReportingPeriod = defaultRelativeReportingPeriod;
    }
    
    
    public Object clone() 
    {
        try 
        {
            //value
            InputDefShared id = (InputDefShared) super.clone();
            
            //need to clone double object (but its value is arbitrary)
            id.setValue(new Double(getValue().doubleValue()));
            id.setIdentifier(new LongIdentifier(identifier.getLongValue().longValue()));
            id.setName(id.getName());

            return id;            
        }
        catch (CloneNotSupportedException e) 
        {
            throw new InternalError(e.toString());
        }
    }
    
}
