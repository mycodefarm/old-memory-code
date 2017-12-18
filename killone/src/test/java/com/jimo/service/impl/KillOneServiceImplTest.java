package com.jimo.service.impl;

import com.jimo.dto.Exposer;
import com.jimo.dto.KillExecution;
import com.jimo.entity.KillOne;
import com.jimo.exception.KillCloseException;
import com.jimo.exception.RepeatKillException;
import com.jimo.service.KillOneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by root on 17-5-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class KillOneServiceImplTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KillOneService killOneService;

    @Test
    public void getKillList() throws Exception {
        List<KillOne> killList = killOneService.getKillList();
        logger.info("killList:{}", killList);
    }

    @Test
    public void getKillById() throws Exception {
        KillOne killOne = killOneService.getKillById(1001);
        logger.info("killOne:{}", killOne);
    }

    @Test
    public void testKillLogic() throws Exception {
        long id = 1000;
        Exposer exposer = killOneService.exportKillUrl(id);
        if (exposer.isExposed()) {
            logger.info("exposer:{}", exposer);
            long phone = 11011111111L;
            try {
                KillExecution killExecution = killOneService.executeKill(id, phone, exposer.getMd5());
                logger.info("result:{}", killExecution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (KillCloseException e) {
                logger.error(e.getMessage());
            }
        } else {
            //秒杀未开启
            logger.warn("exposer:{}", exposer);
        }
    }

    @Test
    public void killByProcedure() {
        Exposer exposer = killOneService.exportKillUrl(1000);
        logger.info("exposor:{}", exposer);
        if (exposer.isExposed()) {
            long phone = 11011111111L;
            KillExecution killExecution = killOneService.killByProcedure(1000, phone, exposer.getMd5());
            logger.info("result:{}", killExecution);
        } else {
            System.out.println("not ok");
        }
    }
}