package com.mtons.mblog.service.internal.impl;

import java.io.File;

/**
 * cmd 命令执行服务
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-10 19:06
 */
public interface IProcessCmd {

  /**
   * 执行系统命令, 返回执行结果。
   *
   * 如执行/root/experiment/test.sh脚本。
   * "test.sh", "/root/experiment/"
   *
   * @param cmd 需要执行的命令
   * @param dir 执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
   */
  String cmd(String cmd, File dir);

  /**
   * 执行系统命令, 返回执行结果。
   *
   * 如执行/root/experiment/test.sh脚本。
   * "test.sh", "/root/experiment/"
   *
   * @param cmd 需要执行的命令
   * @param dir 执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
   */
  String cmd(String cmd, String dir);
}
