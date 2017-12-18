package com.jimo.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jimo.dao.AdminDao;

public class UpdatePassUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanel pn_c;
	private JButton btn_ok;
	private JLabel lb_passold, lb_passnew;
	private JTextField tx_passold, tx_passnew;
	private AdminDao dao;

	public UpdatePassUI() {
		this.dao = new AdminDao();
		init();
	}

	private void init() {
		pn_c = new JPanel();
		pn_c.setLayout(new BoxLayout(pn_c, BoxLayout.Y_AXIS));
		lb_passold = new JLabel("请输入旧密码");
		lb_passnew = new JLabel("请输入新密码");
		tx_passold = new JTextField();
		tx_passnew = new JTextField();
		btn_ok = new JButton("确定");
		btn_ok.addActionListener(this);
		pn_c.add(lb_passold);
		pn_c.add(tx_passold);
		pn_c.add(lb_passnew);
		pn_c.add(tx_passnew);
		pn_c.add(btn_ok);

		this.setVisible(true);
		this.add(pn_c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_ok) {
			String oldp = tx_passold.getText();
			String newp = tx_passnew.getText();
			if (oldp.equals("") || newp.equals("") || newp.length() < 6) {
				JOptionPane.showMessageDialog(null, "不能为空或密码长度小于6位");
				return;
			}

			if (dao.updatePassword(newp, oldp) > 0) {
				JOptionPane.showMessageDialog(null, "更新成功");
			} else {
				JOptionPane.showMessageDialog(null, "更新失败，旧密码错误");
			}
		}
	}

	public static void main(String[] args) {
		new UpdatePassUI();
	}
}
