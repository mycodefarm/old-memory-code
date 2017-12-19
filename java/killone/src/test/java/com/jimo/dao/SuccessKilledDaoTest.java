package com.jimo.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by root on 17-5-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao kd;

    @Test
    public void insertSuccessKilled() throws Exception {
        System.out.println(kd.insertSuccessKilled(1000, 13300000000L));
    }

    @Test
    public void queryByIdWithKillOne() throws Exception {
        System.out.println(kd.queryByIdWithKillOne(1000, 13300000000L));
    }

}