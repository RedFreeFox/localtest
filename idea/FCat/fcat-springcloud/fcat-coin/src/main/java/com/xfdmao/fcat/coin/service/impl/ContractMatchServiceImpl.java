package com.xfdmao.fcat.coin.service.impl;

import com.xfdmao.fcat.coin.entity.ContractMatch;
import com.xfdmao.fcat.coin.mapper.ContractMatchMapper;
import com.xfdmao.fcat.coin.service.ContractMatchService;
import com.xfdmao.fcat.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fier on 2018/09/20
 */
@Service
public class ContractMatchServiceImpl extends BaseServiceImpl<ContractMatchMapper,ContractMatch> implements ContractMatchService {
    @Override
    public void insertBatch(List<ContractMatch> resultList) {
        mapper.insertBatch(resultList);
    }
}
