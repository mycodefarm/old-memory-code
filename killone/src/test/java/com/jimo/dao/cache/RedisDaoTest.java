package com.jimo.dao.cache;

import com.jimo.dao.KillOneDao;
import com.jimo.entity.KillOne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by root on 17-5-30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private KillOneDao killOneDao;

    @Test
    public void testLogic() throws Exception {
        KillOne killOne = redisDao.getKillOne(1000);
        System.out.println(killOne);
        if (killOne == null) {
            killOne = killOneDao.queryById(1000);
            if (killOne == null) {
                System.err.println("error");
            } else {
                System.out.println(redisDao.putKillOne(killOne));
            }
        }
    }

}