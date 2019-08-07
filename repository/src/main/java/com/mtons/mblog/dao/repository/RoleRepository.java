package com.mtons.mblog.dao.repository;

import com.mtons.mblog.entity.jpa.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    List<Role> findAllByStatus(int status);
}
