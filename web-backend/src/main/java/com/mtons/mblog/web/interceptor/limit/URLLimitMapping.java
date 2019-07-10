package com.mtons.mblog.web.interceptor.limit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-10 08:22
 */
@Getter
@Setter
@ToString
public class URLLimitMapping {
    private String[] urls;
    private int rate=0;

    public URLLimitMapping() {
    }

    public URLLimitMapping(String[] urls, int rate) {
        this.urls = urls;
        this.rate = rate;
    }

}
