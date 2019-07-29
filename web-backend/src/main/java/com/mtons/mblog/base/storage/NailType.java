package com.mtons.mblog.base.storage;

import com.mtons.mblog.base.consts.StorageConsts;
import com.mtons.mblog.base.enums.ResourceType;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum NailType {
    /**
     * 博文中的附件
     */
    blogAttr(StorageConsts.blognailPath, ResourceType.BLOG_ATTR, true),

    /**
     * 栏目缩略图
     */
    channelThumb(StorageConsts.thumbnailPathForChannel, ResourceType.THUMB_CHANNEL, false),
    /**
     * 文章缩略图
     */
    blogThumb(StorageConsts.thumbnailPathForBlog, ResourceType.THUMB_BLOG, false),
    /**
     * 其他部分单独分类的缩略图
     */
    thumb(StorageConsts.thumbnailPathForDefault, ResourceType.THUMB, true),

    /**
     * 头像
     */
    avatar(StorageConsts.avatarPath, ResourceType.AVATARS, true),
    /**
     * 不属于上述分类的储存目录
     */
    vague(StorageConsts.vaguePath, ResourceType.VAGUE, true),;

    @Getter
    private String nailPath;
    @Getter
    private ResourceType resourceType;

    /**
     *
     * @param isPlace 是否路径 format， true代表需要 format
     */
    @Getter
    private boolean isFormat;

    NailType(String nailPath, ResourceType resourceType, boolean isFormat){
        this.nailPath = nailPath;
        this.resourceType = resourceType;
        this.isFormat = isFormat;
    }

    public static NailType get(String nailType){
        for (NailType type: values()) {
            if(StringUtils.equals(nailType, type.name())) {
                return type;
            }
        }
        // 啥丢不属于
        return vague;
    }

}
