package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 喜欢/收藏
 * @author langhsu on 2015/8/31.
 */
//@Table(name = "mto_favorite", indexes = {
//        @Index(name = "IK_USER_ID", columnList = "user_id")
//})
@Entity
@TableName("mto_favorite")
@Getter
@Setter
public class Favorite  extends AbstractPlusEntry {
    /**
     * 所属用户
     */
    @TableField(value = "user_id")
    private long userId;
    /**
     * 所属用户
     */
    @TableField(value = "uid")
    private String uid;

    /**
     * 内容ID
     */
    @TableField(value = "post_id")
    private long postId;
    /**
     * 文章扩展ID
     */
    @TableField(value = "article_blog_id")
    private String articleBlogId;

}
