/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service.impl;

import com.google.common.collect.Lists;
import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.base.utils.BeanMapUtils;
import com.mtons.mblog.base.utils.MarkdownUtils;
import com.mtons.mblog.base.utils.PreviewTextUtils;
import com.mtons.mblog.modules.aspect.PostStatusFilter;
import com.mtons.mblog.modules.data.ChannelVO;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.Post;
import com.mtons.mblog.modules.entity.PostAttribute;
import com.mtons.mblog.modules.event.PostUpdateEvent;
import com.mtons.mblog.modules.repository.PostAttributeRepository;
import com.mtons.mblog.modules.repository.PostRepository;
import com.mtons.mblog.modules.seq.SeqType;
import com.mtons.mblog.modules.seq.container.ISeqContainer;
import com.mtons.mblog.modules.service.*;
import com.yueny.rapid.lang.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
@Transactional
public class PostServiceImpl extends BaseService implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostAttributeRepository postAttributeRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ISeqContainer seqContainer;

	@Override
	@PostStatusFilter
	public Page<PostVO> paging(Pageable pageable, Set<Integer> channelIds, Set<Integer> excludeChannelIds) {
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
	public Page<PostVO> paging4Admin(Pageable pageable, int channelId, String title) {
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

	@Override
	@PostStatusFilter
	public Page<PostVO> pagingByAuthorId(Pageable pageable, long userId) {
		Page<Post> page = postRepository.findAllByAuthorId(pageable, userId);
		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	@PostStatusFilter
	public List<PostVO> findLatestPosts(int maxResults) {
		return find("created", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
	}
	
	@Override
	@PostStatusFilter
	public List<PostVO> findHottestPosts(int maxResults) {
		return find("views", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
	}
	
	@Override
	@PostStatusFilter
	public Map<Long, PostVO> findMapByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyMap();
		}

		List<Post> list = postRepository.findAllById(ids);
		Map<Long, PostVO> rets = new HashMap<>();

		HashSet<Long> uids = new HashSet<>();

		list.forEach(po -> {
			rets.put(po.getId(), map(po, PostVO.class));
			uids.add(po.getAuthorId());
		});
		
		// 加载用户信息
		buildUsers(rets.values(), uids);
		return rets;
	}

	@Override
	@Transactional
	public long post(PostVO post) {
		Post po = map(post, Post.class);

		if(StringUtils.isEmpty(po.getArticleBlogId())){
			String articleBlogId = seqContainer.getStrategy(SeqType.ARTICLE_BLOG_ID).get(po.getTitle());

			// 判断该编号是否存在，存在则随机生成
			while(postRepository.findByArticleBlogId(articleBlogId) != null){
				articleBlogId = seqContainer.getStrategy(SeqType.ARTICLE_BLOG_ID).get("");
			}

			po.setArticleBlogId(articleBlogId);
		}

		po.setCreated(new Date());
		po.setStatus(post.getStatus());

		// 处理摘要
		if (StringUtils.isBlank(post.getSummary())) {
			po.setSummary(trimSummary(post.getEditor(), post.getContent()));
		} else {
			po.setSummary(post.getSummary());
		}

		try{
			postRepository.save(po);
			tagService.batchUpdate(po.getTags(), po.getId());

			PostAttribute attr = new PostAttribute();
			attr.setContent(post.getContent());
			attr.setEditor(post.getEditor());
			attr.setId(po.getId());
			postAttributeRepository.save(attr);
		} catch(Exception ex){
			throw new MtonsException("数据操作异常：" + ex.getMessage());
		}

		onPushEvent(po, PostUpdateEvent.ACTION_PUBLISH);
		return po.getId();
	}

	@Override
	public PostVO get(long id) {
		Optional<Post> po = postRepository.findById(id);
		if (po.isPresent()) {
			PostVO d = BeanMapUtils.copy(po.get());

			d.setAuthor(userService.get(d.getAuthorId()));
			d.setChannel(channelService.getById(d.getChannelId()));

			PostAttribute attr = postAttributeRepository.findById(d.getId()).get();
			d.setContent(attr.getContent());
			d.setEditor(attr.getEditor());
			return d;
		}
		return null;
	}

	@Override
	public PostVO get(String articleBlogId) {
		Post po = postRepository.findByArticleBlogId(articleBlogId);
		if(po == null){
			return  null;
		}

		PostVO d = map(po, PostVO.class);

		d.setAuthor(userService.get(d.getAuthorId()));
		d.setChannel(channelService.getById(d.getChannelId()));

		PostAttribute attr = postAttributeRepository.findById(d.getId()).get();
		d.setContent(attr.getContent());
		d.setEditor(attr.getEditor());

		return d;
	}

	/**
	 * 更新文章方法
	 * @param pp
	 */
	@Override
	@Transactional
	public void update(PostVO pp){
		Optional<Post> optional = postRepository.findById(pp.getId());

		if (optional.isPresent()) {
			Post po = optional.get();
			po.setTitle(pp.getTitle());//标题
			po.setChannelId(pp.getChannelId());
			po.setThumbnail(pp.getThumbnail());
			po.setStatus(pp.getStatus());

			if(StringUtils.isEmpty(po.getArticleBlogId())){
				String articleBlogId = seqContainer.getStrategy(SeqType.ARTICLE_BLOG_ID).get(po.getTitle());

				// 判断该编号是否存在，存在则随机生成
				while(postRepository.findByArticleBlogId(articleBlogId) != null){
					articleBlogId = seqContainer.getStrategy(SeqType.ARTICLE_BLOG_ID).get("");
				}

				po.setArticleBlogId(articleBlogId);
			}

			// 处理摘要
			if (StringUtils.isBlank(pp.getSummary())) {
				po.setSummary(trimSummary(pp.getEditor(), pp.getContent()));
			} else {
				po.setSummary(pp.getSummary());
			}

			po.setTags(pp.getTags());//标签

			// 保存扩展
			PostAttribute attr = new PostAttribute();
			attr.setContent(pp.getContent());
			attr.setEditor(pp.getEditor());
			attr.setId(po.getId());
			postAttributeRepository.save(attr);

			tagService.batchUpdate(po.getTags(), po.getId());
		}
	}

	@Override
	@Transactional
	public void updateFeatured(String articleBlogId, BlogFeaturedType featuredType) {
		Post po = postRepository.findByArticleBlogId(articleBlogId);

		po.setFeatured(featuredType);

		postRepository.save(po);
	}

	@Override
	@Transactional
	public void updateWeight(String articleBlogId, int weighted) {
		Post po = postRepository.findByArticleBlogId(articleBlogId);

		// 在实际weight处理中， 如果为置顶操作，则值为当前数据库内置顶weight最大值。
		int max = Consts.ZERO;
		if (Consts.ACTIVE == weighted) {
			max = postRepository.maxWeight() + 1;
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
		postAttributeRepository.deleteById(po.getId());

		onPushEvent(po, PostUpdateEvent.ACTION_DELETE);
	}

	@Override
	@Transactional
	public void delete(Set<String> articleBlogIds) {
		if (CollectionUtils.isNotEmpty(articleBlogIds)) {
			List<Post> list = postRepository.findAllByArticleBlogId(articleBlogIds);
			list.forEach(po -> {
				postRepository.delete(po);

				postAttributeRepository.deleteById(po.getId());
				onPushEvent(po, PostUpdateEvent.ACTION_DELETE);
			});
		}
	}

	@Override
	@Transactional
	public void identityViews(String articleBlogId) {
		// 次数不清理缓存, 等待文章缓存自动过期
		postRepository.updateViews(articleBlogId, Consts.IDENTITY_STEP);
	}

	@Override
	@Transactional
	public void identityComments(String articleBlogId) {
		postRepository.updateComments(articleBlogId, Consts.IDENTITY_STEP);
	}

	@Override
	@Transactional
	public void favor(String uid, String articleBlogId) {
		postRepository.updateFavors(articleBlogId, Consts.IDENTITY_STEP);
		favoriteService.add(uid, articleBlogId);
	}

	@Override
	@Transactional
	public void unfavor(String uid, String articleBlogId) {
		postRepository.updateFavors(articleBlogId,  Consts.DECREASE_STEP);
		favoriteService.delete(uid, articleBlogId);
	}

	@Override
	@PostStatusFilter
	public long count() {
		return postRepository.count();
	}

	@PostStatusFilter
	private List<Post> find(String orderBy, int size) {
		Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, orderBy));

		Set<Integer> excludeChannelIds = new HashSet<>();

		List<ChannelVO> channels = channelService.findRootAll(Consts.STATUS_CLOSED);
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

	/**
	 * 截取文章内容
	 * @param text
	 * @return
	 */
	private String trimSummary(String editor, final String text){
		if (Consts.EDITOR_MARKDOWN.endsWith(editor)) {
			return PreviewTextUtils.getText(MarkdownUtils.renderMarkdown(text), 126);
		} else {
			return PreviewTextUtils.getText(text, 126);
		}
	}

	private List<PostVO> toPosts(List<Post> posts) {
		List<PostVO> rets = new ArrayList<>();

		HashSet<Long> uids = new HashSet<>();
		HashSet<Integer> groupIds = new HashSet<>();

		posts.forEach(po -> {
			uids.add(po.getAuthorId());
			groupIds.add(po.getChannelId());
			rets.add(BeanMapUtils.copy(po));
		});

		// 加载用户信息
		buildUsers(rets, uids);
		buildGroups(rets, groupIds);

		return rets;
	}

	private void buildUsers(Collection<PostVO> posts, Set<Long> uids) {
		Map<Long, UserVO> userMap = userService.findMapByIds(uids);
		posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
	}

	private void buildGroups(Collection<PostVO> posts, Set<Integer> groupIds) {
		Map<Integer, ChannelVO> map = channelService.findMapByIds(groupIds);
		posts.forEach(p -> p.setChannel(map.get(p.getChannelId())));
	}

	private void onPushEvent(Post post, int action) {
		PostUpdateEvent event = new PostUpdateEvent(System.currentTimeMillis());
		event.setPostId(post.getId());
		event.setUserId(post.getAuthorId());
		event.setAction(action);

		event.setArticleBlogId(post.getArticleBlogId());

		applicationContext.publishEvent(event);
	}
}
