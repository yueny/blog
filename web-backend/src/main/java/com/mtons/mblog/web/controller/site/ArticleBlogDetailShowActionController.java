package com.mtons.mblog.web.controller.site;

import com.mtons.mblog.service.util.MarkdownUtils;
import com.mtons.mblog.bo.AccountProfile;
import com.mtons.mblog.bo.FavoriteVO;
import com.mtons.mblog.bo.PostBO;
import com.mtons.mblog.modules.service.FavoriteService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * 【文章显示】控制器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/13 下午12:17
 *
 */
@Controller
public class ArticleBlogDetailShowActionController extends BaseController {

	@Autowired
	private PostService postService;
	@Autowired
	private FavoriteService favoriteService;

	/**
	 * 查看 html 文章详情
	 *
	 * @param articleBlogId
	 *            文章扩展ID
	 */
	@RequestMapping(value = "/article/{articleBlogId}.html", method = { RequestMethod.GET })
	public String getArticleInfoPage(@PathVariable final String articleBlogId, final HttpServletResponse response) {
		logger.info("【查看 html 文章详细页面】入参:{}", articleBlogId);

		PostBO view = postService.get(articleBlogId);

		Assert.notNull(view, "该文章已被删除");

		if ("markdown".endsWith(view.getEditor())) {
			view.setContent(MarkdownUtils.renderMarkdown(view.getContent()));
		}

		AccountProfile accountProfile = getProfile();
		if(accountProfile != null){
			FavoriteVO favoriteVO = favoriteService.findByUidAndArticleBlogId(accountProfile.getUid(), articleBlogId);
			// 1表示文章已收藏， 0表示未收藏或者未登录无法判断
			getModel().addAttribute("isFavorite", (favoriteVO != null) ? 1: 0);
		}else{
			getModel().addAttribute("isFavorite", 0);
		}

		// 自增浏览数
		postService.identityViews(articleBlogId);

		getModel().addAttribute("view", view);
		return view(Views.POST_VIEW);
	}

}
