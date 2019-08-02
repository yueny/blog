package com.mtons.mblog.service.watcher.event.enums;

import com.yueny.superclub.api.enums.core.IEnumType;

/**
 *  文章发布枚举
 */
public enum PostUpdateType implements IEnumType {
    /**
     * 博文发布
     */
    ACTION_PUBLISH,
    /**
     * 博文删除
     */
    ACTION_DELETE;
}
