package main.java.com.mtons.mblog.base.cache;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/6/13 上午12:42
 */
public interface ICacheService {
    /**
     * 服务限流的key
     *
     * TODO 命名需更改，加业务名
     */
    public static String FREQUENCE_BLOCK_UNIT_KEY = "frequence_block_unit";

}
