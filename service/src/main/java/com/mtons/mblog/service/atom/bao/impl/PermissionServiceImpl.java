package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.base.enums.FuncType;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.vo.PermissionTreeVo;
import com.mtons.mblog.entity.bao.Permission;
import com.mtons.mblog.dao.mapper.PermissionMapper;
import com.mtons.mblog.service.atom.bao.MenuService;
import com.mtons.mblog.service.atom.bao.PermissionService;
import com.mtons.mblog.service.exception.MtonsException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author - langhsu on 2018/2/11
 */
@Service
public class PermissionServiceImpl extends AbstractPlusService<PermissionBO, Permission, PermissionMapper>
        implements PermissionService {
    @Autowired
    private MenuService menuService;

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
    public List<PermissionBO> findAllByParentId() {
        return findAllByParentId(0L);
    }

    @Override
    public List<PermissionBO> findAllByParentId(Long parentId) {
        LambdaQueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>().lambda();
        queryWrapper.eq(Permission::getParentId, parentId);

        return findAll(queryWrapper);
    }

    @Override
    public List<PermissionTreeVo> findAllForTree(FuncType funcType) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        if(funcType != null){
            queryWrapper.eq("func_type", funcType.getValue());
        }

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

    @Override
    public PermissionBO findByName(String name) {
        LambdaQueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>().lambda();
        queryWrapper.eq(Permission::getName, name);

        return find(queryWrapper);
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
    public boolean saveOrUpdate(PermissionBO bo) {
        if(bo == null){
            return false;
        }

        // 功能只能挂在菜单下， 菜单只能挂在菜单下， 也就是说，功能下不能挂任何东西
        PermissionBO parentBo = find(bo.getParentId());
        if(bo.getFuncType() == FuncType.FUNC){
            if(parentBo.getFuncType() == FuncType.FUNC){
                throw new MtonsException("功能只能挂在菜单下");
            }
        }else{
            if(parentBo !=null && parentBo.getFuncType() == FuncType.FUNC){
                throw new MtonsException("菜单只能挂在菜单下");
            }
        }

        return super.saveOrUpdate(bo);
    }

    /**
     * 因存在默认排序的服务重写
     *
     * @return
     */
    @Override
    public List<PermissionBO> findAll() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        queryWrapper.orderByAsc("id");

        return super.findAll(queryWrapper);
    }

    /**
     * 因存在删除前置条件的服务重写
     *
     * @param ids 主键ID列表
     * @return
     */
    @Override
    public boolean deleteByIds(Set<Long> ids) {
        for (Long id : ids) {
            // 判断该菜单 id 是否存在子菜单
            if(CollectionUtils.isNotEmpty(findAllByParentId(id))){
                PermissionBO pb = find(id);
                if(pb !=null){
                    throw new MtonsException("菜单项:「" + pb.getName() + "」 存在子菜单，不可以被删除。");
                }
                throw new MtonsException("菜单项:" + id + "存在子菜单，不可以被删除。");
            }

            if(CollectionUtils.isNotEmpty(menuService.findByPermissionId(id))){
                throw new MtonsException("菜单项:" + id + "正在被菜单使用，不可以被删除。");
            }
        }

        return super.deleteByIds(ids);
    }

    /**
     * 因存在权限值不允许重复的前置条件的服务重写
     */
    @Override
    public boolean updateById(PermissionBO t) {
        PermissionBO pb = findByName(t.getName());
        if(pb != null && pb.getId() != t.getId()){
            throw new MtonsException("权限值已存在，不可以被更新。");
        }

        return super.updateById(t);
    }

    /**
     * 因存在权限值不允许重复的前置条件的服务重写
     */
    @Override
    public boolean insert(PermissionBO t) {
        PermissionBO pb = findByName(t.getName());
        if(pb != null){
            throw new MtonsException("权限值已存在，不可以被新增。");
        }

        return super.insert(t);
    }
}
