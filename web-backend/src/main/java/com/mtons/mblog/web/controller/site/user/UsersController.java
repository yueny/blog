/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.site.user;

import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.exception.MtonsException;
import com.mtons.mblog.model.AccountProfile;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.service.atom.jpa.MessageService;
import com.mtons.mblog.service.manager.IAccountProfileService;
import com.mtons.mblog.web.controller.BaseBizController;
import com.mtons.mblog.web.controller.site.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户主页
 *
 * @author langhsu
 */
@Controller
@RequestMapping("/users")
public class UsersController extends BaseBizController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private IAccountProfileService accountProfileService;

    /**
     * 用户文章
     * @param domainHack 用户个性化域名地址
     * @param model  ModelMap
     * @return template name
     */
    @GetMapping(value = "/{domainHack}")
    public String posts(@PathVariable(value = "domainHack") String domainHack,
                        ModelMap model, HttpServletRequest request) {
        return method(domainHack, Views.METHOD_POSTS, model, request);
    }

    /**
     * 通用方法, 访问 users 目录下的页面
     * @param domainHack 用户个性化域名地址
     * @param method 调用方法
     * @param model  ModelMap
     * @return template name
     */
    @GetMapping(value = "/{domainHack}/{method}")
    public String method(@PathVariable(value = "domainHack") String domainHack,
                         @PathVariable(value = "method") String method,
                         ModelMap model, HttpServletRequest request) {
        model.put("pageNo", ServletRequestUtils.getIntParameter(request, "pageNo", 1));

        UserBO userBO = userService.findByDomainHack(domainHack);
        if(userBO == null){
            throw new MtonsException("无效或已失效的地址");
        }

        long userId = userBO.getId();

        // 访问消息页, 判断登录
        if (Views.METHOD_MESSAGES.equals(method)) {
            // 标记已读
            AccountProfile profile = getProfile();

            if (null == profile || profile.getId() != userId) {
                throw new MtonsException("您没有权限访问该页面");
            }
            messageService.readed4Me(profile.getId());
        }

        initUser(userId, model);
        return view(String.format(Views.USER_METHOD_TEMPLATE, method));
    }

    private void initUser(long userId, ModelMap model) {
        model.put("user", userService.find(userId));
        boolean owner = false;

        AccountProfile profile = getProfile();
        if (null != profile && profile.getId() == userId) {
            owner = true;
            putProfile(accountProfileService.find(profile.getId()));
        }
        model.put("owner", owner);
    }

}
