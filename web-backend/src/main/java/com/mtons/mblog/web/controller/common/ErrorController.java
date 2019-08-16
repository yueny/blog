package com.mtons.mblog.web.controller.common;

import com.mtons.mblog.web.controller.BaseBizController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * 错误控制器
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-16 13:29
 */
//@Controller
public class ErrorController extends BaseBizController {
    /**
     * 403
     *
     * @param response
     *            HttpServletResponse
     * @return url page
     */
    @RequestMapping(value = "/403")
    public String to403(final HttpServletResponse response) {
        return "110/404";
    }

    /**
     * 404
     *
     * @param response
     *            HttpServletResponse
     * @return url page
     */
    @RequestMapping(value = "/404")
    public String to404(final HttpServletResponse response) {
        return "110/404";
    }

    /**
     * error
     *
     * @param response
     *            HttpServletResponse
     * @return url page
     */
    @RequestMapping(value = "/error")
    public String toError(final HttpServletResponse response) {
        return "110/502";
    }

}
