package com.gg.calculation;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CalculationTester extends JFrame{

	
	public static void main(String[] args){
		
		
		CalculationTester m = new CalculationTester();

		m.setSize(1000, 500);

		m.add(getPanel());
		
		m.setVisible(true);
	}
	
	
	public static JPanel getPanel(){
		
		JPanel p = new JPanel(new BorderLayout());
		
		p.add(new CalculationPanel());
		p.add(new OperatorPanel(), BorderLayout.EAST);
		p.add(new OutputTreePanel(), BorderLayout.WEST);
		
		return p;
		
	}

}
