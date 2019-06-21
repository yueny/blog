package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.data.PostTagVO;
import com.mtons.mblog.bo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author : langhsu
 */
public interface TagService {
    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<TagVO> pagingQueryTags(Pageable pageable);
    Page<PostTagVO> pagingQueryPosts(Pageable pageable, String tagName);

    void batchUpdate(String names, long latestPostId);

    void deteleMappingByPostId(long postId);
}
