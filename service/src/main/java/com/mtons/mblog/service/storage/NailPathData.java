package com.mtons.mblog.service.storage;

import com.mtons.mblog.base.enums.FileSizeType;
import com.yueny.rapid.lang.date.DateFormatUtil;
import com.yueny.rapid.lang.date.DateUtil;
import com.yueny.scanner.consts.Consts;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 图片上传的配置信息， 以用户、附件类型
 */
@Builder
@ToString
public class NailPathData {
    /**
     * 附件类型， NailType， 如  "/storage/uid/blognails"
     */
    @Getter
    @NonNull
    private NailType nailType;

    /**
     * uid, 如  "uid"
     */
    @Builder.Default
    @NonNull
    private String uid = "";


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
     * @return nailType + uid + 年月
     */
    public String get(){
        StringBuffer path = new StringBuffer();

        // 如果需要格式化，则拿着占位符数据进行格式化
        if(nailType.isFormat()){
            path.append(String.format(nailType.getNailPath(), uid));
        }else{
            path.append(nailType.getNailPath());
        }

        if(!StringUtils.endsWith(path, Consts.PATH_SEPARATOR)){
            path.append(Consts.PATH_SEPARATOR);
        }
        path.append(DateFormatUtil.formatMonth(new Date()));

        return path.toString();
    }
}
