package com.mtons.mblog.service.watcher.event.handler;

import com.mtons.mblog.service.watcher.event.PostUpdateEvent;
import com.mtons.mblog.service.atom.bao.CommentService;
import com.mtons.mblog.service.atom.jpa.FavoriteService;
import com.mtons.mblog.service.atom.jpa.MessageService;
import com.mtons.mblog.service.atom.jpa.TagService;
import com.mtons.mblog.base.enums.watcher.PostUpdateType;
import com.mtons.mblog.service.watcher.executor.UserEventExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 博文发布和博文删除的事件
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/18 下午12:06
 *
 */
@Component
public class PostUpdateEventHandler implements ApplicationListener<PostUpdateEvent> {
    @Autowired
    private UserEventExecutor userEventExecutor;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TagService tagService;
    @Autowired
    private MessageService messageService;

    @Async
    @Override
    public void onApplicationEvent(PostUpdateEvent event) {
        if (event == null) {
            return;
        }

        // 文章删除
        if(StringUtils.equals(event.getAction().name(), PostUpdateType.ACTION_DELETE.name())){
            userEventExecutor.identityPost(event.getUid(), false);

            favoriteService.delete(event.getUid(), event.getArticleBlogId());

            commentService.deleteByPostId(event.getPostId());
            tagService.deteleMappingByPostId(event.getPostId());
            messageService.deleteByPostId(event.getPostId());
        }else{
            // 文章发布 PostUpdateType.ACTION_PUBLISH.name():
            userEventExecutor.identityPost(event.getUid(), true);
        }
    }
}
