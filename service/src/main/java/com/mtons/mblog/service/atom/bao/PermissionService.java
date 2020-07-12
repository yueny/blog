package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.base.enums.FuncType;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.model.PermissionTreeVo;
import com.mtons.mblog.entity.bao.Permission;
import com.mtons.mblog.service.core.api.bao.IPlusBizService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能只能挂在菜单下， 菜单只能挂在菜单下
 *
 * @author - langhsu on 2018/2/11
 */
public interface PermissionService extends IPlusBizService<PermissionBO, Permission> {
    /**
     * 分页查询权限
     * @param pageable 分页对象
     * @param name 权限名称，模糊匹配，如果为null则忽略改查询条件
     */
    @Transactional(readOnly = true)
    Page<PermissionBO> findAll(Pageable pageable, String name);

    /**
     * 查询顶级权限列表， 父权限主键 为0
     */
    List<PermissionBO> findAllByParentId();

    /**
     * 查询子权限列表
     * @param parentId 父权限主键
     */
    List<PermissionBO> findAllByParentId(Long parentId);

    /**
     * 列出所有菜单项
     * @return 菜单列表
     */
    @Transactional(readOnly = true)
    List<PermissionTreeVo> findAllForTree(FuncType funcType);

    /**
     * 查询权限值信息
     */
    PermissionBO findByName(String name);

//    /**
//     * 查询子菜单项
//     * @param parentId 根目录ID
//     * @return 菜单列表
//     */
//    List<PermissionTreeVo> findAllForTree(int parentId);

}
