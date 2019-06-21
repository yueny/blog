package com.mtons.mblog.service.seq.container;

import com.mtons.mblog.service.seq.ISeqStrategy;
import com.mtons.mblog.service.seq.SeqType;

/**
 * 策略容器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/5/17 下午4:45
 */
public interface ISeqContainer {
    /**
     * 获取策略实现
     */
    ISeqStrategy getStrategy(SeqType type);

}
