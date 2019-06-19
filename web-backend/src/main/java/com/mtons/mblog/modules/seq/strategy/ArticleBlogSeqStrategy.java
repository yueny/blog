package com.mtons.mblog.modules.seq.strategy;

import com.mtons.mblog.modules.seq.SeqType;
import com.yueny.rapid.lang.util.ChinesUtil;
import com.yueny.rapid.lang.util.SecureRandomUtil;
import com.yueny.rapid.lang.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 12位
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/13 下午1:46
 *
 */
@Service
@Slf4j
public class ArticleBlogSeqStrategy extends AbstractSeqStrategy {
    private static final Integer SIZE = 12;

    @Override
    public String get(String target) {
        if(StringUtils.isEmpty(target)){
            return "b" + SecureRandomUtil.generateRandom(SIZE-1);
        }

        String articleBlogId = ChinesUtil.getPingYin(target, true, true);

        // 长了太难看，不如随机
        if(StringUtil.length(articleBlogId) == 0 ||
                StringUtil.length(articleBlogId) > 16){
            articleBlogId = SecureRandomUtil.generateRandom(SIZE-1);
        }
        return "b" + articleBlogId;
    }

    @Override
    public SeqType getCondition() {
        return SeqType.ARTICLE_BLOG_ID;
    }
}
