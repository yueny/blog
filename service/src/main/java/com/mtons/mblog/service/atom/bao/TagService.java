package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.PostTagVO;
import com.mtons.mblog.bo.TagBO;
import com.mtons.mblog.entity.bao.Tag;
import com.mtons.mblog.service.core.api.bao.IPlusBizService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : langhsu
 */
public interface TagService extends IPlusBizService<TagBO, Tag> {

    TagBO findByName(String name);




    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<TagBO> pagingQueryTags(Pageable pageable);

    /**
     * 分页且模糊查询
     * @param pageable 分页条件，含 sort
     * @param name tag name 全模糊查询 like %q%
     * @return
     */
    List<TagBO> findPagingTagsByNameLike(Pageable pageable, String name);

    Page<PostTagVO> pagingQueryPosts(Pageable pageable, String tagName);

    void batchUpdate(String names, long latestPostId);

    void deteleMappingByPostId(long postId);
}
