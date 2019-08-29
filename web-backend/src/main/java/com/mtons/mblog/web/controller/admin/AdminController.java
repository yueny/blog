/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.config.ContextStartup;
import com.mtons.mblog.modules.service.PostSearchService;
import com.mtons.mblog.service.atom.bao.CommentService;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.atom.jpa.ChannelService;
import com.mtons.mblog.service.atom.bao.PostService;
import com.mtons.mblog.service.manager.IMenuJsonService;
import com.mtons.mblog.shiro.ShiroRuleFuncNames;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author langhsu
 *
 */
@Controller
public class AdminController {
    @Autowired
    private ChannelService channelService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostSearchService postSearchService;
    @Autowired
    private ContextStartup contextStartup;
    @Autowired
    private IMenuJsonService menuJsonService;

	@RequestMapping("/admin")
	public String index(HttpServletRequest request, ModelMap model) {
		pushSystemStatus(request, model);
		model.put("channelCount", channelService.count());
        model.put("postCount", postService.count());
        model.put("commentCount", commentService.count());
        model.put("userCount", userService.count());
		return "/admin/index";
	}
	
	private void pushSystemStatus(HttpServletRequest request, ModelMap model) {
        float freeMemory = (float) Runtime.getRuntime().freeMemory();
        float totalMemory = (float) Runtime.getRuntime().totalMemory();
        float usedMemory = (totalMemory - freeMemory);
        float memPercent = Math.round(freeMemory / totalMemory * 100) ;
        String os = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");

        model.addAttribute("freeMemory", freeMemory);
        model.addAttribute("totalMemory", totalMemory / 1024 / 1024);
        model.addAttribute("usedMemory", usedMemory / 1024 / 1024);
        model.addAttribute("memPercent", memPercent);
        model.addAttribute("os", os);
        model.addAttribute("javaVersion", javaVersion);
	}


    /**
     * 刷新系统变量
     * @return
     */
    @RequestMapping("/admin/reload_options")
    @ResponseBody

    @RequiresPermissions(ShiroRuleFuncNames.adminCache)
    @RequiresAuthentication
    public Result reloadOptions() {
        contextStartup.reloadOptions(false);
        contextStartup.resetChannels();
        return Result.success();
    }

    /**
     * 重建索引
     * @return
     */
    @RequestMapping("/admin/reset_indexes")
    @ResponseBody
    public Result resetIndexes() {
        postSearchService.resetIndexes();
        return Result.success();
    }

    /**
     * 刷新菜单
     * @return
     */
    @RequestMapping(value = "/admin/reload_menu", method = RequestMethod.POST)
    @ResponseBody
    public Result reloadMenu() {
        menuJsonService.reload();

        return Result.success();
    }
}
