package main.java.com.mtons.mblog.config;

import org.springframework.context.ApplicationContext;

/**
 * Spring 上下文持有化工具
 */
public final class ApplicationContextHolder {
    private static final Object object = new Object();
    private static ApplicationContext applicationContext = null;

    /**
     * spring容器中配置bean之后，会在项目启动的时候给applicationContext赋值
     */
    public static void setApplicationContext(ApplicationContext context) {
        if(applicationContext == null){
            synchronized (object){
                if(applicationContext == null){
                    applicationContext = context;
                }
            }
        }
    }

    /**
     * 获取applicationContext
     */
    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过class获取Bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if (getApplicationContext() == null) {
            return null;
        }
        return getApplicationContext().getBean(clazz);
    }

    /**
     *  通过name获取 Bean
     */
    public static Object getBean(String name){
        if (getApplicationContext() == null) {
            return null;
        }
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        if (getApplicationContext() == null) {
            return null;
        }
        return getApplicationContext().getBean(name, clazz);
    }

}
