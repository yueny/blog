package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.PostTagVO;
import com.mtons.mblog.entity.bao.PostTag;
import com.mtons.mblog.service.api.bao.IPlusBizService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 */
public interface PostTagService extends IPlusBizService<PostTagVO, PostTag> {
    /**
     * 根据博文id 删除博文与标签的关系表
     * @param postId
     * @return
     */
    boolean deleteByPostId(Long postId);

    /**
     *
     * @param postId
     * @param tagId
     * @return
     */
    PostTagVO findByPostIdAndTagId(Long postId, Long tagId);

    /**
     * 分页查询
     * @param pageable 分页对象
     * @param tagId 标签
     */
    Page<PostTagVO> findAll(Pageable pageable, Long tagId);
}
