package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/5/13 下午2:42
 *
 */
@Entity
@Table(name = "view_log",
        indexes = {
        @Index(name = "IK_CLIENT_IP", columnList = "client_ip")
})
@TableName("view_log")
@Getter
@Setter
@ToString
public class ViewLogEntry extends AbstractUpdatePlusEntry {
    /**
     * 客户端ip
     */
    @Column(name = "client_ip")
    private String clientIp;
    /**
     * 客户端 agent
     */
    @Column(name = "client_agent")
    private String clientAgent;

    /**
     * 请求方式(get/post)
     */
    @Column(name = "method")
    private String method;

    /**
     * 请求参数， json串
     */
    @Column(name = "parameter_json")
    private String parameterJson;

    /**
     * 访问资源地址
     */
    @Column(name = "resource_path")
    private String resourcePath;
    /**
     * 访问资源路径描述
     */
    @Column(name = "resource_path_desc")
    private String resourcePathDesc;

}
