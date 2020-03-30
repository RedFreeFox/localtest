package com.xfdmao.fcat.user.mapper;

import com.xfdmao.fcat.user.entity.TUserLog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TUserLogMapper extends Mapper<TUserLog> {
    List<TUserLog> listByPageNewRecord();
}