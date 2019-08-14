/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtons.mblog.entity.bao.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;

/**
 */
public interface UserMapper extends BaseMapper<User> {
    //    User findByUsername(String username);
//
//    User findByUid(String uid);
//
//    User findByEmail(String email);
//
//    User findByDomainHack(String domainHack);

    /**
     * 修改密码
     *
     * @param uid
     *      用户uid
     * @param newPassportForEncode
     *      新密码， 密文， 此处直接存储
     */
    @Update("update mto_user set password = #{newPassportForEncode}, updated=#{updated} where uid = #{uid}")
    int changePassword(@Param("uid") String uid, @Param("newPassportForEncode") String newPassportForEncode, @Param("updated") Date updated);

    @Update("update mto_user set posts = posts + #{increment}, updated=#{updated} where uid = #{uid}")
    int updatePosts(@Param("uid") String uid, @Param("increment") int increment, @Param("updated") Date updated);

    @Update("update mto_user set comments = comments + #{increment}, updated=#{updated} where uid in (#{uids})") // and comments>0
    int updateComments(@Param("uids") Collection<String> uids, @Param("increment") int increment, @Param("updated") Date updated);

    @Update("update mto_user set status = #{status}, updated=#{updated} where uid = #{uid}")
    int updateStatus(@Param("uid") String uid, @Param("status") int status, @Param("updated") Date updated);

}
