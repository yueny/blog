package com.mtons.mblog.web.exceptions;

import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.service.exception.MtonsException;
import com.mtons.mblog.service.util.formatter.JsonUtils;
import com.yueny.rapid.data.resp.pojo.response.BaseResponse;
import com.yueny.rapid.lang.json.JsonUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-16 13:31
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static final String logExceptionFormat = "Capture Exception By GlobalDefaultExceptionHandler: Code: %s Detail: %s";
    private static Logger log = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    //运行时异常
    @ExceptionHandler(MtonsException.class)
    public String mtonsExceptionHandler(HttpServletRequest req,
                                          final HttpServletResponse response, MtonsException ex) throws Exception {
        return resultFormat(req, response, ex.getCode(), ex.getErrorMessage(), ex);
    }

    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    public String runtimeExceptionHandler(HttpServletRequest req,
                  final HttpServletResponse response, RuntimeException ex) throws Exception {
        return resultFormat(req, response, ErrorType.SYSTEM_ERROR.getCode(), ex.getMessage(), ex);
    }

    //其他错误
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(final HttpServletRequest req, final HttpServletResponse response,
                                      final Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        return resultFormat(req, response, ErrorType.SYSTEM_ERROR.getCode(), e.getMessage(), e);
    }

    private <T extends Throwable> String resultFormat(HttpServletRequest req,
                  final HttpServletResponse response, String code, String errorMessage, T e) throws Exception {
        // 添加自己的异常处理逻辑，如日志记录等
        e.printStackTrace();

        // 如果是json格式的ajax请求
        if (req.getHeader("accept").indexOf("application/json") > -1 || (req.getHeader("X-Requested-With") != null
                && req.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {
//            response.setStatus(500);
            response.setStatus(HttpStatus.OK.value()); //设置状态码
            rendJson(response, code, errorMessage);

            return "";
        }

        // 如果是普通请求
        // Otherwise setup and send the user to a default error-view.
        req.setAttribute("code", code);
        req.setAttribute("message", errorMessage);
        req.setAttribute("url", req.getRequestURL());
        return "redirect:/error";
    }

    private void rendJson(final HttpServletResponse response, final String code, final String errorMessage)
            throws IOException {
        BaseResponse resp = new BaseResponse();
        resp.setCode(code);
        resp.setMessage(errorMessage);
//        final JSONObject json = new JSONObject();
//        json.put("code", code);
//        json.put("message", errorMessage);
        rendText(response, JsonUtil.toJson(resp));
    }

    private void rendText(final HttpServletResponse response, final String content) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8"); //避免乱码

        try {
            final PrintWriter writer = response.getWriter();
            writer.write(content);
            writer.flush();
        } catch (final IOException e1) {
            e1.printStackTrace();
        }
    }

}
