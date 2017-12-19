package com.jimo.ui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.jimo.dao.FirmDao;
import com.jimo.model.Firm;

public class FirmTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private FirmDao fDao;
	private int currPage;
	private String str;
	private List<Firm> firms;
	private String[] titles = { "厂商名称", "厂商电话", "厂商描述" };

	public FirmTableModel(int currPage, String str) {
		fDao = new FirmDao();
		this.str = str;
		// 加载分页数据
		firms = fDao.getPageFirms(currPage, str);
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
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return titles[columnIndex];
	}

	@Override
	public int getRowCount() {
		return firms.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return firms.get(rowIndex).getFirmName();
		case 1:
			return firms.get(rowIndex).getFirmTel();
		case 2:
			return firms.get(rowIndex).getFirmDesc();
		default:
			return "NULL";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		try {
			String beforeName = this.getValueAt(rowIndex, 0).toString();
			String name = beforeName;
			System.out.println("name=" + name);
			String tel = this.getValueAt(rowIndex, 1).toString();
			String desc = this.getValueAt(rowIndex, 2).toString();
			switch (columnIndex) {
			case 0:
				name = aValue.toString();
				break;
			case 1:
				tel = aValue.toString();
				break;
			case 2:
				desc = aValue.toString();
				break;
			}
			Firm f = new Firm();
			f.setFirmDesc(desc);
			f.setFirmName(name);
			f.setFirmTel(tel);
			int re = fDao.updateOneFirm(beforeName, f);
			if (re > 0) {
				JOptionPane.showMessageDialog(null, "修改成功");
				firms = fDao.getPageFirms(currPage, str);
				this.fireTableRowsInserted(rowIndex, rowIndex);
			} else {
				JOptionPane.showMessageDialog(null, "修改失败");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "非法数据");
		}
	}

}
