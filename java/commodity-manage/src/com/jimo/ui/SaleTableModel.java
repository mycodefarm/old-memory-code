package com.jimo.ui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.jimo.dao.SaleDao;
import com.jimo.model.Sales;

public class SaleTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private SaleDao dao;
	private int currPage;
	private List<Sales> sales;
	private String str;
	private String[] titles = { "记录编号", "销售日期", "销售金额", "销售记录" };

	public SaleTableModel(int currPage, String str) {
		dao = new SaleDao();
		// 加载分页数据
		this.str = str;
		sales = dao.getAllSaless(currPage, str);
		this.currPage = currPage;
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return titles[columnIndex];
	}

	@Override
	public int getRowCount() {
		return sales.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// System.out.println(rowIndex+"--"+columnIndex);
		switch (columnIndex) {
		case 0:
			return sales.get(rowIndex).getSaleId();
		case 1:
			return sales.get(rowIndex).getSaleDate();
		case 2:
			return sales.get(rowIndex).getSaleMoney();
		case 3:
			return sales.get(rowIndex).getSaleDesc();
		default:
			return "NULL";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex != 0) {
			return true;
		}
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		try {
			int id = Integer.parseInt(this.getValueAt(rowIndex, 0).toString());
			System.out.println("id=" + id);
			String date = this.getValueAt(rowIndex, 1).toString();
			float money = Float.parseFloat(this.getValueAt(rowIndex, 2).toString());
			String desc = this.getValueAt(rowIndex, 3).toString();
			switch (columnIndex) {
			case 1:
				date = aValue.toString();
				break;
			case 2:
				money = Float.parseFloat(aValue.toString());
				break;
			case 3:
				desc = aValue.toString();
				break;
			}
			Sales b = new Sales();
			b.setSaleDesc(desc);
			b.setSaleDate(date);
			b.setSaleMoney(money);
			int re = dao.updateOneSales(id, b);
			if (re > 0) {
				JOptionPane.showMessageDialog(null, "修改成功");
				sales = dao.getAllSaless(currPage, str);
				this.fireTableRowsInserted(rowIndex, rowIndex);
			} else {
				JOptionPane.showMessageDialog(null, "修改失败");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "非法数据");
		}
	}

}
