package com.mtons.mblog.modules.repository;

import com.mtons.mblog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author : langhsu
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {
    /**
     * 根据name查询tag信息
     * @param name
     * @return
     */
    Tag findByName(String name);
}
