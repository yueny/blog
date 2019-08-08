/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.api.jpa;

import com.mtons.mblog.service.api.IBizService;
import com.mtons.mblog.service.api.IColService;
import com.yueny.superclub.api.pojo.IBo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 *
 */
public interface IJpaBizService<T extends IBo, S>
		extends IColService<T>, IBizService<T, S> {
	/**
	 * 查询条件查询列表信息
	 *
	 * @param example 查询条件
	 * @return
	 */
	List<T> findAll(Example<S> example);

	/**
	 * 分页查询
	 *
	 * PageRequest.of(0, 10) 构造
	 * PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id") );
	 *
	 * @param pageable 分页对象，
	 */
	Page<T> findAll(Pageable pageable);

//	List<T> findAll(Example<S> example, Sort sort);

	Page<T> findAll(Example<S> example, Pageable pageable);

	/**
	 * 查询条件查询实体信息
	 *
	 * @param example 查询条件
	 * @return
	 */
	T findOne(Example<S> example);

}
