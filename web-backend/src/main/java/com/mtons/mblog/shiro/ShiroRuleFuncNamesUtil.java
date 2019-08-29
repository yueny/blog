package com.mtons.mblog.shiro;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-28 17:45
 */
@Component
public class ShiroRuleFuncNamesUtil {
    private static Map<String, Object> shiroRuleMap;

    public static Map<String, Object> getShiroRuleMap(){
        if(shiroRuleMap == null){
            synchronized (ShiroRuleFuncNamesUtil.class){
                if(shiroRuleMap == null){
                    Map<String, Object>  maps = new HashMap<>();

                    try{
                        // getDeclaredFields()返回Class中所有的字段，包括私有字段，而getFields()只返回公有字段，即有public修饰的字段。
                        Field[] fields = ShiroRuleFuncNames.class.getFields();
                        for (Field field: fields) {
                            maps.put(field.getName(), field.get(ShiroRuleFuncNames.class));
                        }

                        shiroRuleMap = Collections.unmodifiableMap(maps);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        return shiroRuleMap;
    }

}
