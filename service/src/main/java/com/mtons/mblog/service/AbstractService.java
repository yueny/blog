package com.mtons.mblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基类
 *
 * @author yueny(yueny09@163.com)
 *
 * @date 2015年8月9日 下午7:21:34
 */
public abstract class AbstractService {

	/**
	 * LoggerUtil available to subclasses.
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

}
