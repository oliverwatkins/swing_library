package com.gg.calculation.model;

import java.io.Serializable;

/**
 * Interface to all calculation objects used in application. Extends
 * serializable because all calculationObjects should be draggable and
 * dropable.
 * 
 * 
 * @author WatkinsO
 *
 */
public interface CalculationObject extends Serializable, Cloneable
{
    /**
     * Cloning is used when assembling the calculations in the calculation
     * engine.
     * 
     * All CalculationObjects must be cloneable. This is because when
     * A calculation is assembled and stored in the cache, if it is embedded
     * in another calculation that calculation must be cloned and then tacked
     * onto the other calculation. Otherwise you run into all sorts of problems
     * with changing the RRP. Some clone() methods are simple, and others
     * are not so simple because a deeper level of cloning is needed.
     * 
     * 
     * Created : 25/02/2005
     * @return
     */    
    public Object clone();
}