package com.jimo.dao;

import com.jimo.entity.KillOne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by root on 17-5-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class KillOneDaoTest {

    @Resource
    private KillOneDao kd;

    @Test
    public void reduceNumber() throws Exception {
        System.out.println(kd.reduceNumber(1000,new Date()));
    }

    @Test
    public void queryById() throws Exception {
        KillOne k = kd.queryById(1000);
        System.out.println(k);
    }

    @Test
    public void queryAll() throws Exception {
        List<KillOne> ks = kd.queryAll(0, 100);
        for (KillOne k : ks) {
            System.out.println(k);
        }
    }

}