package com.jimo.service.impl;

import com.jimo.dao.KillOneDao;
import com.jimo.dao.SuccessKilledDao;
import com.jimo.dao.cache.RedisDao;
import com.jimo.dto.Exposer;
import com.jimo.dto.KillExecution;
import com.jimo.entity.KillOne;
import com.jimo.entity.SuccessKilled;
import com.jimo.enums.KillOneStateEnum;
import com.jimo.exception.KillCloseException;
import com.jimo.exception.KillException;
import com.jimo.exception.RepeatKillException;
import com.jimo.service.KillOneService;
import com.sun.org.apache.bcel.internal.ExceptionConstants;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 17-5-26.
 */
@Service
public class KillOneServiceImpl implements KillOneService {

    private Logger logger = LoggerFactory.getLogger(KillOneServiceImpl.class);

    @Autowired
    private KillOneDao killOneDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    private final String SLAT = "qjdajHSBDKJAB324&^%^%%&";//用于MD5加密的密钥

    public List<KillOne> getKillList() {
        return killOneDao.queryAll(0, 4);
    }

    public KillOne getKillById(long killId) {
        return killOneDao.queryById(killId);
    }

    public Exposer exportKillUrl(long killId) {
//        KillOne killOne = killOneDao.queryById(killId);
//        if (killOne == null) {
//            return new Exposer(false, killId);
//        }
        /*
        * 访问缓存步骤：
        * 1、从缓存获取对象
        * 2、为空则从数据库获取，并存入缓存
        * 3、否则从缓存获取
        * */
        KillOne killOne = redisDao.getKillOne(killId);
        if (killOne == null) {
            killOne = killOneDao.queryById(killId);
            if (killOne == null) {
                return new Exposer(false, killId);
            } else {
                redisDao.putKillOne(killOne);
            }
        }

        Date start = killOne.getStartTime();
        Date end = killOne.getEndTime();
        Date now = new Date();
        //不在时间范围内
        if (now.getTime() - start.getTime() < 0 || now.getTime() - end.getTime() > 0) {
            return new Exposer(false, now.getTime(), start.getTime(), end.getTime(), killId);
        }
        String md5 = getMD5(killId);
        return new Exposer(true, md5, killId);
    }

    private String getMD5(long killId) {
        String base = killId + "/" + SLAT;
        return DigestUtils.md5DigestAsHex(base.getBytes());//使用spring的工具类生成MD5
    }

    @Transactional
    /*
    * 使用声明式事务：
    * 1、开发团队风格明确
    * 2、保证事务执行时间尽可能短，不要穿插耗时操作（RPC或其他网络操作），否则剥离出去
    * 3、不是所有方法都需要事务
    * */
    public KillExecution executeKill(long killId, long userPhone, String md5)
            throws KillException, RepeatKillException, KillCloseException {
        if (md5 == null || !md5.equals(getMD5(killId))) {
            throw new KillException("数据篡改");
        }
        try {
            /**old
             //减库存
             int reduceNumber = killOneDao.reduceNumber(killId, new Date());
             if (reduceNumber <= 0) {
             throw new KillCloseException("秒杀结束");
             } else {
             //记录购买行为
             int insertCount = successKilledDao.insertSuccessKilled(killId, userPhone);
             if (insertCount <= 0) {
             throw new KillException("重复秒杀");
             } else {
             SuccessKilled successKilled = successKilledDao.queryByIdWithKillOne(killId, userPhone);
             return new KillExecution(killId, KillOneStateEnum.SUCCESS, successKilled);
             }
             }*/
            /**new
             * 先插入数据，再更新数据，可以减少行级锁的时间
             * 因为默认mysql 的insert没有行级锁，update是有的
             * 如果插入成功，但更新失败了还是会回滚
             * */
            //记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(killId, userPhone);
            if (insertCount <= 0) {
                throw new KillException("重复秒杀");
            } else {
                //减库存
                int reduceNumber = killOneDao.reduceNumber(killId, new Date());
                if (reduceNumber <= 0) {
                    throw new KillCloseException("秒杀结束");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithKillOne(killId, userPhone);
                    return new KillExecution(killId, KillOneStateEnum.SUCCESS, successKilled);
                }
            }
            //异常会执行回滚
        } catch (KillCloseException e) {
            throw e;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e3) {
            logger.error(e3.getMessage(), e3);
            //所有编译期异常转为运行时异常
            throw new KillException("内部错误:" + e3.getMessage());
        }
    }

    /**
     * 使用存储过程执行秒杀
     *
     * @param killId
     * @param userPhone
     * @param md5
     * @return
     */
    public KillExecution killByProcedure(long killId, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMD5(killId))) {
            return new KillExecution(killId, KillOneStateEnum.DATA_REWRITE);
        }
        Date killTime = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("killId", killId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        try {
            killOneDao.killByProcedure(map);
            Integer result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                return new KillExecution(killId, KillOneStateEnum.SUCCESS);
            } else {
                return new KillExecution(killId, KillOneStateEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new KillExecution(killId, KillOneStateEnum.INNER_ERROR);
    }
}
