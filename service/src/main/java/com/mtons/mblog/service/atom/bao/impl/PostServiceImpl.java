/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.bao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.enums.StatusType;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.dao.mapper.PostMapper;
import com.mtons.mblog.service.atom.bao.ResourceService;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.atom.jpa.*;
import com.mtons.mblog.service.atom.bao.PostService;
import com.mtons.mblog.service.util.BeanMapUtils;
import com.mtons.mblog.service.aspect.PostStatusFilter;
import com.mtons.mblog.bo.ChannelVO;
import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.entity.bao.Post;
import com.mtons.mblog.dao.repository.PostRepository;
import com.mtons.mblog.service.seq.SeqType;
import com.mtons.mblog.service.seq.container.ISeqContainer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author langhsu
 *
 */
@Service
public class PostServiceImpl extends AbstractPlusService<PostBo, Post, PostMapper>
		implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserJpaService userJpaService;
	@Autowired
	private UserService userService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ISeqContainer seqContainer;
	@Autowired
	private ResourceService resourceService;

	@Override
	public PostBo getByArticleBlogId(String articleBlogId) {
		LambdaQueryWrapper<Post> queryWrapper = new QueryWrapper<Post>().lambda();
		queryWrapper.eq(Post::getArticleBlogId, articleBlogId);


		Post po = baseMapper.selectOne(queryWrapper);
		if(po == null){
			return  null;
		}

		return map(po, PostBo.class);
	}

	@Override
	@PostStatusFilter
	@Deprecated
	public Page<PostBo> pagingForAuthor(Pageable pageable, Set<Integer> channelIds, Set<Integer> excludeChannelIds) {
		Page<Post> page = postRepository.findAll((root, query, builder) -> {
			Predicate predicate = builder.conjunction();

			if(CollectionUtils.isNotEmpty(channelIds)){
			    if(channelIds.size() == 1){ // 单条
                    //if (channelId > Consts.ZERO) {
                    List list = Lists.newArrayList();
                    list.addAll(channelIds);
                    predicate.getExpressions().add(
                            builder.equal(root.get("channelId").as(Integer.class), list.get(0)));
                }else{
                    predicate.getExpressions().add(
                            builder.in(root.get("channelId")).value(channelIds));
                }
            }

			if (null != excludeChannelIds && !excludeChannelIds.isEmpty()) {
				predicate.getExpressions().add(
						builder.not(root.get("channelId").in(excludeChannelIds)));
			}

//			predicate.getExpressions().add(
//					builder.equal(root.get("featured").as(Integer.class), Consts.FEATURED_DEFAULT));

			return predicate;
		}, pageable);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	@Deprecated
	public Page<PostBo> paging4AdminForAuthor(Pageable pageable, int channelId, String title) {
		Page<Post> page = postRepository.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();
			if (channelId > Consts.ZERO) {
				predicate.getExpressions().add(
						builder.equal(root.get("channelId").as(Integer.class), channelId));
			}
			if (StringUtils.isNotBlank(title)) {
				predicate.getExpressions().add(
						builder.like(root.get("title").as(String.class), "%" + title + "%"));
			}
            return predicate;
        }, pageable);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

//	@Override
//	public Page<PostBO> pagingByAuthorId(Pageable pageable, long userId) {
//		return findAllByAuthorId(pageable, userId);
//	}

	@Override
	@PostStatusFilter
	public List<PostBo> findLatestPosts(int maxResults) {
		return find("created", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
	}
	
	@Override
	@PostStatusFilter
	public List<PostBo> findHottestPosts(int maxResults) {
		return find("views", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
	}
	
	@Override
	@PostStatusFilter
	public Map<Long, PostBo> findMapForAuthorByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyMap();
		}

		List<PostBo> list = findAllById(ids);

		HashSet<Long> uids = new HashSet<>();
		Map<Long, PostBo> rets = new HashMap<>();
		list.forEach(poBo -> {
			uids.add(poBo.getAuthorId());
			rets.put(poBo.getId(), poBo);
		});

		// 加载用户信息
		buildUsers(rets.values(), uids);

		return rets;
	}

	@Override
	@PostStatusFilter
	public Page<PostBo> findAllByAuthorId(Pageable pageable, long authorId) {
		LambdaQueryWrapper<Post> queryWrapper = new QueryWrapper<Post>().lambda();
		queryWrapper.eq(Post::getAuthorId, authorId);

		return findAll(pageable, queryWrapper);
	}

	@Override
	public PostBo getForAuthor(long id) {
//		Post po = baseMapper.selectById(id);
//		PostBO d = BeanMapUtils.copy(po);
		PostBo d = find(id);

		// 加载用户信息s
		buildUsers(Lists.newArrayList(d), Sets.newHashSet(d.getAuthorId()));
		// 加载资源信息
		buildResource(d);

		return d;
	}

	@Override
	public PostBo getForAuthor(String articleBlogId) {
		PostBo d = getByArticleBlogId(articleBlogId);

		// 加载用户信息
		buildUsers(Lists.newArrayList(d), Sets.newHashSet(d.getAuthorId()));
		// 加载资源信息
		buildResource(d);

		return d;
	}

	@Override
	@Transactional
	public Long post(PostBo post) {
		if(StringUtils.isEmpty(post.getArticleBlogId())){
			String articleBlogId = seqContainer.getStrategy(SeqType.ARTICLE_BLOG_ID).get(post.getTitle());

			// 判断该编号是否存在，存在则随机生成
			while(getByArticleBlogId(articleBlogId) != null){
				articleBlogId = seqContainer.getStrategy(SeqType.ARTICLE_BLOG_ID).get("");
			}

			post.setArticleBlogId(articleBlogId);
		}

		post.setStatus(post.getStatus());
		post.setSummary(post.getSummary());

		super.insert(post);
//		Post entry = map(post, Post.class);
//		baseMapper.insert(entry);

		return post.getId();
	}

	/**
	 * 更新文章方法
	 * @param pp
	 */
	@Override
	@Transactional
	@Deprecated
	public void update(PostBo pp){
		PostBo postBo = find(pp.getId());
//		Optional<Post> optional = postRepository.findById(pp.getId());

//		if (optional.isPresent()) {
//			Post po = optional.get();
		postBo.setTitle(pp.getTitle());//标题
		postBo.setChannelId(pp.getChannelId());
		postBo.setThumbnailCode(pp.getThumbnailCode());
		postBo.setStatus(pp.getStatus());

		if(StringUtils.isEmpty(postBo.getArticleBlogId())){
			String articleBlogId = seqContainer.getStrategy(SeqType.ARTICLE_BLOG_ID).get(postBo.getTitle());

			// 判断该编号是否存在，存在则随机生成
			while(getByArticleBlogId(articleBlogId) != null){
				articleBlogId = seqContainer.getStrategy(SeqType.ARTICLE_BLOG_ID).get("");
			}

			postBo.setArticleBlogId(articleBlogId);
		}

		postBo.setSummary(pp.getSummary());
		postBo.setTags(pp.getTags());//标签

		super.updateById(postBo);
//		}
	}

	@Override
	@Transactional
	@Deprecated
	public void updateFeatured(String articleBlogId, BlogFeaturedType featuredType) {
		Post po = postRepository.findByArticleBlogId(articleBlogId);

		po.setFeatured(featuredType);

		postRepository.save(po);
	}

	@Override
	@Transactional
	@Deprecated
	public void updateWeight(String articleBlogId, int weighted) {
		Post po = postRepository.findByArticleBlogId(articleBlogId);

		// 在实际weight处理中， 如果为置顶操作，则值为当前数据库内置顶weight最大值。
		int max = Consts.ZERO;
		if (Consts.ACTIVE == weighted) {
			max = baseMapper.maxWeight() + 1;
		}
		po.setWeight(max);

		postRepository.save(po);
	}

	@Override
	@Transactional
	public void delete(String articleBlogId, long authorId) {
		Post po = postRepository.findByArticleBlogId(articleBlogId);
		// 判断文章是否属于当前登录用户
		Assert.isTrue(po.getAuthorId() == authorId, "认证失败");

		postRepository.deleteById(po.getId());
	}

	@Override
	@Transactional
	public Set<Long> delete(Set<String> articleBlogIds) {
		Set<Long> ids = Sets.newHashSet();
		if (CollectionUtils.isNotEmpty(articleBlogIds)) {
			List<Post> list = postRepository.findAllByArticleBlogId(articleBlogIds);
			list.forEach(po -> {
				postRepository.delete(po);

				ids.add(po.getId());
			});
		}

		return ids;
	}

	@Override
	@Transactional
	public void identityViews(String articleBlogId) {
		// 次数不清理缓存, 等待文章缓存自动过期
		baseMapper.updateViews(articleBlogId, Consts.IDENTITY_STEP);
	}

	@Override
	@Transactional
	public void identityComments(String articleBlogId) {
		identityComments(articleBlogId, true);
	}

	@Override
	public void identityComments(String articleBlogId, boolean plus) {
		if(plus){
			baseMapper.updateComments(articleBlogId, Consts.IDENTITY_STEP);
		}else{
			// 减
			baseMapper.updateComments(articleBlogId, Consts.DECREASE_STEP);
		}
	}

	@Override
	@Transactional
	public void favor(String uid, String articleBlogId) {
		baseMapper.updateFavors(articleBlogId, Consts.IDENTITY_STEP);

		favoriteService.add(uid, articleBlogId);
	}

	@Override
	@Transactional
	public void unfavor(String uid, String articleBlogId) {
		baseMapper.updateFavors(articleBlogId,  Consts.DECREASE_STEP);

		favoriteService.delete(uid, articleBlogId);
	}

	@Override
	@PostStatusFilter
	public int count() {
		return baseMapper.selectCount(Wrappers.emptyWrapper());
	}

	@PostStatusFilter
	@Deprecated
	private List<Post> find(String orderBy, int size) {
		Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, orderBy));

		Set<Integer> excludeChannelIds = new HashSet<>();

    List<ChannelVO> channels = channelService.findRootAll(StatusType.CLOSED.getValue());
		if (channels != null) {
			channels.forEach((c) -> excludeChannelIds.add(c.getId()));
		}

		Page<Post> page = postRepository.findAll((root, query, builder) -> {
			Predicate predicate = builder.conjunction();
			if (excludeChannelIds.size() > 0) {
				predicate.getExpressions().add(
						builder.not(root.get("channelId").in(excludeChannelIds)));
			}
			return predicate;
		}, pageable);
		return page.getContent();
	}

	private List<PostBo> toPosts(List<Post> posts) {
		List<PostBo> list = new ArrayList<>();

		posts.forEach(po -> {
			PostBo postBO = BeanMapUtils.copy(po);

			buildUsers(postBO, postBO.getUid());
			// 加载资源信息
			buildResource(postBO);
			list.add(postBO);
		});

		return list;
	}

	private void buildUsers(PostBo post, String uid) {
		UserBO userBo = userService.find(uid);
		if(userBo != null){
			userBo.setPassword("");
			post.setAuthor(userBo);
		}
	}

	private void buildUsers(Collection<PostBo> posts, Set<Long> uids) {
		Map<Long, UserBO> userMap = userJpaService.findMapByIds(uids);
		posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
	}

	private void buildResource(Collection<PostBo> posts) {
		posts.forEach(post -> {
			buildResource(post);
		});
	}

	private void buildResource(PostBo post) {
		if(StringUtils.isEmpty(post.getThumbnailCode())){
			return;
		}

		ResourceBO resourceBO = resourceService.findByThumbnailCode(post.getThumbnailCode());
		if(resourceBO == null){
			return;
		}
		post.setThumbnail(resourceBO.getPath());
	}

}
