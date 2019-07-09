package com.mtons.mblog.dao.repository;

import com.mtons.mblog.entity.jpa.AttackIpEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 攻击者IP信息
 */
public interface AttackIpRepository extends
        JpaRepository<AttackIpEntry, Integer>,
        JpaSpecificationExecutor<AttackIpEntry> {
    /**
     * 分页查询
     *
     * @param pageable 分页条件
     * @return
     */
    Page<AttackIpEntry> findAll(Pageable pageable);
}
