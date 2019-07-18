package com.mtons.mblog.service.atom.jpa.impl;

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.bo.PostTagVO;
import com.mtons.mblog.bo.PostBO;
import com.mtons.mblog.bo.TagBO;
import com.mtons.mblog.entity.jpa.AttackIpEntry;
import com.mtons.mblog.entity.jpa.PostTag;
import com.mtons.mblog.entity.jpa.Tag;
import com.mtons.mblog.dao.repository.PostTagRepository;
import com.mtons.mblog.dao.repository.TagRepository;
import com.mtons.mblog.service.atom.jpa.TagService;
import com.mtons.mblog.service.atom.jpa.PostService;
import com.mtons.mblog.service.util.BeanMapUtils;
import com.mtons.mblog.service.BaseService;
import com.yueny.superclub.api.pojo.IBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : langhsu
 */
@Service
@Transactional
public class TagServiceImpl extends BaseBizService<TagBO, Tag, TagRepository>
        implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostTagRepository postTagRepository;
    @Autowired
    private PostService postService;

    @Override
    public Page<TagBO> pagingQueryTags(Pageable pageable) {
        Page<TagBO> page = findAll(pageable);

        page.getContent().forEach(n -> {
            // 根据 postid 查询post信息
            n.setPost(postService.get(n.getLatestPostId()));
        });
        return page;
    }

    @Override
    public List<TagBO> findPagingTagsByNameLike(Pageable pageable, String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                //模糊查询匹配开头，即{username}%
                //.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())
                //全部模糊查询，即%{address}%
                .withMatcher("name" ,ExampleMatcher.GenericPropertyMatchers.contains())
                //忽略字段，即不管password是什么值都不加入查询条件
                .withIgnorePaths("id");

        Tag condition = new Tag();
        condition.setName(name);
        Example<Tag> example = Example.of(condition, matcher);

        List<TagBO> list = findAll(example);

        return list;
    }

    @Override
    public Page<PostTagVO> pagingQueryPosts(Pageable pageable, String tagName) {
        Tag tag = tagRepository.findByName(tagName);
        Assert.notNull(tag, "标签不存在");
        Page<PostTag> page = postTagRepository.findAllByTagId(pageable, tag.getId());

        Set<Long> postIds = new HashSet<>();
        List<PostTagVO> rets = page.getContent().stream().map(po -> {
            postIds.add(po.getPostId());
            return BeanMapUtils.copy(po);
        }).collect(Collectors.toList());

        Map<Long, PostBO> posts = postService.findMapByIds(postIds);
        rets.forEach(n -> n.setPost(posts.get(n.getPostId())));
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void batchUpdate(String names, long latestPostId) {
        if (StringUtils.isBlank(names.trim())) {
            return;
        }

        String[] ns = names.split(Consts.SEPARATOR);
        Date current = new Date();
        for (String n : ns) {
            String name = n.trim();
            if (StringUtils.isBlank(name)) {
                continue;
            }

            Tag po = tagRepository.findByName(name);
            if (po != null) {
                PostTag pt = postTagRepository.findByPostIdAndTagId(latestPostId, po.getId());
                if (null != pt) {
                    pt.setWeight(System.currentTimeMillis());
                    postTagRepository.save(pt);
                    continue;
                }
                po.setPosts(po.getPosts() + 1);
                po.setUpdated(current);
            } else {
                po = new Tag();
                po.setName(name);
                po.setCreated(current);
                po.setUpdated(current);
                po.setPosts(1);
            }

            po.setLatestPostId(latestPostId);
            tagRepository.save(po);

            PostTag pt = new PostTag();
            pt.setPostId(latestPostId);
            pt.setTagId(po.getId());
            pt.setWeight(System.currentTimeMillis());
            postTagRepository.save(pt);
        }
    }

    @Override
    @Transactional
    public void deteleMappingByPostId(long postId) {
        postTagRepository.deleteByPostId(postId);
    }
}
