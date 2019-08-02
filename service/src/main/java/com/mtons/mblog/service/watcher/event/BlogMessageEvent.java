package com.mtons.mblog.service.watcher.event;

import com.mtons.mblog.base.enums.watcher.MessageActionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author langhsu on 2015/8/31.
 */
public class BlogMessageEvent extends ApplicationEvent {
	private static final long serialVersionUID = -4261382494171476390L;

    /**
     * 用户UID
     */
    @Getter
    @Setter
    private String fromUid;

    /**
     * 用户UID
     */
    @Getter
    @Setter
    private String toUid;
//	private long UserId;
//    private long toUserId;

    @Getter
    @Setter
    private MessageActionType event;

    private long postId;

    @Getter
    @Setter
    private String articleBlogId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public BlogMessageEvent(Object source) {
        super(source);
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
