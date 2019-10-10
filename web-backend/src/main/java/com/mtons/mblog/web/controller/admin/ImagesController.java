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

import com.mtons.mblog.web.controller.BaseBizController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 图片管理
 */
@Controller
@RequestMapping("/admin/images")
public class ImagesController extends BaseBizController {
//	@Autowired
//	private OptionsService optionsService;

	@RequestMapping("/list.html")
	public String index(ModelMap model) {
		getModel().addAttribute("title", "图片管理");

		return "/admin/images/index";
	}

}
