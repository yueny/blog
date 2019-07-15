package com.mtons.mblog.service.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * https://github.com/KOHGYLW/KCNamer
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/7/15 下午4:29
 */
public class KCNamerTest {
    @Test
    public void test(){
        String alias = KCNamer.getChineseWord(3);

        Assert.assertNotNull(alias);
    }
}
