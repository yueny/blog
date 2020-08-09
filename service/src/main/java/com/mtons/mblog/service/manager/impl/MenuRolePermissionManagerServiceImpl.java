package com.mtons.mblog.service.manager.impl;

import com.mtons.mblog.bo.MenuBo;
import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.bo.RoleBO;
import com.mtons.mblog.bo.RolePermissionBo;
import com.mtons.mblog.vo.MenuVo;
import com.mtons.mblog.vo.RolePermissionVO;
import com.mtons.mblog.vo.menu.MenuTreeVo;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.MenuService;
import com.mtons.mblog.service.atom.bao.PermissionService;
import com.mtons.mblog.service.atom.bao.RolePermissionService;
import com.mtons.mblog.service.atom.bao.RoleService;
import com.mtons.mblog.service.manager.IMenuRolePermissionManagerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色-权限管理服务
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-24 11:04
 */
@Service
public class MenuRolePermissionManagerServiceImpl
        extends BaseService implements IMenuRolePermissionManagerService {
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionBO> findAllPermByRoleId(long roleId) {
        List<RolePermissionBo> rps = rolePermissionService.findPermissionsByRoleId(roleId);
        if(CollectionUtils.isEmpty(rps)){
            return Collections.emptyList();
        }

        Set<Long> pids = new HashSet<>();
        rps.forEach(rp -> pids.add(rp.getPermissionId()));
        return permissionService.findAllById(pids);
    }

    @Override
    public List<MenuTreeVo> findAllMenuForTree() {
        List<MenuBo> data = menuService.findAll();

        List<MenuTreeVo> results = new LinkedList<>();
        if(CollectionUtils.isEmpty(data)){
            return results;
        }

        Map<Long, MenuTreeVo> map = new LinkedHashMap<>();
        for (MenuBo po : data) {
            MenuTreeVo tree = new MenuTreeVo();
            BeanUtils.copyProperties(po, tree);
            map.put(po.getId(), tree);
        }

        for (MenuTreeVo m : map.values()) {
            PermissionBO permissionBO = permissionService.find(m.getPermissionId());
            if(permissionBO == null){
                logger.warn("权限信息不存在，id:{}.", m.getPermissionId());
                continue;
            }
            m.setPermission(permissionBO.getName());

            if (m.getParentId() == 0) {
                results.add(m);
            } else {
                MenuTreeVo p = map.get(m.getParentId());
                if (p != null) {
                    p.addChildren(m);
                }
            }
        }

        return results;
    }

    /**
     * 因存在默认排序的服务重写
     *
     * @return
     */
    @Override
    public List<MenuVo> findAllForPermission() {
        List<MenuBo> menuBos = menuService.findAll();
        if(CollectionUtils.isEmpty(menuBos)){
            return Collections.emptyList();
        }

        List<MenuVo> list = mapAny(menuBos, MenuVo.class);
        for (MenuVo menuVo : list) {
            menuVo.setPermission(permissionService.find(menuVo.getPermissionId()));
        }
        return list;
    }

    @Override
    public Page<RolePermissionVO> findAllByRoleName(Pageable pageable, String roleName) {
        Page<RoleBO> pageResult = roleService.findAll(pageable, roleName);
        return new PageImpl<>(toVO(pageResult.getContent()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public RolePermissionVO getForVo(long roleId) {
        return toVO(roleService.find(roleId));
    }

    @Override
    public Map<Long, RolePermissionVO> findMapByIds(Set<Long> roleIds) {
        List<RoleBO> list = roleService.findAllById(roleIds);
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }

        Map<Long, RolePermissionVO> ret = new LinkedHashMap<>();
        list.forEach(role -> {
            RolePermissionVO vo = toVO(role);
            ret.put(vo.getId(), vo);
        });

        return ret;
    }

    private List<RolePermissionVO> toVO(List<RoleBO> po) {
        if(CollectionUtils.isEmpty(po)){
            return Collections.emptyList();
        }

        List<RolePermissionVO> list = new ArrayList<>(po.size());
        for (RoleBO roleBO : po) {
            list.add(toVO(roleBO));
        }
        return list;
    }

    private RolePermissionVO toVO(RoleBO role) {
        if(role == null){
            return null;
        }

        RolePermissionVO rolePermissionVO = mapAny(role, RolePermissionVO.class);

        // 查询权限
        rolePermissionVO.setPermissions(findAllPermByRoleId(rolePermissionVO.getId()));
        return rolePermissionVO;
    }
}
