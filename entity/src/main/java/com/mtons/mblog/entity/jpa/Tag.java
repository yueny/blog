package com.mtons.mblog.entity.jpa;

import com.mtons.mblog.entity.api.IEntry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : langhsu
 */
@Entity
@Table(name = "mto_tag")
@ToString
public class Tag implements IEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, updatable = false, length = 32)
    private String name;

    @Column(length = 128)
    @Deprecated
    @Getter
    @Setter
    private String thumbnail;

    private String description;

    private long latestPostId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updated;

    private int posts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLatestPostId() {
        return latestPostId;
    }

    public void setLatestPostId(long latestPostId) {
        this.latestPostId = latestPostId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }
}
