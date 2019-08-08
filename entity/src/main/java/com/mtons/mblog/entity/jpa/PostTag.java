package com.mtons.mblog.entity.jpa;

import javax.persistence.*;

/**
 * @author : langhsu
 */
@Entity
@Table(name = "mto_post_tag", indexes = {
        @Index(name = "IK_TAG_ID", columnList = "tag_id")
})
public class PostTag extends com.yueny.kapo.api.pojo.instance.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "post_id")
    private long postId;

    @Column(name = "tag_id")
    private long tagId;

    private long weight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
}
