package com.mtons.mblog.modules.service;

import com.mtons.mblog.bo.PostBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author : langhsu
 * @version : 1.0
 * @date : 2019/1/18
 */
public interface PostSearchService {
    /**
     * 根据关键字搜索
     * @param pageable 分页
     * @param term 关键字
     * @throws Exception
     */
    Page<PostBo> search(Pageable pageable, String term) throws Exception;

    /**
     * 重建索引
     */
    void resetIndexes();
}
