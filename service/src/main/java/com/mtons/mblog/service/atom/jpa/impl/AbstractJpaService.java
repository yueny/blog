package com.mtons.mblog.service.atom.jpa.impl;

import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.api.jpa.IJpaBizService;
import com.mtons.mblog.util.IdFieldSetterUtil;
import com.yueny.kapo.api.pojo.IEntry;
import com.yueny.rapid.lang.util.collect.CollectionUtil;
import com.yueny.superclub.api.pojo.IBo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

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
abstract class AbstractJpaService<T extends IBo, S extends IEntry, M extends JpaRepository>
        extends BaseService
        implements IJpaBizService<T, S>, InitializingBean {

    @Autowired
    private M baseRepository;

    public M getBaseMapper() {
        return baseRepository;
    }

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
        List<S> entrys = baseRepository.findAll();

        if(CollectionUtil.isEmpty(entrys)){
            return Collections.emptyList();
        }

        return map(entrys, boClazz);
    }

    @Override
    public List<T> findAllById(Set<Long> ids) {
        List<S> entrys = baseRepository.findAllById(ids);

        if(CollectionUtil.isEmpty(entrys)){
            return Collections.emptyList();
        }

        return map(entrys, boClazz);
    }

    @Override
    public T find(Long id) {
        Optional<S> optional = baseRepository.findById(id);

        if(optional.isPresent()){
            S s = optional.get();

            return map(s, boClazz);
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        Optional<S> optional =  baseRepository.findById(id);

        if(optional.isPresent()){
            S s = optional.get();

            baseRepository.delete(s);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIds(Set<Long> ids) {
        for (Long id: ids) {
            baseRepository.deleteById(id);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean insert(T t) {
        S entry = map(t, entryClazz);

        baseRepository.save(entry);

        // 主键ID赋值, t.setID
        IdFieldSetterUtil.setPrimaryKey(t, t.getClass(), entry.getPrimaryKey());

        return true;
    }

    @Override
    public boolean updateById(T t) {
        S entry = map(t, entryClazz);

        Object obj = baseRepository.saveAndFlush(entry);
        return true;
    }

    @Override
    @Transactional
    public boolean insertBatchs(Collection<T> boList) {
        return baseRepository.saveAll(boList).size() > 0;
    }

    @Override
    public int count() {
        Long count = baseRepository.count();
        return count.intValue();
    }

    ///////////////////////////////////////////////////
    /////////////////////// 特色 ///////////////////////
    ///////////////////////////////////////////////////
    @Override
    public T findOne(Example<S> example) {
        Optional<S> optional = baseRepository.findOne(example);
        if(optional.isPresent()){
            S s = optional.get();

            return map(s, boClazz);
        }

        return null;
    }

    @Override
    public List<T> findAll(Example<S> example) {
        List<S> entrys = baseRepository.findAll(example);

        if(CollectionUtil.isEmpty(entrys)){
            return Collections.emptyList();
        }

        return map(entrys, boClazz);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        Page<S> pageEntrys = baseRepository.findAll(pageable);

        if(CollectionUtil.isEmpty(pageEntrys.getContent())){
            return new PageImpl<>(Collections.emptyList(),
                    pageEntrys.getPageable(), pageEntrys.getTotalElements());
        }

        List<T> bos = map(pageEntrys.getContent(), boClazz);
        return new PageImpl<>(bos,
                pageEntrys.getPageable(), pageEntrys.getTotalElements());
    }

//    @Override
//    public List<T> findAll(Example<S> example, Sort sort) {
//        List<S> entrys = baseRepository.findAll(example, sort);
//
//        if(CollectionUtil.isEmpty(entrys)){
//            return Collections.emptyList();
//        }
//
//        return map(entrys, boClazz);
//    }

    @Override
    public Page<T> findAll(Example<S> example, Pageable pageable) {
        Page<S> pageEntrys = baseRepository.findAll(example, pageable);

        if(CollectionUtil.isEmpty(pageEntrys.getContent())){
            return new PageImpl<>(Collections.emptyList(),
                    pageEntrys.getPageable(), pageEntrys.getTotalElements());
        }

        List<T> bos = map(pageEntrys.getContent(), boClazz);
        return new PageImpl<>(bos,
                pageEntrys.getPageable(), pageEntrys.getTotalElements());
    }

}
