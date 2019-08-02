package com.mtons.mblog.service.watcher.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author langhsu on 2015/8/31.
 */
public class MessageEvent extends ApplicationEvent {
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

    private int event;

    private long postId;

    @Getter
    @Setter
    private String articleBlogId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public MessageEvent(Object source) {
        super(source);
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
