package com.mtons.mblog.config;

import com.mtons.mblog.model.MenuVo;
import com.mtons.mblog.service.manager.IMenuRolePermissionManagerService;
import com.mtons.mblog.shiro.AccountRealm;
import com.mtons.mblog.shiro.AccountSubjectFactory;
import com.mtons.mblog.shiro.AuthenticatedFilter;
import com.mtons.mblog.shiro.ShiroRuleFuncNames;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
    @Autowired
    private IMenuRolePermissionManagerService menuRolePermissionManagerService;

    @Bean
    public SubjectFactory subjectFactory() {
        return new AccountSubjectFactory();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public Realm accountRealm() {
        return new AccountRealm();
    }

    /**
     * 用户授权信息Cache
     * */
    @Bean
    public CacheManager shiroCacheManager(net.sf.ehcache.CacheManager cacheManager) {
//        return new MemoryConstrainedCacheManager();
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

//    @Bean(name = "securityManager")
//    public DefaultSecurityManager securityManager(){
//        //这里注意，需要实现DefaultWebSecurityManager
//        DefaultSecurityManager sm = new DefaultWebSecurityManager();
//        sm.setCacheManager(cacheManager());
//        return sm;
//    }

    /**
     * Shiro的过滤器链
     */
    @Bean
//    @DependsOn("securityManager")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        // 登陆地址
        shiroFilter.setLoginUrl("/login");
        // 登录成功的路径
        shiroFilter.setSuccessUrl("/");
        // 权限不足
        shiroFilter.setUnauthorizedUrl("/error/reject.html");

        HashMap<String, Filter> filters = new HashMap<>();
        filters.put("authc", new AuthenticatedFilter());
        shiroFilter.setFilters(filters);

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap());
        return shiroFilter;
    }

    /**
     * 配置shiro拦截器链。
     * 过滤器链的执行顺序是自上而下依次匹配, 如果能匹配上, 则不再往下匹配.
     * 顺序从上到下,优先级依次降低
     *
     * <pre>
     *     身份验证相关的
     *     anon  不需要认证。匿名拦截器，即不需要登录即可访问；一般用于静态资源过滤；示例“/static/**=anon”
     *     authc 需要认证，基于表单的拦截器
     *     user  用户拦截器，用户已经身份验证/记住我登录，验证通过或RememberMe登录的都可以
     *
     *     授权相关的
     *     roles  角色授权拦截器，验证用户是否拥有所有角色； 示例“/admin/**=roles[admin]”
     *     perms  权限授权拦截器，验证用户是否拥有所有权限； 示例“/user/**=perms["user:create"]”
     * </pre>
     * <pre>
     *     注： xxx 和 mmm:nnn 均为 permission (资源权限值)
     *     authc,roles[xxx]
     *     authc,perms[mmm:nnn]
     * </pre>
     *
     * @return
     */
    private Map<String, String> filterChainDefinitionMap(){
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
        // 首页 AdminController  ok
        hashMap.put("/admin/reload_options", getAuthc(ShiroRuleFuncNames.adminCache));
        hashMap.put("/admin/reset_indexes", getAuthc(ShiroRuleFuncNames.adminCache));
        hashMap.put("/admin/reload_menu", getAuthc(ShiroRuleFuncNames.adminCache));

        // 栏目管理 ChannelController ok
        hashMap.put("/admin/channel/view.html", getAuthc(ShiroRuleFuncNames.channelList));
        hashMap.put("/admin/channel/view*", getAuthc(ShiroRuleFuncNames.channelList));
        hashMap.put("/admin/channel/tree/*", getAuthc(ShiroRuleFuncNames.channelUpdate));
        hashMap.put("/admin/channel/update.json", getAuthc(ShiroRuleFuncNames.channelUpdate));
        hashMap.put("/admin/channel/delete.json", getAuthc(ShiroRuleFuncNames.channelDelete));
        hashMap.put("/admin/channel/weight.json", getAuthc(ShiroRuleFuncNames.channelWeight));

        // 留言 CommentController ok
        hashMap.put("/admin/comment/delete", getAuthc(ShiroRuleFuncNames.commentDelete));

        // 系统配置 OptionsController ok
        hashMap.put("/admin/options/update", getAuthc(ShiroRuleFuncNames.optionsUpdate));

        // 博文管理 PostController ok
        hashMap.put("/admin/post/view", getAuthc(ShiroRuleFuncNames.postList));
        hashMap.put("/admin/post/update", getAuthc(ShiroRuleFuncNames.postUpdate));
        hashMap.put("/admin/post/delete", getAuthc(ShiroRuleFuncNames.postDelete));
        hashMap.put("/admin/post/featured", getAuthc(ShiroRuleFuncNames.postWeight));
        hashMap.put("/admin/post/weight", getAuthc(ShiroRuleFuncNames.postWeight));

        // 用户管理 UserController
        hashMap.put("/admin/user/view", "authc,perms[user:list]");
        hashMap.put("/admin/user/update_role", "authc,perms[user:role]");
        hashMap.put("/admin/user/pwd", "authc,perms[user:pwd]");
        hashMap.put("/admin/user/open", "authc,perms[user:open]");
        hashMap.put("/admin/user/close", "authc,perms[user:close]");

        // 追踪管理
        // 访问行为分析 ViewerAnalyzeController
        // 访问记录控制器 ViewerController
        hashMap.put("/admin/trace/viewer/get/list.json", "authc,perms[trace:viewer:index]");
        hashMap.put("/admin/trace/viewer/get.json", "authc,perms[trace:viewer:index]");
        hashMap.put("/admin/trace/viewer/delete.json", "authc,perms[trace:viewer:delete]");

        // 角色管理 RoleController
        hashMap.put("/admin/authority/role/list.json", "authc,perms[authority:role:list]");
        hashMap.put("/admin/authority/role/view", "authc,perms[authority:role:list]");
        hashMap.put("/admin/authority/role/update", "authc,perms[authority:role:update]");
        hashMap.put("/admin/authority/role/activate", "authc,perms[authority:role:update]");
        hashMap.put("/admin/authority/role/delete.json", "authc,perms[authority:role:delete]");

//        // 资源权限管理 PermissionController
        hashMap.put("/admin/authority/permission/list.json", "authc,perms[authority:permission:index]");
        hashMap.put("/admin/authority/permission/view", "authc,perms[authority:permission:index]");
        hashMap.put("/admin/authority/permission/update.json", "authc,perms[authority:permission:update]");
        hashMap.put("/admin/authority/permission/delete.json", "authc,perms[authority:permission:delete]");

        // 菜单管理 MenuController
        hashMap.put("/admin/authority/menu/list.json", "authc,perms[authority:menu:index]");
        hashMap.put("/admin/authority/menu/view", "authc,perms[authority:menu:index]");
        hashMap.put("/admin/authority/menu/update.json", "authc,perms[authority:menu:update]");
        hashMap.put("/admin/authority/menu/delete.json", "authc,perms[authority:menu:delete]");

        // 主题管理 ThemeController
        hashMap.put("/admin/theme/active", "authc,perms[theme:index]");
        hashMap.put("/admin/theme/upload", "authc,perms[theme:index]");


        /**
         * 从数据库 加载「菜单」的权限配置，存在重复则覆盖
         */
        List<MenuVo> list = menuRolePermissionManagerService.findAllForPermission();
        list.forEach(menuVo -> {
            if(menuVo.getPermission() != null){
                if(StringUtils.isEmpty(menuVo.getUrl())
                    || StringUtils.equals(menuVo.getUrl(), "/")){
                    return;
                }

                if(StringUtils.startsWith(menuVo.getUrl(), "/")){
                    hashMap.put(menuVo.getUrl(), "authc,perms[" + menuVo.getPermission().getName() + "]");
                }else{
                    hashMap.put("/" + menuVo.getUrl(), "authc,perms[" + menuVo.getPermission().getName() + "]");
                }
            }
        });
//        hashMap.put("/admin/channel/list.html", "authc,perms[channel:list]");
//        hashMap.put("/admin/comment/list", getAuthc(ShiroRuleFuncNames.commentList));
//        hashMap.put("/admin/options/index", "authc,perms[options:index]");
//        hashMap.put("/admin/post/list", "authc,perms[post:list]");
//        hashMap.put("/admin/user/list", "authc,perms[user:list]");
//        hashMap.put("/admin/trace/analyze/index.html", "authc,perms[trace:analyze:index]");
//        hashMap.put("/admin/trace/viewer/index.html", "authc,perms[trace:viewer:index]");
//        hashMap.put("/admin/authority/role/index.html", "authc,perms[authority:role:list]");
//        hashMap.put("/admin/authority/permission/index.html", "authc,perms[authority:permission:index]");
//        hashMap.put("/admin/authority/menu/index.html", "authc,perms[authority:menu:index]");
//        hashMap.put("/admin/theme/index", "authc,perms[theme:index]");
//        hashMap.put("/admin", getAuthc(ShiroRuleFuncNames.admin));

        /*
         * 功能的权限配置，在每个 Controller 控制器中通过注解完成
         */
        //.

        // 默认需要的权限
        hashMap.put("/admin/*", "authc,perms[admin]");

        return hashMap;
    }

    private String getAuthc(String shiroRuleFuncName){
        return "authc,perms[" + shiroRuleFuncName + "]";
    }
}
