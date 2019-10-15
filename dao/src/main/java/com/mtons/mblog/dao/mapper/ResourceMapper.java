package com.mtons.mblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtons.mblog.entity.bao.Resource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Select(value = "SELECT * FROM mto_resource WHERE amount <= 0 AND update_time < #{time} ")
    List<Resource> find0Before(@Param("time") String time);

    @Update({"<script>" +
            "update mto_resource set amount = amount + #{increment} " +
            "where md5 in " +
            "<foreach collection=\"md5s\" index=\"index\" item=\"item\" separator=\",\" open=\"(\" close=\")\">" +
                "#{item}" +
            "</foreach>" +
            "</script>"})
    int updateAmount(@Param("md5s") Collection<String> md5s, @Param("increment") long increment);

    @Update({"<script>" +
            "update mto_resource set amount = amount + #{increment} " +
            "where id in " +
            "<foreach collection=\"ids\" index=\"index\" item=\"item\" separator=\",\" open=\"(\" close=\")\">" +
                "#{item}" +
            "</foreach>" +
            "</script>"})
    int updateAmountByIds(@Param("ids") Collection<Long> md5s, @Param("increment") long increment);

}
