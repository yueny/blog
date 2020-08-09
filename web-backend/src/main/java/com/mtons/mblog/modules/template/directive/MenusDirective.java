package com.mtons.mblog.modules.template.directive;

import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.vo.menu.MenuTreeVo;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import com.mtons.mblog.service.manager.impl.MenuJsonService;
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
        List<MenuTreeVo> menus = filterMenu(SecurityUtils.getSubject());
        handler.put(RESULTS, menus).render();
    }

    private List<MenuTreeVo> filterMenu(Subject subject) {
        List<MenuTreeVo> menus = menuJsonService.getMenus();
        if (!subject.hasRole(Consts.ROLE_ADMIN)) {
            menus = check(subject, menus);
        }
        return menus;
    }

    private List<MenuTreeVo> check(Subject subject, List<MenuTreeVo> menus) {
        List<MenuTreeVo> results = new LinkedList<>();
        for (MenuTreeVo menu : menus) {
            if (check(subject, menu)) {
                results.add(menu);
            }
        }

        return results;
    }

    private boolean check(Subject subject, MenuTreeVo menu) {
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
