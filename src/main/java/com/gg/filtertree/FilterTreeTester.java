package com.gg.filtertree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import com.gg.filter.FilterComponentTester;

/**
 * @deprecated Replaced by {@link FilterComponentTester}.
 */
@Deprecated
public class FilterTreeTester extends JFrame {

	public static void main(String[] args) {
		FilterTreeTester m = new FilterTreeTester();

		m.setVisible(true);
		m.add(getPanel());

		m.pack();
	}

	public static JPanel getPanel() {

		JPanel p = new JPanel();

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
					DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("mouse");
					n2.add(new DefaultMutableTreeNode("lala"));
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

		FilteredTree ftree = new FilteredTree(n);

		final JPanel rightPanel = new JPanel();
		rightPanel.setMinimumSize(new Dimension(300, 300));
		rightPanel.setPreferredSize(new Dimension(300, 300));

		p.setLayout(new BorderLayout());

		p.add(ftree);
		p.add(rightPanel, BorderLayout.EAST);

		final JTree tree = ftree.getTree();

		MouseListener ml = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
				if (selRow != -1) {
					if (e.getClickCount() == 1) {
						rightPanel.removeAll();
						rightPanel.add(new JLabel(""
								+ selPath.getLastPathComponent()));
						rightPanel.updateUI();

						System.out.println("" + selPath.getLastPathComponent());
					}
				}
			}
		};
		tree.addMouseListener(ml);

		return p;
	}
}
