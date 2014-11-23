package com.gg.calculation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;


//Used Action Listner for JMenuItem & JRadioButtonMenuItem
//Used Item Listner for JCheckBoxMenuItem

public class MenTest implements ActionListener, ItemListener{
    JTextArea jtAreaOutput;
    JScrollPane jspPane;

    public JMenuBar createJMenuBar() {
        JMenuBar mainMenuBar;
        JMenu menu1, menu2, submenu;
        JMenuItem plainTextMenuItem, plainTextMenuItem2,plainTextMenuItem3,plainTextMenuItem4, textIconMenuItem, iconMenuItem, subMenuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;
        ImageIcon icon = createImageIcon("jmenu.jpg");

        mainMenuBar = new JMenuBar();

        menu1 = new JMenu("Monkeys");
        menu1.setMnemonic(KeyEvent.VK_M);
        mainMenuBar.add(menu1);

        //Creating the MenuItems
        plainTextMenuItem = new JMenuItem("Manage Banana Orders", 
        							KeyEvent.VK_T);
        plainTextMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_1, 
        						ActionEvent.ALT_MASK));
        plainTextMenuItem.addActionListener(this);

        //Creating the MenuItems
        plainTextMenuItem2 = new JMenuItem("Manage Pineapple Orders", 
        							KeyEvent.VK_T);
        plainTextMenuItem2.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_2, 
        						ActionEvent.ALT_MASK));
        plainTextMenuItem2.addActionListener(this);
        plainTextMenuItem2.setEnabled(false);

        //Creating the MenuItems
        plainTextMenuItem3 = new JMenuItem("Monkey Medical Records", 
        							KeyEvent.VK_T);
        plainTextMenuItem3.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_3, 
        						ActionEvent.ALT_MASK));
        plainTextMenuItem3.addActionListener(this);

        plainTextMenuItem3.setEnabled(false);

        
        //Creating the MenuItems
        plainTextMenuItem4 = new JMenuItem("Manage Banana Orders", 
        							KeyEvent.VK_T);
        plainTextMenuItem4.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_4, 
        						ActionEvent.ALT_MASK));
        plainTextMenuItem4.addActionListener(this);

        plainTextMenuItem4.setEnabled(false);
        
        
        
        menu1.add(plainTextMenuItem);
//        menu1.add(plainTextMenuItem2);
//        menu1.addSeparator();
//        menu1.add(plainTextMenuItem3);
//        menu1.add(plainTextMenuItem4);

//        menu2 = new JMenu("Elephants");
//        mainMenuBar.add(menu2);
//        
//        JMenu menu3 = new JMenu("Zebras");
//        mainMenuBar.add(menu3);
//
//        JMenu menu4 = new JMenu("Kangaroos");
//        mainMenuBar.add(menu4);

        
        return mainMenuBar;
    }

    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel jplContentPane = new JPanel(new BorderLayout());
        jplContentPane.setLayout(new BorderLayout());//Can do it either way to set layout
        jplContentPane.setOpaque(true);

        //Create a scrolled text area.
        jtAreaOutput = new JTextArea(5, 30);
        jtAreaOutput.setEditable(false);
        jspPane = new JScrollPane(jtAreaOutput);

        //Add the text area to the content pane.
        jplContentPane.add(jspPane, BorderLayout.CENTER);

        return jplContentPane;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MenTest.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find image file: " + path);
            return null;
        }
    }

    private static void createGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("ACME Zoo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenTest app = new MenTest();
        frame.setJMenuBar(app.createJMenuBar());
        frame.setContentPane(app.createContentPane());

        frame.setSize(500, 300);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Menu Item source: " + source.getText()
                   + " (an instance of " + getClassName(source) + ")";
        jtAreaOutput.append(s + "\n");
        jtAreaOutput.setCaretPosition(jtAreaOutput.getDocument().getLength());
    }

    public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Menu Item source: " + source.getText()
                   + " (an instance of " + getClassName(source) + ")"
                   + "\n"
                   + "    State of check Box: "
                   + ((e.getStateChange() == ItemEvent.SELECTED) ?
                     "selected":"unselected");
        jtAreaOutput.append(s + "\n");
        jtAreaOutput.setCaretPosition(jtAreaOutput.getDocument().getLength());
    }
    
    // Returns the class name, no package info
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();	
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);	//Returns only Class name
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }
    
    
}


