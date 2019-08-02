package com.mtons.mblog.web.menu;

import com.mtons.mblog.model.menu.Menu;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.entity.jpa.Role;
import com.mtons.mblog.modules.template.TemplateDirective;
import com.mtons.mblog.service.comp.score.MenuJsonService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * 自定义菜单宏
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/20 下午5:33
 *
 */
@Component
public class MenusDirective extends TemplateDirective {
    @Autowired
    private MenuJsonService menuJsonService;

    @Override
    public String getName() {
        return "menus";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        List<Menu> menus = filterMenu(SecurityUtils.getSubject());
        handler.put(RESULTS, menus).render();
    }

    private List<Menu> filterMenu(Subject subject) {
        List<Menu> menus = menuJsonService.getMenus();
        if (!subject.hasRole(Role.ROLE_ADMIN)) {
            menus = check(subject, menus);
        }
        return menus;
    }

    private List<Menu> check(Subject subject, List<Menu> menus) {
        List<Menu> results = new LinkedList<>();
        for (Menu menu : menus) {
            if (check(subject, menu)) {
                results.add(menu);
            }
        }

        return results;
    }

    private boolean check(Subject subject, Menu menu) {
        boolean authorized = false;
        // 菜单项没有加permission， 则允许任何人访问
        if (StringUtils.isBlank(menu.getPermission())) {
            authorized = true;
        } else {
            // 多个权限值,逗号分隔
            for(String perm : menu.getPermission().split(",")){
                if(subject.isPermitted(perm)){
                    authorized = true;
                    break;
                }
            }
        }
        return authorized;
    }

}
