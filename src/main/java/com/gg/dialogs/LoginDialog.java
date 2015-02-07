package com.gg.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
 
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 
/**
 * A Simple Login Dialog
 * 
 * @author Oliver Watkins (c)
 */
public class LoginDialog extends JDialog {
 
	JLabel nameLabel = new JLabel("Name : ");
	JLabel passwordLabel = new JLabel("Password : ");
 
	JTextField nameField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
 
	JButton okButton = new JButton("OK");
	JButton cancelButton = new JButton("Cancel");
 
	public LoginDialog() {
		setupUI();
 
		setUpListeners();
 
	}
 
	public void setupUI() {
 
		this.setTitle("Login");
 
		JPanel topPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
 
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
 
		GridBagConstraints gbc = new GridBagConstraints();
 
		gbc.insets = new Insets(4, 4, 4, 4);
 
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		topPanel.add(nameLabel, gbc);
 
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		topPanel.add(nameField, gbc);
 
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		topPanel.add(passwordLabel, gbc);
 
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1;
		topPanel.add(passwordField, gbc);
 
		this.add(topPanel);
 
		this.add(buttonPanel, BorderLayout.SOUTH);
 
	}
 
	private void setUpListeners() {
 
		passwordField.addKeyListener(new KeyAdapter() {
 
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
 
		okButton.addActionListener(new ActionListener() {
 
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
 
		cancelButton.addActionListener(new ActionListener() {
 
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginDialog.this.setVisible(false);
			}
		});
	}
	
	
	private void login() {
		System.out.println("You logged in!");
		LoginDialog.this.setVisible(false);
	}
 
	public static void main(String[] args) {
 
		LoginDialog ld = new LoginDialog();
 
		ld.setSize(400, 150);
		ld.setVisible(true);
 
	}
}
