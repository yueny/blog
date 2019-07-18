package com.mtons.mblog.service.atom.jpa;

import com.mtons.mblog.entity.api.IEntry;
import com.yueny.superclub.api.pojo.IBo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
     * 查询条件查询列表信息
     *
     * @param example 查询条件
     * @return
     */
    List<T> findAll(Example<S> example);

    /**
     * 主键列表查询
     *
     * @param ids 主键列表
     * @return
     */
    List<T> findAllById(Set<Long> ids);

    /**
     * 分页查询
     *
     * PageRequest.of(0, 10) 构造
     * PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id") );
     *
     * @param pageable 分页对象，
     */
    Page<T> findAll(Pageable pageable);

    List<T> findAll(Example<S> example, Sort sort);

    Page<T> findAll(Example<S> example, Pageable pageable);

    /**
     * 根据ID获得信息
     * @param id ID
     * @return 不存在则返回为null
     */
    T get(Long id);

    /**
     * 查询条件查询实体信息
     *
     * @param example 查询条件
     * @return
     */
    T findOne(Example<S> example);

    /**
     * 删除
     * @param id ID
     * @return true/false
     */
    boolean delete(Long id);

    /**
     * 插入一条记录
     */
    boolean save(T t);

}
