package com.mtons.mblog.web.controller.site;

import com.mtons.mblog.base.utils.MarkdownUtils;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;
import com.yueny.rapid.lang.exception.DataVerifyAnomalyException;
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

	/**
	 * 查看 html 文章详情
	 *
	 * @param articleBlogId
	 *            文章扩展ID
	 */
	@RequestMapping(value = "/article/{articleBlogId}.html", method = { RequestMethod.GET })
	public String getArticleInfoPage(@PathVariable final String articleBlogId, final HttpServletResponse response) {
		logger.info("【查看 html 文章详细页面】入参:{}", articleBlogId);

		PostVO view = postService.get(articleBlogId);

		Assert.notNull(view, "该文章已被删除");

		if ("markdown".endsWith(view.getEditor())) {
			view.setContent(MarkdownUtils.renderMarkdown(view.getContent()));
		}

		postService.identityViews(articleBlogId);
		getModel().addAttribute("view", view);
		return view(Views.POST_VIEW);
	}

}
