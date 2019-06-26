package com.mtons.mblog.modules.data;

import com.mtons.mblog.bo.PostVO;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.entity.Message;

/**
 * @author langhsu on 2015/8/31.
 */
public class MessageVO extends Message {
    // extend
    private UserBO from;
    private PostVO post;

    public UserBO getFrom() {
        return from;
    }

    public void setFrom(UserBO from) {
        this.from = from;
    }

    public PostVO getPost() {
        return post;
    }

    public void setPost(PostVO post) {
        this.post = post;
    }
}
