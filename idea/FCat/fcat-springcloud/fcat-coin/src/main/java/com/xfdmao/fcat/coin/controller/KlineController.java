package com.xfdmao.fcat.coin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xfdmao.fcat.coin.CoinBootstrap;
import com.xfdmao.fcat.coin.base.entity.KlineInfo;
import com.xfdmao.fcat.coin.base.entity.Matrix;
import com.xfdmao.fcat.coin.base.util.KlineInfoUtil;
import com.xfdmao.fcat.coin.base.util.StrategyUtil;
import com.xfdmao.fcat.coin.constant.CoinConstant;
import com.xfdmao.fcat.coin.entity.Kline;
import com.xfdmao.fcat.coin.entity.Strategy;
import com.xfdmao.fcat.coin.entity.TDict;
import com.xfdmao.fcat.coin.huobi.contract.api.HbdmClient;
import com.xfdmao.fcat.coin.huobi.contract.api.HbdmRestApiV1;
import com.xfdmao.fcat.coin.huobi.contract.api.IHbdmRestApi;
import com.xfdmao.fcat.coin.huobi.util.KlineUtil;
import com.xfdmao.fcat.coin.service.KlineService;
import com.xfdmao.fcat.coin.service.StrategyService;
import com.xfdmao.fcat.coin.service.TDictService;
import com.xfdmao.fcat.common.controller.BaseController;
import com.xfdmao.fcat.common.util.DateUtil;
import com.xfdmao.fcat.common.util.JsonUtil;
import com.xfdmao.fcat.common.util.StrUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by fier on 2018/10/22
 */
@RestController
@RequestMapping("v1/kline")
public class KlineController extends BaseController<KlineService, Kline, Long> {
    private static Logger logger = Logger.getLogger(KlineController.class);

    @Autowired
    private StrategyService strategyService;

    @Value("${spring.profiles.active}")
    public String active;
    /**
     * // 合约接口地址 "https://api.hbdm.com";//火币api接口地址https://api.hbdm.com;
     */
    @Value("${huobi.contract.url_prex}")
    public String url_prex;

    @Autowired
    private TDictService tDictService;

    @GetMapping(value = "/queryStrategy")
    public JSONObject queryStrategy() {
        String buySell = "buy";
        String symbol = "BTC";
        String period = "15min";
        String contractType = "quarter";
        TDict tDict = new TDict();
        String key = symbol + contractType + period + buySell;
        tDict.setCode(key);
        tDict.setName(key);
        tDict = tDictService.selectOne(tDict);
        JSONObject jsonObject = JSONObject.parseObject(tDict.getValue());
        JSONArray jsonArray = jsonObject.getJSONArray("kLines");
        JSONObject result = new JSONObject();
        JSONArray k = new JSONArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            JSONArray remark = new JSONArray();
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("k", "测试1");
            jsonObject2.put("V", "1");
            remark.add(jsonObject2);
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("k", "测试2");
            jsonObject3.put("V", "2");
            remark.add(jsonObject3);
            jsonObject1.put("remark", remark);
            k.add(jsonObject1);

        }
        result.put("k", k);

        JSONArray mark = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("value", "高点");
        jsonObject1.put("xAxis", "2019-09-17 05:00");
        jsonObject1.put("yAxis", "10666");
        jsonObject1.put("itemStyle", "{\"color\":\"#00da3c\"}");
        mark.add(jsonObject1);
        result.put("mark", mark);

        JSONArray mas = new JSONArray();
        mas.add(10);
        mas.add(20);
        result.put("mas", mas);

        return JsonUtil.getSuccessJsonObject(result);
    }


    /**
     * 获取历史K线数据并保存到数据库
     *
     * @return
     */
    public void queryHistoryKlineAndSave() {
        if (!"transaction".equals(active)) return;
        while (true) {
            try {
                /**
                 * get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
                 */
                IHbdmRestApi futureGetV1 = new HbdmRestApiV1(url_prex);
                String[] coinNames = {"BTC", "LTC", "ETH", "EOS", "BSV", "BCH", "TRX", "XRP"};
                String[] periods = {"5min", "15min", "60min", "4hour", "1day"};
                for (int i = 0; i < coinNames.length; i++) {
                    String coinName = coinNames[i];
                    for (int j = 0; j < periods.length; j++) {
                        String period = periods[j];
                        try {
                            // 获取K线数据
                            String historyKline = futureGetV1.futureMarketHistoryKline(coinName + "_CQ", period, 2000 + "");
                            logger.debug("获取K线数据" + historyKline);
                            JSONObject jsonObject = JSONObject.parseObject(historyKline);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<com.xfdmao.fcat.coin.huobi.globle.response.Kline> klines = jsonArray.toJavaList(com.xfdmao.fcat.coin.huobi.globle.response.Kline.class);
                            KlineUtil.sort(klines);
                            klines.remove(0);//移除第一个未收盘的K先
                            List<Kline> kLineList = KlineUtil.toTableKline(klines);
                            for (Kline kline : kLineList) {
                                kline.setSymbol(coinName);
                                kline.setContractType("quarter");
                                kline.setPeriod(period);
                            }
                            baseServiceImpl.insertBatch(kLineList);
                        } catch (Exception e) {
                            try {
                                Thread.sleep(60 * 1000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                    }
                }
                Thread.sleep(60 * 1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 做多策略：EOS，BTC
     */
    @GetMapping(value = "/queryCissalcTwoMaStrategy")
    public JSONObject queryCissalcTwoMaStrategy(@RequestParam("strategyId") Long strategyId) {
        Strategy strategy = strategyService.selectById(strategyId);
        JSONObject jsonObject = queryTwoMaStrategyJsonObject(Integer.valueOf(strategy.getUpMa()), Integer.valueOf(strategy.getDownMa()), strategy);
        return JsonUtil.getSuccessJsonObject(jsonObject);
    }

    @GetMapping(value = "/queryTwoMaStrategy")
    public JSONObject queryTwoMaStrategy(@RequestParam("symbol") String symbol, @RequestParam("period") String period, @RequestParam("contractType") String contractType) {
        String buySell = "buy";
        TDict tDict = new TDict();
        String key = symbol + contractType + period + buySell;
        tDict.setCode(key);
        tDict.setName(key);
        tDict = tDictService.selectOne(tDict);
        JSONObject jsonObject = JSONObject.parseObject(tDict.getValue());
        return JsonUtil.getSuccessJsonObject(jsonObject);
    }


    /**
     * 存储做多策略到字典表
     */
    public void saveTwoMaStrategy() {
        if (!"web".equals(active)) return;
        while (true) {
            try {
                JSONObject jsonObject = null;
                TDict tDict;
                TDict tDictS;
                Date now;

                List<Strategy> strategyList = strategyService.selectListAll();
                for (Strategy strategy : strategyList) {
                    if ("BTC".equals(strategy.getSymbol()) && "buy".equals(strategy.getBuySell())) {
                        jsonObject = queryTwoMaStrategyJsonObject(Integer.valueOf(strategy.getUpMa()), Integer.valueOf(strategy.getDownMa()), strategy);
                    } else if ("BTC".equals(strategy.getSymbol()) && "sell".equals(strategy.getBuySell())) {
                        jsonObject = queryTwoMaStrategySell(strategy);
                    } else if ("EOS".equals(strategy.getSymbol()) && "buy".equals(strategy.getBuySell())) {
                        jsonObject = queryTwoMaStrategyJsonObject(Integer.valueOf(strategy.getUpMa()), Integer.valueOf(strategy.getDownMa()), strategy);
                    } else if ("EOS".equals(strategy.getSymbol()) && "sell".equals(strategy.getBuySell())) {
                        jsonObject = queryTwoMaStrategySell(strategy);
                    } else {
                        continue;
                    }
                    tDict = new TDict();
                    String code = strategy.getSymbol() + strategy.getContractType() + strategy.getPeriod() + strategy.getBuySell();
                    tDict.setCode(code);
                    tDict.setName(code);
                    now = new Date();
                    tDictS = tDictService.selectOne(tDict);
                    if (tDictS == null) {
                        tDict.setValue(jsonObject.toJSONString());
                        tDict.setCreateTime(now);
                        tDict.setUpdateTime(now);
                        tDictService.insert(tDict);
                    } else {
                        tDictS.setValue(jsonObject.toJSONString());
                        tDictS.setCreateTime(now);
                        tDictS.setUpdateTime(now);
                        tDictService.updateById(tDictS);
                    }
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理做多策略
     */
    private JSONObject queryTwoMaStrategyJsonObject(Integer upMaNum, Integer downMaNum, Strategy strategy) {
        List<Kline> kLineList = baseServiceImpl.queryList(strategy.getSymbol(), strategy.getContractType(), strategy.getPeriod());
        List<KlineInfo> kLines = KlineInfoUtil.getKLines(kLineList);
        List<KlineInfo> result = StrategyUtil.strategyTwoAvg(kLines, upMaNum, downMaNum, strategy);
        Collections.reverse(result);
        KlineInfoUtil.dealSumIncomeRateList(result, "0");
        KlineInfoUtil.print(result, upMaNum, KlineInfoUtil.getAvg(kLines, upMaNum), downMaNum, KlineInfoUtil.getAvg(kLines, downMaNum));
        double income = KlineInfoUtil.getSumIncomeRateRealList(result);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("income", income * 100);
        jsonObject.put("sumNum", result.size());
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < result.size(); i++) {
            KlineInfo klineInfo = result.get(i);
            JSONObject jsonObject1 = JSONObject.parseObject(JSONObject.toJSONString(klineInfo));
            jsonObject1.put("date", DateUtil.formatDate(klineInfo.getDate(), DateUtil.TIME_PATTERN_DISPLAY));
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("detail", jsonArray);

        JSONArray klinesArr = new JSONArray();
        for (int i = 0; i < kLines.size(); i++) {
            KlineInfo klineInfo = kLines.get(i);
            for (KlineInfo klineInfo1 : result) {
                if (klineInfo.getDate().getTime() == klineInfo1.getDate().getTime()) {
                    klineInfo.setBuySellStatus(klineInfo1.getBuySellStatus());
                    klineInfo.setIncomeRate(klineInfo1.getIncomeRate());
                    break;
                }
            }
            if (StrUtil.isBlank(klineInfo.getBuySellStatus())) {
                klineInfo.setBuySellStatus("");
                klineInfo.setIncomeRate(0);
            }
            JSONObject jsonObject1 = JSONObject.parseObject(JSONObject.toJSONString(klineInfo));
            jsonObject1.put("date", DateUtil.formatDate(klineInfo.getDate(), DateUtil.TIME_PATTERN_DISPLAY));
            klinesArr.add(jsonObject1);
        }

        double incomeRealKlines = KlineInfoUtil.getSumIncomeRateRealList(kLines);
        jsonObject.put("incomeKlines", incomeRealKlines * 100);
        jsonObject.put("kLines", klinesArr);
        return jsonObject;
    }

    /**
     * 均线策略：做多
     * 获取长期均线和短期均线的矩阵收益值，返回二位数组的对象列表
     *
     * @param strategyId
     * @return
     */
    @GetMapping(value = "/queryCissalcTwoMaStrategyBest")
    public JSONObject queryCissalcTwoMaStrategyBest(@RequestParam("strategyId") Long strategyId) {
        Strategy strategy = strategyService.selectById(strategyId);
        //buyType 0-等额，1-复利，默认为等额
        String buyType = "0";
        int maxMA = 60;
        JSONArray result = queryTwoMaStrategyBestJSONArray(maxMA, strategy);
        return JsonUtil.getSuccessJsonObject(result);
    }

    @GetMapping(value = "/queryTwoMaStrategyBest")
    public JSONObject queryTwoMaStrategyBest(@RequestParam("symbol") String symbol, @RequestParam("contractType") String contractType, @RequestParam("period") String period) {
        String buySell = "buy";
        TDict tDict = new TDict();
        String key = keyStart + symbol + contractType + period + buySell;
        tDict.setCode(key);
        tDict.setName(key);
        tDict = tDictService.selectOne(tDict);
        JSONArray jsonObject = JSONArray.parseArray(tDict.getValue());
        return JsonUtil.getSuccessJsonObject(jsonObject);
    }

    String keyStart = "Matrix-";

    /**
     * 矩阵，做多
     */
    public void saveTwoMaStrategyBest() {
        if (!"web".equals(active)) return;
        int maMax = 60;
        while (true) {
            try {
                JSONArray jsonObject;
                TDict tDict;
                TDict tDictS;
                Date now;
                String keyStart = "Matrix-";
                List<Strategy> strategyList = strategyService.selectListAll();
                for (Strategy strategy : strategyList) {
                    if ("BTC".equals(strategy.getSymbol()) && "buy".equals(strategy.getBuySell())) {
                        jsonObject = queryTwoMaStrategyBestJSONArray(maMax, strategy);
                    } else if ("BTC".equals(strategy.getSymbol()) && "sell".equals(strategy.getBuySell())) {
                        jsonObject = queryTwoMaStrategySellBestJsonArray(maMax, strategy);
                    } else if ("EOS".equals(strategy.getSymbol()) && "buy".equals(strategy.getBuySell())) {
                        jsonObject = queryTwoMaStrategyBestJSONArray(maMax, strategy);
                    } else if ("EOS".equals(strategy.getSymbol()) && "sell".equals(strategy.getBuySell())) {
                        jsonObject = queryTwoMaStrategySellBestJsonArray(maMax, strategy);
                    } else {
                        continue;
                    }
                    tDict = new TDict();
                    String code = keyStart + strategy.getSymbol() + strategy.getContractType() + strategy.getPeriod() + strategy.getBuySell();
                    tDict.setCode(code);
                    tDict.setName(code);
                    now = new Date();
                    tDictS = tDictService.selectOne(tDict);
                    if (tDictS == null) {
                        tDict.setValue(jsonObject.toJSONString());
                        tDict.setCreateTime(now);
                        tDict.setUpdateTime(now);
                        tDictService.insert(tDict);
                    } else {
                        tDictS.setValue(jsonObject.toJSONString());
                        tDictS.setCreateTime(now);
                        tDictS.setUpdateTime(now);
                        tDictService.updateById(tDictS);
                    }
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    private JSONArray queryTwoMaStrategyBestJSONArray(Integer maxMA, Strategy strategy) {
        List<Kline> kLineList = baseServiceImpl.queryList(strategy.getSymbol(), strategy.getContractType(), strategy.getPeriod());
        List<KlineInfo> kLines = KlineInfoUtil.getKLines(kLineList);
        List<Matrix> matrixs = StrategyUtil.queryTwoMaStrategyBest(kLines, maxMA, strategy);
        JSONArray result = new JSONArray();
        for (int i = 0; i < matrixs.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(matrixs.get(i)));
            jsonObject.put("value", new BigDecimal(jsonObject.getDouble("value")).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN));
            result.add(jsonObject);
        }
        return result;
    }

    /**
     * 均线策略：做空
     * 获取长期均线和短期均线的矩阵收益值，返回二位数组的对象列表
     */
    @GetMapping(value = "/queryCissalcTwoMaStrategySellBest")
    public JSONObject queryCissalcTwoMaStrategySellBest(@RequestParam("stratetyId") String stratetyId) {
        Strategy strategy = strategyService.selectById(stratetyId);
        JSONArray result = queryTwoMaStrategySellBestJsonArray(60, strategy);
        return JsonUtil.getSuccessJsonObject(result);
    }

    @GetMapping(value = "/queryTwoMaStrategySellBest")
    public JSONObject queryTwoMaStrategySellBest(@RequestParam("symbol") String symbol, @RequestParam("contractType") String contractType, @RequestParam("period") String period) {
        String buySell = "sell";
        TDict tDict = new TDict();
        String key = keyStart + symbol + contractType + period + buySell;
        tDict.setCode(key);
        tDict.setName(key);
        tDict = tDictService.selectOne(tDict);
        JSONArray jsonObject = JSONArray.parseArray(tDict.getValue());
        return JsonUtil.getSuccessJsonObject(jsonObject);
    }

    private JSONArray queryTwoMaStrategySellBestJsonArray(Integer maxMA, Strategy strategy) {
        List<Kline> kLineList = baseServiceImpl.queryList(strategy.getSymbol(), strategy.getContractType(), strategy.getPeriod());
        List<KlineInfo> kLines = KlineInfoUtil.getKLines(kLineList);
        List<Matrix> matrixs = StrategyUtil.queryTwoMaStrategySellBest(kLines, maxMA, strategy);
        JSONArray result = new JSONArray();
        for (int i = 0; i < matrixs.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(matrixs.get(i)));
            jsonObject.put("value", new BigDecimal(jsonObject.getDouble("value")).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN));
            result.add(jsonObject);
        }
        return result;
    }

    /**
     * 均线策略：做空
     *
     * @return
     */
    @GetMapping(value = "/queryCissalcTwoMaStrategySell")
    public JSONObject queryCissalcTwoMaStrategySell(@RequestParam("strategyId") Long strategyId) {
        Strategy strategy = strategyService.selectById(strategyId);
        JSONObject jsonObject = queryTwoMaStrategySell(strategy);
        return JsonUtil.getSuccessJsonObject(jsonObject);
    }

    @GetMapping(value = "/queryTwoMaStrategySell")
    public JSONObject queryTwoMaStrategySell(@RequestParam("symbol") String symbol, @RequestParam("contractType") String contractType, @RequestParam("period") String period) {
        String buySell = "sell";
        TDict tDict = new TDict();
        String key = symbol + contractType + period + buySell;
        tDict.setCode(key);
        tDict.setName(key);
        tDict = tDictService.selectOne(tDict);
        JSONObject jsonObject = JSONObject.parseObject(tDict.getValue());
        return JsonUtil.getSuccessJsonObject(jsonObject);
    }

    /**
     * 处理做空策略详情
     *
     * @return
     */
    private JSONObject queryTwoMaStrategySell(Strategy strategy) {
        List<Kline> kLineList = baseServiceImpl.queryList(strategy.getSymbol(), strategy.getContractType(), strategy.getPeriod());
        List<KlineInfo> kLines = KlineInfoUtil.getKLines(kLineList);
        List<KlineInfo> result = StrategyUtil.strategySellTwoAvg(kLines, Integer.valueOf(strategy.getUpMa()), Integer.valueOf(strategy.getDownMa()), strategy);
        KlineInfoUtil.dealSumIncomeRateList(result, "0");
        KlineInfoUtil.print(result, Integer.valueOf(strategy.getUpMa()), KlineInfoUtil.getAvg(kLines, Integer.valueOf(strategy.getUpMa())), Integer.valueOf(strategy.getDownMa()), KlineInfoUtil.getAvg(kLines, Integer.valueOf(strategy.getDownMa())));
        double income = KlineInfoUtil.getSumIncomeRateRealList(result);
        Collections.reverse(result);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("income", income * 100);
        jsonObject.put("sumNum", result.size());
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < result.size(); i++) {
            KlineInfo klineInfo = result.get(i);
            JSONObject jsonObject1 = JSONObject.parseObject(JSONObject.toJSONString(klineInfo));
            jsonObject1.put("date", DateUtil.formatDate(klineInfo.getDate(), DateUtil.TIME_PATTERN_DISPLAY));
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("detail", jsonArray);

        JSONArray klinesArr = new JSONArray();
        for (int i = 0; i < kLines.size(); i++) {
            KlineInfo klineInfo = kLines.get(i);
            for (KlineInfo klineInfo1 : result) {
                if (klineInfo.getDate().getTime() == klineInfo1.getDate().getTime()) {
                    klineInfo.setBuySellStatus(klineInfo1.getBuySellStatus());
                    klineInfo.setIncomeRate(klineInfo1.getIncomeRate());
                    break;
                }
            }
            if (StrUtil.isBlank(klineInfo.getBuySellStatus())) {
                klineInfo.setBuySellStatus("");
                klineInfo.setIncomeRate(0);
            }
            JSONObject jsonObject1 = JSONObject.parseObject(JSONObject.toJSONString(klineInfo));
            jsonObject1.put("date", DateUtil.formatDate(klineInfo.getDate(), DateUtil.TIME_PATTERN_DISPLAY));
            klinesArr.add(jsonObject1);
        }

        double incomeRealKlines = KlineInfoUtil.getSumIncomeRateRealList(kLines);
        jsonObject.put("incomeKlines", incomeRealKlines * 100);
        jsonObject.put("kLines", klinesArr);
        return jsonObject;
    }


    /**
     * 均线策略：
     * 关键K线策略
     *
     * @param symbol
     * @param contractType
     * @param period
     * @param upMaNum
     * @param downMaNum
     * @param upperLeadFactor
     * @param gainFactor
     * @param buyType
     * @param protectionDownFactor
     * @return
     */
    @GetMapping(value = "/querykeyKlineStrategy")
    public JSONObject querykeyKlineStrategy(@RequestParam("symbol") String symbol, @RequestParam("contractType") String contractType,
                                            @RequestParam("period") String period, @RequestParam("upMaNum") Integer upMaNum, @RequestParam("downMaNum") Integer downMaNum,
                                            @RequestParam("upperLeadFactor") double upperLeadFactor,
                                            @RequestParam("gainFactor") double gainFactor,
                                            @RequestParam("buyType") String buyType,
                                            @RequestParam("protectionDownFactor") double protectionDownFactor,
                                            @RequestParam(value = "volFactory", required = false, defaultValue = "2.5") double volFactory,
                                            @RequestParam(value = "upperLeadGain", required = false, defaultValue = "0.0072") double upperLeadGain
    ) {
        List<Kline> kLineList = baseServiceImpl.queryList(symbol, contractType, period);
        List<KlineInfo> kLines = KlineInfoUtil.getKLines(kLineList);
        List<KlineInfo> result = StrategyUtil.strategykeyKline(kLines, upMaNum, downMaNum, symbol, upperLeadFactor, gainFactor, protectionDownFactor, volFactory, upperLeadGain);
        Collections.reverse(result);
        KlineInfoUtil.dealSumIncomeRateList(result, buyType);
        KlineInfoUtil.print(result, upMaNum, KlineInfoUtil.getAvg(kLines, upMaNum), downMaNum, KlineInfoUtil.getAvg(kLines, downMaNum));
        double income = KlineInfoUtil.getSumIncomeRateRealList(result);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("income", income * 100);
        jsonObject.put("sumNum", result.size());
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < result.size(); i++) {
            KlineInfo klineInfo = result.get(i);
            JSONObject jsonObject1 = JSONObject.parseObject(JSONObject.toJSONString(klineInfo));
            jsonObject1.put("date", DateUtil.formatDate(klineInfo.getDate(), DateUtil.TIME_PATTERN_DISPLAY));
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("detail", jsonArray);

        JSONArray klinesArr = new JSONArray();
        for (int i = 0; i < kLines.size(); i++) {
            KlineInfo klineInfo = kLines.get(i);
            for (KlineInfo klineInfo1 : result) {
                if (klineInfo.getDate().getTime() == klineInfo1.getDate().getTime()) {
                    klineInfo.setBuySellStatus(klineInfo1.getBuySellStatus());
                    klineInfo.setIncomeRate(klineInfo1.getIncomeRate());
                    break;
                }
            }
            if (StrUtil.isBlank(klineInfo.getBuySellStatus())) {
                klineInfo.setBuySellStatus("");
                klineInfo.setIncomeRate(0);
            }
            JSONObject jsonObject1 = JSONObject.parseObject(JSONObject.toJSONString(klineInfo));
            jsonObject1.put("date", DateUtil.formatDate(klineInfo.getDate(), DateUtil.TIME_PATTERN_DISPLAY));
            klinesArr.add(jsonObject1);
        }

        double incomeRealKlines = KlineInfoUtil.getSumIncomeRateRealList(kLines);
        jsonObject.put("incomeKlines", incomeRealKlines * 100);
        jsonObject.put("kLines", klinesArr);

        return JsonUtil.getSuccessJsonObject(jsonObject);
    }
}
