package com.xfdmao.fcat.coin.mapper;

import com.xfdmao.fcat.coin.entity.ContractMatch;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ContractMatchMapper extends Mapper<ContractMatch> {
    void insertBatch(List<ContractMatch> resultList);
}