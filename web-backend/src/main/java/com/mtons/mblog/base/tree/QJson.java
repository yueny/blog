package com.mtons.mblog.base.tree;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class QJson {
    private boolean success = true;
    private String msg;// 消息
    private String type;// 类型
    private Object object;// 对象

}
