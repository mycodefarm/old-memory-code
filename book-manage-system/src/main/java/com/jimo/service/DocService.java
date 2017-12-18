package com.jimo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jimo.dao.DocDao;
import com.jimo.model.doc.BookChartDTO;
import com.jimo.model.doc.BookEnterDTO;
import com.jimo.model.doc.BookInfoDTO;
import com.jimo.util.ExcelUtil;
import com.jimo.util.Result;
import com.jimo.util.ResultHelper;

@Service
public class DocService {
	@Autowired
	private DocDao docDao;

	public Result getBookType() {
		List<String> re = docDao.getBookType();
		if (re == null) {
			return ResultHelper.error("获取类型失败");
		}
		return ResultHelper.success(re);
	}

	public Result getBookInfos(String type) {
		List<BookInfoDTO> re = docDao.getBookInfos(type);
		if (re == null) {
			return ResultHelper.error("获取失败");
		}
		return ResultHelper.success(re);
	}

	public Result getBookEntersByYear(String year) {
		List<BookEnterDTO> re = docDao.getBookEntersByYear(year);
		if (re == null) {
			return ResultHelper.error("获取失败");
		}
		return ResultHelper.success(re);
	}

	public Result getChartData(String year) {
		BookChartDTO re = docDao.getChartData(year);
		if (re == null) {
			return ResultHelper.error("获取图表数据失败");
		}
		return ResultHelper.success(re);
	}

	public void exportExcel(String path) {
		ExcelUtil.export(docDao.getBookInfos(""), path);
	}
}
