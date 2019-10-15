package com.mtons.mblog.service.manager;

import com.mtons.mblog.service.exception.BizException;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/**
 * 图片管理服务
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-11 16:17
 */
public interface IImageManagerService {
    /**
     * 删除图片资源
     *
     * @param thumbnailCode 图片资源编号
     * @param md5 md5唯一值
     * @return true/false
     */
    boolean delete(String thumbnailCode, String md5) throws BizException;

    /**
     * 删除图片资源
     *
     * @param thumbnailCode 图片资源编号列表
     * @return true/false
     */
    boolean delete(Set<Flag> thumbnailCode) throws BizException;

    @Builder
    class Flag{
        /**
         * 图片资源编号
         */
        @Getter
        private String thumbnailCode;

        /**
         * md5唯一值
         */
        @Getter
        private String md5;
    }
}
