package com.mtons.mblog.config;

import com.mtons.mblog.shiro.AccountRealm;
import com.mtons.mblog.shiro.AccountSubjectFactory;
import com.mtons.mblog.shiro.AuthenticatedFilter;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro权限管理的配置
 *
 * @author langhsu
 * @since 3.0
 */
@Configuration
@ConditionalOnProperty(name = "shiro.web.enabled", matchIfMissing = true)
public class ShiroConfiguration {
    @Bean
    public SubjectFactory subjectFactory() {
        return new AccountSubjectFactory();
    }

    @Bean
    public Realm accountRealm() {
        return new AccountRealm();
    }

    @Bean
    public CacheManager shiroCacheManager(net.sf.ehcache.CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    /**
     * Shiro的过滤器链
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/");
        shiroFilter.setUnauthorizedUrl("/error/reject.html");

        HashMap<String, Filter> filters = new HashMap<>();
        filters.put("authc", new AuthenticatedFilter());
        shiroFilter.setFilters(filters);

        /**
         * 配置shiro拦截器链。 后续应该从数据库读取 TODO
         *
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         *
         * 顺序从上到下,优先级依次降低
         *
         */
        Map<String, String> hashMap = new LinkedHashMap<>();
        // 静态资源
        hashMap.put("/dist/**", "anon");
        hashMap.put("/theme/**", "anon");
        hashMap.put("/storage/**", "anon");
        hashMap.put("/login", "anon");

        /** 前台 */
        /* https://host/users/.. 路径属于前端展示, 不拦截  */
        hashMap.put("/user/**", "authc");
        hashMap.put("/settings/**", "authc");
        hashMap.put("/post/editing", "authc");
        hashMap.put("/post/submit", "authc");
        hashMap.put("/post/delete/*", "authc");
        hashMap.put("/post/upload", "authc");

        // ApiController
        // CallbackController
        // EmailController
        // ForgotController
        // LoginController
        // RegisterController
        // LogoutController
        // CommentController
        // PostController
        // UploadController
        // FavorController
        // SettingsController
        // UsersController
        // ArticleBlogDetailShowActionController
        // ChannelController
        // IndexController
        // SearchController
        // TagController


        /** 后台：页面权限 */
        // 首页 AdminController

        // 栏目管理 ChannelController
        hashMap.put("/admin/channel/list.html", "authc,perms[channel:list]");
        hashMap.put("/admin/channel/view*", "authc,perms[channel:list]");
        hashMap.put("/admin/channel/tree/add*", "authc,perms[channel:update]");
        hashMap.put("/admin/channel/tree/query.json", "authc,perms[channel:list]");
        hashMap.put("/admin/channel/update.json", "authc,perms[channel:update]");
        hashMap.put("/admin/channel/weight.json", "authc,perms[channel:update]");
        hashMap.put("/admin/channel/delete.json", "authc,perms[channel:delete]");

        // 留言 CommentController
        hashMap.put("/admin/comment/list", "authc,perms[comment:list]");
        hashMap.put("/admin/comment/delete", "authc,perms[comment:delete]");

        // 系统配置 OptionsController
        hashMap.put("/admin/options/index", "authc,perms[options:index]");
        hashMap.put("/admin/options/update", "authc,perms[options:update]");
        hashMap.put("/admin/options/reload_options", "authc,perms[options:update]");
        hashMap.put("/admin/options/reset_indexes", "authc,perms[options:update]");
        hashMap.put("/admin/options/reload_menu", "authc,perms[options:update]");

        // 博文管理 PostController
        hashMap.put("/admin/post/list", "authc,perms[post:list]");
        hashMap.put("/admin/post/view", "authc,perms[post:list]");
        hashMap.put("/admin/post/update", "authc,perms[post:update]");
        hashMap.put("/admin/post/featured", "authc,perms[post:update]");
        hashMap.put("/admin/post/weight", "authc,perms[post:update]");
        hashMap.put("/admin/post/delete", "authc,perms[post:delete]");

        // 用户管理 UserController
        hashMap.put("/admin/user/list", "authc,perms[user:list]");
        hashMap.put("/admin/user/view", "authc,perms[user:list]");
        hashMap.put("/admin/user/update_role", "authc,perms[user:role]");
        hashMap.put("/admin/user/pwd", "authc,perms[user:pwd]");
        hashMap.put("/admin/user/open", "authc,perms[user:open]");
        hashMap.put("/admin/user/close", "authc,perms[user:close]");

        // 追踪管理
        // 访问行为分析 ViewerAnalyzeController
        hashMap.put("/admin/trace/analyze/index.html", "authc,perms[trace:analyze:index]");
        // 访问记录控制器 ViewerController
        hashMap.put("/admin/trace/viewer/index.html", "authc,perms[trace:viewer:index]");
        hashMap.put("/admin/trace/viewer/get/list.json", "authc,perms[trace:viewer:index]");
        hashMap.put("/admin/trace/viewer/get.json", "authc,perms[trace:viewer:index]");
        hashMap.put("/admin/trace/viewer/delete.json", "authc,perms[trace:viewer:delete]");

        // 角色管理 RoleController
        hashMap.put("/admin/authority/role/list", "authc,perms[authority:role:list]");
        hashMap.put("/admin/authority/role/view", "authc,perms[authority:role:list]");
        hashMap.put("/admin/authority/role/update", "authc,perms[authority:role:update]");
        hashMap.put("/admin/authority/role/activate", "authc,perms[authority:role:update]");
        hashMap.put("/admin/authority/role/delete", "authc,perms[authority:role:delete]");

//        // 资源权限管理 PermissionController
//        hashMap.put("/admin/authority/permission/index.html", "authc,perms[authority:permission:index]");
//        hashMap.put("/admin/authority/permission/list.json", "authc,perms[authority:permission:index]");
//        hashMap.put("/admin/authority/permission/view", "authc,perms[authority:permission:index]");
//        hashMap.put("/admin/authority/permission/update.json", "authc,perms[authority:permission:update]");
//        hashMap.put("/admin/authority/permission/delete.json", "authc,perms[authority:permission:delete]");
//
//        // 菜单管理 MenuController
//        hashMap.put("/admin/authority/menu/index.html", "authc,perms[authority:menu:index]");
//        hashMap.put("/admin/authority/menu/list.json", "authc,perms[authority:menu:index]");
//        hashMap.put("/admin/authority/menu/view", "authc,perms[authority:menu:index]");
//        hashMap.put("/admin/authority/menu/update.json", "authc,perms[authority:menu:update]");
//        hashMap.put("/admin/authority/menu/delete.json", "authc,perms[authority:menu:delete]");

        // 主题管理 ThemeController
        hashMap.put("/admin/theme/*", "authc,perms[theme:index]");

        // 后台首页
        hashMap.put("/admin", "authc,perms[admin]");

        hashMap.put("/admin/*", "authc,perms[admin]");

        shiroFilter.setFilterChainDefinitionMap(hashMap);
        return shiroFilter;
    }

}
