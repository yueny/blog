/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.service.EntityService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * @author langhsu
 */
@Service
public class EntityServiceImpl implements EntityService {
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public void initSettings(Resource resource) {
		Session session = entityManager.unwrap(Session.class);
		session.doWork(connection -> ScriptUtils.executeSqlScript(connection, resource));
	}

}
