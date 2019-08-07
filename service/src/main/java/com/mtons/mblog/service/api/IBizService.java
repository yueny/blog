package com.mtons.mblog.service.api;

import com.mtons.mblog.entity.api.IEntry;
import com.yueny.superclub.api.pojo.IBo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-08 17:35
 */
public interface IBizService<T extends IBo, S extends IEntry> {
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
    T get(Long id);

    /**
     * 删除
     * @param id ID
     * @return true/false
     */
    boolean delete(Long id);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    boolean deleteByIds(Collection<Long> idList);

    /**
     * 插入一条记录
     */
    boolean insert(T t);

    /**
	 * 根据 ID 选择修改
	 *
	 * @param entity 实体对象
	 */
	boolean updateById(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    boolean insertBatchs(Collection<T> entityList);


}
