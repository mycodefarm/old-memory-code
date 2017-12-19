package com.jimo.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ParentPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	protected JPanel pn_1;
	protected JButton btn_add, btn_next, btn_pre, btn_del, btn_find;
	protected JTextField txt_find;
	protected JTable table;
	protected int currPage = 1;
	protected String str;

	public ParentPanel() {
	}

	public void initPanel() {
		this.setLayout(new BorderLayout());
		pn_1 = new JPanel();
		btn_add = new JButton("添加");
		btn_next = new JButton("下一页");
		btn_pre = new JButton("上一页");
		btn_del = new JButton("删除");
		btn_find = new JButton("搜索");
		txt_find = new JTextField();
		txt_find.setPreferredSize(new Dimension(100, 30));
		btn_add.addActionListener(this);
		btn_next.addActionListener(this);
		btn_pre.addActionListener(this);
		btn_del.addActionListener(this);
		btn_find.addActionListener(this);
		pn_1.add(btn_add);
		pn_1.add(btn_pre);
		pn_1.add(btn_next);
		pn_1.add(btn_del);
		pn_1.add(txt_find);
		pn_1.add(btn_find);

		JPanel pn_up = new JPanel(new GridLayout(1, 1));
		table = new JTable();
		initTable();
		JScrollPane scroolPane = new JScrollPane(table);
		scroolPane.setAutoscrolls(true);

		this.add(scroolPane);
		this.add(pn_up, BorderLayout.NORTH);
		this.add(pn_1, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public void initTable() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ee = e.getSource();
		if (ee == btn_next) {
			str = txt_find.getText();
			nextPage();
		} else if (ee == btn_pre) {
			str = txt_find.getText();
			prePage();
		} else if (ee == btn_del) {
			str = txt_find.getText();
			deleteRows();
		} else if (ee == btn_find) {
			str = txt_find.getText();
			find();
		} else if (ee == btn_add) {
			add();
		}
	}

	public void add() {

	}

	public void find() {
	}

	public void nextPage() {

	}

	public void prePage() {
	}

	public void deleteRows() {
	}
}
