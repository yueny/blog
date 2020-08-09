package com.mtons.mblog.service.manager;

import com.mtons.mblog.vo.AccountProfile;

/**
 * AccountProfile 服务
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-26 10:02
 */
public interface IAccountProfileService {
    /**
     * 根据 userId 获取 AccountProfile 信息
     * @param userId
     * @return
     */
    AccountProfile find(Long userId);
}
