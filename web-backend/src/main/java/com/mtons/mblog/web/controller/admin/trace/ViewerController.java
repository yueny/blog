package com.mtons.mblog.web.controller.admin.trace;

import com.google.common.collect.Sets;
import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.bo.ViewLogBo;
import com.mtons.mblog.condition.ViewerQueryCondition;
import com.mtons.mblog.service.atom.bao.ViewLogService;
import com.mtons.mblog.service.util.PageHelper;
import com.mtons.mblog.web.controller.BaseBizController;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;
import com.yueny.rapid.data.resp.pojo.response.PageListResponse;
import com.yueny.rapid.lang.date.DateTimeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 访问记录控制器
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-16 13:42
 */
@Controller
@RequestMapping("/admin/trace/viewer")
public class ViewerController extends BaseBizController {
    @Autowired
    private ViewLogService viewLogService;

    /**
     * 访问记录列表
     */
    @RequestMapping("/index.html")
    public String list(ModelMap model, HttpServletRequest request) {
        // 当前日期
        model.put("nowDate", DateTimeUtil.today());

        return "/admin/viewer/list";
    }

    /**
     * 访问记录列表
     */
    @RequestMapping("/get/list.json")
    @ResponseBody
    public PageListResponse<ViewLogBo> getListData(ViewerQueryCondition condition) {
        PageListResponse<ViewLogBo> resp = new PageListResponse<>();

        try{
            // 查询分页结果
            Page<ViewLogBo> page = viewLogService.findAllByCondition(wrapPageable(Sort.by(Sort.Direction.DESC,
                    "created")), condition);

            resp.setList(PageHelper.fromSpringToYuenyPage(page));
        }catch (Exception e){
            resp.setCode(ErrorType.INVALID_ERROR.getCode());

            String msg = String.format(ErrorType.INVALID_ERROR.getMessage(), e.getMessage());
            resp.setMessage(msg);
        }

        return resp;
    }

    /**
     * 访问记录详情
     */
    @RequestMapping(value="/get.json", method= RequestMethod.POST)
    @ResponseBody
    public NormalResponse<ViewLogBo> get(Long id) {
        NormalResponse<ViewLogBo> resp = new NormalResponse<>();

        ViewLogBo viewLogVO = viewLogService.find(id);
        resp.setData(viewLogVO);

        return resp;
    }

    /**
     * 批量删除
     * @param ids 数据主键列表
     * @return 删除结果
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public Result delete(@RequestParam("id") List<Long> ids) {
        Result data = Result.failure("批量删除操作失败");

        if (CollectionUtils.isNotEmpty(ids)) {
            try {
                Set<Long> idList = Sets.newHashSet();
                idList.addAll(ids);

                viewLogService.deleteByIds(idList);

                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
                logger.error("exception:", e);
            }
        }
        return data;
    }
}
