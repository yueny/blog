package main.java.com.mtons.mblog.modules.seq;

import com.yueny.superclub.util.strategy.IStrategy;

/**
 * 序列号
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/13 下午1:44
 *
 */
public interface ISeqStrategy extends IStrategy<SeqType> {
    /**
     * 序列号获取
     *
     * @param target 参考数据
     */
    String get(String target);

}
