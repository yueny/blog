package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.bo.PostTagVO;
import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.bo.TagBO;
import com.mtons.mblog.dao.mapper.TagMapper;
import com.mtons.mblog.entity.bao.Tag;
import com.mtons.mblog.service.atom.bao.PostTagService;
import com.mtons.mblog.service.atom.bao.TagService;
import com.mtons.mblog.service.atom.bao.PostService;
import org.apache.commons.collections4.CollectionUtils;
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
public class TagServiceImpl extends AbstractPlusService<TagBO, Tag, TagMapper>
        implements TagService {
    @Autowired
    private PostTagService postTagService;
    @Autowired
    private PostService postService;

    @Override
    public TagBO findByName(String name) {
        LambdaQueryWrapper<Tag> queryWrapper = new QueryWrapper<Tag>().lambda();
        queryWrapper.eq(Tag::getName, name);

        return find(queryWrapper);
    }

    @Override
    public Page<TagBO> pagingQueryTags(Pageable pageable) {
        Page<TagBO> page = findAll(pageable);

        page.getContent().forEach(n -> {
            // 根据 postid 查询post信息
            n.setPost(postService.getForAuthor(n.getLatestPostId()));
        });
        return page;
    }

    @Override
    public List<TagBO> findPagingTagsByNameLike(Pageable pageable, String name) {
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                //模糊查询匹配开头，即{username}%
//                //.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                //全部模糊查询，即%{address}%
//                .withMatcher("name" ,ExampleMatcher.GenericPropertyMatchers.contains())
//                //忽略字段，即不管password是什么值都不加入查询条件
//                .withIgnorePaths("id");

//        Tag condition = new Tag();
//        condition.setName(name);
//        Example<Tag> example = Example.of(condition, matcher);

        LambdaQueryWrapper<Tag> queryWrapper = new QueryWrapper<Tag>().lambda();
        queryWrapper.like(Tag::getName, name);

        List<TagBO> list = findAll(queryWrapper);

        return list;
    }

    @Override
    public Page<PostTagVO> pagingQueryPosts(Pageable pageable, String tagName) {
        TagBO tag = findByName(tagName);
        Assert.notNull(tag, "标签不存在");
        Page<PostTagVO> page = postTagService.findAll(pageable, tag.getId());

        Set<Long> postIds = new HashSet<>();
        List<PostTagVO> rets = page.getContent().stream().map(po -> {
            postIds.add(po.getPostId());
            return po;
        }).collect(Collectors.toList());

        Map<Long, PostBo> posts = postService.findMapForAuthorByIds(postIds);
        rets.forEach(n -> n.setPost(posts.get(n.getPostId())));
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void batchUpdate(String names, long latestPostId) {
        // 删除博文所拥有的所有标签
        postTagService.deleteByPostId(latestPostId);

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

            TagBO tagBo = findByName(name);
            if (tagBo != null) {
                // 已经存在的标签
                tagBo.setPosts(tagBo.getPosts() + 1);
                tagBo.setUpdated(current);
            } else {
                // 新标签，则新增
                tagBo = new TagBO();
                tagBo.setName(name);
                tagBo.setCreated(current);
                tagBo.setUpdated(current);
                tagBo.setPosts(1);
            }
            tagBo.setLatestPostId(latestPostId);
            // 更新tag的数量
            saveOrUpdate(tagBo);

            PostTagVO pt = new PostTagVO();
            pt.setPostId(latestPostId);
            pt.setTagId(tagBo.getId());
            pt.setWeight(System.currentTimeMillis());
            postTagService.insert(pt);
        }
    }

    @Override
    @Transactional
    public void deteleMappingByPostId(long postId) {
        postTagService.deleteByPostId(postId);
    }
}
