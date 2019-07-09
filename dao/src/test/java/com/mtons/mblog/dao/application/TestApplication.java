package com.mtons.mblog.dao.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *
 */
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class,
        DataSourceAutoConfiguration.class})
@ComponentScan(basePackages={"com.mtons.mblog.dao"})
@EnableJpaAuditing
@ImportResource(locations = { "classpath:/config/cfg-properties-test.xml",
        "classpath:/config/spring-jpa-test.xml",
        "classpath:/config/spring-bean-test.xml"})
public class TestApplication {
    //.
}
