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

import com.mtons.mblog.config.ContextStartup;
import com.mtons.mblog.modules.data.FuncDefaultVO;
import com.mtons.mblog.modules.service.OptionsService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * 功能配置 默认值管理
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午8:05
 *
 */
@Controller
@RequestMapping("/admin/func/default")
public class FuncDefaultController extends BaseController {
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private ContextStartup contextStartup;

	@RequestMapping("/index")
	public String index(ModelMap model) {
	    model.put("tt", "默认项管理");

	    model.put("list", Arrays.asList(new FuncDefaultVO()));

		return "/admin/func/default/list";
	}

//	@RequestMapping("/update")
//	public String update(@RequestParam Map<String, String> body, ModelMap model) {
//		// body 为整个表单元素， 不论是否有值
//		optionsService.update(body);
//		contextStartup.reloadOptions(false);
//		model.put("data", Result.success());
//		return "/admin/options/index";
//	}
}
