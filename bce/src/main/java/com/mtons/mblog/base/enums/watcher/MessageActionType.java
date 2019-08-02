package com.mtons.mblog.base.enums.watcher;

import com.yueny.superclub.api.enums.core.IEnumType;
import lombok.Getter;

/**
 *  消息动作枚举
 */
public enum MessageActionType implements IEnumType {
    /**
     * 有人喜欢了你的文章
     */
    MESSAGE_EVENT_FAVOR_POST(1),
    /**
     * 有人评论了你
     */
    MESSAGE_EVENT_COMMENT(3),
    /**
     * 有人回复了你
     */
    MESSAGE_EVENT_COMMENT_REPLY(4);

    @Getter
    private int val;

    MessageActionType(int val){
        this.val = val;
    }
}
