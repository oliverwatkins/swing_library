package com.gg.filter;

import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FilterListPanel<T> extends JPanel {

	private static final long	serialVersionUID	= -1896522436719351893L;

	private JTextField			filterField;
	private JList<T>			list;
	private FilterListModel<T>	model;

	public FilterListPanel(ComponentFilter filter, T[] data) {
		super(new BorderLayout());

		model = new FilterListModel<T>(filter, data);
		list = new JList<T>(model);
		list.setCellRenderer(new FilterListCellRenderer(model));

		JScrollPane pane = new JScrollPane(list);
		add(pane);

		filterField = new JTextField(10);
		filterField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}
		});
		add(filterField, BorderLayout.NORTH);
		update();
	}

	public JList<T> getList() {
		return list;
	}

	public FilterListModel<T> getListModel() {
		return model;
	}

	public void update() {
		model.setFilterText(filterField.getText());
	}
}