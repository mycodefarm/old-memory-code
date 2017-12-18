package com.jimo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jimo.service.MenService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GirlApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private MenService ms;

	@Test
	public void menServiceTest() {
		Assert.assertEquals(1, ms.getMenAge(1));
	}
}
