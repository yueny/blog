package com.mtons.mblog.com.mtons.mblog.modules.comp;

import com.mtons.mblog.BootApplication;
import com.mtons.mblog.service.internal.IPasswdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-06-26 12:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class PasswdServiceTest {
    @Autowired
    private IPasswdService passwdService;

    @Test
    public void testEncode() {
        String pw = "123456";
        String salt = "";
        String mima = passwdService.encode(pw, salt);

        System.out.println(salt);
        System.out.println(pw);
        System.out.println(mima);
    }
}
