package com.mtons.mblog.web.controller.site.user;

import com.mtons.mblog.base.enums.watcher.MessageActionType;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.model.AccountProfile;
import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.service.watcher.event.BlogMessageEvent;
import com.mtons.mblog.service.atom.jpa.PostService;
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

                PostBo postBO = postService.getForAuthor(articleBlogId);
                publicMessage(up.getUid(), postBO.getId(), articleBlogId);

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
     * @param uid
     * @param postId
     */
    private void publicMessage(String uid, long postId, String articleBlogId) {
        BlogMessageEvent event = new BlogMessageEvent("MessageEvent" + System.currentTimeMillis());
        event.setFromUid(uid);
        event.setEvent(MessageActionType.MESSAGE_EVENT_FAVOR_POST);

        // 此处不知道文章作者, 让通知事件系统补全
        event.setPostId(postId);
        event.setArticleBlogId(articleBlogId);

        applicationContext.publishEvent(event);
    }
}
