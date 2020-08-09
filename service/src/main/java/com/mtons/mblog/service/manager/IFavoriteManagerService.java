package com.mtons.mblog.service.manager;

import com.mtons.mblog.vo.FavoriteVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 收藏管理服务
 */
public interface IFavoriteManagerService {
    /**
     * 查询用户收藏记录
     * @param pageable
     * @param uid
     * @return
     */
    Page<FavoriteVO> findByUserId(Pageable pageable, String uid);

    /**
     * 查询用户收藏记录
     * @param uid
     */
    FavoriteVO findByUid(String uid, String articleBlogId);
}
