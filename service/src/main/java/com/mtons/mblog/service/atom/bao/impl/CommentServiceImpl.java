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
import com.mtons.mblog.service.util.DefaultMojoFactory;
import com.mtons.mblog.bo.CommentBo;
import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.entity.bao.Comment;
import com.mtons.mblog.dao.mapper.CommentMapper;
import com.mtons.mblog.service.atom.bao.CommentService;
import com.mtons.mblog.service.atom.bao.PostService;
import com.mtons.mblog.service.atom.jpa.UserJpaService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
public class CommentServiceImpl extends AbstractPlusService<CommentBo, Comment, CommentMapper>
		implements CommentService {
	@Autowired
	private UserJpaService userService;
	@Autowired
	private PostService postService;
	
	@Override
	public Page<CommentBo> paging4Admin(Pageable pageable) {
		Page<CommentBo> page = findAll(pageable);
		List<CommentBo> commentList = page.getContent();

		Set<Long> parentCommentIds = new HashSet<>();
		HashSet<Long> authorIds= new HashSet<>();
		Set<Long> postIds = new HashSet<>();
		commentList.forEach(commentBo -> {
			if (commentBo.getPid() > 0) {
				parentCommentIds.add(commentBo.getPid());
			}

			authorIds.add(commentBo.getAuthorId());
//			commentBo = BeanMapUtils.copy(commentEntry);
			postIds.add(commentBo.getPostId());
		});

		buildUsers(commentList, authorIds);
		buildParent(commentList, parentCommentIds);
		buildPosts(commentList, postIds);

		return page;
	}

	@Override
	public Page<CommentBo> pagingByAuthorId(Pageable pageable, long authorId) {
		LambdaQueryWrapper<Comment> queryWrapper = new QueryWrapper<Comment>().lambda();
		queryWrapper.eq(Comment::getAuthorId, authorId);

		Page<CommentBo> page = findAll(pageable, queryWrapper);
		//commentRepository.findAllByAuthorId(pageable, authorId);

		List<CommentBo> rets = page.getContent();
		Set<Long> parentIds = new HashSet<>();
		Set<Long> authorIds = new HashSet<>();
		Set<Long> postIds = new HashSet<>();

		rets.forEach(bo -> {
			//CommentVO c = map(po, CommentVO.class);
//			CommentBo c = BeanMapUtils.copy(po);

			if (bo.getPid() > 0) {
				parentIds.add(bo.getPid());
			}
			authorIds.add(bo.getAuthorId());
			postIds.add(bo.getPostId());
		});

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, authorIds);
		buildPosts(rets, postIds);

		return page;
//		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Page<CommentBo> pagingByPostId(Pageable pageable, long postId) {
		LambdaQueryWrapper<Comment> queryWrapper = new QueryWrapper<Comment>().lambda();
		queryWrapper.eq(Comment::getPostId, postId);

		Page<CommentBo> page = findAll(pageable, queryWrapper);
//		Page<Comment> page = commentRepository.findAllByPostId(pageable, postId);
		
		List<CommentBo> commentList = page.getContent();
		Set<Long> parentCommentIds = new HashSet<>();
		Set<Long> authorIds = new HashSet<>();

		commentList.forEach(commentBo -> {
//			CommentBo c = map(comment, CommentBo.class);

			if (commentBo.getPid() > 0) {
				parentCommentIds.add(commentBo.getPid());
			}
			authorIds.add(commentBo.getAuthorId());
		});

		// 加载父节点
		buildParent(commentList, parentCommentIds);

		buildUsers(commentList, authorIds);

		return page;
	}

	@Override
	public List<CommentBo> findLatestComments(int maxResults) {
		// 由大到小倒叙
		Pageable pageable = PageRequest.of(0, maxResults, new Sort(Sort.Direction.DESC, "id"));
		Page<CommentBo> page = findAll(pageable);
//		Page<Comment> page = commentRepository.findAll(pageable);

		List<CommentBo> rets = page.getContent();

		HashSet<Long> authorIds= new HashSet<>();

		rets.forEach(bo -> {
			authorIds.add(bo.getAuthorId());
//			rets.add(BeanMapUtils.copy(po));
		});

		buildUsers(rets, authorIds);
		return rets;
	}

	@Override
	public List<CommentBo> findByPostId(String articleBlogId) {
		LambdaQueryWrapper<Comment> queryWrapper = new QueryWrapper<Comment>().lambda();
		queryWrapper.eq(Comment::getArticleBlogId, articleBlogId);

		List<Comment> entryList = list(queryWrapper);
		if(CollectionUtils.isEmpty(entryList)){
			return Collections.emptyList();
		}

		return map(entryList, CommentBo.class);
	}

	@Override
	@Deprecated
	public List<CommentBo> findByPostId(Long postId) {
		LambdaQueryWrapper<Comment> queryWrapper = new QueryWrapper<Comment>().lambda();
		queryWrapper.eq(Comment::getPostId, postId);

		List<Comment> entryList = list(queryWrapper);
		if(CollectionUtils.isEmpty(entryList)){
			return Collections.emptyList();
		}

		return map(entryList, CommentBo.class);
	}

	@Override
	public Map<Long, CommentBo> findByIds(Set<Long> ids) {
		List<CommentBo> list = findAllById(ids);

		Map<Long, CommentBo> ret = new HashMap<>();
		Set<Long> authorIds = new HashSet<>();
		list.forEach(bo -> {
			authorIds.add(bo.getAuthorId());
			ret.put(bo.getId(), bo);
		});

		buildUsers(ret.values(), authorIds);
		return ret;
	}

	@Override
	@Transactional
	@Deprecated
	public long post(CommentBo comment) {
		// 0默认为有效
		comment.setStatus(0);
		boolean rs = insert(comment);

		return comment.getId();
	}

	@Override
	public void delete(long id, String uid) {
		CommentBo bo = find(id);
		// 判断文章是否属于当前登录用户
		Assert.isTrue(StringUtils.equals(bo.getUid(), uid), "认证失败");

		delete(id);
	}

	@Override
	public void deleteByPostId(String articleBlogId) {
		LambdaQueryWrapper<Comment> wrapper = new QueryWrapper<Comment>().lambda();
		wrapper.eq(Comment::getArticleBlogId, articleBlogId);

		delete(wrapper);
	}

	@Override
	public int countByAuthorIdAndPostId(long authorId, long toId) {
		LambdaQueryWrapper<Comment> queryWrapper = new QueryWrapper<Comment>().lambda();
		queryWrapper.eq(Comment::getAuthorId, authorId);
		queryWrapper.eq(Comment::getPostId, toId);

		return count(queryWrapper);
//		return commentRepository.countByAuthorIdAndPostId(authorId, toId);
	}

	private void buildUsers(Collection<CommentBo> comments, Set<Long> authorIds) {
		Map<Long, UserBO> userMap = userService.findMapByIds(authorIds);

		comments.forEach(comment -> {
			if(!comment.getCommitAuthoredType().isAuthor()){
				// 存在访客评论的评论信息
				CommentBo.UserCommentModel author = DefaultMojoFactory.guestCommentGet(comment.getClientIp(), comment.getClientAgent());
				comment.setAuthor(author);
				return;
			}

			UserBO userBO = userMap.get(comment.getAuthorId());
			if (userBO == null) {
				return;
			}
			CommentBo.UserCommentModel uc = mapAny(userBO, CommentBo.UserCommentModel.class);
			comment.setAuthor(uc);
		});
	}

	private void buildPosts(Collection<CommentBo> comments, Set<Long> postIds) {
		Map<Long, PostBo> postMap = postService.findMapForAuthorByIds(postIds);
		comments.forEach(p -> {
			PostBo postBO = postMap.get(p.getPostId());
			p.setPost(postBO);
		});
	}

	private void buildParent(Collection<CommentBo> comments, Set<Long> parentCommentIds) {
		if (!parentCommentIds.isEmpty()) {
			Map<Long, CommentBo> pm = findByIds(parentCommentIds);

			comments.forEach(c -> {
				if (c.getPid() > 0) {
					// 如果存在评论删除情况，则此处pm.get 为null， 赋值为null, 会被当成未被评论处理
					c.setParent(pm.get(c.getPid()));
				}else{
					//c.setParent(CommentVO.builder().build());
				}
			});
		}
	}

}
