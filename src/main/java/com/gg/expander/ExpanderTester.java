package com.gg.expander;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ExpanderTester {

	public static void main(String[] args){
		
		JFrame frame = new JFrame();
		
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 700);
		
		
		frame.setVisible(true);
		
		frame.getContentPane().add(getPanel());
		
	}
	
	public static JPanel getPanel() {
		
		
		JPanel p = new JPanel();
		
		
		
		p.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		p.add(new JLabel("Name : "), gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		p.add(new JTextField(10), gbc);


		gbc.gridx = 0;
		gbc.gridy = 1;
		p.add(new JLabel("Date of Birth : "), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		p.add(new JTextField(10), gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		p.add(new JLabel("Experience : "), gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		
		JExpandableTextArea mta = new JExpandableTextArea(10,10);
		mta.setLineWrap(true);
		
		p.add(new JScrollPane(mta), gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		p.add(new JLabel("Phone Number : "), gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		p.add(new JTextField(10), gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		
		JTextArea ta = new JTextArea(5,5);
		ta.setLineWrap(true);
		
		return p;
		
		
	}
	
}
