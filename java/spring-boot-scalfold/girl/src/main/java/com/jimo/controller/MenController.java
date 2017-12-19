package com.jimo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jimo.dao.MenRepository;
import com.jimo.model.Men;
import com.jimo.service.MenService;
import com.jimo.utils.Result;
import com.jimo.utils.ResultHelper;

@RestController
@RequestMapping("/men")
public class MenController {

	@Autowired
	MenRepository mp;

	@Autowired
	MenService ms;

	/**
	 * 查询所有人
	 */
	@GetMapping("/getAll")
	public List<Men> getAllMen() {
		return mp.findAll();
	}

	// /**
	// * 添加一个人
	// */
	// @PostMapping("/addMen")
	// public Men addOneMen(@RequestParam("name") String name,
	// @RequestParam("age") Integer age) {
	// Men men = new Men();
	// men.setAge(age);
	// men.setName(name);
	// return mp.save(men);
	// }

	/**
	 * 添加一个人
	 */
	@PostMapping("/addMen")
	public Result<Object> addOneMen(@Valid Men men, BindingResult br) {
		if (br.hasErrors()) {
			return ResultHelper.error(1, br.getAllErrors().toString());
		}
		return ResultHelper.success(mp.save(men));
	}

	/**
	 * 根据id查询
	 */
	@GetMapping("/getMen/{id}")
	public Men getMenById(@PathVariable("id") Integer id) {
		return mp.findOne(id);
	}

	/**
	 * 更新
	 */
	@PutMapping("/updateMen/{id}")
	public Men updateMen(@PathVariable("id") Integer id, @RequestParam("name") String name,
			@RequestParam("age") Integer age) {
		Men men = new Men();
		men.setId(id);
		men.setAge(age);
		men.setName(name);
		return mp.save(men);
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/deleteMen/{id}")
	public void deleteMen(@PathVariable("id") Integer id) {
		mp.delete(id);
	}

	/**
	 * 根据年龄查询
	 */
	@GetMapping("/getByAge/{age}")
	public List<Men> getMenByAge(@PathVariable("age") Integer age) {
		return mp.getAllMenByAge(age);
	}

	@GetMapping("/getAge/{id}")
	public void getAge(@PathVariable("id") Integer id) throws Exception {
		ms.getAge(id);
	}
}
