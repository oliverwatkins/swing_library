package com.gg.calculation;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.CellEditor;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

public class EditorTextField extends JTextField implements CellEditor {
	private Double value = new Double(1);
	private Vector<CellEditorListener> listeners = new Vector<CellEditorListener>();
	private static final int minWidth = 64;

	// mimic all of the constructors people expect with text fields
	public EditorTextField() {
		this("", 5);
	}

	public EditorTextField(String s) {
		this(s, 5);
	}

	public EditorTextField(int w) {
		this("", w);
	}

	public EditorTextField(String s, int w) {
		super(s, w);

		// listen to our own action events, so we know when to stop editing
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("action performed");
				if (stopCellEditing()) {
					System.out.println(">>stop cell editing");
					fireEditingStopped();
				} else {
					System.out.println(">>no stop cell editing");
				}
			}
		});
	}

	// Implement the CellEditor methods
	public void cancelCellEditing() {
		// just reset?
		setText("");
	}

	// only stop editing if the user entered a valid value
	public boolean stopCellEditing() {
		try {
			value = Double.valueOf(getText());
			return true;
		} catch (Exception e) {
			System.out.println("ERROR IN CELL EDITING");
			// something went wrong (most likely we don't have a valid integer)
			return false;
		}
	}

	public Object getCellEditorValue() {
		return value;
	}

	// start editing when the right mouse button is clicked
	public boolean isCellEditable(EventObject eo) {
		if ((eo == null)
				|| ((eo instanceof MouseEvent) && (((MouseEvent) eo)
						.isMetaDown()))) {
			return true;
		}
		return false;
	}

	public boolean shouldSelectCell(EventObject eo) {
		return true;
	}

	// Add in support for listeners
	public void addCellEditorListener(CellEditorListener cel) {
		listeners.addElement(cel);
	}

	public void removeCellEditorListener(CellEditorListener cel) {
		listeners.removeElement(cel);
	}

	protected void fireEditingStopped() {
		System.out.println("fireEditingStopped()");
		if (listeners.size() > 0) {
			ChangeEvent ce = new ChangeEvent(this);
			for (int i = listeners.size() - 1; i >= 0; i--) {
				listeners.elementAt(i)
						.editingStopped(ce);
			}
		}
	}

	// override setBounds() to make sure that JTree gives us enough space
	public void setBounds(Rectangle r) {
		r.width = Math.max(minWidth, r.width);
		super.setBounds(r);
	}

	public void setBounds(int x, int y, int w, int h) {
		w = Math.max(minWidth, w);
		super.setBounds(x, y, w, h);
	}
}
