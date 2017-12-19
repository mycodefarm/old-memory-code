package com.jimo.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jimo.dao.CommodityDao;
import com.jimo.model.Book;

public class AddBookUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Container container;
	private JPanel pn_c, pn_d;
	private JButton btn_ok;
	private JLabel lb_name, lb_price, lb_author;
	private JTextField tx_name, tx_price, tx_author;
	private CommodityDao cd;

	public AddBookUI(CommodityDao cd) {
		this.cd = cd;
		init();
	}

	private void init() {
		container = this.getContentPane();
		pn_c = new JPanel(new GridLayout(3, 2));
		pn_d = new JPanel();
		lb_name = new JLabel("书名");
		lb_price = new JLabel("价格");
		lb_author = new JLabel("作者");
		tx_name = new JTextField();
		tx_price = new JTextField();
		tx_author = new JTextField();
		btn_ok = new JButton("确定");
		btn_ok.addActionListener(this);
		pn_c.add(lb_name);
		pn_c.add(tx_name);
		pn_c.add(lb_price);
		pn_c.add(tx_price);
		pn_c.add(lb_author);
		pn_c.add(tx_author);
		pn_d.add(btn_ok);

		container.add(pn_c, BorderLayout.NORTH);
		container.add(pn_d);
		this.setTitle("添加一本书");
		this.setMinimumSize(new Dimension(300, 150));
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_ok) {
			String name = tx_name.getText();
			float price = 0.0f;
			try {
				price = Float.parseFloat(tx_price.getText());
			} catch (Exception ee) {
				JOptionPane.showMessageDialog(null, "数字价格");
				return;
			}
			String author = tx_author.getText();
			Book b = new Book();
			b.setBookAuthor(author);
			b.setBookName(name);
			b.setBookPrice(price);
			if (cd.addOneBook(b) > 0) {
				JOptionPane.showMessageDialog(null, "添加成功");
			} else {
				JOptionPane.showMessageDialog(null, "添加失败");
			}
		}
	}

	public static void main(String[] args) {
		new AddBookUI(new CommodityDao());
	}
}
