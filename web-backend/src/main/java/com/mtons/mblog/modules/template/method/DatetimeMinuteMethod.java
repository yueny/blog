package com.mtons.mblog.modules.template.method;

import com.mtons.mblog.modules.template.BaseMethod;
import freemarker.template.TemplateModelException;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 输出格式为 yyyy-MM-dd HH:mm
 *
 * <p>使用方式
 *
 * <pre>
 *     # 显示年月日时分
 *     ${datetimeMinute(row.created)}
 *
 *     # datetime: 时间和日期同时显示
 *     ${row.created?datetime} 或 ${row.created?datetime('yyyy-MM-dd hh:mm:ss')}
 *
 *     # date: 只显示日期，不显示时间.
 *     ${row.created?date} 或 ${row.created?date('yyyy-MM-dd')}
 *
 *     # time: 只显示时间，不显示日期
 *     ${row.created?time} 或${row.created?time('hh:mm:ss')}
 *
 *     Freemarker预置了一些日期格式
 *     ${createTime?string.short}  01:45 PM
 *     ${createTime?string.medium}  01:45:09 PM
 *     ${createTime?string.long}  01:45:09 PM PST
 *     ${createTime?string.full}  01:45:09 PM PST
 *     ${createTime?string.xs}  13:45:09-08:00
 *     ${createTime?string.iso}  13:45:09-08:00
 * </pre>
 */
public class DatetimeMinuteMethod extends BaseMethod {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Date time = getDate(arguments, 0);
        return format(time);
    }

    public static String format(Date date) {
        return (new DateTime(date)).toString("yyyy-MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE);
    }

}