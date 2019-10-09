package com.mtons.mblog.service.api;

import com.yueny.superclub.api.pojo.IBo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-08 17:35
 */
public interface IBizService<T extends IBo, S> {
    /**
     * 查询所有
     * @return 列表
     */
    List<T> findAll();

    /**
     * 主键列表查询
     *
     * @param ids 主键列表
     * @return
     */
    List<T> findAllById(Set<Long> ids);

    /**
     * 根据ID获得信息
     * @param id ID
     * @return 不存在则返回为null
     */
    T find(Long id);

    /**
     * 删除
     * @param id ID
     * @return true/false
     */
    boolean delete(Long id);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param ids 主键ID列表
     */
    boolean deleteByIds(Set<Long> ids);

    /**
     * 插入一条记录
     */
    boolean insert(T t);

    /**
	 * 根据 ID 选择修改
	 *
	 * @param t 实体对象
	 */
	boolean updateById(T t);

    /**
     * 插入（批量）
     *
     * @param boList 实体对象集合
     */
    boolean insertBatchs(Collection<T> boList);


}
