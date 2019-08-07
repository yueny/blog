package com.mtons.mblog.web.controller.site;

import com.mtons.mblog.model.PostVO;
import com.mtons.mblog.service.manager.PostManagerService;
import com.mtons.mblog.service.util.MarkdownUtils;
import com.mtons.mblog.bo.DemoBo;
import com.mtons.mblog.service.atom.bao.DemoService;
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
	private PostManagerService postManagerService;
	@Autowired
	private DemoService demoService;

	/**
	 * 查看 html 文章详情
	 *
	 * @param articleBlogId
	 *            文章扩展ID
	 */
	@RequestMapping(value = "/demo", method = { RequestMethod.GET })
	public DemoBo demo(String articleBlogId) {
		PostVO view = postManagerService.get(articleBlogId);

		Assert.notNull(view, "该文章已被删除");

		if ("markdown".endsWith(view.getEditor())) {
			view.setContent(MarkdownUtils.renderMarkdown(view.getContent()));
		}

		DemoBo demoVO = demoService.selectByOrderId(articleBlogId);
		if(demoVO == null){
			return null;
		}

		demoVO.setPost(view);

		return demoVO;
	}

}
