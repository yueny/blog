package com.mtons.mblog.web.controller.site;

import com.mtons.mblog.base.utils.MarkdownUtils;
import com.mtons.mblog.bo.DemoVO;
import com.mtons.mblog.bo.PostVO;
import com.mtons.mblog.service.DemoService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 【文章显示】控制器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/13 下午12:17
 *
 */
//@RestController
public class DemoController extends BaseController {

	@Autowired
	private PostService postService;
	@Autowired
	private DemoService demoService;

	/**
	 * 查看 html 文章详情
	 *
	 * @param articleBlogId
	 *            文章扩展ID
	 */
	@RequestMapping(value = "/demo", method = { RequestMethod.GET })
	public DemoVO demo(String articleBlogId) {
		PostVO view = postService.get(articleBlogId);

		Assert.notNull(view, "该文章已被删除");

		if ("markdown".endsWith(view.getEditor())) {
			view.setContent(MarkdownUtils.renderMarkdown(view.getContent()));
		}

		DemoVO demoVO = demoService.selectByOrderId(articleBlogId);
		if(demoVO == null){
			return null;
		}

		demoVO.setPost(view);

		return demoVO;
	}

}
