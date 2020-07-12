package com.mtons.mblog.service.core.api;

import com.yueny.superclub.api.pojo.IBo;

/**
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-08 17:35
 */
public interface IColService<T extends IBo> {
    /**
     * 查询总数量
     * @return 数量
     */
    int count();

}
