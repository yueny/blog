package com.mtons.mblog.service.manager.impl;

import com.mtons.mblog.bo.CommentBo;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.CommentService;
import com.mtons.mblog.service.manager.ICommentManagerService;
import com.mtons.mblog.service.watcher.executor.UserEventExecutor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019年8月9日 10:02:34
 */
@Service
public class CommentManagerServiceImpl extends BaseService implements ICommentManagerService {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserEventExecutor userEventExecutor;

    @Override
    @Transactional
    public void delete(Set<Long> ids) {
        // 获取评论的信息
        List<CommentBo> list =  commentService.findAllById(ids);

        // 重新统计评论相关数据
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(bo -> {
                userEventExecutor.identityComment(bo.getUid(), false);
                //TODO  重新统计评论所在文章的评论总数
                //
            });
        }

        // 批量删除评论
        commentService.deleteByIds(ids);
    }

    @Override
    @Transactional
    public void delete(long id, String uid) {
        commentService.delete(id, uid);

        // 重新统计评论相关数据
        userEventExecutor.identityComment(uid, false);
        //TODO  重新统计评论所在文章的评论总数
        //
    }

    @Override
    @Transactional
    public void deleteByPostId(long postId) {
        List<CommentBo> list = commentService.findByPostId(postId);
        if (CollectionUtils.isNotEmpty(list)) {
            Set<String> uids = new HashSet<>();
            list.forEach(n -> uids.add(n.getUid()));

            userEventExecutor.identityComment(uids, false);
            //TODO  重新统计评论所在文章的评论总数
            //
        }

        commentService.deleteByPostId(postId);
    }

    @Override
    public Long post(CommentBo comment) {
        Long pk = commentService.post(comment);

        // 如果不是匿名，则更新用户个人的评论总数
        if(!comment.isAnonymity()){
            userEventExecutor.identityComment(comment.getUid(), true);
        }

        return pk;
    }
}
