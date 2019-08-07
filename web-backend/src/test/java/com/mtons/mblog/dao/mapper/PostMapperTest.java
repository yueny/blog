package com.mtons.mblog.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtons.mblog.BootApplication;
import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.mtons.mblog.entity.bao.Post;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * pic repo test
 *
 * @author saxing 2019/4/5 17:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class PostMapperTest {

    @Autowired
    PostMapper postMapper;

    @Test
    public void testMaxWeight() {
        Post entry = insert();

        int ee = postMapper.maxWeight();
        Assert.assertTrue(ee > 0);
    }

    private Post insert() {
        Post entry = new Post();
        entry.setArticleBlogId(UUID.randomUUID().toString());
        entry.setChannelId(3);
        entry.setTitle("标题");
        entry.setSummary("摘要");
//        entry.setTags();
        entry.setAuthorId(1);
        entry.setUid("1");
//        entry.setfavors
//        entry.setcomments
//        entry.setviews
//        entry.setstatus
        entry.setFeatured(BlogFeaturedType.FEATURED_DEFAULT);
//        entry.setweight
//        entry.setthumbnailCode

        int pk = postMapper.insert(entry);
//        postRepository.flush();
        Assert.assertTrue(pk > 0);
//        Assert.assertTrue(pp.getId() > 0);

        LambdaQueryWrapper<Post> queryWrapper = new QueryWrapper<Post>().lambda();
        queryWrapper.eq(Post::getArticleBlogId, entry.getArticleBlogId());

        Post np = postMapper.selectOne(queryWrapper);
        Assert.assertNotNull(np);

        return np;
    }
}