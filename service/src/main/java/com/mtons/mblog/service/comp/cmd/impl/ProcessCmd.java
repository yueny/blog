package com.mtons.mblog.service.comp.cmd.impl;

import com.mtons.mblog.service.comp.cmd.IProcessCmd;
import com.yueny.rapid.lang.util.burrow.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-10 19:06
 */
@Service
@Slf4j
public class ProcessCmd implements IProcessCmd {
    @Override
    public String cmd(String cmd, File dir) {
        StringBuilder result = new StringBuilder();

        String[] commond = {"/bin/sh", "-c", cmd};
        System.out.println(commond.toString());

        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;
        try {
            // 在指定环境和工作目录的独立进程中执行指定的命令和变量, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(commond, null, dir);

            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

            //必须等待该进程结束，否则时间设置就无法生效.
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            process.waitFor(100, TimeUnit.SECONDS);

            // 读取输出
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
//                log.info(line);
                result.append(line).append('\n');
            }
            while ((line = bufrError.readLine()) != null) {
                result.append(line).append('\n');
            }
        } catch (Exception error) {
            error.printStackTrace();
        }finally{
            IoUtil.close(bufrIn);
            IoUtil.close(bufrError);

            if(process!=null){
                process.destroy();
            }
        }

        // 返回执行结果
        return result.toString();
    }

    @Override
    public String cmd(String cmd, String dir) {
        File dirs = null;
        if(StringUtils.isNotEmpty(dir)){
            dirs = new File(dir);
        }

        return cmd(cmd, dirs);
    }

}
