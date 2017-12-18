package com.jimo.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jimo.dao.FirmDao;
import com.jimo.model.Firm;

public class AddFirmUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Container container;
	private JPanel pn_c, pn_d;
	private JButton btn_ok;
	private JLabel lb_name, lb_desc, lb_tel;
	private JTextField tx_name, tx_tel;
	private JTextArea txa_desc;
	private FirmDao fDao;

	public AddFirmUI(FirmDao fDao) {
		this.fDao = fDao;
		init();
	}

	private void init() {
		container = this.getContentPane();
		pn_c = new JPanel();
		pn_c.setLayout(new BoxLayout(pn_c, BoxLayout.Y_AXIS));
		pn_d = new JPanel();
		lb_name = new JLabel("厂商名称");
		lb_tel = new JLabel("厂商电话");
		lb_desc = new JLabel("厂商描述");
		tx_name = new JTextField();
		tx_tel = new JTextField();
		txa_desc = new JTextArea();
		txa_desc.setRows(4);
		btn_ok = new JButton("确定");
		btn_ok.addActionListener(this);
		pn_c.add(lb_name);
		pn_c.add(tx_name);
		pn_c.add(lb_tel);
		pn_c.add(tx_tel);
		pn_c.add(lb_desc);
		pn_c.add(txa_desc);
		pn_d.add(btn_ok);

		container.add(pn_c, BorderLayout.NORTH);
		container.add(pn_d);
		this.setTitle("添加厂商");
		this.setMinimumSize(new Dimension(300, 250));
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_ok) {
			String name = tx_name.getText();
			String tel = tx_tel.getText();
			String desc = txa_desc.getText();
			if (name.equals("") || tel.equals("") || desc.equals("")) {
				JOptionPane.showMessageDialog(null, "不能为空");
				return;
			}
			Firm f = new Firm();
			f.setFirmDesc(desc);
			f.setFirmName(name);
			f.setFirmTel(tel);
			if (fDao.addOneFirm(f) > 0) {
				JOptionPane.showMessageDialog(null, "添加成功");
			} else {
				JOptionPane.showMessageDialog(null, "添加失败");
			}
		}
	}

	public static void main(String[] args) {
		new AddFirmUI(null);
	}
}
