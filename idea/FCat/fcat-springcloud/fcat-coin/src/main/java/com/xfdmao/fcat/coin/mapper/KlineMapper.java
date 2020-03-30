package com.xfdmao.fcat.coin.mapper;

import com.xfdmao.fcat.coin.entity.Kline;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface KlineMapper extends Mapper<Kline> {
    void insertBatch(List<Kline> klines);

    List<Kline> equalsListBySymbol(Map<String, Object> map);
}