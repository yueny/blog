package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtons.mblog.service.api.bao.IPlusBizService;
import com.mtons.mblog.util.IdFieldSetterUtil;
import com.yueny.kapo.api.pojo.IEntry;
import com.yueny.superclub.api.pojo.IBo;
import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 基类
 *
 * @author yueny(yueny09@163.com)
 *
 * @date 2015年8月9日 下午7:21:34
 */
 class AbstractPlusService<T extends IBo, S extends IEntry, M extends BaseMapper<S>>
		extends ServiceImpl<M, S>
		implements IPlusBizService<T, S>, InitializingBean {
	///////////////////////////////////////////////////
	/////////////////////// AbstractService ///////////////////////
	///////////////////////////////////////////////////
	/**
	 * LoggerUtil available to subclasses.
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	///////////////////////////////////////////////////
	/////////////////////// BaseService ///////////////////////
	///////////////////////////////////////////////////
	/** */
	@Autowired
	private Mapper mapper;

	/**
	 * 映射数组, Entry --> Bo
	 *
	 * @param <T>
	 *            目标数据类型
	 * @param <S>
	 *            源数据类型
	 * @param sourceList
	 *            源数据列表
	 * @param targetInfo
	 *            目标数据类型信息
	 * @return 映射后的目标数据列表
	 */
	protected <T extends IBo, S> List<T> map(final List<S> sourceList, final Class<T> targetInfo) {
		if (CollectionUtils.isEmpty(sourceList)) {
			logger.warn("使用空数据源进行映射!");
			return Collections.<T>emptyList();
		}

		final List<T> targetList = new ArrayList<T>(sourceList.size());
		for (final S entry : sourceList) {
			// targetList.add(mapper.map(entry, targetInfo));
			targetList.add(map(entry, targetInfo));
		}
		return targetList;
	}

	/**
	 * 映射数组, Bo --> Entry
	 *
	 * @param <T>
	 *            目标数据类型
	 * @param <S>
	 *            源数据类型
	 * @param sourceList
	 *            源数据列表
	 * @param targetInfo
	 *            目标数据类型信息
	 * @return 映射后的目标数据列表
	 */
	protected <T extends IBo, S> List<S> map(final Set<T> sourceList, final Class<S> targetInfo) {
		if (CollectionUtils.isEmpty(sourceList)) {
			logger.warn("使用空数据源进行映射!");
			return Collections.<S>emptyList();
		}

		final List<S> targetList = new ArrayList<S>(sourceList.size());
		for (final T bo : sourceList) {
			targetList.add(map(bo, targetInfo));
		}
		return targetList;
	}

	/**
	 * 映射数组, Any--> bo
	 *
	 * @param <T>
	 *            目标数据类型
	 * @param <S>
	 *            源数据类型
	 * @param sourceList
	 *            源数据列表
	 * @param targetInfo
	 *            目标数据类型信息
	 * @return 映射后的目标数据列表
	 */
	protected <T extends IBo, S> List<T> mapAny(final List<S> sourceList, final Class<T> targetInfo) {
		if (CollectionUtils.isEmpty(sourceList)) {
			logger.warn("使用空数据源进行映射!");
			return Collections.<T>emptyList();
		}

		final List<T> targetList = new ArrayList<T>(sourceList.size());
		for (final S entry : sourceList) {
			targetList.add(mapAny(entry, targetInfo));
		}
		return targetList;
	}

	/**
	 * 将对象实体转换为业务对象, Entry --> Bo
	 *
	 * @param <T>
	 *            业务对象类型
	 * @param <S>
	 *            对象实体类型
	 * @param sourceEntry
	 *            实体对象
	 * @param targetBOClass
	 *            业务对象类
	 * @return 映射后的业务对象
	 */
	protected <T extends IBo, S> T map(final S sourceEntry, final Class<T> targetBOClass) {
		Assert.notNull(sourceEntry, "映射的实体对象不可为空");
		return mapper.map(sourceEntry, targetBOClass);
	}

	/**
	 * 将业务对象转换为对象实体, Bo --> Entry
	 *
	 * @param <T>
	 *            业务对象类型
	 * @param <S>
	 *            对象实体类型
	 * @param sourceBO
	 *            业务对象
	 * @param targetEntryClass
	 *            实体对象类
	 * @return 映射后的实体对象
	 */
	protected <T extends IBo, S> S map(final T sourceBO, final Class<S> targetEntryClass) {
		Assert.notNull(sourceBO, "映射的业务对象不可为空");
		return mapper.map(sourceBO, targetEntryClass);
	}


	/**
	 * 将对象实体转换为业务对象, Any --> Bo
	 *
	 * @param <T>
	 *            业务对象类型
	 * @param <S>
	 *            对象实体类型
	 * @param sourceEntry
	 *            实体对象
	 * @param targetBOClass
	 *            业务对象类
	 * @return 映射后的业务对象
	 */
	protected <T extends IBo, S> T mapAny(final S sourceEntry, final Class<T> targetBOClass) {
		Assert.notNull(sourceEntry, "映射的实体对象不可为空");
		return mapper.map(sourceEntry, targetBOClass);
	}

	///////////////////////////////////////////////////
	/////////////////////// AbstractPlusService ///////////////////////
	///////////////////////////////////////////////////
	/**
	 * Bo 类实例
	 */
	private Class<T> boClazz;
	/**
	 * Entry 类实例
	 */
	private Class<S> entryClazz;

	@Override
	public void afterPropertiesSet() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			// bo --> T extends IBo
			boClazz = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
			// entry --> S extends IEntry
			entryClazz = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
		}
	}

	@Override
	public List<T> findAll() {
		List<S> entryList = list(Wrappers.emptyWrapper());

		if(CollectionUtils.isEmpty(entryList)){
			return Collections.emptyList();
		}

		return map(entryList, boClazz);
	}

	@Override
	public List<T> findAllById(Set<Long> ids) {
		List<S> entryList = baseMapper.selectBatchIds(ids);

		if(CollectionUtils.isEmpty(entryList)){
			return Collections.emptyList();
		}

		return map(entryList, boClazz);
	}

	@Override
	public T get(Long id) {
		S entry = baseMapper.selectById(id);

		if(entry == null){
			return null;
		}

		return map(entry, boClazz);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(Long id) {
		return baseMapper.deleteById(id) > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteByIds(Set<Long> ids) {
		return baseMapper.deleteBatchIds(ids) == ids.size();
	}

	@Override
	@Transactional
	public boolean insert(T t) {
		S entry = map(t, entryClazz);

		int pk = baseMapper.insert(entry);

		// 主键ID赋值, t.setID
		Long id = entry.getPrimaryKey();
		IdFieldSetterUtil.setPrimaryKey(t, t.getClass(), id);

		return pk == 1;
	}

	@Override
	public boolean updateById(T t) {
		S entry = map(t, entryClazz);

		return baseMapper.updateById(entry) > 0;
	}

	@Override
	@Transactional
	public boolean insertBatchs(Collection<T> entityList) {
		for (T t : entityList) {
			S entry = map(t, entryClazz);

			int rs = baseMapper.insert(entry);

			// 主键ID赋值, t.setID
			IdFieldSetterUtil.setPrimaryKey(t, t.getClass(), entry.getPrimaryKey());
		}

		return true;
	}

	@Override
	public int count() {
		return super.count();
	}

	///////////////////////////////////////////////////
	/////////////////////// 特色 ///////////////////////
	///////////////////////////////////////////////////

	@Override
	public T get(Wrapper<S> queryWrapper) {
		S entry = baseMapper.selectOne(queryWrapper);

		if(entry == null){
			return null;
		}

		return map(entry, boClazz);
	}

	@Override
	public List<T> findAll(Wrapper<S> queryWrapper) {
		List<S> entryList = baseMapper.selectList(queryWrapper);

		if(CollectionUtils.isEmpty(entryList)){
			return Collections.emptyList();
		}

		return map(entryList, boClazz);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return findAll(pageable, Wrappers.emptyWrapper());
	}

//	@Override
//	public List<T> findAll(Wrapper<S> queryWrapper, Sort sort) {
//		com.baomidou.mybatisplus.extension.plugins.pagination.Page<S> pageablePlus = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
//
//
//		Set<String> ascs = new HashSet<>();
//		Set<String> descss = new HashSet<>();
//		sort.get().forEach(order->{
//			if(order.isAscending()){
//				ascs.add(order.getProperty());
//			}else{
//				descss.add(order.getProperty());
//			}
//		});
//		if(!ascs.isEmpty()){
////			String[] arrays = ascs.stream().toArray(String[]::new);
//			pageablePlus.setAsc(ascs.stream().toArray(String[]::new));
//		}
//		if(!descss.isEmpty()){
//			pageablePlus.setAsc(descss.stream().toArray(String[]::new));
//		}
//
//		IPage<S> pageList = page(pageablePlus, queryWrapper);
//
//		Pageable pageable = PageRequest.of(1, 10);
//		return findAllToConvertPage(pageable, pageList);
//	}

	@Override
	public Page<T> findAll(Pageable pageable, Wrapper<S> queryWrapper) {
		com.baomidou.mybatisplus.extension.plugins.pagination.Page<S> pageablePlus = convertToPlusPage(pageable);

		IPage<S> pageList = page(pageablePlus, queryWrapper);

		return findAllConvertToSpringPage(pageable, pageList);
	}

	/**
	 * 分页对象的转换
	 * org.springframework.data.domain.Page 转为 com.baomidou.mybatisplus.core.metadata.IPage
	 *
	 * @param pageableData springframework分页查询条件
	 *
	 * @return mybatisplus 分页结果对象
	 */
	private com.baomidou.mybatisplus.extension.plugins.pagination.Page<S> convertToPlusPage(Pageable pageableData) {
		// 因为 springframework PageRequest的页数从0开始，但mybatisplus中从1开始，因为需要+1
		com.baomidou.mybatisplus.extension.plugins.pagination.Page<S> pageablePlus = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(
				pageableData.getPageNumber()+1, pageableData.getPageSize());

		if(pageableData.getSort() != null){
			Set<String> ascs = new HashSet<>();
			Set<String> descss = new HashSet<>();
			pageableData.getSort().get().forEach(order->{
				if(order.isAscending()){
					ascs.add(order.getProperty());
				}else{
					descss.add(order.getProperty());
				}
			});
			if(!ascs.isEmpty()){
//			    String[] arrays = ascs.stream().toArray(String[]::new);
				pageablePlus.setAsc(ascs.stream().toArray(String[]::new));
			}
			if(!descss.isEmpty()){
				pageablePlus.setDesc(descss.stream().toArray(String[]::new));
			}
		}

		return pageablePlus;
	}

	/**
	 * 查询分页对象结果的转换
	 * com.baomidou.mybatisplus.core.metadata.IPage 转为 org.springframework.data.domain.Page
	 *
	 * @param pageableData springframework 分页查询条件
	 * @param pagePlusList mybatisplus 查询到的分页结果对象
	 * @return org.springframework.data.domain.Page
	 */
	private Page<T> findAllConvertToSpringPage(Pageable pageableData, IPage<S> pagePlusList) {
		if(CollectionUtils.isEmpty(pagePlusList.getRecords())){
			return new PageImpl(Collections.emptyList(), pageableData, pagePlusList.getTotal());
		}

		List<T> list = map(pagePlusList.getRecords(), boClazz);
		return new PageImpl<>(list, pageableData, pagePlusList.getTotal());
	}

	@Override
	public boolean delete(Wrapper<S> wrapper) {
		return remove(wrapper);
	}

}
