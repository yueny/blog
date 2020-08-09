package com.mtons.mblog.modules.template.directive;

import com.mtons.mblog.vo.AccountProfile;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import com.yueny.rapid.lang.common.enums.YesNoType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * 自定义组件展示宏。权限控制的信息展示页面。
 * 因权限不同来决定是否可以看到该信息
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-28 15:10
 */
@Component
public class ShiroRuleOptsDirective extends TemplateDirective {
    @Override
    public String getName() {
        return "shiroRuleOpts";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        // 资源权限值
        String permission = handler.getString("permission");

        // 有权限，则 render()
        if (filter(SecurityUtils.getSubject(), permission)) {
            handler.render();
        } else {
            // 无权限， 则无任何操作
        }
    }

    /**
     * 权限过滤。
     * @param subject
     * @param expectPermission 期望的权限值
     *
     * @return true 表示拥有权限，可以展示; false表示没有权限， 不显示
     */
    private boolean filter(Subject subject, String expectPermission) {
        if(StringUtils.isEmpty(expectPermission)){
            return true;
        }

        // 非超级管理员需要进一步判断具体权限
        if (getProfile().getSide() != YesNoType.Y) {
            return check(getProfile(), expectPermission);
        }

        // 超级管理员拥有所有权限
        return true;
    }

    /**
     * @return true 表示拥有权限，可以展示; false表示没有权限， 不显示
     */
    private boolean check(AccountProfile accountProfile, String expectPermission) {
        boolean authorized = false;

        // 没有加permission， 则允许任何人访问
        if (StringUtils.isBlank(expectPermission)) {

        } else {
            // 权限列表为空
            if(CollectionUtils.isEmpty(accountProfile.getPermissionNames())){
                authorized = true;
            }else{
                // 权限列表不为空，但该权限expectPermission 不在拥有的权限列表中
                if(accountProfile.getPermissionNames().contains(expectPermission)){
                    authorized = true;
                }
            }
        }

        return authorized;
    }

}
