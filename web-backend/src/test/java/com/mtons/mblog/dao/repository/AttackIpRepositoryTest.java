package com.mtons.mblog.dao.repository;

import com.mtons.mblog.dao.BaseDaoTest;
import com.mtons.mblog.entity.jpa.AttackIpEntry;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
@Slf4j
public class AttackIpRepositoryTest  extends BaseDaoTest {
    @Autowired
    private AttackIpRepository attackIpRepository;

    @Test
    public void testSave() {
        AttackIpEntry entry = insert();

        AttackIpEntry ee = attackIpRepository.save(entry);
        Assert.assertNotNull(ee);
    }

//    /**
//     * 集成的接口测试
//     */
//    @Test
//    public void testSelectById() {
//        DemoEntry demoEntry = insert();
//
//        DemoEntry demo = demoMapper.selectById(demoEntry.getId());
//        Assert.assertNotNull(demo);
//        Assert.assertEquals(demo.getAssetCode(), demoEntry.getAssetCode());
//    }
//
//    /**
//     * 删除测试
//     */
//    @Test
//    public void testDeleteById() {
//        DemoEntry demoEntry = insert();
//
//        demoEntry = demoMapper.selectByOrderId(demoEntry.getOrderId());
//
//        int count = demoMapper.deleteById(demoEntry.getId());
//        Assert.assertTrue(count == 1);
//
//        demoEntry = demoMapper.selectByOrderId(demoEntry.getOrderId());
//        Assert.assertEquals(demoEntry.getDel().getVal(), DelEnum.DEL.getVal());
//    }

    private AttackIpEntry insert() {
        AttackIpEntry entry = new AttackIpEntry();
        entry.setClientIp("127.0.0.1");
        entry.setDenyTimeVal(5L);

//        Assert.assertEquals(1, demoMapper.insert(demoEntry));
//        Assert.assertTrue(demoEntry.getId() > 0);

        return entry;
    }

}
