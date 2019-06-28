package com.mtons.mblog.bo;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author langhsu on 2015/8/31.
 */
@Getter
@Setter
public class MessageVO extends AbstractMaskBo {
    private long id;

    private long userId;

    private long fromId;

    private int event; // 事件

    private long postId; // 关联文章ID

    private Date created;

    private int status; // 阅读状态

    // extend
    private UserBO from;
    private PostBO post;

    public UserBO getFrom() {
        return from;
    }

    public void setFrom(UserBO from) {
        this.from = from;
    }

    public PostBO getPost() {
        return post;
    }

    public void setPost(PostBO post) {
        this.post = post;
    }
}
