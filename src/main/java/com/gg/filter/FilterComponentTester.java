package com.gg.filter;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import com.gg.filter.ComponentFilter.ContainsComponentFilter;
import com.gg.filter.ComponentFilter.EndsWithComponentFilter;
import com.gg.filter.ComponentFilter.NoComponentFilter;
import com.gg.filter.ComponentFilter.StartsWithComponentFilter;
import com.gg.layout.BoxFlowLayout;

public class FilterComponentTester extends JFrame {

	private static final long serialVersionUID = -4770235491864619829L;

	public static void main(String[] args) {
		FilterComponentTester m = new FilterComponentTester();
		m.setDefaultCloseOperation(3);
		m.setVisible(true);
		m.add(getPanel());

		m.pack();
	}

	public static JPanel getPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		final CardLayout layout = new CardLayout();
		JPanel center = new JPanel(layout);

		final FilterTreePanel tree = new FilterTreePanel(new ContainsComponentFilter(), defaultTree());
		center.add(tree, "Tree");
		final FilterListPanel<String> list = new FilterListPanel<String>(new ContainsComponentFilter(), defaultList());
		center.add(list, "List");
		p.add(center);

		final JPanel rightPanel = new JPanel(new BoxFlowLayout(BoxLayout.Y_AXIS));
		rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		p.add(rightPanel, BorderLayout.EAST);

		final JComboBox<String> component = new JComboBox<>(new String[] { "Tree", "List" });
		final JComboBox<String> mode = new JComboBox<>(new String[] { "Contains", "Starts with", "Ends with", "Off" });
		final JCheckBox caseSensitive = new JCheckBox("case sensitive");
		final JCheckBox highlightAll = new JCheckBox("highlight all");
		final JLabel selected = new JLabel();

		component.addItemListener(e -> {
			layout.show(center, (String) component.getSelectedItem());
			p.revalidate();
			p.repaint();
		});
		mode.addItemListener(e -> {
			caseSensitive.setEnabled(mode.getSelectedIndex() != 3);
			ComponentFilter filter = null;
			boolean sensitive = caseSensitive.isSelected();
			switch (mode.getSelectedIndex()) {
				case 0:
					filter = new ContainsComponentFilter(sensitive);
					break;
				case 1:
					filter = new StartsWithComponentFilter(sensitive);
					break;
				case 2:
					filter = new EndsWithComponentFilter(sensitive);
					break;
				case 3:
					filter = new NoComponentFilter();
					break;
			}
			tree.getTreeModel().setFilter(filter);
			tree.update();
			list.getListModel().setFilter(filter);
			list.update();
		});
		caseSensitive.addActionListener(e -> {
			ComponentFilter filter = null;
			boolean sensitive = caseSensitive.isSelected();
			switch (mode.getSelectedIndex()) {
				case 0:
					filter = new ContainsComponentFilter(sensitive);
					break;
				case 1:
					filter = new StartsWithComponentFilter(sensitive);
					break;
				case 2:
					filter = new EndsWithComponentFilter(sensitive);
					break;
				case 3:
					filter = new NoComponentFilter();
					break;
			}
			tree.getTreeModel().setFilter(filter);
			tree.update();
			list.getListModel().setFilter(filter);
			list.update();
		});

		highlightAll.addActionListener(e -> tree.getTree().setCellRenderer(new FilterTreeCellRenderer(tree.getTreeModel(),
				highlightAll.isSelected())));
		highlightAll.addActionListener(e -> list.getList().setCellRenderer(new FilterListCellRenderer(list.getListModel(),
				highlightAll.isSelected())));

		rightPanel.setPreferredSize(new Dimension(150, 300));
		rightPanel.add(component);
		rightPanel.add(mode);
		rightPanel.add(caseSensitive);
		rightPanel.add(highlightAll);
		rightPanel.add(new JSeparator());
		rightPanel.add(selected);

		MouseListener ml = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getTree().getRowForLocation(e.getX(), e.getY());
				TreePath selPath = tree.getTree().getPathForLocation(e.getX(), e.getY());
				if (selRow != -1) {
					if (e.getClickCount() == 1) {
						selected.setText(selPath.getLastPathComponent().toString());
					}
				}
			}
		};
		tree.getTree().addMouseListener(ml);

		list.getList().addListSelectionListener(e -> {
			selected.setText(list.getList().getSelectedValue());
		});

		return p;
	}

	private static String[] defaultList() {
		return new String[] { "animals", "bear", "cat", "boor", "dog", "billy", "cassie", "bat", "crow", "cow", "carp", "boa constrictor", "cockatoo", "dragon", "adder", "alligator", "snake", "spider", "salamander", "lala", "mouse",
				"shark", "llama", "ant" };
	}

	private static TreeNode defaultTree() {
		DefaultMutableTreeNode n = new DefaultMutableTreeNode("animals");
		{
			DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("bear");
			n.add(n1);
		}
		{
			DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("cat");
			n.add(n1);
		}
		{
			DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("boor");
			n.add(n1);
		}
		{
			DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("dog");
			n.add(n1);
			{
				DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("billy");
				n1.add(n2);
			}
			{
				DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("cassie");
				n1.add(n2);
			}
		}
		{
			DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("bat");
			n.add(n1);
		}

		{
			DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("crow");
			n.add(n1);
		}
		{
			DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("cow");
			n.add(n1);

			{
				DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("carp");
				n1.add(n2);
			}
			{
				DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("boa constrictor");
				n1.add(n2);

				{
					DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("cockatoo");
					n2.add(n3);
				}
				{
					DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("dragon");
					n2.add(n3);
				}
				{
					DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("adder");
					n2.add(n3);
				}
			}
			{
				DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("alligator");
				n1.add(n2);
			}
			{
				DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("snake");
				n1.add(n2);
			}
			{
				DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("spider");
				n1.add(n2);
			}
			{
				DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("salamander");
				n1.add(n2);

				{
					DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("lala");
					n2.add(n3);
				}

				{
					DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("mouse");
					n2.add(n3);
				}
				{
					DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("shark");
					n2.add(n3);
				}
				{
					DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("llama");
					n2.add(n3);
				}
			}
			{
				DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("ant");
				n1.add(n2);
			}
		}
		return n;
	}
}
