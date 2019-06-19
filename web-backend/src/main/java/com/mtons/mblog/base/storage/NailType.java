package com.mtons.mblog.base.storage;

import com.mtons.mblog.base.lang.StorageConsts;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum NailType {
    /**
     * 博文
     */
    blog(StorageConsts.blognailPath, true),
    /**
     * 栏目缩略图
     */
    channel(StorageConsts.thumbnailPath, false),
    /**
     * 缩略图， 如文章缩略图等
     */
    thumb(StorageConsts.thumbnailPath, false),
    /**
     * 头像
     */
    avatar(StorageConsts.avatarPath, true),
    /**
     * 不属于上述分类的储存目录
     */
    vague(StorageConsts.vaguePath, true),;

    @Getter
    private String nailPath;

    /**
     *
     * @param isPlace 是否路径 format， true代表需要 format
     */
    @Getter
    private boolean isFormat;

    NailType(String nailPath, boolean isFormat){
        this.nailPath = nailPath;
        this.isFormat = isFormat;
    }

    public static NailType get(String uType){
        for (NailType type: values()) {
            if(StringUtils.equals(uType, type.name())) {
                return type;
            }
        }
        // 啥丢不属于
        return vague;
    }

}
