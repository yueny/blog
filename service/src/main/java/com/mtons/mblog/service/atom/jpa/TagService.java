package com.mtons.mblog.service.atom.jpa;

import com.mtons.mblog.bo.PostTagVO;
import com.mtons.mblog.bo.TagBO;
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
    Page<TagBO> pagingQueryTags(Pageable pageable);

    Page<PostTagVO> pagingQueryPosts(Pageable pageable, String tagName);

    void batchUpdate(String names, long latestPostId);

    void deteleMappingByPostId(long postId);
}
