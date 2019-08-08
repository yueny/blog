package com.mtons.mblog.service.util;

import com.mtons.mblog.base.consts.BlogConstant;
import com.mtons.mblog.base.enums.AuthoredType;
import com.mtons.mblog.bo.CommentBo;
import org.apache.commons.lang3.StringUtils;

/**
 * <code>
 *
 * </code>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/6/21 上午10:34
 */
public class DefaultMojoFactory {
    public static CommentBo.UserCommentModel guestCommentGet(String clientIp, String clientAgent){
        // 存在访客评论的评论信息
        CommentBo.UserCommentModel uc = CommentBo.UserCommentModel.builder()
                .name(StringUtils.isEmpty(clientAgent) ? clientIp : clientIp + "/" + clientAgent)
                .avatar("")
                .domainHack("guest")
                .uid(BlogConstant.DEFAULT_GUEST_U_ID)
                .username("guest")
                .commitAuthoredType(AuthoredType.GUEST)
                .build();

        return uc;
    }
}
