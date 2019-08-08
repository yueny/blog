/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.api.bao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.mtons.mblog.service.api.IBizService;
import com.mtons.mblog.service.api.IColService;
import com.yueny.superclub.api.pojo.IBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 *
 */
public interface IPlusBizService<T extends IBo, S>
		extends IBizService<T, S>, IColService<T> {
  /**
   * 查询条件查询列表信息
   *
   * @param queryWrapper 查询条件。 如：
   *    <pre>
   *     LambdaQueryWrapper<S> queryWrapper = new QueryWrapper<S>().lambda();
   *     queryWrapper.eq(Post::getAuthorId, authorId);
   *    </pre>
   *
   * @return
   */
  List<T> findAll(Wrapper<S> queryWrapper);

	/**
	 * 分页查询
	 *
	 * PageRequest.of(0, 10) 构造
	 * PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id") );
	 *
	 * @param pageable 分页对象，
	 */
	Page<T> findAll(Pageable pageable);

//	List<T> findAll(Wrapper<S> queryWrapper, Sort sort);

	Page<T> findAll(Pageable pageable, Wrapper<S> queryWrapper);

	/**
	 * 根据 entity 条件，删除记录
	 */
	boolean delete(Wrapper<S> queryWrapper);

//	/**
//	 * 根据 whereEntity 条件，更新记录
//	 *
//	 * @param entity        实体对象
//	 * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
//	 */
//	boolean update(T entity, Wrapper<T> updateWrapper);
//
//	/**
//	 * 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
//	 *
//	 * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
//	 */
//	default boolean update(Wrapper<T> updateWrapper) {
//		return update(null, updateWrapper);
//	}
//
//	/**
//	 * 根据ID 批量更新
//	 *
//	 * @param entityList 实体对象集合
//	 */
//	@Transactional(rollbackFor = Exception.class)
//	default boolean updateBatchById(Collection<T> entityList) {
//		return updateBatchById(entityList, 1000);
//	}
//
//	/**
//	 * 根据ID 批量更新
//	 *
//	 * @param entityList 实体对象集合
//	 * @param batchSize  更新批次数量
//	 */
//	boolean updateBatchById(Collection<T> entityList, int batchSize);
//
//	/**
//	 * TableId 注解存在更新记录，否插入一条记录
//	 *
//	 * @param entity 实体对象
//	 */
//	boolean saveOrUpdate(T entity);
//
//	/**
//	 * 根据 ID 查询
//	 *
//	 * @param id 主键ID
//	 */
//	T getById(Serializable id);
//
//	/**
//	 * 查询（根据ID 批量查询）
//	 *
//	 * @param idList 主键ID列表
//	 */
//	Collection<T> listByIds(Collection<? extends Serializable> idList);
//
//	/**
//	 * 查询（根据 columnMap 条件）
//	 *
//	 * @param columnMap 表字段 map 对象
//	 */
//	Collection<T> listByMap(Map<String, Object> columnMap);
//
//	/**
//	 * 根据 Wrapper，查询一条记录 <br/>
//	 * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
//	 *
//	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
//	 */
//	default T getOne(Wrapper<T> queryWrapper) {
//		return getOne(queryWrapper, true);
//	}
//
//	/**
//	 * 根据 Wrapper，查询一条记录
//	 *
//	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
//	 * @param throwEx      有多个 result 是否抛出异常
//	 */
//	T getOne(Wrapper<T> queryWrapper, boolean throwEx);
//
//	/**
//	 * 根据 Wrapper，查询一条记录
//	 *
//	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
//	 */
//	Map<String, Object> getMap(Wrapper<T> queryWrapper);
//
//	/**
//	 * 根据 Wrapper，查询一条记录
//	 *
//	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
//	 * @param mapper       转换函数
//	 */
//	default <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
//		return SqlHelper.getObject(listObjs(queryWrapper, mapper));
//	}
//
//	/**
//	 * 根据 Wrapper 条件，查询总记录数
//	 *
//	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
//	 */
//	int count(Wrapper<T> queryWrapper);
//
//	/**
//	 * 查询总记录数
//	 *
//	 * @see Wrappers#emptyWrapper()
//	 */
//	default int count() {
//		return count(Wrappers.emptyWrapper());
//	}
//
//	/**
//	 * 查询列表
//	 *
//	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
//	 */
//	List<T> list(Wrapper<T> queryWrapper);
//
//	/**
//	 * 查询所有
//	 *
//	 * @see Wrappers#emptyWrapper()
//	 */
//	default List<T> list() {
//		return list(Wrappers.emptyWrapper());
//	}
//
//	/**
//	 * 翻页查询
//	 *
//	 * @param page         翻页对象
//	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
//	 */
//	IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);
//
//	/**
//	 * 无条件翻页查询
//	 *
//	 * @param page 翻页对象
//	 * @see Wrappers#emptyWrapper()
//	 */
//	default IPage<T> page(IPage<T> page) {
//		return page(page, Wrappers.emptyWrapper());
//	}

}
