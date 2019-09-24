/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.manager.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.bo.*;
import com.mtons.mblog.service.atom.bao.IPostAttributeService;
import com.mtons.mblog.service.manager.PostManagerService;
import com.mtons.mblog.service.util.PreviewTextUtils;
import com.mtons.mblog.model.PostVO;
import com.mtons.mblog.service.watcher.event.PostUpdateEvent;
import com.mtons.mblog.service.atom.bao.PostService;
import com.mtons.mblog.service.atom.bao.TagService;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.jpa.ChannelService;
import com.mtons.mblog.service.atom.bao.ResourceService;
import com.mtons.mblog.service.exception.MtonsException;
import com.mtons.mblog.service.util.MarkdownUtils;
import com.mtons.mblog.base.enums.watcher.PostUpdateType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 *
 */
@Service
@Transactional
public class PostManagerServiceImpl extends BaseService implements PostManagerService {
	@Autowired
	private PostService postService;
	@Autowired
	private IPostAttributeService postAttributeService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ResourceService resourceService;

	@Override
	public Page<PostVO> paging(Pageable pageable, Set<Integer> channelIds, Set<Integer> excludeChannelIds) {
		Page<PostBo> page = postService.pagingForAuthor(pageable, channelIds, excludeChannelIds);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public Page<PostVO> paging4Admin(Pageable pageable, int channelId, String title) {
		Page<PostBo> page = postService.paging4AdminForAuthor(pageable, channelId, title);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

//	@Override
//	public Page<PostVO> pagingByAuthorId(Pageable pageable, long userId) {
//		Page<PostBO> page = postService.findAllByAuthorId(pageable, userId);
//		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
//	}

	@Override
	public Map<Long, PostVO> findMapByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Long, PostBo> rets = postService.findMapByIds(ids);

		Map<Long, PostVO> list = new HashMap<>();
		for (Map.Entry<Long, PostBo> entry : rets.entrySet()) {
			PostVO postVO = mapAny(entry.getValue(), PostVO.class);

			list.put(entry.getKey(), postVO);
		}

		return list;
	}

	@Override
	@Transactional
	public Long post(PostVO post) {
		// 处理摘要
		if (StringUtils.isBlank(post.getSummary())) {
			post.setSummary(trimSummary(post.getEditor(), post.getContent()));
		}

		Long id = postService.post(post);

		try{
			tagService.batchUpdate(post.getTags(), id);

			PostAttributeBo attr = new PostAttributeBo();
			attr.setContent(post.getContent());
			attr.setEditor(post.getEditor());
			attr.setId(id);
			postAttributeService.insert(attr);
		} catch(Exception ex){
			throw new MtonsException("数据操作异常：" + ex.getMessage());
		}

		PostBo postBO = postService.getForAuthor(post.getId());
		onPushEvent(postBO, PostUpdateType.ACTION_PUBLISH);

		return id;
	}

	@Override
	public PostVO get(long id) {
		PostBo po = postService.getForAuthor(id);
		if(po == null){
			return null;
		}

		PostVO vo = mapAny(po, PostVO.class);
		buildGroups(Lists.newArrayList(vo), Sets.newHashSet(vo.getChannelId()));

		buildResource(vo);

		// 博文内容
		PostAttributeBo attr = postAttributeService.get(vo.getId());
		if(attr != null && StringUtils.isNotEmpty(attr.getContent())){
			vo.setContent(attr.getContent());
			vo.setEditor(attr.getEditor());
		}

		return vo;
	}

	@Override
	public PostVO get(String articleBlogId) {
		PostBo po = postService.getForAuthor(articleBlogId);
		if(po == null){
			return null;
		}

		PostVO vo = mapAny(po, PostVO.class);

		buildGroups(Lists.newArrayList(vo), Sets.newHashSet(vo.getChannelId()));

		buildResource(vo);

		// 博文内容
		PostAttributeBo attr = postAttributeService.get(vo.getId());
		if(attr != null && StringUtils.isNotEmpty(attr.getContent())){
			vo.setContent(attr.getContent());
			vo.setEditor(attr.getEditor());
		}

		return vo;
	}

	/**
	 * 更新文章方法
	 * @param pp
	 */
	@Override
	@Transactional
	public boolean update(PostVO pp){
		// 处理摘要
		if (StringUtils.isBlank(pp.getSummary())) {
			pp.setSummary(trimSummary(pp.getEditor(), pp.getContent()));
		}

		postService.update(pp);

		// 保存扩展
		PostAttributeBo attr = postAttributeService.get(pp.getId());
		if(attr == null){
			attr = new PostAttributeBo();
			attr.setId(pp.getId());
			attr.setContent(pp.getContent());
			attr.setEditor(pp.getEditor());
			postAttributeService.insert(attr);
		}else{
			attr.setContent(pp.getContent());
			attr.setEditor(pp.getEditor());
			postAttributeService.updateById(attr);
		}

		tagService.batchUpdate(pp.getTags(), pp.getId());

		return true;
	}

	@Override
	@Transactional
	public void delete(String articleBlogId, long authorId) {
		PostBo po = postService.getForAuthor(articleBlogId);

		postAttributeService.delete(po.getId());

		postService.delete(articleBlogId, authorId);

		onPushEvent(po, PostUpdateType.ACTION_DELETE);
	}

	@Override
	@Transactional
	public void delete(Set<String> articleBlogIds) {
		// 先备份
		Map<Long, PostBo> maps = new HashMap<>();
		if (CollectionUtils.isNotEmpty(articleBlogIds)) {
			articleBlogIds.forEach(id -> {
				PostBo postBO = postService.getForAuthor(id);
				maps.put(postBO.getId(), postBO);
			});
		}

		// 再删除
		Set<Long> ids = postService.delete(articleBlogIds);
		if (CollectionUtils.isNotEmpty(ids)) {
			ids.forEach(id -> {
				postAttributeService.delete(id);

				// 再通知
				onPushEvent(maps.get(id), PostUpdateType.ACTION_DELETE);
			});
		}
	}


	private List<PostVO> toPosts(List<PostBo> posts) {
		List<PostVO> rets = new ArrayList<>();

		HashSet<Long> uids = new HashSet<>();
		HashSet<Integer> groupIds = new HashSet<>();

		posts.forEach(po -> {
			uids.add(po.getAuthorId());
			groupIds.add(po.getChannelId());

			PostVO vo = mapAny(po, PostVO.class);
			rets.add(vo);
		});

		buildGroups(rets, groupIds);

		// 加载资源
		buildResource(rets);

		return rets;
	}

	private void buildResource(Collection<PostVO> posts) {
		posts.forEach(post -> {
			buildResource(post);
		});
	}

	private void buildResource(PostVO post) {
		if(StringUtils.isEmpty(post.getThumbnailCode())){
			return;
		}

		ResourceBO resourceBO = resourceService.findByThumbnailCode(post.getThumbnailCode());
		if(resourceBO == null){
			return;
		}
		post.setResource(resourceBO);
		if(StringUtils.isEmpty(post.getThumbnail())){
			post.setThumbnail(resourceBO.getPath());
		}
	}

	private void buildTags(PostVO post) {
		if(StringUtils.isEmpty(post.getTags())){
			return;
		}

		List<String> tags = Splitter.on(",").splitToList(post.getTags());
		List<TagBO> tagsList = new ArrayList<>(tags.size());
		for (String tag : tags) {
			TagBO tagBO = tagService.findByName(tag);
			if(tagBO == null){
				continue;
			}

			tagsList.add(tagBO);
		}

		post.setTagsList(tagsList);
	}

	private void buildGroups(Collection<PostVO> posts, Set<Integer> groupIds) {
		Map<Integer, ChannelVO> map = channelService.findMapByIds(groupIds);
		posts.forEach(p -> p.setChannel(map.get(p.getChannelId())));
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

	private void onPushEvent(PostBo postBO, PostUpdateType action) {
		PostUpdateEvent event = new PostUpdateEvent(System.currentTimeMillis());
		event.setUid(postBO.getUid());
		event.setAction(action);

		event.setPostId(postBO.getId());
		event.setArticleBlogId(postBO.getArticleBlogId());

		applicationContext.publishEvent(event);
	}


//	@Override
//	@Transactional
//	public void updateFeatured(String articleBlogId, BlogFeaturedType featuredType) {
//		Post po = postRepository.findByArticleBlogId(articleBlogId);
//
//		po.setFeatured(featuredType);
//
//		postRepository.save(po);
//	}
//
//	@Override
//	@Transactional
//	public void updateWeight(String articleBlogId, int weighted) {
//		Post po = postRepository.findByArticleBlogId(articleBlogId);
//
//		// 在实际weight处理中， 如果为置顶操作，则值为当前数据库内置顶weight最大值。
//		int max = Consts.ZERO;
//		if (Consts.ACTIVE == weighted) {
//			max = postRepository.maxWeight() + 1;
//		}
//		po.setWeight(max);
//
//		postRepository.save(po);
//	}
//
//	@Override
//	@PostStatusFilter
//	public List<PostBO> findLatestPosts(int maxResults) {
//		return find("created", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
//	}
//
//	@Override
//	@PostStatusFilter
//	public List<PostBO> findHottestPosts(int maxResults) {
//		return find("views", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
//	}
//
//	@Override
//	@Transactional
//	public void identityViews(String articleBlogId) {
//		// 次数不清理缓存, 等待文章缓存自动过期
//		postRepository.updateViews(articleBlogId, Consts.IDENTITY_STEP);
//	}
//
//	@Override
//	@Transactional
//	public void identityComments(String articleBlogId) {
//		postRepository.updateComments(articleBlogId, Consts.IDENTITY_STEP);
//	}
//
//	@Override
//	@Transactional
//	public void favor(String uid, String articleBlogId) {
//		postRepository.updateFavors(articleBlogId, Consts.IDENTITY_STEP);
//		favoriteService.add(uid, articleBlogId);
//	}
//
//	@Override
//	@Transactional
//	public void unfavor(String uid, String articleBlogId) {
//		postRepository.updateFavors(articleBlogId,  Consts.DECREASE_STEP);
//		favoriteService.delete(uid, articleBlogId);
//	}
//
//	@Override
//	@PostStatusFilter
//	public long count() {
//		return postRepository.count();
//	}
//
//	@PostStatusFilter
//	private List<Post> find(String orderBy, int size) {
//		Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, orderBy));
//
//		Set<Integer> excludeChannelIds = new HashSet<>();
//
//		List<ChannelVO> channels = channelService.findRootAll(Consts.STATUS_CLOSED);
//		if (channels != null) {
//			channels.forEach((c) -> excludeChannelIds.add(c.getId()));
//		}
//
//		Page<Post> page = postRepository.findAll((root, query, builder) -> {
//			Predicate predicate = builder.conjunction();
//			if (excludeChannelIds.size() > 0) {
//				predicate.getExpressions().add(
//						builder.not(root.get("channelId").in(excludeChannelIds)));
//			}
//			return predicate;
//		}, pageable);
//		return page.getContent();
//	}
//
//	/**
//	 * 截取文章内容
//	 * @param text
//	 * @return
//	 */
//	private String trimSummary(String editor, final String text){
//		if (Consts.EDITOR_MARKDOWN.endsWith(editor)) {
//			return PreviewTextUtils.getText(MarkdownUtils.renderMarkdown(text), 126);
//		} else {
//			return PreviewTextUtils.getText(text, 126);
//		}
//	}

}
