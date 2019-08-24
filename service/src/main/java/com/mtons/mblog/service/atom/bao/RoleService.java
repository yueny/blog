package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.PermissionBO;
import com.mtons.mblog.bo.RoleBO;
import com.mtons.mblog.entity.bao.Role;
import com.mtons.mblog.model.RolePermissionVO;
import com.mtons.mblog.service.api.bao.IPlusBizService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author - langhsu on 2018/2/11
 */
public interface RoleService extends IPlusBizService<RoleBO, Role> {
    /**
     * 分页查询角色
     * @param pageable 分页对象
     * @param roleName 角色名称，模糊匹配，如果为null则忽略改查询条件
     */
    Page<RoleBO> findAll(Pageable pageable, String roleName);

    /**
     * 查询所有活动角色
     * @return 角色列表
     */
    List<RoleBO> findByActivate();

    /**
     * 保存角色信息。如果角色存在，则更新其信息，如果角色不存在，则添加新角色
     * @param r 角色对象
     */
    void update(RoleBO r, Set<PermissionBO> permissions);

    /**
     * 激活、停用角色
     * @param id 角色ID
     * @param active true：激活，false：停用
     */
    void activate(long id, boolean active);

}
