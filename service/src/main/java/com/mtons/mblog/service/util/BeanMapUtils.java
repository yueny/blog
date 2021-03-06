/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.util;

import com.mtons.mblog.base.enums.AuthoredType;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.bo.*;
import com.mtons.mblog.entity.bao.*;
import com.mtons.mblog.entity.jpa.*;
import org.springframework.beans.BeanUtils;

/**
 * @author langhsu
 */
public class BeanMapUtils {
    private static String[] USER_IGNORE = new String[]{"password", "extend", "roles"};

    public static UserBO copy(User po) {
        if (po == null) {
            return null;
        }
        UserBO ret = new UserBO();
        BeanUtils.copyProperties(po, ret, USER_IGNORE);
        return ret;
    }

//    public static AccountProfile copyPassport(User entry) {
//        AccountProfile passport = new AccountProfile(entry.getId(), entry.getUsername());
//        passport.setName(entry.getName());
//        passport.setEmail(entry.getEmail());
//        passport.setAvatar(entry.getAvatar());
//        passport.setLastLogin(entry.getLastLogin());
//        passport.setStatus(entry.getStatus());
//        passport.setDomainHack(entry.getDomainHack());
//        passport.setUid(entry.getUid());
//
//        return passport;
//    }

    public static CommentBo copy(Comment entry) {
        CommentBo ret = new CommentBo();
        BeanUtils.copyProperties(entry, ret);

        ret.setCommitAuthoredType(AuthoredType.getBy(entry.getCommitAuthoredType()));
        return ret;
    }

    public static PostBo copy(Post entry) {
        PostBo d = new PostBo();
        BeanUtils.copyProperties(entry, d);
        return d;
    }

    public static MessageVO copy(Message entry) {
        MessageVO ret = new MessageVO();
        BeanUtils.copyProperties(entry, ret);
        return ret;
    }

    public static FavoriteVO copy(Favorite po) {
        FavoriteVO ret = new FavoriteVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static PostTagVO copy(PostTag po) {
        PostTagVO ret = new PostTagVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static TagBO copy(Tag po) {
        TagBO ret = new TagBO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static String[] postOrder(String order) {
        String[] orders;
        switch (order) {
            case Consts.order.HOTTEST:
                orders = new String[]{"comments", "views", "created"};
                break;
            case Consts.order.FAVOR:
                orders = new String[]{"favors", "created"};
                break;
            default:
                orders = new String[]{"weight", "created"};
                break;
        }
        return orders;
    }
}
