package com.jimo.dao;

import com.jimo.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by root on 17-5-21.
 */
public interface SuccessKilledDao {

    /**
     * 插入成功明细，可过滤重复
     *
     * @param killId
     * @param userPhone
     * @return
     */
    public int insertSuccessKilled(@Param("killId") long killId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询成功明细并携带商品对象
     *
     * @param killId
     * @return
     */
    public SuccessKilled queryByIdWithKillOne(@Param("killId") long killId, @Param("userPhone") long userPhone);
}
