package com.jimo.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.jimo.model.Book;
import com.jimo.model.Sales;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@SuppressWarnings("deprecation")
public class ExcelUtil {
	public static void exportBookExcel(String fileName, List<Book> b) {
		WritableWorkbook wwb;
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileName);
			wwb = Workbook.createWorkbook(fos);
			WritableSheet ws = wwb.createSheet("书籍信息表", 10); // 创建一个工作表

			// 设置单元格的文字格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			ws.setRowView(1, 500);

			ws.addCell(new Label(1, 0, "id", wcf));
			ws.addCell(new Label(2, 0, "名字", wcf));
			ws.addCell(new Label(3, 0, "价格", wcf));
			ws.addCell(new Label(4, 0, "作者", wcf));
			wcf = new WritableCellFormat();
			// 填充数据的内容
			for (int i = 0; i < b.size(); i++) {
				ws.addCell(new Label(1, i + 1, String.valueOf(b.get(i).getBookId()), wcf));
				ws.addCell(new Label(2, i + 1, b.get(i).getBookName(), wcf));
				ws.addCell(new Label(3, i + 1, String.valueOf(b.get(i).getBookPrice()), wcf));
				ws.addCell(new Label(4, i + 1, b.get(i).getBookAuthor(), wcf));
			}
			wwb.write();
			wwb.close();

		} catch (IOException e) {
		} catch (RowsExceededException e) {
		} catch (WriteException e) {
		}
	}

	public static void exportSaleExcel(String fileName, List<Sales> b) {
		WritableWorkbook wwb;
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileName);
			wwb = Workbook.createWorkbook(fos);
			WritableSheet ws = wwb.createSheet("销售记录表", 10); // 创建一个工作表

			// 设置单元格的文字格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GREEN);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			ws.setRowView(1, 500);

			ws.addCell(new Label(1, 0, "记录编号", wcf));
			ws.addCell(new Label(2, 0, "记录日期", wcf));
			ws.addCell(new Label(3, 0, "销售金额", wcf));
			ws.addCell(new Label(4, 0, "销售记录描述", wcf));
			wcf = new WritableCellFormat();
			// 填充数据的内容
			for (int i = 0; i < b.size(); i++) {
				ws.addCell(new Label(1, i + 1, String.valueOf(b.get(i).getSaleId()), wcf));
				ws.addCell(new Label(2, i + 1, b.get(i).getSaleDate(), wcf));
				ws.addCell(new Label(3, i + 1, String.valueOf(b.get(i).getSaleMoney()), wcf));
				ws.addCell(new Label(4, i + 1, b.get(i).getSaleDesc(), wcf));
			}
			wwb.write();
			wwb.close();

		} catch (IOException e) {
		} catch (RowsExceededException e) {
		} catch (WriteException e) {
		}
	}
}
