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

import com.mtons.mblog.base.BlogConstant;
import com.mtons.mblog.modules.comp.util.DefaultMojoFactory;
import com.mtons.mblog.modules.data.CommentVO;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.entity.Comment;
import com.mtons.mblog.modules.repository.CommentRepository;
import com.mtons.mblog.modules.service.CommentService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.modules.service.UserEventService;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.base.utils.BeanMapUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author langhsu
 *
 */
@Service
@Transactional(readOnly = true)
public class CommentServiceImpl extends BaseService implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserEventService userEventService;
	@Autowired
	private PostService postService;
	
	@Override
	public Page<CommentVO> paging4Admin(Pageable pageable) {
		Page<Comment> page = commentRepository.findAll(pageable);
		List<CommentVO> rets = new ArrayList<>();

		HashSet<Long> uids= new HashSet<>();

		page.getContent().forEach(po -> {
			uids.add(po.getAuthorId());
			rets.add(BeanMapUtils.copy(po));
		});

		buildUsers(rets, uids);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Page<CommentVO> pagingByAuthorId(Pageable pageable, long authorId) {
		Page<Comment> page = commentRepository.findAllByAuthorId(pageable, authorId);

		List<CommentVO> rets = new ArrayList<>();
		Set<Long> parentIds = new HashSet<>();
		Set<Long> uids = new HashSet<>();
		Set<Long> postIds = new HashSet<>();

		page.getContent().forEach(po -> {
			CommentVO c = map(po, CommentVO.class);

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());
			postIds.add(c.getPostId());

			rets.add(c);
		});

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, uids);
		buildPosts(rets, postIds);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Page<CommentVO> pagingByPostId(Pageable pageable, long postId) {
		Page<Comment> page = commentRepository.findAllByPostId(pageable, postId);
		
		List<CommentVO> rets = new ArrayList<>();
		Set<Long> parentIds = new HashSet<>();
		Set<Long> uids = new HashSet<>();

		page.getContent().forEach(po -> {
			CommentVO c = map(po, CommentVO.class);

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());

			rets.add(c);
		});

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, uids);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public List<CommentVO> findLatestComments(int maxResults) {
		// 由大到小倒叙
		Pageable pageable = PageRequest.of(0, maxResults, new Sort(Sort.Direction.DESC, "id"));
		Page<Comment> page = commentRepository.findAll(pageable);
		List<CommentVO> rets = new ArrayList<>();

		HashSet<Long> uids= new HashSet<>();

		page.getContent().forEach(po -> {
			uids.add(po.getAuthorId());
			rets.add(BeanMapUtils.copy(po));
		});

		buildUsers(rets, uids);
		return rets;
	}

	@Override
	public Map<Long, CommentVO> findByIds(Set<Long> ids) {
		List<Comment> list = commentRepository.findAllById(ids);
		Map<Long, CommentVO> ret = new HashMap<>();
		Set<Long> uids = new HashSet<>();

		list.forEach(po -> {
			uids.add(po.getAuthorId());
			ret.put(po.getId(), BeanMapUtils.copy(po));
		});

		buildUsers(ret.values(), uids);
		return ret;
	}

	@Override
	@Transactional
	public long post(CommentVO comment) {
		Comment po = map(comment, Comment.class);
		po.setCreated(new Date());
		// 0默认为有效
		po.setStatus(0);

		commentRepository.save(po);

		// 更新用户评论总数
		userEventService.identityComment(comment.getAuthorId(), true);
		return po.getId();
	}

	@Override
	@Transactional
	public void delete(List<Long> ids) {
		List<Comment> list = commentRepository.removeByIdIn(ids);
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(po -> {
				userEventService.identityComment(po.getAuthorId(), false);
			});
		}
	}

	@Override
	@Transactional
	public void delete(long id, long authorId) {
		Optional<Comment> optional = commentRepository.findById(id);
		if (optional.isPresent()) {
			Comment po = optional.get();
			// 判断文章是否属于当前登录用户
			Assert.isTrue(po.getAuthorId() == authorId, "认证失败");
			commentRepository.deleteById(id);

			userEventService.identityComment(authorId, false);
		}
	}

	@Override
	@Transactional
	public void deleteByPostId(long postId) {
		List<Comment> list = commentRepository.removeByPostId(postId);
		if (CollectionUtils.isNotEmpty(list)) {
			Set<Long> userIds = new HashSet<>();
			list.forEach(n -> userIds.add(n.getAuthorId()));
			userEventService.identityComment(userIds, false);
		}
	}

	@Override
	public long count() {
		return commentRepository.count();
	}

	@Override
	public long countByAuthorIdAndPostId(long authorId, long toId) {
		return commentRepository.countByAuthorIdAndPostId(authorId, toId);
	}

	private void buildUsers(Collection<CommentVO> posts, Set<Long> uids) {
		Map<Long, UserVO> userMap = userService.findMapByIds(uids);

		posts.forEach(post -> {
			if(!post.getCommitAuthoredType().isAuthor()){
				// 存在访客评论的评论信息
				post.setAuthor(DefaultMojoFactory.guestCommentGet(post.getClientIp()));
				return;
			}

			UserVO userVO = userMap.get(post.getAuthorId());
			if (userVO == null) {
				return;
			}
			CommentVO.UserCommentModel uc = mapAny(userVO, CommentVO.UserCommentModel.class);
			post.setAuthor(uc);
		});
	}

	private void buildPosts(Collection<CommentVO> comments, Set<Long> postIds) {
		Map<Long, PostVO> postMap = postService.findMapByIds(postIds);
		comments.forEach(p -> {
			PostVO postVo = postMap.get(p.getPostId());
			p.setPost(postVo);
		});
	}

	private void buildParent(Collection<CommentVO> comments, Set<Long> parentIds) {
		if (!parentIds.isEmpty()) {
			Map<Long, CommentVO> pm = findByIds(parentIds);

			comments.forEach(c -> {
				if (c.getPid() > 0) {
					c.setParent(pm.get(c.getPid()));
				}
			});
		}
	}

}
