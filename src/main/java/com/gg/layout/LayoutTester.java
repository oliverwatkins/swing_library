package com.gg.layout;

import java.awt.*;
import javax.swing.*;

/** @author piegames */
public class LayoutTester {

	public static JPanel getPanel() {
		final JPanel ret = new JPanel(new BorderLayout());
		JPanel top = new JPanel();
		final JComboBox<String> comboBox = new JComboBox<>(new String[] { "BoxFlowLayout", "CoordGridLayout", "CenteredLayout" });
		comboBox.addActionListener(e -> {
			ret.removeAll();
			ret.add(top, BorderLayout.NORTH);
			switch (comboBox.getSelectedIndex()) {
				case 0:
					ret.add(getBoxFlowPanel(), BorderLayout.SOUTH);
					break;
				case 1:
					ret.add(getCoordGridPanel(), BorderLayout.SOUTH);
					break;
				case 2:
					ret.add(getCenteredPanel(), BorderLayout.SOUTH);
					break;
			}
			ret.revalidate();
			ret.repaint();
		});
		top.add(comboBox);
		ret.add(top, BorderLayout.NORTH);
		ret.add(getBoxFlowPanel(), BorderLayout.SOUTH);
		return ret;
	}

	public static JPanel getCenteredPanel() {
		final CenteredLayout layout = new CenteredLayout(0);
		final JPanel ret = new JPanel(layout);
		ret.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createEtchedBorder(1)));

		JPanel main = new JPanel(new BorderLayout());
		main.setPreferredSize(new Dimension(300, 150));
		main.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		main.add(new JLabel("Panel with preferred size of (300 x 150)", JLabel.CENTER));
		ret.add(main);

		JPanel ctrl = new JPanel();
		ctrl.add(new JLabel("Scale mode: "));
		JComboBox<String> mode = new JComboBox<>(new String[] { "KEEP_SIZE", "DOWNSCALE", "SCALE", "MAXIMIZE" });
		mode.addActionListener(e -> {
			layout.setMode(mode.getSelectedIndex());
			ret.revalidate();
			ret.repaint();
		});
		ctrl.add(mode);
		main.add(ctrl, BorderLayout.NORTH);
		return ret;
	}

	public static JPanel getCoordGridPanel() {
		final CoordGridLayout layout = new CoordGridLayout(2);
		final JPanel ret = new JPanel(layout);
		// JPanel ret = new JPanel(new CoordGridLayout(new GridCreator.FixedCountGridCreator(0, 0)));
		ret.add(new JTextField("3*3"), new Rectangle(2, 1, 3, 1));
		ret.add(new JLabel("4", JLabel.CENTER), new Point(6, 1));
		ret.add(new JButton("Back"), new Rectangle(0, 1, 2, 1));
		ret.add(new JButton("="), new Rectangle(5, 1, 1, 1));

		ret.add(new JButton("/"), new Rectangle(0, 2, 3, 1));
		ret.add(new JButton("*"), new Rectangle(3, 2, 3, 1));
		ret.add(new JButton("-"), new Rectangle(6, 2, 1, 4));
		ret.add(new JButton("+"), new Rectangle(6, 6, 1, 4));

		ret.add(new JButton("0"), new Rectangle(0, 9, 6, 1));
		ret.add(new JButton("1"), new Rectangle(0, 7, 2, 2));
		ret.add(new JButton("2"), new Rectangle(2, 7, 2, 2));
		ret.add(new JButton("3"), new Rectangle(4, 7, 2, 2));
		ret.add(new JButton("4"), new Rectangle(0, 5, 2, 2));
		ret.add(new JButton("5"), new Rectangle(2, 5, 2, 2));
		ret.add(new JButton("6"), new Rectangle(4, 5, 2, 2));
		ret.add(new JButton("7"), new Rectangle(0, 3, 2, 2));
		ret.add(new JButton("8"), new Rectangle(2, 3, 2, 2));
		ret.add(new JButton("9"), new Rectangle(4, 3, 2, 2));

		ret.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createEtchedBorder(1)));

		JComboBox<String> mode = new JComboBox<String>(new String[] { "FIXED_SIZE", "FIXED_COUNT", "FIT_HORIZONTAL", "FIT_VERTICAL", "FIT_BOTH" });
		mode.setSelectedIndex(4);
		ret.add(mode, new Dimension(7, 1));
		mode.addActionListener(e -> {
			switch (mode.getSelectedIndex()) {
				case 0:
					layout.setFixedSize(35, 35);
					break;
				case 1:
					layout.setFixedCount();
					break;
				case 2:
					layout.setFitHorizontal();
					break;
				case 3:
					layout.setFitVertical();
					break;
				case 4:
					layout.setFitBoth();
					break;
			}
			ret.revalidate();
			ret.repaint();
		});
		return ret;
	}

	public static JPanel getBoxFlowPanel() {
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
		gap.addChangeListener(e -> {
			layout.setGap((Integer) gap.getValue());
			layout2.setGap((Integer) gap.getValue());
			tmp.revalidate();
			main.revalidate();
			ret.repaint();
		});
		tmp.add(gap);

		tmp.add(new JLabel("Component alignment"));
		final JComboBox<String> align = new JComboBox<>(new String[] { "LEADING", "TRAILING", "CENTER", "SPREAD" });
		align.addActionListener(e -> {
			layout.setAlign(align.getSelectedIndex());
			layout2.setAlign(align.getSelectedIndex());
			tmp.revalidate();
			main.revalidate();
			ret.repaint();
		});
		tmp.add(align);

		final JCheckBox vert = new JCheckBox("Vertical orientation   ", true);
		vert.addActionListener(e -> {
			layout.setAxis(vert.isSelected() ? 1 : 0);
			layout2.setAxis(vert.isSelected() ? 0 : 1);
			tmp.revalidate();
			main.revalidate();
			ret.repaint();
		});
		tmp.add(vert);

		main.add(tmp);

		main.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createEtchedBorder(1)));
		main.setLayout(layout);

		main.add(new JLabel("Some text"));
		main.add(new JTable(new Object[][] { { 1, "Some", "table" }, { 0, 1, 0 }, { 0, "matrix?", 1 } }, new Object[] { "Alpha", "Beta", "Gamma" }));

		JTree tree = new JTree();
		main.add(tree);

		ret.add(main, BorderLayout.CENTER);
		return ret;
	}
}