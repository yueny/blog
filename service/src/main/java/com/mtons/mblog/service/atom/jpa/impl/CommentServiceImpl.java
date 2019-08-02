/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.service.atom.jpa.impl;

import com.mtons.mblog.service.util.DefaultMojoFactory;
import com.mtons.mblog.bo.CommentVO;
import com.mtons.mblog.bo.PostBO;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.entity.jpa.Comment;
import com.mtons.mblog.dao.repository.CommentRepository;
import com.mtons.mblog.service.atom.jpa.CommentService;
import com.mtons.mblog.service.atom.jpa.PostService;
import com.mtons.mblog.service.watcher.executor.UserEventExecutor;
import com.mtons.mblog.service.atom.jpa.UserService;
import com.mtons.mblog.service.util.BeanMapUtils;
import com.mtons.mblog.service.BaseService;
import org.apache.commons.collections4.CollectionUtils;
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
	private UserEventExecutor userEventExecutor;
	@Autowired
	private PostService postService;
	
	@Override
	public Page<CommentVO> paging4Admin(Pageable pageable) {
		Page<Comment> page = commentRepository.findAll(pageable);

		List<CommentVO> commentList = new ArrayList<>();
		Set<Long> parentCommentIds = new HashSet<>();
		HashSet<Long> authorIds= new HashSet<>();
		Set<Long> postIds = new HashSet<>();
		page.getContent().forEach(comment -> {
			if (comment.getPid() > 0) {
				parentCommentIds.add(comment.getPid());
			}

			authorIds.add(comment.getAuthorId());
			commentList.add(BeanMapUtils.copy(comment));
			postIds.add(comment.getPostId());
		});

		buildUsers(commentList, authorIds);
		buildParent(commentList, parentCommentIds);
		buildPosts(commentList, postIds);

		return new PageImpl<>(commentList, pageable, page.getTotalElements());
	}

	@Override
	public Page<CommentVO> pagingByAuthorId(Pageable pageable, long authorId) {
		Page<Comment> page = commentRepository.findAllByAuthorId(pageable, authorId);

		List<CommentVO> rets = new ArrayList<>();
		Set<Long> parentIds = new HashSet<>();
		Set<Long> authorIds = new HashSet<>();
		Set<Long> postIds = new HashSet<>();

		page.getContent().forEach(po -> {
			//CommentVO c = map(po, CommentVO.class);
			CommentVO c = BeanMapUtils.copy(po);

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			authorIds.add(c.getAuthorId());
			postIds.add(c.getPostId());

			rets.add(c);
		});

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, authorIds);
		buildPosts(rets, postIds);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Page<CommentVO> pagingByPostId(Pageable pageable, long postId) {
		Page<Comment> page = commentRepository.findAllByPostId(pageable, postId);
		
		List<CommentVO> commentList = new ArrayList<>();
		Set<Long> parentCommentIds = new HashSet<>();
		Set<Long> authorIds = new HashSet<>();

		page.getContent().forEach(comment -> {
			CommentVO c = map(comment, CommentVO.class);

			if (c.getPid() > 0) {
				parentCommentIds.add(c.getPid());
			}
			authorIds.add(c.getAuthorId());

			commentList.add(c);
		});

		// 加载父节点
		buildParent(commentList, parentCommentIds);

		buildUsers(commentList, authorIds);

		return new PageImpl<>(commentList, pageable, page.getTotalElements());
	}

	@Override
	public List<CommentVO> findLatestComments(int maxResults) {
		// 由大到小倒叙
		Pageable pageable = PageRequest.of(0, maxResults, new Sort(Sort.Direction.DESC, "id"));
		Page<Comment> page = commentRepository.findAll(pageable);
		List<CommentVO> rets = new ArrayList<>();

		HashSet<Long> authorIds= new HashSet<>();

		page.getContent().forEach(po -> {
			authorIds.add(po.getAuthorId());
			rets.add(BeanMapUtils.copy(po));
		});

		buildUsers(rets, authorIds);
		return rets;
	}

	@Override
	public Map<Long, CommentVO> findByIds(Set<Long> ids) {
		List<Comment> list = commentRepository.findAllById(ids);
		Map<Long, CommentVO> ret = new HashMap<>();
		Set<Long> authorIds = new HashSet<>();

		list.forEach(po -> {
			authorIds.add(po.getAuthorId());
			ret.put(po.getId(), BeanMapUtils.copy(po));
		});

		buildUsers(ret.values(), authorIds);
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

		// 更新用户个人的评论总数
		if(!comment.isAnonymity()){
			userEventExecutor.identityComment(comment.getUid(), true);
		}

		return po.getId();
	}

	@Override
	@Transactional
	public void delete(List<Long> ids) {
		List<Comment> list = commentRepository.removeByIdIn(ids);
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(po -> {
				userEventExecutor.identityComment(po.getUid(), false);
			});
		}
	}

	@Override
	@Transactional
	public void delete(long id, String uid) {
		Optional<Comment> optional = commentRepository.findById(id);
		if (optional.isPresent()) {
			Comment po = optional.get();
			// 判断文章是否属于当前登录用户
			Assert.isTrue(po.getUid() == uid, "认证失败");
			commentRepository.deleteById(id);

			userEventExecutor.identityComment(uid, false);
		}
	}

	@Override
	@Transactional
	public void deleteByPostId(long postId) {
		List<Comment> list = commentRepository.removeByPostId(postId);
		if (CollectionUtils.isNotEmpty(list)) {
			Set<String> uids = new HashSet<>();
			list.forEach(n -> uids.add(n.getUid()));
			userEventExecutor.identityComment(uids, false);
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

	private void buildUsers(Collection<CommentVO> comments, Set<Long> authorIds) {
		Map<Long, UserBO> userMap = userService.findMapByIds(authorIds);

		comments.forEach(comment -> {
			if(!comment.getCommitAuthoredType().isAuthor()){
				// 存在访客评论的评论信息
				CommentVO.UserCommentModel author = DefaultMojoFactory.guestCommentGet(comment.getClientIp(), comment.getClientAgent());
				comment.setAuthor(author);
				return;
			}

			UserBO userBO = userMap.get(comment.getAuthorId());
			if (userBO == null) {
				return;
			}
			CommentVO.UserCommentModel uc = mapAny(userBO, CommentVO.UserCommentModel.class);
			comment.setAuthor(uc);
		});
	}

	private void buildPosts(Collection<CommentVO> comments, Set<Long> postIds) {
		Map<Long, PostBO> postMap = postService.findMapByIds(postIds);
		comments.forEach(p -> {
			PostBO postBO = postMap.get(p.getPostId());
			p.setPost(postBO);
		});
	}

	private void buildParent(Collection<CommentVO> comments, Set<Long> parentCommentIds) {
		if (!parentCommentIds.isEmpty()) {
			Map<Long, CommentVO> pm = findByIds(parentCommentIds);

			comments.forEach(c -> {
				if (c.getPid() > 0) {
					c.setParent(pm.get(c.getPid()));
				}else{
					c.setParent(CommentVO.builder().build());
				}
			});
		}
	}

}
