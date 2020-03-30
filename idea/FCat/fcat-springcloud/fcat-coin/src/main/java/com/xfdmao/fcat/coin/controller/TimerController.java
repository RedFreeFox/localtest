package com.xfdmao.fcat.coin.controller;

import org.apache.http.HttpException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by fier on 2018/10/22
 */
@RestController
public class TimerController {
    private static Logger logger = Logger.getLogger(TimerController.class);

    @Autowired
    private KlineController klineController;

    @Autowired
    private ContractMatchController contractMatchController;


    @Value("${spring.profiles.active}")
    private String active;

/*
    *//**
     * 测试交易环境
     * @throws IOException
     * @throws HttpException
     *//*
    @Scheduled(cron = "0 0/1 * * * ?")
    public void stratege1Test() throws IOException, HttpException {
        if(!"dev143".equals(active))return;
        logger.debug("\n\n\n");
        List<StrategyParam> strategyParamList = strategyParamService.selectListAll();
        List<Token> tokens = tokenService.selectListAll();
        try{
            for(Token token:tokens){
                if(!token.getTestEnable())continue;//过滤非测试账号
                logger.debug("===================="+token.getUsername()+" start====================");
                for(StrategyParam strategyParam:strategyParamList){
                    if(strategyParam.getUsername().equals(token.getUsername())){
                        if("EOS".equals(strategyParam.getSymbol())){
                            if("buy".equals(strategyParam.getBuySell())){
                                StrategyUtil.towMaEOS(strategyParam,token);
                            }else if("sell".equals(strategyParam.getBuySell())){
                                StrategyUtil.towMaEOSSell(strategyParam,token);
                            }
                        }else if("BTC".equals(strategyParam.getSymbol())){
                            if("buy".equals(strategyParam.getBuySell())){
                                StrategyUtil.towMaBTC(strategyParam,token);
                            }
                        }
                    }
                }
                logger.debug("|||||||||||||||||||||| = "+token.getUsername()+" end = ||||||||||||||||||||||");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/


    /**
     * 1天执行一次
     */
/*    @Scheduled(cron = "0 0 0 0/1 * ?")
    public void getBitinfocharts(){
        klineController.saveTwoMaStrategyBest();
    }*/

/*    @Autowired
    private BtcHourController btcHourController;

    @Autowired
    private HttpController httpController;
    @Scheduled(cron = "0 0/5 * * * ?")
    public void getBitinfocharts(){
        new Thread(() -> httpController.btcByBitinfocharts()).start();
    }
    //@Scheduled(cron = "0 5 8 * * ?")
    public void getTime1(){
        new Thread(() -> btcHourController.getBtcHour("btcusdt","1week","2")).start();
        new Thread(() -> btcHourController.getBtcHour("btcusdt","1mon","2")).start();
        new Thread(() -> btcHourController.getBtcHour("btcusdt","1year","2")).start();
        new Thread(() -> btcHourController.getBtcHour("btcusdt","1day","2")).start();
        new Thread(() -> btcHourController.getBtcHour("btcusdt","60min","26")).start();
        new Thread(() -> btcHourController.getBtcHour("btcusdt","5min","300")).start();
        new Thread(() -> btcHourController.getBtcHour("btcusdt","4hour","7")).start();
    }


    //@Scheduled(cron = "0 0/5 * * * ?")
    public void getTime2(){
        new Thread(() -> btcHourController.getBtcHour("btcusdt","5min","2")).start();
    }

    //@Scheduled(cron = "0 0/60 * * * ?")
    public void getTime3(){
        new Thread(() -> btcHourController.getBtcHour("btcusdt","60min","2")).start();
        new Thread(() -> btcHourController.getBtcHour("btcusdt","4hour","2")).start();
    }*/
}
