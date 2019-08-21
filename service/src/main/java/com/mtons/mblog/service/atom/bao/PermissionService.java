package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.model.PermissionTreeVo;
import com.mtons.mblog.entity.bao.Permission;
import com.mtons.mblog.service.api.bao.IPlusBizService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author - langhsu on 2018/2/11
 */
public interface PermissionService extends IPlusBizService<PermissionBO, Permission> {
    /**
     * 分页查询权限
     * @param pageable 分页对象
     * @param name 权限名称，模糊匹配，如果为null则忽略改查询条件
     */
    Page<PermissionBO> findAll(Pageable pageable, String name);

    /**
     * 列出所有菜单项
     * @return 菜单列表
     */
    List<PermissionTreeVo> findAllForTree();

//    /**
//     * 查询子菜单项
//     * @param parentId 根目录ID
//     * @return 菜单列表
//     */
//    List<PermissionTreeVo> findAllForTree(int parentId);

}
