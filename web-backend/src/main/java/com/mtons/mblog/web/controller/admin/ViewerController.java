package com.mtons.mblog.web.controller.admin;

import com.google.common.collect.Sets;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.enums.BlogFeaturedType;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.bo.ViewLogVO;
import com.mtons.mblog.condition.ViewerQueryCondition;
import com.mtons.mblog.model.PostVO;
import com.mtons.mblog.service.atom.bao.ViewLogService;
import com.mtons.mblog.web.controller.BaseBizController;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;
import com.yueny.rapid.lang.date.DateTimeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
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
@RequestMapping("/admin/viewer")
public class ViewerController extends BaseBizController {
    @Autowired
    private ViewLogService viewLogService;

    /**
     * 访问记录列表
     */
    @RequestMapping("/list.html")
    public String list(ViewerQueryCondition condition, ModelMap model, HttpServletRequest request) {
        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "created"));
        Page<ViewLogVO> page = viewLogService.findAllByCondition(pageable, condition);

        model.put("page", page);
        // 当前日期
        model.put("nowDate", DateTimeUtil.today());
        // 当前日期
        model.put("condition", condition);

        return "/admin/viewer/list";
    }

    /**
     * 访问记录详情
     */
    @RequestMapping(value="/get.json", method= RequestMethod.POST)
    @ResponseBody
    public NormalResponse<ViewLogVO> get(Long id) {
        NormalResponse<ViewLogVO> resp = new NormalResponse<>();

        ViewLogVO viewLogVO = viewLogService.get(id);
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
