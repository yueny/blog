/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.jpa;

import com.mtons.mblog.bo.AttackIpBo;
import com.mtons.mblog.entity.jpa.AttackIpEntry;
import com.mtons.mblog.service.api.jpa.IJpaBizService;

import java.util.Set;

/**
 *
 */
public interface AttackIpService extends IJpaBizService<AttackIpBo, AttackIpEntry> {
    /**
     * 插入一条记录， 如果数据存在则忽略
     *
     * @param attackIpBo 实体对象
     */
    boolean saveIfAbsent(AttackIpBo attackIpBo);

    /**
     * 根据ip查询
     *
     * @param ip
     * @return
     */
    AttackIpBo findByIp(String ip);

    /**
     * 查询所有ip列表
     * @return 列表
     */
    Set<String> findAllIps();

    /**
     * 根据ip删除
     *
     * @param ip
     * @return
     */
    boolean deleteByIp(String ip);
}
