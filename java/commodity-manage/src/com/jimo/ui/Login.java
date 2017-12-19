package com.jimo.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jimo.dao.AdminDao;
import com.jimo.util.MyConst;

public class Login extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private AdminDao loginDao;
	private Container container;
	private JPanel pn_up;
	private JLabel lb_name, lb_pass;
	private JTextField txt_name;
	private JPasswordField txt_pass;
	private JButton btn_sure, btn_cancel;

	public Login() {
		loginDao = new AdminDao();
		init();
	}

	private void init() {
		this.setSize(300, 120);
		container = this.getContentPane();
		pn_up = new JPanel();
		pn_up.setLayout(new GridLayout(3, 2));
		container.add(pn_up, BorderLayout.NORTH);

		lb_name = new JLabel("username:");
		lb_pass = new JLabel("password:");
		txt_name = new JTextField();
		txt_name.setSize(200, 30);
		txt_pass = new JPasswordField();
		txt_pass.setEchoChar('*');
		btn_sure = new JButton("login");
		btn_cancel = new JButton("cancel");
		btn_sure.addActionListener(this);
		btn_cancel.addActionListener(this);

		pn_up.add(lb_name);
		pn_up.add(txt_name);
		pn_up.add(lb_pass);
		pn_up.add(txt_pass);
		pn_up.add(btn_sure);
		pn_up.add(btn_cancel);
		this.setTitle("寂寞的商品管理员登录");
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_sure) {
			String name = txt_name.getText();
			String pass = String.valueOf(txt_pass.getPassword());
			if (loginDao.login(name, pass)) {
				MyConst.USER_NAME = name;//存储用户名
//				JOptionPane.showMessageDialog(null, "login success");
				new MainUI();
				this.setVisible(false);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "login failed!");
			}
		} else if (e.getSource() == btn_cancel) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new Login();
	}
}
