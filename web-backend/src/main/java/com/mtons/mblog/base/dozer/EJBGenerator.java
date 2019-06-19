package com.mtons.mblog.base.dozer;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Lazy(true)
public class EJBGenerator implements IGenerator {
    @Autowired
    protected Mapper mapper;

    public <T, S> T convert(S s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        return this.mapper.map(s, clz);
    }

    public <T, S> List<T> convert(List<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        for (S vs : s) {
            list.add(this.mapper.map(vs, clz));
        }
        return list;
    }

    public <T, S> Set<T> convert(Set<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        Set<T> set = new HashSet<T>();
        for (S vs : s) {
            set.add(this.mapper.map(vs, clz));
        }
        return set;
    }

    public <T, S> T[] convert(S[] s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clz, s.length);
        for (int i = 0; i < s.length; i++) {
            arr[i] = this.mapper.map(s[i], clz);
        }
        return arr;
    }
}