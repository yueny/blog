package com.mtons.mblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtons.mblog.entity.bao.Resource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/21 下午1:47
 *
 */
public interface ResourceMapper extends BaseMapper<Resource> {
    @Select(value = "SELECT * FROM mto_resource WHERE amount <= 0 AND update_time < :time ")
    List<Resource> find0Before(@Param("time") String time);

//    @Modifying
    @Update("update Resource set amount = amount + :increment where md5 in (:md5s)")
    int updateAmount(@Param("md5s") Collection<String> md5s, @Param("increment") long increment);

//    @Modifying
    @Update("update Resource set amount = amount + :increment where id in (:ids)")
    int updateAmountByIds(@Param("ids") Collection<Long> md5s, @Param("increment") long increment);

//    Resource findByMd5(String md5);
//
//    Resource findByThumbnailCode(String thumbnailCode);
//
//    List<Resource> findByMd5In(List<String> md5);
//
//    @Query(value = "SELECT * FROM mto_resource WHERE amount <= 0 AND update_time < :time ", nativeQuery = true)
//    List<Resource> find0Before(@Param("time") String time);
//
//    @Modifying
//    @Query("update Resource set amount = amount + :increment where md5 in (:md5s)")
//    int updateAmount(@Param("md5s") Collection<String> md5s, @Param("increment") long increment);
//
//    @Modifying
//    @Query("update Resource set amount = amount + :increment where id in (:ids)")
//    int updateAmountByIds(@Param("ids") Collection<Long> md5s, @Param("increment") long increment);
}
