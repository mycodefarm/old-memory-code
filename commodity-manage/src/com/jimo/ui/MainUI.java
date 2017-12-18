package com.jimo.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private Container container;
	private JTabbedPane tab;
	private String[] titles = { "商品管理", "订单管理", "厂商管理", "销售记录管理", "信息管理" };

	public MainUI() {
		init();
	}

	private void init() {
		container = this.getContentPane();
		int i = 0;
		tab = new JTabbedPane();
		tab.add(titles[i++], new BookPanel());
		tab.add(titles[i++], new OrderPanel());
		tab.add(titles[i++], new FirmPanel());
		tab.add(titles[i++], new SalePanel(this));
		tab.add(titles[i++], new UpdatePassUI());

		tab.validate();
		this.setLayout(new GridLayout(1, 1));
		container.add(tab);
		this.validate();
		this.setTitle("图书销售管理");
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(600, 600));
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MainUI();
	}
}
