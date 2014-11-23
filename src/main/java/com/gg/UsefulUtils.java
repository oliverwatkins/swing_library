package com.gg;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

public class UsefulUtils {

	
	
	/**
	 * Centers the frame, or any container.
	 * 
	 * @param c the main frame
	 */

	public static void center(Container c) {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		c.setLocation(d.width/2 - c.getWidth()/2, 
				d.height/2 - c.getHeight()/2);
		
	}

}
