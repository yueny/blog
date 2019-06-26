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

import com.google.common.collect.Sets;
import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.bo.AccountProfile;
import com.mtons.mblog.bo.PostVO;
import com.mtons.mblog.service.atom.ChannelService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @author langhsu
 *
 */
@Controller("adminPostController")
@RequestMapping("/admin/post")
public class PostController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private ChannelService channelService;

	/**
	 * 文章列表
	 */
	@RequestMapping("/list")
	public String list(String title, ModelMap model, HttpServletRequest request) {
		//long id = ServletRequestUtils.getLongParameter(request, "id", Consts.ZERO);
		int channelId = ServletRequestUtils.getIntParameter(request, "channelId", Consts.ZERO);

		Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "weight", "created"));
		Page<PostVO> page = postService.paging4Admin(pageable, channelId, title);

		model.put("page", page);
		model.put("title", title);
//		model.put("id", id);
		model.put("channelId", channelId);
		model.put("channels", channelService.findRootAll(Consts.IGNORE));

		model.put("featuredTypes", BlogFeaturedType.values());

		return "/admin/post/list";
	}
	
	/**
	 * 控制台文章编辑
	 * @param articleBlogId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String toUpdate(String articleBlogId, ModelMap model) {
		String editor = siteOptions.getValue("editor");

		if(StringUtils.isNotEmpty(articleBlogId)){
			PostVO view = postService.get(articleBlogId);
			if(view != null){
				if (StringUtils.isNoneBlank(view.getEditor())) {
					editor = view.getEditor();
				}
				model.put("view", view);
			}
		}
		model.put("editor", editor);
		model.put("channels", channelService.findRootAll(Consts.IGNORE));
		return "/admin/post/view";
	}
	
	/**
	 * 更新文章方法
	 * @author LBB
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String subUpdate(PostVO post) {
		if (post != null) {
			if (post.getId() > 0 || StringUtils.isNotEmpty(post.getArticleBlogId())) {
				postService.update(post);
			} else {
				AccountProfile profile = getProfile();
				post.setAuthorId(profile.getId());
				post.setUid(profile.getUid());

				postService.post(post);
			}
		}
		return "redirect:/admin/post/list";
	}


	/**
	 * 博文推荐/消荐
	 */
	@RequestMapping("/featured")
	@ResponseBody
	public Result featured(String articleBlogId, HttpServletRequest request) {
		Result data = Result.failure("操作失败");
		int featured = ServletRequestUtils.getIntParameter(request, "featured",
				BlogFeaturedType.FEATURED_ACTIVE.getValue());

		BlogFeaturedType featuredType = BlogFeaturedType.getBy(featured);
		if (StringUtils.isNotEmpty(articleBlogId)) {
			try {
				postService.updateFeatured(articleBlogId, featuredType);
				data = Result.success();
			} catch (Exception e) {
				data = Result.failure(e.getMessage());
				logger.error("exception:", e);
			}
		}
		return data;
	}

	/**
	 * 博文置顶/消顶
	 */
	@RequestMapping("/weight")
	@ResponseBody
	public Result weight(String articleBlogId, HttpServletRequest request) {
		Result data = Result.failure("操作失败");

		// 如果没传， 置顶权重默认设置为1
		int weight = ServletRequestUtils.getIntParameter(request, "weight", BlogFeaturedType.FEATURED_ACTIVE.getValue());
		if (StringUtils.isNotEmpty(articleBlogId)) {
			try {
				postService.updateWeight(articleBlogId, weight);
				data = Result.success();
			} catch (Exception e) {
				data = Result.failure(e.getMessage());
				logger.error("exception:", e);
			}
		}
		return data;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(@RequestParam("articleBlogId") List<String> articleBlogId) {
		Result data = Result.failure("操作失败");
		if (CollectionUtils.isNotEmpty(articleBlogId)) {
			try {
				Set<String> articleBlogIds = Sets.newHashSet();
				articleBlogIds.addAll(articleBlogId);

				postService.delete(articleBlogIds);

				data = Result.success();
			} catch (Exception e) {
				data = Result.failure(e.getMessage());
				logger.error("exception:", e);
			}
		}
		return data;
	}
}
