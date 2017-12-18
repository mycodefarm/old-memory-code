package com.jimo.ui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.jimo.dao.OrderDao;
import com.jimo.model.COrder;

public class OrderTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private OrderDao oDao;
	private int currPage;
	private String str;
	private List<COrder> orders;
	private String[] titles = { "订单号", "订单金额", "订单日期", "订单厂商", "订单描述", "订单状态" };

	public OrderTableModel(int currPage, String str) {
		oDao = new OrderDao();
		this.str = str;
		// 加载分页数据
		orders = oDao.getPageCOrders(currPage, str);
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
		return 6;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return titles[columnIndex];
	}

	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return orders.get(rowIndex).getOrderId();
		case 1:
			return orders.get(rowIndex).getOrderMoney();
		case 2:
			return orders.get(rowIndex).getOrderDate();
		case 3:
			return orders.get(rowIndex).getFirmName();
		case 4:
			return orders.get(rowIndex).getOrderDesc();
		case 5:
			return orders.get(rowIndex).getOrderState();
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
			String orderId = this.getValueAt(rowIndex, 0).toString();
			float money = Float.parseFloat(this.getValueAt(rowIndex, 1).toString());
			String date = this.getValueAt(rowIndex, 2).toString();
			String fname = this.getValueAt(rowIndex, 3).toString();
			String desc = this.getValueAt(rowIndex, 4).toString();
			String state = this.getValueAt(rowIndex, 5).toString();
			switch (columnIndex) {
			case 1:
				money = Float.parseFloat(aValue.toString());
				break;
			case 2:
				date = aValue.toString();
				break;
			case 3:
				fname = aValue.toString();
				break;
			case 4:
				desc = aValue.toString();
				break;
			case 5:
				state = aValue.toString();
				break;
			}
			COrder c = new COrder();
			c.setFirmName(fname);
			c.setOrderDate(date);
			c.setOrderMoney(money);
			c.setOrderState(state);
			c.setOrderDesc(desc);
			int re = oDao.updateOneCOrder(Integer.parseInt(orderId), c);
			if (re > 0) {
				JOptionPane.showMessageDialog(null, "修改成功");
				orders = oDao.getPageCOrders(currPage, str);
				this.fireTableRowsInserted(rowIndex, rowIndex);
			} else {
				JOptionPane.showMessageDialog(null, "修改失败");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "非法数据");
		}
	}

}
