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
    public void delete(long id, String uid) {
//
//        Optional<Comment> optional = commentRepository.findById(id);
//        if (optional.isPresent()) {
//            Comment po = optional.get();
//            // 判断文章是否属于当前登录用户
//            Assert.isTrue(po.getUid() == uid, "认证失败");
//            commentRepository.deleteById(id);
//
//            userEventExecutor.identityComment(uid, false);
//            //TODO  重新统计评论所在文章的评论总数
//            //
//        }
    }

    @Override
    public void deleteByPostId(long postId) {

    }
}
