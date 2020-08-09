/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.api;

import com.google.common.collect.Sets;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.bo.CommentBo;
import com.mtons.mblog.service.atom.bao.CommentService;
import com.mtons.mblog.service.util.BeanMapUtils;
import com.mtons.mblog.vo.PostVO;
import com.mtons.mblog.service.manager.PostManagerService;
import com.mtons.mblog.web.controller.BaseBizController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 侧边栏数据加载
 *
 * @author langhsu
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseBizController {
    @Autowired
    private PostManagerService postManagerService;
    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/login")
    public Result login(String username, String password) {
        return executeLogin(username, password, false);
    }

    @RequestMapping("/posts")
    public Page<PostVO> posts(HttpServletRequest request) {
        String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
        int channelId = ServletRequestUtils.getIntParameter(request, "channelId", 0);
        return postManagerService.findByPaging(wrapPageable(Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order))), Sets.newHashSet(channelId), null);
    }

    /**
     * 获取最新评论
     *
     * @param request
     * @returnC
     */
    @RequestMapping("/latest_comments")
    public List<CommentBo> latestComments(HttpServletRequest request) {
        int size = ServletRequestUtils.getIntParameter(request, "size", 6);

        List<CommentBo> list = commentService.findLatestComments(size);

        return list;
    }

}
