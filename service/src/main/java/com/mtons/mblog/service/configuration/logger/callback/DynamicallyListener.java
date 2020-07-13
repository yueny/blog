package com.mtons.mblog.service.configuration.logger.callback;

import com.mtons.mblog.service.configuration.logger.change.AbstractChanger;
import com.mtons.mblog.service.configuration.logger.change.IChanger;
import com.mtons.mblog.service.configuration.logger.change.LogbackChanger;
import com.taobao.diamond.annotation.DiamondListener;
import com.taobao.diamond.extend.DynamicProperties;
import com.taobao.diamond.listener.DiamondDataCallback;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 日志级别变化的监听
 */
@DiamondListener(dataId = "log", groupId = "blog")
public class DynamicallyListener implements InitializingBean, ApplicationContextAware,
        DiamondDataCallback {
    private IChanger changer = new LogbackChanger();
    private ConfigurableEnvironment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
       //.
    }

    @Override
    public void received(String data) {
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(data));

            PropertiesPropertySource propertySource = new PropertiesPropertySource("dynamic", properties);
            environment.getPropertySources().addLast(propertySource);
        } catch (IOException e) {
            throw new RuntimeException("装载properties失败：" + data, e);
        }

        CollectionUtils.mergePropertiesIntoMap(properties, DynamicProperties.staticProperties);

        change(properties.entrySet());
    }

    /**
     * 设置制定包的日志级别
     * @param entrys  entrys
     */
    public void change(Set<Map.Entry<Object, Object>> entrys){
        entrys.forEach(entry -> {
            if(AbstractChanger.isAllowed(entry.getValue().toString())){
                change(entry.getKey().toString(), entry.getValue().toString());
            }
        });
    }
    /**
     * 设置制定包的日志级别
     * @param packageName           包名
     * @param level                 日志等级
     */
    public void change(String packageName, String level){
        // 发现变更
        changer.setLevel(packageName, level);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        environment = (ConfigurableEnvironment) applicationContext.getEnvironment();
    }
}
