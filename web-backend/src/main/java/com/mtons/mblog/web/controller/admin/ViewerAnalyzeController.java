package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.web.controller.BaseBizController;
import com.yueny.rapid.lang.date.DateTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 访问行为分析
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-20 18:16
 */
@Controller
@RequestMapping("/admin/viewer/analyze")
public class ViewerAnalyzeController extends BaseBizController {
    /**
     * 首页
     */
    @RequestMapping("/index.html")
    public String list(ModelMap model, HttpServletRequest request) {
        // 当前日期
        model.put("nowDate", DateTimeUtil.today());

        return "/admin/viewer/ana-list";
    }

//    /**
//     * 访问记录列表
//     */
//    @RequestMapping("/get/list.json")
//    @ResponseBody
//    public PageListResponse<ViewLogVO> getListData(ViewerQueryCondition condition) {
//        PageListResponse<ViewLogVO> resp = new PageListResponse<>();
//
//        try{
//            // 查询分页结果
//            Page<ViewLogVO> page = viewLogService.findAllByCondition(wrapPageable(Sort.by(Sort.Direction.DESC,
//                    "created")), condition);
//
//            resp.setList(PageHelper.fromSpringToYuenyPage(page));
//        }catch (Exception e){
//            resp.setCode(ErrorType.INVALID_ERROR.getCode());
//
//            String msg = String.format(ErrorType.INVALID_ERROR.getMessage(), e.getMessage());
//            resp.setMessage(msg);
//        }
//
//        return resp;
//    }
}
