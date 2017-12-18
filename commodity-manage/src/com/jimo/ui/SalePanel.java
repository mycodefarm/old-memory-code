package com.jimo.ui;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.jimo.dao.SaleDao;
import com.jimo.util.ExcelUtil;

public class SalePanel extends ParentPanel {
	private static final long serialVersionUID = 1L;

	private SaleDao dao;
	private JButton btn_excel;
	private Frame frame;

	public SalePanel(Frame f) {
		frame = f;
		dao = new SaleDao();
		initPanel();
		btn_excel = new JButton("导出报表");
		btn_excel.addActionListener(this);
		pn_1.add(btn_excel);
		this.btn_add.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == btn_excel) {
			FileDialog d = new FileDialog(frame, "导出报表", FileDialog.SAVE);
			String filename = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()) + ".xls";
			d.setFile(filename);
			d.setVisible(true);
			String path = d.getDirectory();
			if (null != path) {
				ExcelUtil.exportSaleExcel(path + filename, dao.getAllSaless(currPage, str));
				System.out.println(path + filename);
				JOptionPane.showMessageDialog(null, "导出成功");
			}
		}
	}

	@Override
	public void initTable() {
		table.setModel(new SaleTableModel(currPage, ""));
	}

	@Override
	public void prePage() {
		System.out.println(str);
		table.setModel(new SaleTableModel(--currPage, str));
	}

	@Override
	public void nextPage() {
		System.out.println(str);
		table.setModel(new SaleTableModel(++currPage, str));
	}

	@Override
	public void find() {
		System.out.println(str);
		table.setModel(new SaleTableModel(currPage, str));
	}

	@Override
	public void deleteRows() {
		int[] rowIndexes = table.getSelectedRows();
		if (rowIndexes.length == 0) {
			JOptionPane.showMessageDialog(null, "请选择行后再删除!");
			return;
		}
		int r = JOptionPane.showConfirmDialog(null, "你确定要删除吗？", "确认删除", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (r != JOptionPane.YES_OPTION) {
			return;
		}
		for (int i = 0; i < rowIndexes.length; i++) {
			// 转换为Model的索引，这句很重要，否则索引不对应
			rowIndexes[i] = table.convertRowIndexToModel(rowIndexes[i]);
		}
		// 排序，这句很重要，否则顺序是乱的
		Arrays.sort(rowIndexes);
		AbstractTableModel tm = (AbstractTableModel) table.getModel();
		// 降序删除
		for (int i = rowIndexes.length - 1; i >= 0; i--) {
			int id = Integer.parseInt(tm.getValueAt(rowIndexes[i], 0).toString());
			System.out.println("saleid" + id);
			dao.delOneSales(id);
			table.setModel(new SaleTableModel(currPage, ""));
		}
	}
}
