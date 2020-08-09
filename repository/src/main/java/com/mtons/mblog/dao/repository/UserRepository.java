///*
//+--------------------------------------------------------------------------
//|   Mblog [#RELEASE_VERSION#]
//|   ========================================
//|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
//|   http://www.mtons.com
//|
//+---------------------------------------------------------------------------
//*/
//package com.mtons.mblog.dao.repository;
//
//import com.mtons.mblog.entity.bao.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
///**
// * JpaRepository 中的save方法单纯的插入记录
// * CrudRepository 中的save方法是相当于merge+save ，它会先判断记录是否存在，如果存在则更新，不存在则插入记录
// */
//public interface UserRepository
//        extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
//    User findByUsername(String username);
//
//    User findByUid(String uid);
//
//    User findByEmail(String email);
//
//    User findByDomainHack(String domainHack);
//}
