package com.mtons.mblog.vo;

import com.mtons.mblog.bo.PermissionBO;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author - langhsu on 2018/2/11
 */
@Getter
@Setter
public class PermissionTreeVo extends PermissionBO {
    // 扩展
    private List<PermissionTreeVo> items;

    public void addItem(PermissionTreeVo item){
        if(this.items == null){
            this.items = new LinkedList<>();
        }
        this.items.add(item);
    }
}
