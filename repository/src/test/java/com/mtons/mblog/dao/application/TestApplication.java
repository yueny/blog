package com.mtons.mblog.dao.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *
 */
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
@ImportResource(locations = { "classpath:/config/cfg-properties-test.xml",
        "classpath:/config/spring-jpa-test.xml",
        "classpath:/config/spring-bean-test.xml"})
public class TestApplication {
    //.
}
