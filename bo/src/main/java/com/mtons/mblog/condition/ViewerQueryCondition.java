package com.mtons.mblog.condition;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;

/**
 * 访客记录的查询条件
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-16 19:00
 */
@Getter
@Setter
public class ViewerQueryCondition extends AbstractMaskBo {
    /**
     * 客户端ip
     */
    private String clientIp;
    /**
     * 不包含的客户端ip列表，逗号分割
     */
    private String eclientIps;
    /**
     * 请求方式(get/post)
     */
    private String method;
    /**
     * 访问资源路径，全模糊查询
     */
    private String resourcePath;
    /**
     * 客户端 agent，全模糊查询
     */
    private String clientAgent;
    /**
     * 访问时间, 格式 yyyy-MM-dd
     */
    private String createDate;
}
