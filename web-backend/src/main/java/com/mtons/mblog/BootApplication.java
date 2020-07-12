package com.mtons.mblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SprintBootApplication
 */
@Slf4j
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
@ImportResource(locations = { "classpath:/config/cfg-properties.xml"
//        "classpath:/config/spring-jpa.xml"
})
@EnableScheduling
public class BootApplication {

    public static void main(String[] args) {
        try{
            ApplicationContext context = SpringApplication.run(BootApplication.class, args);
            String serverPort = context.getEnvironment().getProperty("server.port");
            log.info("mblog started at http://localhost:" + serverPort);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}