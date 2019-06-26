package com.mtons.mblog.service.dozer;

import java.util.List;
import java.util.Set;

public interface IGenerator {
    /**
     * S对象转 T对象
     */
    <T, S> T convert(S s, Class<T> clz);

    /**
     * S列表转 T列表List
     */
    <T, S> List<T> convert(List<S> s, Class<T> clz);

    /**
     * S列表转 T列表Set
     */
    <T, S> Set<T> convert(Set<S> s, Class<T> clz);

    /**
     * S数组转 T数组
     */
    <T, S> T[] convert(S[] s, Class<T> clz);
}
