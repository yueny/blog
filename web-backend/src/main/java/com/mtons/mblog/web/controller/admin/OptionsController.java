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
import com.mtons.mblog.service.atom.bao.OptionsService;
import com.mtons.mblog.web.controller.BaseBizController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	private ContextStartup contextStartup;

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

}
