package com.mtons.mblog.modules.seq.strategy;

import com.mtons.mblog.modules.seq.SeqType;
import com.yueny.rapid.lang.util.SecureRandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 简单的序列数， 均未12位
 */
@Service
@Slf4j
public class SimpleSeqStrategy extends AbstractSeqStrategy {
    private static final Integer SIZE = 12;

    @Override
    public String get(String target) {
        return SecureRandomUtil.generateRandom(SIZE);
    }

    @Override
    public SeqType getCondition() {
        return SeqType.SIMPLE;
    }
}
