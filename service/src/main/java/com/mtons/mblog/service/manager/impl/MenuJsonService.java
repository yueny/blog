package com.mtons.mblog.service.manager.impl;

import com.alibaba.fastjson.JSONArray;
import com.mtons.mblog.vo.menu.MenuTreeVo;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.manager.IMenuJsonService;
import com.mtons.mblog.service.manager.IMenuRolePermissionManagerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 加载后台Menu菜单
 *
 * @author - langhsu
 * @create - 2018/5/18
 */
@Service
public class MenuJsonService extends BaseService implements IMenuJsonService {
    @Autowired
    private IMenuRolePermissionManagerService menuRolePermissionManagerService;

    // 此处从 json文件加载，也可以改为从数据库加载
    private static String config = "/scripts/menu.json";
    private List<MenuTreeVo> menus;

    /**
     * 将配置文件转换成 List 对象
     *
     * @return
     */
    private synchronized List<MenuTreeVo> loadJson() throws IOException {
        InputStream inStream = MenuJsonService.class.getResourceAsStream(config);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, Charset.forName("UTF-8")));

        StringBuilder json = new StringBuilder();
        String tmp;
        try {
            while ((tmp = reader.readLine()) != null) {
                json.append(tmp);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            reader.close();
            inStream.close();
        }

        List<MenuTreeVo> menus = JSONArray.parseArray(json.toString(), MenuTreeVo.class);
        return menus;
    }

    @Override
    public List<MenuTreeVo> getMenus() {
        if (null == menus) {
            synchronized (this){
                if (null == menus) {
                    reload();
                }
            }
        }
        return menus;
    }

    @Override
    public boolean reload() {
        try {
            List<MenuTreeVo> menuTreeVos = menuRolePermissionManagerService.findAllMenuForTree();

            if(CollectionUtils.isEmpty(menuTreeVos)){
                menuTreeVos = loadJson();
            }

            menus = menuTreeVos;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
