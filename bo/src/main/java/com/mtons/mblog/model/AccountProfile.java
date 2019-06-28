/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.model;

import com.mtons.mblog.bo.BadgesCount;
import com.yueny.superclub.api.pojo.IBo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/14 下午5:18
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountProfile implements IBo, Serializable {
    private static final long serialVersionUID = 1748764917028425871L;
    private long id;

    /**
     * 用户唯一标示
     */
    private String uid;

    private String username;
    private String avatar;
    private String name;
    private String email;

    private Date lastLogin;
    private int status;

    private String domainHack;

    private BadgesCount badgesCount;

    public AccountProfile(long id, String username) {
        this.id = id;
        this.username = username;
    }

}
