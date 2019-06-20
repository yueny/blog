package com.mtons.mblog.web.controller.site.user;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.event.MessageEvent;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author langhsu
 */
@RestController
@RequestMapping("/user")
public class FavorController extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 收藏文章
     * @param articleBlogId
     * @return
     */
    @RequestMapping("/favor")
    public Result favor(String articleBlogId) {
        Result data = Result.failure("操作失败");
        if (StringUtils.isNotEmpty(articleBlogId)) {
            try {
                AccountProfile up = getProfile();
                postService.favor(up.getUid(), articleBlogId);

                PostVO postVo = postService.get(articleBlogId);
                sendMessage(up.getId(), postVo.getId(), articleBlogId);

                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
                logger.error("exception:", e);
            }
        }
        return data;
    }

    /**
     * 取消收藏
     * @param articleBlogId
     * @return
     */
    @RequestMapping("/unfavor")
    public Result unfavor(String articleBlogId) {
        Result data = Result.failure("操作失败");
        if (StringUtils.isNotEmpty(articleBlogId)) {
            try {
                AccountProfile up = getProfile();
                postService.unfavor(up.getUid(), articleBlogId);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
                logger.error("exception:", e);
            }
        }
        return data;
    }

    /**
     * 发送通知
     * @param userId
     * @param postId
     */
    private void sendMessage(long userId, long postId, String articleBlogId) {
        MessageEvent event = new MessageEvent("MessageEvent" + System.currentTimeMillis());
        event.setFromUserId(userId);
        event.setEvent(Consts.MESSAGE_EVENT_FAVOR_POST);
        // 此处不知道文章作者, 让通知事件系统补全
        event.setPostId(postId);

        event.setArticleBlogId(articleBlogId);

        applicationContext.publishEvent(event);
    }
}