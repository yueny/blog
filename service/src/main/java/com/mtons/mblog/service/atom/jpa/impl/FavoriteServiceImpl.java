package com.mtons.mblog.service.atom.jpa.impl;

import com.mtons.mblog.bo.FavoriteVO;
import com.mtons.mblog.bo.PostBO;
import com.mtons.mblog.dao.repository.FavoriteRepository;
import com.mtons.mblog.service.util.BeanMapUtils;
import com.mtons.mblog.entity.jpa.Favorite;
import com.mtons.mblog.service.atom.jpa.FavoriteService;
import com.mtons.mblog.service.atom.jpa.PostService;
import com.mtons.mblog.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class FavoriteServiceImpl extends BaseService implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private PostService postService;

    @Override
    public Page<FavoriteVO> pagingByUserId(Pageable pageable, String uid) {
        Page<Favorite> page = favoriteRepository.findAllByUid(pageable, uid);

        List<FavoriteVO> rets = new ArrayList<>();
        Set<Long> postIds = new HashSet<>();
        for (Favorite po : page.getContent()) {
            rets.add(BeanMapUtils.copy(po));
            postIds.add(po.getPostId());
        }

        if (postIds.size() > 0) {
            Map<Long, PostBO> posts = postService.findMapByIds(postIds);

            for (FavoriteVO t : rets) {
                PostBO p = posts.get(t.getPostId());
                t.setPost(p);
            }
        }
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public FavoriteVO findByUidAndArticleBlogId(String uid, String articleBlogId) {
        Favorite favorite = favoriteRepository.findByUidAndArticleBlogId(uid, articleBlogId);

        if(favorite == null){
            return null;
        }

        return  map(favorite, FavoriteVO.class);
    }

    @Override
    @Transactional
    public void add(String uid, String articleBlogId) {
        Favorite po = favoriteRepository.findByUidAndArticleBlogId(uid, articleBlogId);

        Assert.isNull(po, "您已经收藏过此文章");

        PostBO articleBlog = postService.get(articleBlogId);

        // 如果没有喜欢过, 则添加记录
        po = new Favorite();
        po.setUserId(articleBlog.getAuthorId());
        po.setUid(uid);
        po.setPostId(articleBlog.getId());
        po.setArticleBlogId(articleBlogId);
        po.setCreated(new Date());

        favoriteRepository.save(po);
    }

    @Override
    @Transactional
    public void delete(String uid, String articleBlogId) {
        int rows = favoriteRepository.deleteByArticleBlogIdAndUid(articleBlogId, uid);
        log.info("favoriteRepository delete {}", rows);

//        Favorite po = favoriteRepository.findByUidAndArticleBlogId(uid, articleBlogId);
//        Assert.notNull(po, "还没有喜欢过此文章");
//        favoriteRepository.delete(po);
    }

}
