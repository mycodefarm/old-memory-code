package com.jimo.ui;

import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.jimo.dao.OrderDao;

public class OrderPanel extends ParentPanel {
	private static final long serialVersionUID = 1L;

	private OrderDao dao;

	public OrderPanel() {
		dao = new OrderDao();
		initPanel();
		this.btn_add.setVisible(false);
	}

	@Override
	public void initTable() {
		table.setModel(new OrderTableModel(currPage, ""));
	}

	@Override
	public void prePage() {
		System.out.println(str);
		table.setModel(new OrderTableModel(--currPage, str));
	}

	@Override
	public void nextPage() {
		System.out.println(str);
		table.setModel(new OrderTableModel(++currPage, str));
	}

	@Override
	public void find() {
		System.out.println(str);
		table.setModel(new OrderTableModel(currPage, str));
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
			System.out.println("order_id=" + id);
			dao.delOneCOrder(id);
			table.setModel(new OrderTableModel(currPage, str));
		}
	}
}
