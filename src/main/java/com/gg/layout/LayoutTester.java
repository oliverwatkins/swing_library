package com.gg.layout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LayoutTester {

	public static JPanel getPanel() {
		final BoxFlowLayout layout = new BoxFlowLayout(1);
		final BoxFlowLayout layout2 = new BoxFlowLayout(0);
		final JPanel main = new JPanel();
		final JPanel ret = new JPanel();
		ret.setLayout(new BorderLayout());

		final JPanel tmp = new JPanel();
		tmp.setBorder(BorderFactory.createEtchedBorder(1));
		tmp.setLayout(layout2);
		tmp.add(new JLabel("Component gap:"));
		final JSpinner gap = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
		gap.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				layout.setGap((Integer) gap.getValue());
				layout2.setGap((Integer) gap.getValue());
				tmp.revalidate();
				main.revalidate();
				ret.repaint();
			}
		});
		tmp.add(gap);

		tmp.add(new JLabel("Component alignment"));
		final JComboBox<String> align = new JComboBox<>(new String[] { "LEADING", "TRAILING", "CENTER", "SPREAD" });
		align.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				layout.setAlign(align.getSelectedIndex());
				layout2.setAlign(align.getSelectedIndex());
				tmp.revalidate();
				main.revalidate();
				ret.repaint();
			}
		});
		tmp.add(align);

		final JCheckBox vert = new JCheckBox("Vertical orientation   ", true);
		vert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				layout.setAxis(vert.isSelected() ? 1 : 0);
				layout2.setAxis(vert.isSelected() ? 0 : 1);
				tmp.revalidate();
				main.revalidate();
				ret.repaint();
			}
		});
		tmp.add(vert);

		main.add(tmp);

		main.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		main.setLayout(layout);

		main.add(new JLabel("Some text"));
		main.add(new JTable(new Object[][] { { 1, "Some", "table" }, { 0, 1, 0 }, { 0, "matrix?", 1 } }, new Object[] { "Alpha", "Beta", "Gamma" }));

		ret.add(main, BorderLayout.CENTER);
		return ret;
	}
}