/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.dao.repository;

import com.mtons.mblog.entity.bao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JpaRepository 中的save方法单纯的插入记录
 * CrudRepository 中的save方法是相当于merge+save ，它会先判断记录是否存在，如果存在则更新，不存在则插入记录
 */
public interface UserRepository
        extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);

    User findByUid(String uid);

    User findByEmail(String email);

//    /**
//     * 修改密码
//     *
//     * @param uid
//     *      用户uid
//     * @param newPassportForEncode
//     *      新密码， 密文， 此处直接存储
//     */
//    @Modifying
//    @Query("update User set password = :newPassportForEncode, updated=:updated where uid = :uid")
//    int changePassword(@Param("uid") String uid, @Param("newPassportForEncode") String newPassportForEncode, @Param("updated") Date updated);
//
//    @Modifying
//    @Query("update User set posts = posts + :increment, updated=:updated where uid = :uid")
//    int updatePosts(@Param("uid") String uid, @Param("increment") int increment, @Param("updated") Date updated);
//
//    @Modifying
//    @Query("update User set comments = comments + :increment, updated=:updated where uid in (:uids)") // and comments>0
//    int updateComments(@Param("uids") Collection<String> uids, @Param("increment") int increment, @Param("updated") Date updated);
//
//    @Modifying
//    @Query("update User set status = :status, updated=:updated where uid = :uid")
//    int updateStatus(@Param("uid") String uid, @Param("status") int status, @Param("updated") Date updated);

    User findByDomainHack(String domainHack);
}
