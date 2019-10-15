package com.mtons.mblog.service.comp.storage;

import com.mtons.mblog.base.enums.ResourceType;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum NailType {
    /**
     * 博文中的附件
     */
    blogAttr(PathType.blognail.getPath() + "/%s", ResourceType.BLOG_ATTR, true),

    /**
     * 栏目缩略图
     */
    channelThumb(PathType.thumbnailForChannel.getPath(), ResourceType.THUMB_CHANNEL, false),
    /**
     * 文章缩略图
     */
    blogThumb(PathType.thumbnailForBlog.getPath(), ResourceType.THUMB_BLOG, false),
    /**
     * 其他部分单独分类的缩略图
     */
    thumb(PathType.thumbnailForDefault.getPath(), ResourceType.THUMB, false),

    /**
     * 头像
     */
    avatar(PathType.avatar.getPath() + "/%s", ResourceType.AVATARS, true),
    /**
     * 不属于上述分类的储存目录
     */
    vague(PathType.vague.getPath() + "/%s", ResourceType.VAGUE, true),;

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

    public static NailType get(ResourceType resourceType){
        for (NailType type: values()) {
            if(StringUtils.equals(resourceType.name(), type.getResourceType().name())) {
                return type;
            }
        }

        return null;
    }

    /**
     * 文件存储的路径枚举
     */
    public enum PathType{
        /**
         * 文件存储-发布文章目录. %s为用户uid
         */
        blognail("/storage/blognails"),

        /**
         * 默认文件存储-缩略图目录
         */
        thumbnailForDefault("/storage/thumbnails"),
        /**
         * 渠道栏目缩略图目录
         */
        thumbnailForChannel(thumbnailForDefault.path + "/channel"),
        /**
         * 文章缩略图目录
         */
        thumbnailForBlog(thumbnailForDefault.path + "/blog"),

        /**
         * 文件存储-头像目录
         */
        avatar("/storage/avatars"),
        /**
         * 文件存储-不属于上述分类的储存目录
         */
        vague("/storage/vague"),;

        @Getter
        private String path;

        PathType(String path){
            this.path = path;
        }
    }
}
