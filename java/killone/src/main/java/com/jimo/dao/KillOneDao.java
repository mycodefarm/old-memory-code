package com.jimo.dao;

import com.jimo.entity.KillOne;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 17-5-21.
 */
public interface KillOneDao {

    /**
     * 减库存
     *
     * @param killId
     * @param killTime
     * @return
     */
    public int reduceNumber(@Param("killId") long killId, @Param("killTime") Date killTime);

    /**
     * 查询秒杀对象
     *
     * @param killId
     * @return
     */
    public KillOne queryById(long killId);

    /**
     * 根据偏移量查询列表
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<KillOne> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 使用存储过程
     *
     * @param params
     */
    public void killByProcedure(Map<String, Object> params);
}
