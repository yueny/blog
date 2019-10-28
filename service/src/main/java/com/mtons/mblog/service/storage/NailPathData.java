package com.mtons.mblog.service.storage;

import com.mtons.mblog.base.enums.FileSizeType;
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
    @Builder.Default
    private String placeVal = "";

    /**
     * NailType 占位符之后追加的值
     */
    private String nailTypeAppend;

    /**
     * 上传的原始文件名
     */
    @Getter
    private String originalFilename;

    /**
     * 文件的大小,以字节为单位。
     */
    @Getter
    private Long size;
    /**
     * 文件大小单位,默认字节
     */
    @Getter
    private FileSizeType fileSizeType;

    /**
     * 获取路径值
     *
     * @return nailType + placeVal + nailTypeAppend
     */
    public String get(){
        String path = "";

        // 如果需要格式化，则拿着占位符数据进行格式化
        if(nailType.isFormat() && StringUtils.isNotEmpty(placeVal)){
            path = String.format(nailType.getNailPath(), placeVal);
        }else{
            path = nailType.getNailPath();
        }

        if(StringUtils.isNotEmpty(nailTypeAppend)){
            path = path + nailTypeAppend;
        }

        return path;
    }
}
