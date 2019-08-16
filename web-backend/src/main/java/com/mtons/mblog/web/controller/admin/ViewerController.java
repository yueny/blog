package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.mtons.mblog.bo.ViewLogVO;
import com.mtons.mblog.model.PostVO;
import com.mtons.mblog.service.atom.bao.ViewLogService;
import com.mtons.mblog.web.controller.BaseBizController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 访问记录控制器
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-16 13:42
 */
@Controller
@RequestMapping("/admin/viewer")
public class ViewerController extends BaseBizController {
    @Autowired
    private ViewLogService viewLogService;

    /**
     * 访问记录列表
     */
    @RequestMapping("/list")
    public String list(ModelMap model, HttpServletRequest request) {
        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "created"));
        Page<ViewLogVO> page = viewLogService.findAll(pageable);

        model.put("page", page);

        return "/admin/viewer/list";
    }
}
