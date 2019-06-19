package main.java.com.mtons.mblog.modules.seq.strategy;

import main.java.com.mtons.mblog.modules.seq.SeqType;
import com.yueny.rapid.lang.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 33位
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/18 下午6:00
 *
 */
@Service
@Slf4j
public class UserUidSeqStrategy extends AbstractSeqStrategy {
    @Override
    public String get(String target) {
        StringBuilder sb = new StringBuilder("u");
        sb.append(UuidUtil.uuid());

        return sb.toString();
    }

    @Override
    public SeqType getCondition() {
        return SeqType.USER_U_ID;
    }
}
