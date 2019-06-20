/**
 *
 */
package com.mtons.mblog.web.controller.site.comment;

import com.mtons.mblog.base.BlogConstant;
import com.mtons.mblog.base.enums.AuthoredType;
import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.modules.comp.ISiteOptionsControlsService;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.data.CommentVO;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.event.MessageEvent;
import com.mtons.mblog.modules.service.CommentService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import com.yueny.rapid.lang.agent.UserAgentResource;
import com.yueny.rapid.lang.util.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author langhsu
 */
@RestController
@RequestMapping("/comment")
@ConditionalOnProperty(name = "site.controls.comment", havingValue = "true", matchIfMissing = true)
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ISiteOptionsControlsService controlsService;
    @Autowired
    private PostService postService;

    @RequestMapping("/list/{toId}")
    public Page<CommentVO> view(@PathVariable Long toId, String articleBlogId) {
        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "id"));

        Page<CommentVO> pager =  commentService.pagingByPostId(pageable, toId);

        return pager;
    }

    /**
     * 评论提交
     * @param toId postId 博文 id, 来自数据库唯一标示
     * @param text 回复内容
     * @param request
     * @return
     */
    @RequestMapping("/submit")
    public Result post(Long toId, String text, HttpServletRequest request, UserAgentResource clientUserAgent) {
        // 未登录且不允许匿名提交是否允许匿名提交评论功能, 如果允许, 则校验通过
        if (!isAuthenticated() && !controlsService.getControls().isCommentAllowAnonymous()) {
            return Result.failure("请先登录在进行操作");
        }

        if (toId <= 0 || StringUtils.isBlank(text)) {
            return Result.failure("操作失败");
        }

        CommentVO c = new CommentVO();
        // 所属博文内容的主键ID
        c.setPostId(toId);
        c.setContent(HtmlUtils.htmlEscape(text));

        // 针对性回复的评论ID(父评论ID)
        long pid = ServletRequestUtils.getLongParameter(request, "pid", 0);
        c.setPid(pid);

        if (isAuthenticated()) {
            AccountProfile profile = getProfile();
            c.setAuthorId(profile.getId());
            c.setUid(profile.getUid());
            c.setCommitAuthoredType(AuthoredType.AUTHORED);
        }else{
            c.setAuthorId(BlogConstant.DEFAULT_GUEST_AUTHOR_ID);
            c.setUid(BlogConstant.DEFAULT_GUEST_U_ID);
            c.setCommitAuthoredType(AuthoredType.GUEST);
        }

        String clientIp = IpUtil.getClientIp(request);
        c.setClientIp(clientIp);

        // 如果允许匿名评论且 authorId 为空或者未登录, 则取 clientUserAgent
        String clentAgent = "";
        // TODO clentAgent值
        clientUserAgent.getBrowser();
        c.setClientAgent(clentAgent);

        commentService.post(c);

        if (isAuthenticated()) {
            AccountProfile profile = getProfile();
            // 回复人的ID 与 评论文章所有者ID 不同, 则评论数+1. 即自己给自己评论, 不做加1
            if (toId != profile.getId()) {
                sendMessage(profile.getId(), toId, pid);
            }
        }else{
            sendMessage(BlogConstant.DEFAULT_GUEST_AUTHOR_ID, toId, pid);
        }

        return Result.successMessage("发表成功");
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam(name = "id") Long id) {
        Result data;
        try {
            commentService.delete(id, getProfile().getId());
            data = Result.success();
        } catch (Exception e) {
            data = Result.failure(e.getMessage());
            logger.error("exception:", e);
        }
        return data;
    }

    /**
     * 发送通知
     *
     * @param userId
     * @param postId
     */
    private void sendMessage(Long userId, long postId, long pid) {
        MessageEvent event = new MessageEvent("MessageEvent");
        event.setFromUserId(userId);

        if (pid > 0) {
            // 有人回复了你
            event.setEvent(Consts.MESSAGE_EVENT_COMMENT_REPLY);
        } else {
            // 有人评论了你
            event.setEvent(Consts.MESSAGE_EVENT_COMMENT);
        }
        // 此处不知道文章作者, 让通知事件系统补全
        event.setPostId(postId);

        PostVO postVO = postService.get(postId);
        event.setArticleBlogId(postVO.getArticleBlogId());

        applicationContext.publishEvent(event);
    }
}