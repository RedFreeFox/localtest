package com.xfdmao.fcat.user.service;

import com.xfdmao.fcat.common.service.BaseService;
import com.xfdmao.fcat.user.entity.TUserLog;

import java.util.List;

/**
 * Created by fier on 2017/11/28.
 */
public interface TUserLogService extends BaseService<TUserLog>{
    List<TUserLog> listByPageNewRecord();
}
