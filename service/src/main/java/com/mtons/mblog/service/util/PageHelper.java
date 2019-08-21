package com.mtons.mblog.service.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yueny.superclub.api.page.PageList;
import com.yueny.superclub.api.page.core.PageCond;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * 分液转换帮助类
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-19 19:50
 */
public class PageHelper {
    /**
     * 组装springframework分页条件
     *
     * @param pageNo 当前分页
     * @param pageSize 分页查询的每页查询条数
     * @param sort 排序对象
     */
    public static <T> PageRequest wrapPageableForSpring(int pageNo, int pageSize, Sort sort) {
        if (null == sort) {
            sort = Sort.unsorted();
        }

        // springframework PageRequest的页数从0开始， 但传入参数和默认分页均为1， 需要-1
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }

    /**
     * 组装springframework分页条件
     *
     * @param pageNo 当前分页
     * @param pageSize 分页查询的每页查询条数
     */
    public static <T> PageRequest wrapPageableForSpring(Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo == 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        // springframework PageRequest的页数从0开始， 但传入参数和默认分页均为1， 需要-1
        return PageRequest.of(pageNo - 1, pageSize);
    }

    /**
     * 分页对象的转换， 含分组条件
     * org.springframework.data.domain.Page 转为 com.baomidou.mybatisplus.core.metadata.IPage.<br>
     *
     * springframework分页从0开始，mybatisplus中从1开始
     *
     * @param pageableData springframework分页查询条件
     * @return mybatisplus 分页结果对象
     */
    public static <S> com.baomidou.mybatisplus.extension.plugins.pagination.Page<S> fromSpringToPlusPage(Pageable pageableData) {
        // 因为 springframework PageRequest的页数从0开始，但mybatisplus中从1开始，因为需要+1
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<S> pageablePlus = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(
                pageableData.getPageNumber()+1, pageableData.getPageSize());

        if(pageableData.getSort() != null){
            Set<String> ascs = new HashSet<>();
            Set<String> descss = new HashSet<>();
            pageableData.getSort().get().forEach(order->{
                if(order.isAscending()){
                    ascs.add(order.getProperty());
                }else{
                    descss.add(order.getProperty());
                }
            });
            if(!ascs.isEmpty()){
//			    String[] arrays = ascs.stream().toArray(String[]::new);
                pageablePlus.setAsc(ascs.stream().toArray(String[]::new));
            }
            if(!descss.isEmpty()){
                pageablePlus.setDesc(descss.stream().toArray(String[]::new));
            }
        }

        return pageablePlus;
    }

    /**
     * 查询分页对象结果的转换， 含分组条件
     * com.baomidou.mybatisplus.core.metadata.IPage 转为 org.springframework.data.domain.Page.<br>
     *
     * springframework分页从0开始，mybatisplus中从1开始
     *
     * @param pagePlus mybatisplus 查询到的分页结果对象
     * @return org.springframework.data.domain.Page springframework 分页查询条件
     */
    public static <S> Page<S> fromPlusToSpringPage(IPage<S> pagePlus) {
        /**
         * SQL 排序 ASC 数组
         */
        String[] ascs = pagePlus.ascs();
        /**
         * SQL 排序 DESC 数组
         */
        String[] descs = pagePlus.descs();
        List<Sort.Order> orders = new ArrayList<>();
        if(descs != null){
            for(int i =0; i<descs.length; i++){
                orders.add(new Sort.Order(Sort.Direction.DESC, descs[i]));
            }
        }
        if(ascs != null){
            for(int i =0; i<ascs.length; i++){
                orders.add(new Sort.Order(Sort.Direction.ASC, ascs[i]));
            }
        }

        Pageable pageableData = null;
        int size = new Long(pagePlus.getSize()).intValue();
        if(CollectionUtils.isNotEmpty(orders)){
            Sort sort = Sort.by(orders);
            pageableData = PageRequest.of(new Long(pagePlus.getCurrent() - 1).intValue(), size, sort);
        }else{
            pageableData = PageRequest.of(new Long(pagePlus.getCurrent() - 1).intValue(), size);
        }

        if(CollectionUtils.isEmpty(pagePlus.getRecords())){
            return new PageImpl(Collections.emptyList(), pageableData, pagePlus.getTotal());
        }

        return new PageImpl<S>(pagePlus.getRecords(), pageableData, pagePlus.getTotal());
    }

    /**
     * 分页对象的转换，不含分组条件
     * org.springframework.data.domain.Page 转为 com.yueny.superclub.api.page.PageList.<br>
     *
     * springframework分页从0开始，yueny.superclub.api 的 currentPage 中从1开始
     *
     * @param pageData springframework分页查询条件
     * @return yueny.superclub.api 分页结果对象
     */
    public static <S> PageList<S> fromSpringToYuenyPage(org.springframework.data.domain.Page<S> pageData) {
        PageList<S> pageList = PageList.<S>builder()
                .list(pageData.getContent())
                .paginator(new PageCond(pageData.getNumber(),
                        pageData.getSize(),
                        pageData.getTotalElements()))
                .build();

        return pageList;
    }
}
