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

import com.mtons.mblog.base.enums.ChannelNodeType;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.tree.QJson;
import com.mtons.mblog.base.tree.QTree;
import com.mtons.mblog.config.ContextStartup;
import com.mtons.mblog.modules.data.ChannelVO;
import com.mtons.mblog.modules.data.model.ChannelTreeVO;
import com.mtons.mblog.modules.service.ChannelService;
import com.mtons.mblog.web.controller.BaseController;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author langhsu
 *
 */
@Controller("adminChannelController")
@RequestMapping("/admin/channel")
public class ChannelController extends BaseController {
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ContextStartup contextStartup;

	/** ======================== 栏目管理 =========================== **/
	@RequestMapping("/list")
//	@RequiresPermissions("channel:list")
	public String list(ModelMap model) {
		model.put("list", channelService.findRootAll(Consts.IGNORE));
		return "/admin/channel/list";
	}
	
	@RequestMapping("/view")
	public String view(Integer id, ModelMap model) {
		model.put("nodeTypeList", ChannelNodeType.values());

		if (id != null) {
			ChannelVO view = channelService.getById(id);
			model.put("view", view);
		}
		return "/admin/channel/view";
	}

	/**
	 * 新增和修改栏目
	 */
	@RequestMapping("/update")
//	@RequiresPermissions("channel:update")
	public String update(ChannelVO view) {
		if (view != null) {
			channelService.update(view);

			contextStartup.resetChannels();
		}
		return "redirect:/admin/channel/list";
	}

	@RequestMapping("/weight")
	@ResponseBody
	public Result weight(@RequestParam Integer id, HttpServletRequest request) {
		int weight = ServletRequestUtils.getIntParameter(request, "weight", Consts.FEATURED_ACTIVE);
		channelService.updateWeight(id, weight);
		contextStartup.resetChannels();
		return Result.success();
	}

	@RequestMapping("/delete")
	@ResponseBody
//	@RequiresPermissions("channel:delete")
	public Result delete(Integer id) {
		Result data = Result.failure("操作失败");
		if (id != null) {
			try {
				channelService.delete(id);
				data = Result.success();

				contextStartup.resetChannels();
			} catch (Exception e) {
				data = Result.failure(e.getMessage());
			}
		}
		return data;
	}


	/** ======================== 栏目树的一系列直接操作 =========================== **/
	/**
	 * 根据栏目编号和栏目id查询该栏目下的子栏目树
	 * @return
	 */
	@RequestMapping("/tree/nodetor/query")
	@ResponseBody
	public QJson tree(Integer id, String code) {
		ChannelVO vo = channelService.getByChannelCode(code);
		if(vo == null){
			return QJson.builder().msg("不存在的数据！").type("").build();
		}

		List<ChannelTreeVO> list = channelService.findAllForTree(Consts.IGNORE, vo.getChannelCode());
		List<QTree> children = cover(list);

		QTree tree = QTree.builder().id(vo.getId())
				.url(vo.getFlag()).text(vo.getName())
				.children(children)
				.build();

		QJson resp = QJson.builder().msg("成功").type("").object(tree).build();

		return resp;
	}

	private List<QTree> cover(List<ChannelTreeVO> list){
		List<QTree> treeList = new ArrayList<>(list.size());

		list.forEach(channelTreeVO -> {
			QTree tree = QTree.builder().id(channelTreeVO.getId()).text(channelTreeVO.getName())
					.url(channelTreeVO.getFlag())
					.build();

			if(CollectionUtils.isNotEmpty(channelTreeVO.getChildren())){
				tree.setChildren(cover(channelTreeVO.getChildren()));
			}

			treeList.add(tree);
		});

		return treeList;
	}
	
}
