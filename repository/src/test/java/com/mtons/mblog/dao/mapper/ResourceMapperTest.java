//package com.mtons.mblog.dao.mapper;
//
//import com.mtons.mblog.dao.BaseDaoTest;
//import com.mtons.mblog.entity.bao.Resource;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
///**
// *
// */
//public class ResourceMapperTest extends BaseDaoTest {
//    @Autowired
//    ResourceMapper resourceMapper;
//
//    @Test
//    public void find0Before() {
//        LocalDateTime now = LocalDateTime.now();
//        String timeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now);
//        List<Resource> beforeResources = resourceMapper.find0Before(timeStr);
//        System.out.println(beforeResources);
//    }
//}