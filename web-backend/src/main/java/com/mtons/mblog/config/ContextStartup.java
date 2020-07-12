package com.mtons.mblog.config;

import com.mtons.mblog.base.enums.StatusType;
import com.mtons.mblog.bo.OptionsBo;
import com.mtons.mblog.modules.service.EntityService;
import com.mtons.mblog.service.atom.bao.OptionsService;
import com.mtons.mblog.service.atom.jpa.ChannelService;
import com.mtons.mblog.modules.comp.MailService;
import com.mtons.mblog.service.configuration.site.SiteOptionsConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

/**
 * 加载配置信息到系统
 * @since 3.0
 */
@Slf4j
@Order(2)
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private MailService mailService;
    @Autowired
    private SiteOptionsConfiguration siteOptionsConfiguration;

    private ServletContext servletContext;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("initialization ...");

        reloadOptions(true);
        resetChannels();

        log.info("OK, completed");
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * 重新加载配置
     * @param startup 是否重启应用
     */
    public void reloadOptions(boolean startup) {
        List<OptionsBo> options = optionsService.findAll();

        log.info("find options ({})...", options.size());

        if (startup && CollectionUtils.isEmpty(options)) {
            try {
                log.info("init options...");
                Resource resource = new ClassPathResource("/scripts/schema.sql");
                entityService.initSettings(resource);

                options = optionsService.findAll();
            } catch (Exception e) {
                log.error("------------------------------------------------------------");
                log.error("-          ERROR: The database is not initialized          -");
                log.error("------------------------------------------------------------");
                log.error(e.getMessage(), e);
                System.exit(1);
            }
        }

        // 获取站点相关的配置， 将数据库的配置信息存入站点配置集合中
        Map<String, String> map = siteOptionsConfiguration.getOptions();
        options.forEach(opt -> {
            if (StringUtils.isNoneBlank(opt.getKey(), opt.getValue())) {
                map.put(opt.getKey(), opt.getValue());
            }
        });
        servletContext.setAttribute("options", map);
        servletContext.setAttribute("site", siteOptionsConfiguration);

        // 邮件配置重置
        mailService.config();

        System.setProperty("site.location", siteOptionsConfiguration.getLocation());
    }

    public void resetChannels() {
        servletContext.setAttribute("channels", channelService.findRootAllForTree(StatusType.NORMAL.getValue()));
    }

}
