package com.jimo.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jimo.service.DocService;
import com.jimo.util.Result;

@RestController
@RequestMapping("/doc")
public class DocController {

	@Autowired
	private DocService docService;

	@PostMapping("/getBookType")
	public Result getBookType() {
		return docService.getBookType();
	}

	@PostMapping("/getBookInfos")
	public Result getBookInfos(String type) {
		return docService.getBookInfos(type);
	}

	@PostMapping("/getBookEntersByYear")
	public Result getBookEntersByYear(String year) {
		return docService.getBookEntersByYear(year);
	}

	@PostMapping("/getChartData")
	public Result getChartData(String year) {
		return docService.getChartData(year);
	}

	@RequestMapping("/downloadExcel")
	public ResponseEntity<byte[]> exportExcel() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		String fileName = "图书库存一览表.xls";
		File f = new File(fileName);
		if (!f.exists()) {
			docService.exportExcel(fileName);
		}
		fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(f), headers, HttpStatus.CREATED);
	}
}
