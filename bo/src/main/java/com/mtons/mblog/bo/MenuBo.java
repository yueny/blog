package com.mtons.mblog.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.yueny.superclub.api.pojo.IBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 菜单实体
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-22 17:08
 */
@Getter
@Setter
public class MenuBo  extends BaseBo implements IBo {

    /** 表修改时间 */
    @JSONField(name="updated", serialize = true, format = "yyyy-MM-dd HH:mm:ss")
    private Date updated;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单链接地址
     */
    private String url;

    /**
     * 权限主键
     */
    private Long permissionId;

    /**
     * 父菜单主键
     */
    private Long parentId;
}
