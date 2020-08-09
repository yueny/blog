//package com.mtons.mblog.dao.repository;
//
//import com.mtons.mblog.entity.bao.Favorite;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
///**
// * @author langhsu
// */
//@Deprecated
//public interface FavoriteRepository extends JpaRepository<Favorite, Long>, JpaSpecificationExecutor<Favorite> {
//    Favorite findByUserIdAndPostId(long userId, long postId);
//    Favorite findByUidAndArticleBlogId(String uid, String articleBlogId);
//
//    Page<Favorite> findAllByUserId(Pageable pageable, long userId);
//    Page<Favorite> findAllByUid(Pageable pageable, String uid);
//
//    int deleteByPostIdAndUid(long postId, String uid);
//    int deleteByArticleBlogIdAndUid(String articleBlogId, String uid);
//}
