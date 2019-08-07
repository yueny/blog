package com.mtons.mblog.dao.repository;

import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.mtons.mblog.dao.BaseDaoTest;
import com.mtons.mblog.entity.jpa.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


@Slf4j
public class PostRepositoryTest extends BaseDaoTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void testMaxWeight() {
        Post entry = insert();

    }

//    /**
//     * 集成的接口测试
//     */
//    @Test
//    public void testSelectById() {
//        DemoEntry demoEntry = insert();
//
//        DemoEntry demo = demoMapper.selectById(demoEntry.getId());
//        Assert.assertNotNull(demo);
//        Assert.assertEquals(demo.getAssetCode(), demoEntry.getAssetCode());
//    }
//
//    /**
//     * 删除测试
//     */
//    @Test
//    public void testDeleteById() {
//        DemoEntry demoEntry = insert();
//
//        demoEntry = demoMapper.selectByOrderId(demoEntry.getOrderId());
//
//        int count = demoMapper.deleteById(demoEntry.getId());
//        Assert.assertTrue(count == 1);
//
//        demoEntry = demoMapper.selectByOrderId(demoEntry.getOrderId());
//        Assert.assertEquals(demoEntry.getDel().getVal(), DelEnum.DEL.getVal());
//    }

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

        Post pp = postRepository.save(entry);
//        postRepository.flush();
        Assert.assertNotNull(pp);
//        Assert.assertTrue(pp.getId() > 0);

        Post np = postRepository.findByArticleBlogId(entry.getArticleBlogId());
        Assert.assertNotNull(np);

        return pp;
    }

}
