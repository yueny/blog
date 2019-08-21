package com.mtons.mblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtons.mblog.entity.bao.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author langhsu on 2018/8/12.
 */
public interface PermissionMapper extends BaseMapper<Permission> {
//    List<Permission> findAllByParentId(int parentId, Sort sort);

    @Select("select coalesce(max(weight), 0) from shiro_permission")
    int maxWeight();
}
