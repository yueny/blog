package com.mtons.mblog.com.mtons.mblog.config;

import com.mtons.mblog.BootApplication;
import com.mtons.mblog.service.configuration.site.SiteOptionsConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-06-26 12:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class SiteOptionsConfigurationTest {
    @Autowired
    private SiteOptionsConfiguration siteOptionsConfiguration;

    @Test
    public void testRunner() {
        Assert.assertNotNull(siteOptionsConfiguration);

        System.out.println(siteOptionsConfiguration.getSettings());
    }
}
