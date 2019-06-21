package com.mtons.mblog.modules.comp.util;

import com.mtons.mblog.base.BlogConstant;
import com.mtons.mblog.base.enums.AuthoredType;
import com.mtons.mblog.modules.data.CommentVO;

/**
 * <code>
 *
 * </code>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/6/21 上午10:34
 */
public class DefaultMojoFactory {
    public static CommentVO.UserCommentModel guestCommentGet(String clientIp){
        // 存在访客评论的评论信息
        CommentVO.UserCommentModel uc = CommentVO.UserCommentModel.builder()
                .name(clientIp)
                .avatar("")
                .domainHack("guest")
                .uid(BlogConstant.DEFAULT_GUEST_U_ID)
                .username("guest")
                .commitAuthoredType(AuthoredType.GUEST)
                .build();

        return uc;
    }
}
