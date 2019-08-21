package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.model.PermissionTreeVo;
import com.mtons.mblog.entity.bao.Permission;
import com.mtons.mblog.dao.mapper.PermissionMapper;
import com.mtons.mblog.service.atom.bao.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author - langhsu on 2018/2/11
 */
@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl extends AbstractPlusService<PermissionBO, Permission, PermissionMapper>
        implements PermissionService {
    @Override
    public Page<PermissionBO> findAll(Pageable pageable, String name) {
        LambdaQueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>().lambda();

        if (StringUtils.isNoneBlank(name)) {
            queryWrapper.like(Permission::getName, name);
        }
        queryWrapper.orderByDesc(Permission::getId);

        return findAll(pageable, queryWrapper);
//
//        Page<Permission> page = permissionRepository.findAll((root, query, builder) -> {
//            Predicate predicate = builder.conjunction();
//
//            if (StringUtils.isNoneBlank(name)) {
//                predicate.getExpressions().add(
//                        builder.like(root.get("name"), "%" + name + "%"));
//            }
//
//            query.orderBy(builder.desc(root.get("id")));
//            return predicate;
//        }, pageable);
//        return page;
    }

    @Override
    public List<PermissionTreeVo> findAllForTree() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        queryWrapper.orderByDesc("weight");
        queryWrapper.orderByAsc("id");

        List<PermissionBO> data = findAll(queryWrapper);

        List<PermissionTreeVo> results = new LinkedList<>();

        Map<Long, PermissionTreeVo> map = new LinkedHashMap<>();
        for (PermissionBO po : data) {
            PermissionTreeVo tree = new PermissionTreeVo();
            BeanUtils.copyProperties(po, tree);
            map.put(po.getId(), tree);
        }

        for (PermissionTreeVo m : map.values()) {
            if (m.getParentId() == 0) {
                results.add(m);
            } else {
                PermissionTreeVo p = map.get(m.getParentId());
                if (p != null) {
                    p.addItem(m);
                }
            }
        }

        return results;
    }

//    @Override
//    public List<PermissionTreeVo> tree(int parentId) {
//        LambdaQueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>().lambda();
//        queryWrapper.eq(Permission::getParentId, parentId);
//        queryWrapper.orderByDesc(Permission::getWeight);
//        queryWrapper.orderByAsc(Permission::getId);
//
//        List<PermissionBO> list = findAll(queryWrapper);
//
//        List<PermissionTreeVo> results = new ArrayList<>();
//        list.forEach(po -> {
//            PermissionTreeVo menu = new PermissionTreeVo();
//            BeanUtils.copyProperties(po, menu);
//            results.add(menu);
//        });
//        return results;
//    }
////    @Override
////    public List<PermissionTree> tree(int parentId) {
////        List<Permission> list = permissionRepository.findAllByParentId(parentId, sort);
////        List<PermissionTree> results = new ArrayList<>();
////
////        list.forEach(po -> {
////            PermissionTree menu = new PermissionTree();
////            BeanUtils.copyProperties(po, menu);
////            results.add(menu);
////        });
////        return results;
////    }

    @Override
    public List<PermissionBO> findAll() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        queryWrapper.orderByAsc("id");

        return super.findAll(queryWrapper);
    }
}
