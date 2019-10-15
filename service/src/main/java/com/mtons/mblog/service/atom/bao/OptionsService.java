package com.mtons.mblog.service.atom.bao;

import com.mtons.mblog.bo.OptionsBo;
import com.mtons.mblog.entity.bao.Options;
import com.mtons.mblog.service.api.bao.IPlusBizService;

import java.util.Map;

/**
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-10-11 10:37
 */
public interface OptionsService extends IPlusBizService<OptionsBo, Options> {

    /**
     * 根据key获得信息
     * @param key 查询key
     * @return 不存在则返回为null
     */
    OptionsBo findByKey(String key);

    /**
     * 添加或修改配置
     * - 修改时根据key判断唯一性
     * @param options
     */
    void update(Map<String, String> options);
}
