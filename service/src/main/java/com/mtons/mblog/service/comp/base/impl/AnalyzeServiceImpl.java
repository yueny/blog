package com.mtons.mblog.service.comp.base.impl;

import com.mtons.mblog.service.comp.base.IAnalyzeService;
import com.mtons.mblog.service.comp.configure.impl.ConfigureGetServiceImpl;
import com.mtons.mblog.service.manager.impl.MenuJsonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 分析服务
 *
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-08 13:19
 */
@Service
public class AnalyzeServiceImpl implements IAnalyzeService {
    @Autowired
    private MenuJsonService menuJsonService;

    @Override
    public boolean isAttackUrl(String resourcePath) {
        Set<String> menuList = new HashSet<>();
        // 添加首页
        menuList.add("/");
        menuJsonService.getMenus().forEach(mm->{
            if(StringUtils.isNotEmpty(mm.getUrl())){
                if(mm.getUrl().startsWith("/")){
                    menuList.add(mm.getUrl());
                }else{
                    menuList.add("/"+mm.getUrl());
                }
            }
        });

        if(menuList.contains(resourcePath)){
            // 菜单全路径匹配地址，安全
            return false;
        }

        // 攻击行为地址标签
        Set<String> attackUrl = ConfigureGetServiceImpl.getSecurityAttackUrl();

        for (String attack: attackUrl) {
            //  此处为地址包含关系
            if(resourcePath.contains(attack)){
                // 存在攻击迹象
                return true;
            }
        }

        // 安全
        return false;
    }
}
