package com.xfdmao.fcat.user.service.impl;

import com.xfdmao.fcat.common.service.impl.BaseServiceImpl;
import com.xfdmao.fcat.user.entity.TUserLog;
import com.xfdmao.fcat.user.mapper.TUserLogMapper;
import com.xfdmao.fcat.user.service.TUserLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fier on 2017/11/28.
 */
@Service
public class TUserLogServiceImpl extends BaseServiceImpl<TUserLogMapper,TUserLog> implements TUserLogService {
    @Override
    public List<TUserLog> listByPageNewRecord() {
        return mapper.listByPageNewRecord();
    }
}
