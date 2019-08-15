package com.mtons.mblog.service.watcher.event.handler;

import com.mtons.mblog.base.consts.BlogConstant;
import com.mtons.mblog.base.enums.watcher.MessageActionType;
import com.mtons.mblog.bo.MessageVO;
import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.service.AbstractService;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.watcher.event.BlogMessageEvent;
import com.mtons.mblog.service.atom.jpa.MessageService;
import com.mtons.mblog.service.atom.bao.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author langhsu on 2015/8/31.
 */
@Component
public class BlogMessageEventHandler extends AbstractService
        implements ApplicationListener<BlogMessageEvent> {
    @Autowired
    private MessageService messageService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Async
    @Override
    public void onApplicationEvent(BlogMessageEvent event) {
        MessageVO nt = new MessageVO();
        nt.setPostId(event.getPostId());

        UserBO userBo = userService.get(event.getFromUid());
        if(userBo == null){
            nt.setFromId(BlogConstant.DEFAULT_GUEST_AUTHOR_ID);
        }else{
            nt.setFromId(userBo.getId());
        }
        nt.setEvent(event.getEvent());

        // 有人喜欢了你的文章
        if(event.getEvent().getVal() == MessageActionType.MESSAGE_EVENT_FAVOR_POST.getVal()){
            PostBo p = postService.getForAuthor(event.getPostId());
            nt.setUserId(p.getAuthorId());
        }else if(event.getEvent().getVal() == MessageActionType.MESSAGE_EVENT_COMMENT.getVal()
            || event.getEvent().getVal() == MessageActionType.MESSAGE_EVENT_COMMENT_REPLY.getVal()){
            //有人评论了你
            // 有人回复了你
            PostBo p2 = postService.getForAuthor(event.getPostId());
            nt.setUserId(p2.getAuthorId());

            // 自增评论数
            postService.identityComments(p2.getArticleBlogId());
        }else{
            nt.setUserId(userService.get(event.getToUid()).getId());
        }

        messageService.send(nt);
    }

}
