package com.xfdmao.fcat.coin.service.impl;

import com.xfdmao.fcat.coin.entity.Kline;
import com.xfdmao.fcat.coin.mapper.KlineMapper;
import com.xfdmao.fcat.coin.service.KlineService;
import com.xfdmao.fcat.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by fier on 2018/09/20
 */
@Service
public class KlineServiceImpl extends BaseServiceImpl<KlineMapper,Kline> implements KlineService {
    @Override
    public void insertBatch(List<Kline> klines) {
        mapper.insertBatch(klines);
    }

    @Override
    public List<Kline> queryList(String symbol, String contractType, String period) {
        Map<String,Object> map = new HashMap<>();
        map.put("symbol",symbol);
        map.put("contractType",contractType);
        map.put("period",period);
        return mapper.equalsListBySymbol(map);
    }
}
