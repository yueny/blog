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

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.consts.OptionsKeysConsts;
import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.bo.ResourceBO;
import com.mtons.mblog.service.atom.bao.OptionsService;
import com.mtons.mblog.service.atom.bao.ResourceService;
import com.mtons.mblog.service.exception.BizException;
import com.mtons.mblog.service.manager.IImageManagerService;
import com.mtons.mblog.service.util.PageHelper;
import com.mtons.mblog.web.controller.BaseBizController;
import com.yueny.rapid.data.resp.pojo.response.BaseResponse;
import com.yueny.rapid.data.resp.pojo.response.PageListResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 图片管理
 */
@Controller
@RequestMapping("/admin/images")
public class ImagesController extends BaseBizController {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private IImageManagerService imageManagerService;


	@RequestMapping("/list.html")
	public String index(ModelMap model) {
		getModel().addAttribute("title", "图片管理");
		getModel().addAttribute("siteDomain",
				optionsService.findByKey(OptionsKeysConsts.SITE_DOMAIN).getValue());

		return "/admin/images/index";
	}

	/**
	 * 获取图片列表
	 *
	 * @return
	 */
	@RequestMapping("/list.json")
	@ResponseBody
	public PageListResponse<ResourceBO> paging(String name) {
		PageListResponse<ResourceBO> resp = new PageListResponse<>();
		try{
			// 查询分页结果
			Pageable pageable = wrapPageable(Sort.Direction.DESC, "created");
			Page<ResourceBO> page = resourceService.findAll(pageable);

			resp.setList(PageHelper.fromSpringToYuenyPage(page));
		}catch (Exception e){
			resp.setCode(ErrorType.INVALID_ERROR.getCode());

			String msg = String.format(ErrorType.INVALID_ERROR.getMessage(), e.getMessage());
			resp.setMessage(msg);

			logger.error("exception: ", e);
		}

		return resp;
	}


	/**
	 * 删除图片
	 *
	 * @param thumbnailCodes
	 * @return
	 */
	@RequestMapping("/delete.json")
	@ResponseBody
	public BaseResponse delete(@RequestParam("thumbnailCodes") String thumbnailCodes, @RequestParam("mds") String mds) {
//		if(StringUtils.isEmpty(thumbnailCodes)){
//			return BaseResponse.failure("图片资源编号不能为空！");
//		}

		try{
			imageManagerService.delete(thumbnailCodes, mds);
		}catch (BizException e){
			e.printStackTrace();
		}

//		Result ret;
//		if (Consts.ADMIN_ID == id) {
//			ret = Result.failure("管理员不能操作");
//		}else if(roleService.delete(id)){
//			ret = Result.success();
//		}else{
//			ret = Result.failure("该角色不能被操作");
//		}
		return new BaseResponse();
	}

}
