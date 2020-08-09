package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.bo.FavoriteBo;
import com.mtons.mblog.dao.mapper.FavoriteMapper;
import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.entity.bao.Favorite;
import com.mtons.mblog.service.atom.bao.IFavoriteService;
import com.mtons.mblog.service.atom.bao.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author langhsu on 2015/8/31.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class FavoriteServiceImpl extends AbstractPlusService<FavoriteBo, Favorite, FavoriteMapper>
        implements IFavoriteService {
    @Autowired
    private PostService postService;

    @Override
    public FavoriteBo findByUid(String uid, String articleBlogId) {
        LambdaQueryWrapper<Favorite> queryWrapper = new QueryWrapper<Favorite>().lambda();
        queryWrapper.eq(Favorite::getUid, uid);
        queryWrapper.eq(Favorite::getArticleBlogId, articleBlogId);

        return find(queryWrapper);
    }

    @Override
    @Transactional
    public void add(String uid, Long postId) {
        PostBo postBo = postService.find(postId);

        FavoriteBo po = findByUid(uid, postBo.getArticleBlogId());
        Assert.isNull(po, "您已经收藏过此文章");

        // 如果没有喜欢过, 则添加记录
        PostBo articleBlog = postService.getForAuthor(postBo.getArticleBlogId());

        po = new FavoriteBo();
        po.setUserId(articleBlog.getAuthorId());
        po.setUid(uid);
        po.setPostId(postId);
        po.setArticleBlogId(postBo.getArticleBlogId());
        po.setCreated(new Date());
        insert(po);
    }

    @Override
    @Transactional
    public void delete(String uid, Long postId) {
        PostBo postBo = postService.find(postId);

        delete(postBo.getArticleBlogId(), uid);

//        Favorite po = favoriteRepository.findByUidAndArticleBlogId(uid, articleBlogId);
//        Assert.notNull(po, "还没有喜欢过此文章");
//        favoriteRepository.delete(po);
    }

    @Override
    @Transactional
    public void delete(String uid, String articleBlogId) {
        LambdaQueryWrapper<Favorite> queryWrapper = new QueryWrapper<Favorite>().lambda();
        queryWrapper.eq(Favorite::getUid, uid);
        queryWrapper.eq(Favorite::getArticleBlogId, articleBlogId);

        delete(queryWrapper);

//        int rows = favoriteRepository.deleteByArticleBlogIdAndUid(articleBlogId, uid);
//        log.info("favoriteRepository delete {}", rows);
    }

    @Override
    public int countBy(long postId) {
        LambdaQueryWrapper<Favorite> queryWrapper = new QueryWrapper<Favorite>().lambda();
        queryWrapper.eq(Favorite::getPostId, postId);

        return count(queryWrapper);
    }
}
