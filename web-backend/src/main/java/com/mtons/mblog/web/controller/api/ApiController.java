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
import com.mtons.mblog.service.util.BeanMapUtils;
import com.mtons.mblog.model.PostVO;
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

    @PostMapping(value = "/login")
    public Result login(String username, String password) {
        return executeLogin(username, password, false);
    }

    @RequestMapping("/posts")
    public Page<PostVO> posts(HttpServletRequest request) {
        String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
        int channelId = ServletRequestUtils.getIntParameter(request, "channelId", 0);
        return postManagerService.paging(wrapPageable(Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order))), Sets.newHashSet(channelId), null);
    }
}
