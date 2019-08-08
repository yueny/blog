package com.mtons.mblog.service;

import com.yueny.superclub.api.pojo.IBo;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 业务基类
 *
 * @author yueny(yueny09@163.com)
 *
 * @date 2015年8月9日 下午7:21:34
 */
public abstract class BaseService extends AbstractService {
	/** */
	@Autowired
	@Getter
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
}
