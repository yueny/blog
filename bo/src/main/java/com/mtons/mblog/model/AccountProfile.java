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
import com.yueny.rapid.lang.common.enums.YesNoType;
import com.yueny.superclub.api.pojo.IBo;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/14 下午5:18
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
///**
// * @Value等价于
// * final @ToString @EqualsAndHashCode
// * @AllArgsConstructor
// * @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
// * @Getter
// */
//@Value
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

    // StatusType.CLOSED.getValue()
    private int status;

    private String domainHack;

    private BadgesCount badgesCount;

    /**
     * 是否为超级管理员。 1 是， 0否
     */
    private YesNoType side;

    /**
     * 拥有的权限
     */
    // 这个注解和@Builder一起使用，为Builder生成字段是集合类型的add方法，字段名不能是单数形式，否则需要指定value值
    @Singular
    private Set<String> permissionNames;

    public AccountProfile(long id, String username) {
        this.id = id;
        this.username = username;
    }

}
