package com.mtons.mblog.service.atom.jpa.impl;

import com.google.common.collect.Sets;
import com.mtons.mblog.bo.PermissionVO;
import com.mtons.mblog.bo.RolePermissionVO;
import com.mtons.mblog.bo.RoleVO;
import com.mtons.mblog.entity.jpa.Role;
import com.mtons.mblog.entity.jpa.RolePermission;
import com.mtons.mblog.entity.jpa.UserRole;
import com.mtons.mblog.dao.repository.PermissionRepository;
import com.mtons.mblog.dao.repository.RoleRepository;
import com.mtons.mblog.dao.repository.UserRoleRepository;
import com.mtons.mblog.service.atom.jpa.RoleService;
import com.mtons.mblog.service.atom.jpa.RolePermissionService;
import com.mtons.mblog.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @author - langhsu on 2018/2/11
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseService implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Page<RoleVO> paging(Pageable pageable, String name) {
        Page<Role> page = roleRepository.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (StringUtils.isNoneBlank(name)) {
                predicate.getExpressions().add(
                        builder.like(root.get("name"), "%" + name + "%"));
            }

            query.orderBy(builder.desc(root.get("id")));
            return predicate;
        }, pageable);

        Page<RoleVO> pager = new PageImpl<>(map(page.getContent(), RoleVO.class), page.getPageable(), page.getTotalElements());
        return pager;
    }

    @Override
    public List<RoleVO> list() {
        List<Role> list = roleRepository.findAllByStatus(Role.STATUS_NORMAL);

        return map(list, RoleVO.class);
    }

    @Override
    public Map<Long, RoleVO> findByIds(Set<Long> ids) {
        List<Role> list = roleRepository.findAllById(ids);

        Map<Long, RoleVO> ret = new LinkedHashMap<>();
        list.forEach(po -> {
            RoleVO vo = toVO(po);
            ret.put(vo.getId(), vo);
        });
        return ret;
    }

    @Override
    public RoleVO get(long id) {
        return toVO(roleRepository.findById(id).get());
    }

    @Override
    public void update(RoleVO r, Set<PermissionVO> permissions) {
        Optional<Role> optional = roleRepository.findById(r.getId());
        Role po = optional.orElse(new Role());
            po.setName(r.getName());
        po.setDescription(r.getDescription());
        po.setStatus(r.getStatus());

        roleRepository.save(po);

        rolePermissionService.deleteByRoleId(po.getId());

        if (permissions != null && permissions.size() > 0) {
            List<RolePermission> rps = new ArrayList<>();
            long roleId = po.getId();
            permissions.forEach(p -> {
                RolePermission rp = new RolePermission();
                rp.setRoleId(roleId);
                rp.setPermissionId(p.getId());
                rps.add(rp);
            });

            List<RolePermissionVO> list = map(rps, RolePermissionVO.class);
            Set<RolePermissionVO> sets = Sets.newHashSet();
            sets.addAll(list);

            rolePermissionService.add(sets);
        }
    }

    @Override
    public boolean delete(long id) {
        List<UserRole> urs = userRoleRepository.findAllByRoleId(id);
        Assert.state(urs == null || urs.size() == 0, "该角色已经被使用,不能被删除");
        roleRepository.deleteById(id);
        rolePermissionService.deleteByRoleId(id);
        return true;
    }

    @Override
    public void activate(long id, boolean active) {
        Role po = roleRepository.findById(id).get();
        po.setStatus(active ? Role.STATUS_NORMAL : Role.STATUS_CLOSED);
    }

    private RoleVO toVO(Role po) {
        RoleVO r = new RoleVO();
        r.setId(po.getId());
        r.setName(po.getName());
        r.setDescription(po.getDescription());
        r.setStatus(po.getStatus());

        r.setPermissions(rolePermissionService.findPermissions(r.getId()));
        return r;
    }
}
