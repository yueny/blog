package com.mtons.mblog.base.storage;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * 图片上传的配置信息
 */
@Builder
@ToString
public class NailPathData {
    /**
     * StorageConsts 里面的路径值
     */
    @Getter
    private NailType nailType;

    /**
     * nailType 的占位符的值， 可以为空
     */
    private String placeVal;

    /**
     * NailType 占位符之后追加的值
     */
    private String nailTypeAppend;

    /**
     * @return nailType + placeVal + nailTypeAppend
     */
    public String get(){
        if(StringUtils.isEmpty(placeVal)){
            if(StringUtils.isEmpty(nailTypeAppend)){
                return nailType.getNailPath();
            }else{
                return nailType.getNailPath() + nailTypeAppend;
            }
        }

        if(nailType.isFormat()){
            if(StringUtils.isNotEmpty(nailTypeAppend)){
                return String.format(nailType.getNailPath(), placeVal) + nailTypeAppend;
            }else{
                return String.format(nailType.getNailPath(), placeVal);
            }
        }

        if(StringUtils.isNotEmpty(nailTypeAppend)){
            return nailType.getNailPath() + nailTypeAppend;
        }else{
            return nailType.getNailPath();
        }
    }
}
