package com.mtons.mblog.service.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.bo.FavoriteBo;
import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.entity.bao.Favorite;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.FeatureStatisticsPostAtomService;
import com.mtons.mblog.service.atom.bao.IFavoriteService;
import com.mtons.mblog.service.atom.bao.PostService;
import com.mtons.mblog.service.manager.IFavoriteManagerService;
import com.mtons.mblog.vo.FavoriteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FavoriteManagerServiceImpl extends BaseService implements IFavoriteManagerService {
    @Autowired
    private FeatureStatisticsPostAtomService featureStatisticsPostAtomService;
    @Autowired
    private IFavoriteService favoriteService;
    @Autowired
    private PostService postService;

    @Override
    public Page<FavoriteVO> findByUserId(Pageable pageable, String uid) {
        LambdaQueryWrapper<Favorite> queryWrapper = new QueryWrapper<Favorite>().lambda();
        queryWrapper.eq(Favorite::getUid, uid);
        Page<FavoriteBo> page = favoriteService.findAll(pageable, queryWrapper);

        List<FavoriteVO> favoriteVOS = new ArrayList<>();
        Set<Long> postIds = new HashSet<>();
        for (FavoriteBo po : page.getContent()) {
            favoriteVOS.add(mapAny(po, FavoriteVO.class));

            postIds.add(po.getPostId());
        }

        if (postIds.size() > 0) {
            Map<Long, PostBo> posts = postService.findMapForAuthorByIds(postIds);

            for (FavoriteVO favoriteVO : favoriteVOS) {
                PostBo postBo = posts.get(favoriteVO.getPostId());
                favoriteVO.setPost(postBo);
            }
        }
        return new PageImpl<>(favoriteVOS, pageable, page.getTotalElements());
    }

    @Override
    public FavoriteVO findByUid(String uid, String articleBlogId) {
        FavoriteBo favorite = favoriteService.findByUid(uid, articleBlogId);
        if(favorite == null){
            return null;
        }

        FavoriteVO favoriteVO = mapAny(favorite, FavoriteVO.class);

        PostBo postBo = postService.getByArticleBlogId(articleBlogId);
        favoriteVO.setPost(postBo);

        return favoriteVO;
    }
}
