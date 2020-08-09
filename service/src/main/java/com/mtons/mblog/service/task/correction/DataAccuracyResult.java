package com.mtons.mblog.service.task.correction;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataAccuracyResult extends AbstractMaskBo {
    /**
     * 新增数
     */
    private Long inserts;

    /**
     * 修改数
     */
    private Long updates;

    /**
     * 类型
     */
    private DataAccuracyCorrectionType condition;

    /**
     * 计划预处理总数
     */
    private Long sourceTotal;

}
