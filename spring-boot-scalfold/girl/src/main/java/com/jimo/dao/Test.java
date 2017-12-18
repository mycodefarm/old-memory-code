package com.jimo.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

	@Autowired
	TestDao d;

	@org.junit.Test
	public void test() {
		d.getMen();
	}
}
