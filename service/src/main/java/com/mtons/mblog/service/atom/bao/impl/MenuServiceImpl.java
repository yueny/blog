package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.bo.MenuBo;
import com.mtons.mblog.dao.mapper.MenuMapper;
import com.mtons.mblog.entity.bao.Menu;
import com.mtons.mblog.service.atom.bao.MenuService;
import com.mtons.mblog.service.exception.MtonsException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-22 17:19
 */
@Service
public class MenuServiceImpl extends AbstractPlusService<MenuBo, Menu, MenuMapper>
        implements MenuService {

    @Override
    public List<MenuBo> findAllByParentId() {
        return findAllByParentId(0L);
    }

    @Override
    public List<MenuBo> findAllByParentId(Long parentId) {
        LambdaQueryWrapper<Menu> queryWrapper = new QueryWrapper<Menu>().lambda();
        queryWrapper.eq(Menu::getParentId, parentId);

        return findAll(queryWrapper);
    }

    /**
     * 重写排序
     *
     * @return
     */
    @Override
    public List<MenuBo> findAll() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("weight");
        queryWrapper.orderByAsc("id");

        return findAll(queryWrapper);
    }

    @Override
    public List<MenuBo> findByPermissionId(Long permissionId) {
        LambdaQueryWrapper<Menu> queryWrapper = new QueryWrapper<Menu>().lambda();
        queryWrapper.eq(Menu::getPermissionId, permissionId);

        return findAll(queryWrapper);
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
                MenuBo pb = find(id);
                if(pb !=null){
                    throw new MtonsException("菜单项:「" + pb.getName() + "」 存在子菜单，不可以被删除。");
                }
                throw new MtonsException("菜单项:" + id + "存在子菜单，不可以被删除。");
            }
        }

        return super.deleteByIds(ids);
    }

}
