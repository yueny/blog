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

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.bo.ChannelVO;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.service.ChannelService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Channel 主页
 * @author langhsu
 *
 */
@Controller
public class ChannelController extends BaseController {
	@Autowired
	private ChannelService channelService;
	@Autowired
	private PostService postService;
	
	@RequestMapping("/channel/{flag}")
	public String channel(@PathVariable String flag, ModelMap model,
			HttpServletRequest request) {
		// init params
		String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);

		ChannelVO channel = channelService.getByFlag(flag);

		List<ChannelVO> children = channelService.findAll(0, channel.getChannelCode());

		// callback params
		model.put("channel", channel);
		model.put("children", children);
		model.put("order", order);
		model.put("pageNo", pageNo);
		return view(Views.POST_INDEX);
	}

	/**
	 * 查看文章详情
	 */
	@RequestMapping("/post/{id:\\d*}")
	@Deprecated
	public String view(@PathVariable Long id, ModelMap model) {
		PostVO postVO = postService.get(id);
		if(postVO != null){
			return redirectAction("/article/" + postVO.getArticleBlogId() + ".html");
		}

		// 找不到文章，回首页
		return view(Views.INDEX);
	}
}
