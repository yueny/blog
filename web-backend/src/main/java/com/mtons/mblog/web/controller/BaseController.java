package com.mtons.mblog.web.controller;

import com.mtons.mblog.service.util.formatter.StringEscapeEditor;
import com.yueny.rapid.lang.agent.UserAgentResource;
import com.yueny.rapid.lang.agent.handler.UserAgentUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller 基类
 *
 * @author langhsu
 * @since 3.0
 */
class BaseController {
    /**
     * 存放当前线程的HttpServletRequest对象
     */
    private final static ThreadLocal<HttpServletRequest> httpServletRequestThreadLocal = new ThreadLocal<>();
    /**
     * 存放当前线程的Model对象
     */
    private final static ThreadLocal<Model> modelThreadLocal = new ThreadLocal<>();
    /**
     * 存放当前线程的Model对象
     */
    private final static ThreadLocal<ModelMap> modelMapThreadLocal = new ThreadLocal<>();
    /**
     * 日志记录器
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }

    /**
     * 获取当前线程的UserAgent
     */
    protected UserAgentResource getCurrentUserAgent() {
        return UserAgentUtils.getCurrentUserAgent(getRequest());
    }

    /**
     * 获取当前线程的Model对象
     *
     * @return 当前线程的Model对象
     */
    protected Model getModel() {
        return modelThreadLocal.get();
    }

    /**
     * 获取当前线程的ModelMap对象
     *
     * @return 当前线程的 ModelMap 对象
     */
    protected ModelMap getModelMap() {
        return modelMapThreadLocal.get();
    }

    /**
     * 获取当前线程的HttpServletRequest对象
     *
     * @return 当前线程的HttpServletRequest对象
     */
    protected HttpServletRequest getRequest() {
        return httpServletRequestThreadLocal.get();
    }

    /**
     * 使用@ModelAttribute注解标识的方法会在每个控制器中的方法访问之前先调用
     *
     * @param request
     * @param model
     */
    @ModelAttribute
    protected void setThreadLocal(final HttpServletRequest request, final Model model, ModelMap modelMap) {
        httpServletRequestThreadLocal.set(request);
        modelThreadLocal.set(model);
        modelMapThreadLocal.set(modelMap);
    }

    /**
     * 从 HttpServletRequest 中获取属性值
     *
     * @param name
     *            属性名
     * @return 属性值
     */
    protected Object getRequestAttribute(final String name) {
        final HttpServletRequest request = this.getRequest();
        final Object value = request.getAttribute(name);
        return value;
    }

    /**
     * 请求重定向
     *
     * @param url
     *            重定向请求
     * @return 重定向请求
     */
    protected String redirectAction(final String url) {
        return String.format("redirect:%s", url);
    }

    /**
     * 向 Model 设置属性值
     *
     * @param name
     *            属性名
     * @param value
     *            属性值
     */
    protected void setModelAttribute(final String name, final Object value) {
        getModel().addAttribute(name, value);
    }

    /**
     * 向 HttpServletRequest 设置属性值
     *
     * @param name
     *            属性名
     * @param value
     *            属性值
     */
    protected void setRequestAttribute(final String name, final Object value) {
        final HttpServletRequest request = this.getRequest();
        request.setAttribute(name, value);
    }

    /**
     * 参数转换,key为空spring会自动过滤掉
     *
     * @param request
     *            HttpServletRequest
     * @return 转换后的参数
     */
    protected Map<String, String> getRequestParameter(
            final HttpServletRequest request) {
        final Map<String, String> requestParam = new HashMap<String, String>();
        for (final Map.Entry<String, String[]> entry : request.getParameterMap()
                .entrySet()) {
            final String key = entry.getKey();
            if (entry.getValue() == null
                    || StringUtils.isBlank(entry.getValue()[0])) {
                requestParam.put(key, StringUtils.EMPTY);
            }
            requestParam.put(key, entry.getValue()[0]);
        }
        return requestParam;
    }

}
