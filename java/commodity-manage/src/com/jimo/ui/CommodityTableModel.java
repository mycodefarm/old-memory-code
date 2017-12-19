package com.jimo.ui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.jimo.dao.CommodityDao;
import com.jimo.model.Book;

public class CommodityTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private CommodityDao cDao;
	private int currPage;
	private List<Book> boos;
	private String str;
	private String[] titles = { "书编号", "书名", "价格", "作者" };

	public CommodityTableModel(int currPage,String str) {
		cDao = new CommodityDao();
		// 加载分页数据
		this.str = str;
		boos = cDao.getAllBooks(currPage,str);
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
		return boos.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// System.out.println(rowIndex+"--"+columnIndex);
		switch (columnIndex) {
		case 0:
			return boos.get(rowIndex).getBookId();
		case 1:
			return boos.get(rowIndex).getBookName();
		case 2:
			return boos.get(rowIndex).getBookPrice();
		case 3:
			return boos.get(rowIndex).getBookAuthor();
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
			String name = this.getValueAt(rowIndex, 1).toString();
			float price = Float.parseFloat(this.getValueAt(rowIndex, 2).toString());
			String author = this.getValueAt(rowIndex, 3).toString();
			switch (columnIndex) {
			case 1:
				name = aValue.toString();
				break;
			case 2:
				price = Float.parseFloat(aValue.toString());
				break;
			case 3:
				author = aValue.toString();
				break;
			}
			Book b = new Book();
			b.setBookAuthor(author);
			b.setBookName(name);
			b.setBookPrice(price);
			int re = cDao.updateOneBook(id, b);
			if (re > 0) {
				JOptionPane.showMessageDialog(null, "修改成功");
				boos = cDao.getAllBooks(currPage,str);
				this.fireTableRowsInserted(rowIndex, rowIndex);
			} else {
				JOptionPane.showMessageDialog(null, "修改失败");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "非法数据");
		}
	}

}
