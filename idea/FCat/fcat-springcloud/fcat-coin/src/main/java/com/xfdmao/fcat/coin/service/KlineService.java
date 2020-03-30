package com.xfdmao.fcat.coin.service;

import com.xfdmao.fcat.coin.entity.Kline;
import com.xfdmao.fcat.common.service.BaseService;

import java.util.List;

/**
 * Created by fier on 2018/09/20
 */
public interface KlineService extends BaseService<Kline>{
    void insertBatch(List<Kline> klines);

    List<Kline> queryList(String symbol, String contractType, String period);
}
