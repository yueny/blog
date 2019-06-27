package com.mtons.mblog.bo;

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
public class PermissionTree extends AbstractMaskBo {
    private long id;

    private long parentId;

    private String name;

    private String description;

    private int weight;

    private Integer version;

    // 扩展
    private List<PermissionTree> items;

    public List<PermissionTree> getItems() {
        return items;
    }

    public void setItems(List<PermissionTree> items) {
        this.items = items;
    }

    public void addItem(PermissionTree item){
        if(this.items == null){
            this.items = new LinkedList<>();
        }
        this.items.add(item);
    }
}
