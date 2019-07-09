package com.mtons.mblog.service.manager.impl;

import com.mtons.mblog.bo.ViewLogVO;
import com.mtons.mblog.entity.bao.ViewLogEntry;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.atom.bao.ViewLogService;
import com.mtons.mblog.service.manager.IViewLogManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author yueny09 <deep_blue_yang@163.com>
 * @Date 2019-07-05 17:33
 */
@Service
public class ViewLogManagerServiceImpl extends BaseService implements IViewLogManagerService {

    @Autowired
    private ViewLogService viewLogService;

    @Override
    public boolean record(ViewLogVO viewLog) {
        ViewLogEntry entry = map(viewLog, ViewLogEntry.class);
        return viewLogService.save(entry);
    }
}
