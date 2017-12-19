package com.jimo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jimo.dao.MenRepository;
import com.jimo.enumm.ResultEnum;
import com.jimo.exception.MenException;
import com.jimo.model.Men;

@Service
public class MenService {

	@Autowired
	MenRepository mp;

	@Transactional
	public void addTwoMen() {

	}

	public void getAge(Integer id) throws Exception {
		Men m = mp.getOne(id);
		if (m.getAge() < 10) {
			throw new MenException(ResultEnum.YOUNG);
		} else if (m.getAge() < 16) {
			throw new MenException(ResultEnum.OLD);
		}
		// 其他事务......
	}

	public int getMenAge(Integer id) {
		Men m = mp.findOne(id);
		return m.getAge();
	}
}
