package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.FavoriteBo;
import com.mtons.mblog.entity.bao.Favorite;
import com.mtons.mblog.service.core.api.bao.IPlusBizService;
import com.mtons.mblog.vo.FavoriteVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 收藏记录
 * @author langhsu
 */
public interface IFavoriteService extends IPlusBizService<FavoriteBo, Favorite> {
    /**
     * 查询用户收藏记录
     * @param uid
     */
    FavoriteBo findByUid(String uid, String articleBlogId);

    void add(String uid, Long postId);
    void delete(String uid, Long postId);
//    void add(String uid, String articleBlogId);
    void delete(String uid, String articleBlogId);

    /**
     * 根据条件统计 博文的收藏次数
     *
     * @param postId
     * @return
     */
    int countBy(long postId);
}
