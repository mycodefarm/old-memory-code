package com.jimo.service;

import com.jimo.dto.Exposer;
import com.jimo.dto.KillExecution;
import com.jimo.entity.KillOne;
import com.jimo.exception.KillCloseException;
import com.jimo.exception.KillException;
import com.jimo.exception.RepeatKillException;

import java.util.List;

/**
 * 业务逻辑类
 * 站在使用者的角度上来写接口
 * 三个方面：方法的粒度，参数，返回值，异常
 * Created by root on 17-5-24.
 */
public interface KillOneService {

    List<KillOne> getKillList();

    KillOne getKillById(long killId);

    /**
     * 暴露秒杀接口地址，未开启则返回服务器时间和秒杀时间
     *
     * @param killId
     */
    Exposer exportKillUrl(long killId);

    /**
     * 执行秒杀
     *
     * @param killId
     * @param userPhone
     * @param md5
     */
    KillExecution executeKill(long killId, long userPhone, String md5)
            throws KillException, RepeatKillException, KillCloseException;

    KillExecution killByProcedure(long killId, long userPhone, String md5);
}
