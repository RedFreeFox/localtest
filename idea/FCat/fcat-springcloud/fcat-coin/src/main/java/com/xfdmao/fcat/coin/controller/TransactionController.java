package com.xfdmao.fcat.coin.controller;

import com.alibaba.fastjson.JSONObject;
import com.xfdmao.fcat.coin.constant.CoinConstant;
import com.xfdmao.fcat.coin.entity.Strategy;
import com.xfdmao.fcat.coin.entity.StrategyUser;
import com.xfdmao.fcat.coin.entity.Token;
import com.xfdmao.fcat.coin.huobi.util.StrategyUtil;
import com.xfdmao.fcat.coin.service.StrategyService;
import com.xfdmao.fcat.coin.service.StrategyUserService;
import com.xfdmao.fcat.coin.service.TokenService;
import com.xfdmao.fcat.common.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fier on 2018/10/31.
 */
@RestController
@RequestMapping("transaction")
public class TransactionController {
    private static Logger logger = Logger.getLogger(TransactionController.class);

    @Value("${spring.profiles.active}")
    public String active;

    /**
     * // 合约接口地址 "https://api.hbdm.com";//火币api接口地址https://api.hbdm.com;
     */
    @Value("${huobi.contract.url_prex}")
    public String url_prex;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private StrategyUserService strategyUserService;

    @Autowired
    private TokenService tokenService;

    @GetMapping(value = "/dealTwoMaStrategy")
    public JSONObject queryCissalcTwoMaStrategy(@RequestParam("strategyUserId") Long strategyUserId) {
        StrategyUser strategyUser = strategyUserService.selectById(strategyUserId);
        Strategy strategy = strategyService.selectById(strategyUser.getStrategyId());
        Token token = new Token();
        token.setUsername(strategyUser.getUsername());
        token = tokenService.selectOne(token);
        try {
            if ("EOS".equals(strategy.getSymbol())) {
                if ("buy".equals(strategy.getBuySell())) {
                    StrategyUtil.towMaEOS(url_prex,strategy, strategyUser, token);
                } else if ("sell".equals(strategy.getBuySell())) {
                    StrategyUtil.towMaEOSSell(url_prex,strategy, strategyUser, token);
                }
            } else if ("BTC".equals(strategy.getSymbol())) {
                if ("buy".equals(strategy.getBuySell())) {
                    StrategyUtil.towMaBTC(url_prex,strategy, strategyUser, token);
                }else if("sell".equals(strategy.getBuySell())){
                    StrategyUtil.towMaBTCSell(url_prex,strategy, strategyUser, token);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.getFailJsonObject(e.getMessage());
        }
        return JsonUtil.getSuccessJsonObject();
    }

    public void towAvg() {
        if (!"transaction".equals(active)) return;
        while (true) {
            logger.debug("\n\n\n");
            try {
                List<Strategy> strategyList = strategyService.selectListAll();
                List<StrategyUser> strategyUsers = strategyUserService.selectListAll();
                List<Token> tokens = tokenService.selectListAll();
                for (Token token : tokens) {
                    if (token.getTestEnable()) continue;//过滤测试账号
                    logger.debug("///////////////////////" + token.getUsername() + " start///////////////////////");
                    for (StrategyUser strategyUser:strategyUsers){
                        if (!strategyUser.getUsername().equals(token.getUsername()))continue;
                        if(!strategyUser.getEnable())continue;
                        for(Strategy strategy:strategyList){
                            if(!strategyUser.getStrategyId().equals(strategy.getId()))continue;
                            logger.debug("\n");
                            if ("EOS".equals(strategy.getSymbol())) {
                                if ("buy".equals(strategy.getBuySell())) {
                                    StrategyUtil.towMaEOS(url_prex,strategy, strategyUser, token);
                                } else if ("sell".equals(strategy.getBuySell())) {
                                    StrategyUtil.towMaEOSSell(url_prex,strategy, strategyUser, token);
                                }
                            } else if ("BTC".equals(strategy.getSymbol())) {
                                if ("buy".equals(strategy.getBuySell())) {
                                    StrategyUtil.towMaBTC(url_prex,strategy, strategyUser, token);
                                }else if("sell".equals(strategy.getBuySell())){
                                    StrategyUtil.towMaBTCSell(url_prex,strategy, strategyUser, token);
                                }
                            }
                        }
                    }
                    logger.debug("|||||||||||||||||||||| = " + token.getUsername() + " end = ||||||||||||||||||||||\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}