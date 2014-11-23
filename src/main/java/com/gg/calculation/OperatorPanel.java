package com.gg.calculation;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.gg.calculation.model.BinaryEmptyNode;
import com.gg.calculation.model.operators.AdditionOperator;
import com.gg.calculation.model.operators.AverageOperator;
import com.gg.calculation.model.operators.DivisionOperator;
import com.gg.calculation.model.operators.MultiplicationOperator;
import com.gg.calculation.model.operators.Operator;
import com.gg.calculation.model.operators.SubtractionOperator;
import com.gg.calculation.model.operators.SumOperator;

public class OperatorPanel extends JPanel {
	private OperatorTable table;

	public OperatorPanel() {
		init();
	}

	private void init() {
		setBorder(BorderFactory.createTitledBorder("Operators"));
		setLayout(new BorderLayout());

		Collection<Operator> operators = new ArrayList<Operator>();

		AdditionOperator ado = new AdditionOperator();
		AverageOperator avo = new AverageOperator();
		MultiplicationOperator mo = new MultiplicationOperator();
		DivisionOperator divo = new DivisionOperator();
		SubtractionOperator subo = new SubtractionOperator();
		SumOperator so = new SumOperator();

		mo.setFirstOperand(new BinaryEmptyNode(1));
		mo.setSecondOperand(new BinaryEmptyNode(2));

		divo.setFirstOperand(new BinaryEmptyNode(1));
		divo.setSecondOperand(new BinaryEmptyNode(2));

		subo.setFirstOperand(new BinaryEmptyNode(1));
		subo.setSecondOperand(new BinaryEmptyNode(2));

		ado.setFirstOperand(new BinaryEmptyNode(1));
		ado.setSecondOperand(new BinaryEmptyNode(2));

		operators.add(ado);
		operators.add(avo);
		operators.add(mo);
		operators.add(divo);
		operators.add(subo);
		operators.add(so);

		OperatorTableModel model = new OperatorTableModel(operators);

		table = new OperatorTable(model);
		for (int i = 0; i < model.getColumnCount(); i++) {
			table.setDefaultRenderer(table.getColumnClass(i),
					new OperatorTableRenderer());
		}

		add(new JScrollPane(table));
	}

	public OperatorTable getTable() {
		return table;
	}

}
