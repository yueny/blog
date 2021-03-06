/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.site;

import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.modules.service.PostSearchService;
import com.mtons.mblog.web.controller.BaseBizController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文章搜索
 * @author langhsu
 *
 */
@Controller
public class SearchController extends BaseBizController {
	@Autowired
	private PostSearchService postSearchService;

	@RequestMapping("/search")
	public String search(String kw, ModelMap model) {
		Pageable pageable = wrapPageable();
		try {
			if (StringUtils.isNotEmpty(kw)) {
				Page<PostBo> page = postSearchService.search(pageable, kw);
				model.put("results", page);
			}
		} catch (Exception e) {
			logger.error("exception:", e);
		}
		model.put("kw", kw);
		return view(Views.SEARCH);
	}
	
}
