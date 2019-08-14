package com.mtons.mblog.service.watcher.event;

import com.mtons.mblog.base.enums.watcher.PostUpdateType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 *   文章发布事件
 *
 * - 推送Feed给粉丝
 * - 文章发布者文章数统计
 * - 推送通知
 *
 * created by langhsu at 2018/05/30
 */
public class PostUpdateEvent extends ApplicationEvent {
    /**
     * 所属博文的ID
     */
//    @Deprecated
    @Getter
    @Setter
    private long postId;
    /**
     * 文章扩展ID
     */
    @Getter
    @Setter
    private String articleBlogId;

    /**
     * 用户UID
     */
    @Getter
    @Setter
    private String uid;

    /**
     * 更新类型
     */
    @Getter
    @Setter
    private PostUpdateType action = PostUpdateType.ACTION_PUBLISH;

    public PostUpdateEvent(Object source) {
        super(source);
    }
}
