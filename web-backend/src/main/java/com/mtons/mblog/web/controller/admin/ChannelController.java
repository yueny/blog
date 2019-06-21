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
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.tree.QJson;
import com.mtons.mblog.base.tree.QTree;
import com.mtons.mblog.config.ContextStartup;
import com.mtons.mblog.bo.ChannelVO;
import com.mtons.mblog.model.ChannelTreeVO;
import com.mtons.mblog.service.ChannelService;
import com.mtons.mblog.web.controller.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
	/**
	 *
	 * @param queryAll 是否查询全部。 1是查询全部。0是否
	 */
	@RequestMapping("/list.html")
//	@RequiresPermissions("channel:list")
	public String list(@RequestParam(name = "queryAll", defaultValue = "0") Integer queryAll, ModelMap model) {
		if(queryAll == 1){
			model.put("list", channelService.findAll(Consts.IGNORE));
		}else{
			model.put("list", channelService.findRootAll(Consts.IGNORE));
		}

		model.put("queryAll", queryAll);

		// 页面
		return "/admin/channel/list";
	}

	/**
	 * 直接添加栏目\在树上添加子栏目
	 * @param channelCode   渠道编号
	 */
	@RequestMapping("/view/{channelCode}.html")
	public String view(@PathVariable final String channelCode, ModelMap model) {
		viewed(channelCode);

		return "/admin/channel/view";
	}
	/**
	 * 在树上添加子栏目
	 * @param channelCode 渠道编号
	 */
	@Deprecated
	@RequestMapping(value = "/tree/add/{channelCode}.html", method = { RequestMethod.POST })
	public String addByTree(@PathVariable final String channelCode, ModelMap model) {
		viewed(channelCode);

		return "/admin/channel/channel_node_add";
	}

	/**
	 * 添加子栏目
	 * @param channelCode   渠道编号
	 */
	private void viewed(String channelCode) {
		setModelAttribute("nodeTypeList", ChannelNodeType.values());

		if (channelCode != null) {
			// 取得该栏目基本信息
			ChannelVO channelVO = channelService.getByChannelCode(channelCode);
			if(channelVO == null){
				// 不存在该渠道，无效请求
				return;
			}

			setModelAttribute("view", channelVO);
//			model.put("parentChannelCode", channelVO.getParentChannelCode());
			setModelAttribute("parentChannelCode", channelVO.getParentChannelCode());
		}else{
			setModelAttribute("parentChannelCode", "-1");
		}
	}

	/**
	 * 列表新增和修改栏目、树上保存子栏目
	 * @param view view
	 * @param type 添加类型，如 channel「栏目」
	 */
	@RequestMapping("/update.json")
//	@RequiresPermissions("channel:update")
	public String update(ChannelVO view, @RequestParam(name = "type", defaultValue = "") String type) {
		if (view != null) {
			channelService.update(view);

			contextStartup.resetChannels();
		}
		// 请求
		return "redirect:/admin/channel/list.html";
	}
//	/**
//	 * 在树上保存子栏目
//	 * @param view view
//	 * @param type 添加类型，如 channel「栏目」
//	 */
//	@RequestMapping("/readerUpdateSave")
//	public String saveByTree(ChannelVO view, @RequestParam(name = "type") String type) {
//		if (view != null) {
//			channelService.update(view);
//
//			contextStartup.resetChannels();
//		}
//		return "redirect:/admin/channel/list";
//	}

	/**
	 * 权重修改
	 */
	@RequestMapping("/weight.json")
	@ResponseBody
	public Result weight(@RequestParam String channelCode, HttpServletRequest request) {
		int weight = ServletRequestUtils.getIntParameter(request, "weight", Consts.FEATURED_ACTIVE);

		ChannelVO vo = channelService.getByChannelCode(channelCode);
		if(vo == null){
			return Result.failure("不存在该数据！");
		}

		channelService.updateWeight(vo.getId(), weight);
		contextStartup.resetChannels();
		return Result.success();
	}

	/**
	 * 删除
	 * @param ids 渠道编码，如果是多个，则逗号,分隔
	 * @return
	 */
	@RequestMapping("/delete.json")
	@ResponseBody
//	@RequiresPermissions("channel:delete")
	public Result delete(String ids) {
		Result<String> data = Result.failure("操作失败");
		if (ids != null) {
			try {
				String channelCode = ids;
				channelService.delete(ids);
				data = Result.success();

				contextStartup.resetChannels();
			} catch (Exception e) {
				data = Result.failure(e.getMessage());
				logger.error("exception:", e);
			}
		}
		return data;
	}


	/** ======================== 栏目树的一系列直接操作 =========================== **/
	/**
	 * 根据栏目编号和栏目id查询该栏目下的子栏目树
	 * @return
	 */
	@RequestMapping("/tree/query.json")
	@ResponseBody
	public QJson tree(Integer id, String code) {
		ChannelVO vo = channelService.getByChannelCode(code);
		if(vo == null){
			return QJson.builder().msg("不存在的数据！").type("").build();
		}

		List<ChannelTreeVO> list = channelService.findAllForTree(Consts.IGNORE, vo.getChannelCode());
		List<QTree> children = cover(list);

		QTree tree = QTree.builder().id(vo.getId())
				.code(vo.getChannelCode())
				.url(vo.getFlag()).text(vo.getName())
				.children(children)
				.build();

		QJson resp = QJson.builder().msg("成功").type("").object(tree).build();

		return resp;
	}

	private List<QTree> cover(List<ChannelTreeVO> list){
		List<QTree> treeList = new ArrayList<>(list.size());

		list.forEach(channelTreeVO -> {
			QTree tree = QTree.builder().id(channelTreeVO.getId())
					.code(channelTreeVO.getChannelCode())
					.text(channelTreeVO.getName())
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
