package com.jimo.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.jimo.model.doc.BookInfoDTO;

public class ExcelUtil {

	public static void export(List<BookInfoDTO> datas, String path) {
		Workbook wb = new HSSFWorkbook();
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			Sheet sheet = wb.createSheet("sheet");

			// 头部跨列的标题
			CellStyle style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			style.setBorderBottom(CellStyle.BORDER_NONE);
			Font font1 = wb.createFont();
			font1.setFontHeight((short) 900);
			style.setFont(font1);
			Row headRow = sheet.createRow(0);
			Cell cell = headRow.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue("图书库存一览表");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

			// 生成日期
			CellStyle style2 = wb.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_RIGHT);
			style2.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			style2.setBorderTop(CellStyle.BORDER_NONE);
			Row dateRow = sheet.createRow(1);
			Cell cell2 = dateRow.createCell(0);
			cell2.setCellStyle(style2);
			cell2.setCellValue("报表生成日期：" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

			String[] titles = { "书号", "书名", "作者", "图书分类", "开本", "库存数", "单价", "总码样" };

			Font font2 = wb.createFont();
			font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
			CellStyle s2 = wb.createCellStyle();
			s2.setFont(font2);
			Row titleRow = sheet.createRow(2);
			for (int i = 0; i < titles.length; i++) {
				Cell c = titleRow.createCell(i);
				c.setCellStyle(s2);
				c.setCellValue(titles[i]);
			}

			for (int i = 0; i < datas.size(); i++) {
				Row row = sheet.createRow(i + 3);
				Cell c = row.createCell(0);
				c.setCellValue(datas.get(i).getShuh());
				c = row.createCell(1);
				c.setCellValue(datas.get(i).getShum());
				c = row.createCell(2);
				c.setCellValue(datas.get(i).getZuozhe());
				c = row.createCell(3);
				c.setCellValue(datas.get(i).getTsfl());
				c = row.createCell(4);
				c.setCellValue(datas.get(i).getKb());
				c = row.createCell(5);
				c.setCellValue(datas.get(i).getCs());
				c = row.createCell(6);
				c.setCellValue(datas.get(i).getDj());
				c = row.createCell(7);
				c.setCellValue(datas.get(i).getZmy());
			}

			for (int i = 0; i < titles.length; i++) {
				sheet.autoSizeColumn(i);
			}

			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
