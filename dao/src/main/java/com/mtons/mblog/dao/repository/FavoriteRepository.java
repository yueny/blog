package com.mtons.mblog.dao.repository;

import com.mtons.mblog.entity.jpa.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author langhsu
 */
public interface FavoriteRepository extends JpaRepository<Favorite, Long>, JpaSpecificationExecutor<Favorite> {
    @Deprecated
    Favorite findByUserIdAndPostId(long userId, long postId);
    Favorite findByUidAndArticleBlogId(String uid, String articleBlogId);

    @Deprecated
    Page<Favorite> findAllByUserId(Pageable pageable, long userId);
    Page<Favorite> findAllByUid(Pageable pageable, String uid);

    @Deprecated
    int deleteByPostIdByUid(long postId, String uid);
    int deleteByArticleBlogIdByUid(String articleBlogId, String uid);
}
