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
import com.mtons.mblog.base.utils.BlogUtils;
import com.mtons.mblog.config.ContextStartup;
import com.mtons.mblog.modules.service.OptionsService;
import com.mtons.mblog.modules.service.PostSearchService;
import com.mtons.mblog.service.manager.IMenuJsonService;
import com.mtons.mblog.web.controller.BaseBizController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 系统配置
 *
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/admin/options")
public class OptionsController extends BaseBizController {
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private PostSearchService postSearchService;
	@Autowired
	private ContextStartup contextStartup;
	@Autowired
	private IMenuJsonService menuJsonService;

	@RequestMapping("/index")
	public String index(ModelMap model) {
		return "/admin/options/index";
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam Map<String, String> body, ModelMap model) {
		// body 为整个表单元素， 不论是否有值
		optionsService.update(body);
		contextStartup.reloadOptions(false);
		model.put("data", Result.success());
		return "/admin/options/index";
	}

	/**
	 * 刷新系统变量
	 * @return
	 */
	@RequestMapping("/reload_options")
	@ResponseBody
	public Result reloadOptions() {
		contextStartup.reloadOptions(false);
		contextStartup.resetChannels();
		return Result.success();
	}

	/**
	 * 重建索引
	 * @return
	 */
	@RequestMapping("/reset_indexes")
	@ResponseBody
	public Result resetIndexes() {
		postSearchService.resetIndexes();
		return Result.success();
	}

	/**
	 * 刷新菜单
	 * @return
	 */
	@RequestMapping(value = "/reload_menu", method = RequestMethod.POST)
	@ResponseBody
	public Result reloadMenu() {
		menuJsonService.reload();

		return Result.success();
	}
}
