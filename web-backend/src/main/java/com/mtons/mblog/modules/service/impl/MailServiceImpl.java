package com.mtons.mblog.modules.service.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.config.SiteOptions;
import com.mtons.mblog.modules.service.MailService;
import com.yueny.rapid.email.OkEmail;
import com.yueny.rapid.email.sender.entity.ThreadEmailEntry;
import com.yueny.rapid.email.util.MailSmtpType;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author : langhsu
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    private SiteOptions siteOptions;
    @Autowired
    private ListeningExecutorService executorService;

    @Override
    public void config() {
        String mailHost = siteOptions.getValue("mail_smtp_host");
        String mailUsername = siteOptions.getValue("mail_smtp_username");
        String mailPassowrd = siteOptions.getValue("mail_smtp_password");

        if (StringUtils.isNoneBlank(mailHost, mailUsername, mailPassowrd)) {
            OkEmail.config(MailSmtpType._126, mailUsername, mailPassowrd);
        } else {
            log.error("邮件服务配置信息未设置, 请在后台系统配置中进行设置");
        }
    }

    @Override
    public void sendTemplateEmail(String to, String title, String template, Map<String, Object> content) {
        String text = render(template, content);
        String from = siteOptions.getValue("site_name");

        ListenableFuture<Future<ThreadEmailEntry>> task = executorService.submit(() -> {
            Future<ThreadEmailEntry> future = OkEmail.subject(title)
                    .from(from)
                    .to(to)
                    .html(text)
                    .sendFuture();

            return future;
        });

        Futures.addCallback(task, new FutureCallback<Future<ThreadEmailEntry>>() {
            @Override
            public void onSuccess(Future<ThreadEmailEntry> future) {
                try{
                    if(future == null){
                        log.error("mail send fail, result is null.");
                    }else{
                        log.info("email: {} send result:{}.", to, future.get());
                    }
                } catch(Exception ex){
                    log.error("mail send error: ", ex);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.error("回调异步处理异常, 失败异常信息throwable:", throwable);
            }
        });
    }

    private String render(String templateName, Map<String, Object> model) {
        try {
            Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName, "UTF-8");
            t.setOutputEncoding("UTF-8");
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (Exception e) {
            throw new MtonsException(e.getMessage(), e);
        }
    }
}
