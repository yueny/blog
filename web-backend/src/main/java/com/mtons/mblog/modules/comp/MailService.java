package com.mtons.mblog.modules.comp;

import java.util.Map;

/**
 * @author : langhsu
 */
public interface MailService {
    void config();
    void sendTemplateEmail(String to, String title, String template, Map<String, Object> content);
}
