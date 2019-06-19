package main.java.com.mtons.mblog.config;

import org.dozer.DozerBeanMapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;

/**
 * dozer配置
 *
 * @author yuany
 * @since 3.6-GA
 */
@Configuration
public class DozerConfiguration {
//    @Bean(name = "org.dozer.Mapper")
//    public DozerBeanMapperFactoryBean mapper(@Value("classpath:dozer/dozer-config.xml") Resource[] resources)
//            throws Exception {
//        final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
//        dozerBeanMapperFactoryBean.setMappingFiles(resources);
//        return dozerBeanMapperFactoryBean;
//    }

    @Bean(name = "org.dozer.Mapper")
    public DozerBeanMapper mapper() {
        // 配置文件的路径
        List<String> mappingFiles = Arrays.asList("dozer/dozer-config.xml");

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(mappingFiles);
        return mapper;
    }

}
