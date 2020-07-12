package com.mtons.mblog.modules.comp.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.mtons.mblog.base.consts.options.OptionsKeysConsts;
import com.mtons.mblog.service.ability.ISiteOptionsAbilityService;
import com.mtons.mblog.service.exception.MtonsException;
import com.mtons.mblog.modules.comp.MailService;
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
    protected ISiteOptionsAbilityService siteConfigService;
    @Autowired
    private ListeningExecutorService commonExecutorService;

    @Override
    public void config() {
        String mailHost = siteConfigService.getValue(OptionsKeysConsts.MAIL_SMTP_HOST);
        String mailUsername = siteConfigService.getValue(OptionsKeysConsts.MAIL_SMTP_USERNAME);
        String mailPassowrd = siteConfigService.getValue(OptionsKeysConsts.MAIL_SMTP_PASSWORD);
        String pwPs = siteConfigService.getValue(OptionsKeysConsts.MAIL_PW_PS);

        if (StringUtils.isNoneBlank(mailHost, mailUsername, mailPassowrd)) {
            MailSmtpType type = MailSmtpType._126;
            if(StringUtils.endsWith(mailHost, "126.com")){
                type = MailSmtpType._126;
            }else if(StringUtils.endsWith(mailHost, "aliyun.com")){
                type = MailSmtpType._ALIYUN;
            }

            if(StringUtils.isNotEmpty(pwPs)){
                OkEmail.config(type, mailUsername, mailPassowrd, true, pwPs);
            }else{
                OkEmail.config(type, mailUsername, mailPassowrd);
            }
        } else {
            log.error("邮件服务配置信息未设置, 请在后台系统配置中进行设置");
        }
    }

    @Override
    public void sendTemplateEmail(String to, String title, String template, Map<String, Object> content) {
        String text = render(template, content);
        String from = siteConfigService.getValue(OptionsKeysConsts.SITE_NAME);

        ListenableFuture<Future<ThreadEmailEntry>> task = commonExecutorService.submit(() -> {
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
                        ThreadEmailEntry entry = future.get();
                        if(entry.isSucess()){
                            log.info("email: {} send result:{}.", to, entry);
                        }else{
                            log.error("mail send error: ", entry.getThrowable());
                        }
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
