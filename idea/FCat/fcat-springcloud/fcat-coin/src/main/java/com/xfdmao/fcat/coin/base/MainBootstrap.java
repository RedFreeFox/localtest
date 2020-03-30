package com.xfdmao.fcat.coin.base;

import com.xfdmao.fcat.coin.base.entity.KlineInfo;
import com.xfdmao.fcat.coin.base.util.KlineInfoUtil;
import com.xfdmao.fcat.coin.base.util.StrategyUtil;

import java.util.*;

/**
 * Created by cissa on 2019/7/27.
 */
public class MainBootstrap {
    public static void main(String[] args) {
        String file = "src/main/resources/data/EOS";
        file = "src/main/resources/data/BTC";
        List<KlineInfo> kLines = KlineInfoUtil.getKLines(file);
        Collections.reverse(kLines);
        Map<Date,KlineInfo> kLineMap = KlineInfoUtil.getKLineMap(kLines);
        Map<Date,Double> avgMap20 =  KlineInfoUtil.getAvg(kLines,20);
        Map<Date,Double> avgMap10 =  KlineInfoUtil.getAvg(kLines,10);
        Map<Date,Double> avgMap5 =  KlineInfoUtil.getAvg(kLines,5);
        KlineInfoUtil.dealGain(kLines);
        StrategyUtil.strategyUpDown(kLines,avgMap5,avgMap10,avgMap20);
        StrategyUtil.strategyAvgAll(kLines);
    }
}
