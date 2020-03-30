package com.xfdmao.fcat.coin.service;

import com.xfdmao.fcat.coin.entity.ContractMatch;
import com.xfdmao.fcat.common.service.BaseService;

import java.util.List;

/**
 * Created by fier on 2018/09/20
 */
public interface ContractMatchService extends BaseService<ContractMatch>{
    void insertBatch(List<ContractMatch> resultList);
}
