package com.mtons.mblog.model;

import com.mtons.mblog.bo.PermissionBO;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
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
