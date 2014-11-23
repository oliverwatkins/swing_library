package com.gg.calculation;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main extends JFrame{

	public static void main(String[] args){
		Main m = new Main();
		
		m.setSize(1000, 500);
		
		m.getContentPane().add(new CalculationPanel());
		m.getContentPane().add(new OperatorPanel(), BorderLayout.EAST);
		m.getContentPane().add(new OutputTreePanel(), BorderLayout.WEST);
		
		m.setVisible(true);
	}
}
