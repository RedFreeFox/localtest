package com.xfdmao.fcat.coin.base.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xfdmao.fcat.coin.base.constant.KLineConstant;
import com.xfdmao.fcat.coin.base.entity.KlineInfo;
import com.xfdmao.fcat.coin.base.entity.Matrix;
import com.xfdmao.fcat.coin.entity.Strategy;
import com.xfdmao.fcat.common.util.DateUtil;
import com.xfdmao.fcat.common.util.StrUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cissa on 2019/7/28.
 */
public class StrategyUtil {

    /**
     * 各合约对应的最新的建仓
     * key:用户名+symbol+contractType
     * value:建仓K线对应的时间
     */
    private static Map<String, String> openMap = new HashMap<>();

    /**
     * 两条均线最佳做空的策略,找出长期均线和短期均线的值
     *
     * @param klineInfos
     */
    public static List<Matrix> queryTwoMaStrategySellBest(List<KlineInfo> klineInfos, int maxMA, Strategy strategy) {
        System.out.println("找出最佳的两条做空均线值");
        double[][] mns = new double[maxMA][maxMA];
        boolean[][] top20 = new boolean[maxMA][maxMA];
        String kineInfosJson = JSONArray.toJSONString(klineInfos); //由于在每次循环计算过程中，当发生
        for (int m = 0; m < maxMA; m++) {
            for (int n = 0; n < maxMA; n++) {
                if (m >= n) {
                    List<KlineInfo> klineInfoList = JSONArray.parseArray(kineInfosJson, KlineInfo.class);
                    List<KlineInfo> result = strategySellTwoAvg(klineInfoList, m + 1, n + 1, strategy);
                    KlineInfoUtil.dealSumIncomeRateList(result, "0");
                    double income = KlineInfoUtil.getSumIncomeRateRealList(result);
                    if (income > 0) {
                        mns[m][n] = income;
                    } else {
                        mns[m][n] = 0.0;
                    }
                } else {
                    mns[m][n] = 0.0;
                }

            }
        }
        List<Matrix> matrixList = MatrixUtil.dealMatrix(mns);
        MatrixUtil.print(mns);


        Matrix matrix = MatrixUtil.queryMaxMatrix(mns);
        Integer maxM = matrix.getM();
        Integer maxN = matrix.getN();
        System.out.println("最佳的策略：" + JSONObject.toJSONString(matrix));
        return matrixList;
    }

    /**
     * 找出两条均线最佳的策略
     *
     * @param klineInfos
     */
    public static List<Matrix> queryTwoMaStrategyBest(List<KlineInfo> klineInfos, int maxMA, Strategy strategy) {
        System.out.println("找出最佳的两条做多均线值");
        double[][] mns = new double[maxMA][maxMA];
        boolean[][] top20 = new boolean[maxMA][maxMA];
        String kineInfosJson = JSONArray.toJSONString(klineInfos); //由于在每次循环计算过程中，当发生
        for (int m = 0; m < maxMA; m++) {
            for (int n = 0; n < maxMA; n++) {
                if (m >= n) {
                    List<KlineInfo> klineInfoList = JSONArray.parseArray(kineInfosJson, KlineInfo.class);
                    List<KlineInfo> result = strategyTwoAvg(klineInfoList, m + 1, n + 1, strategy);
                    KlineInfoUtil.dealSumIncomeRateList(result, "0");
                    double income = KlineInfoUtil.getSumIncomeRateRealList(result);
                    if (income > 0) {
                        mns[m][n] = income;
                    } else {
                        mns[m][n] = 0.0;
                    }
                } else {
                    mns[m][n] = 0.0;
                }

            }
        }
        List<Matrix> matrixList = MatrixUtil.dealMatrix(mns);
        MatrixUtil.print(mns);


        Matrix matrix = MatrixUtil.queryMaxMatrix(mns);
        Integer maxM = matrix.getM();
        Integer maxN = matrix.getN();
        System.out.println("最佳的策略：" + JSONObject.toJSONString(matrix));
        return matrixList;
    }


    //找出涨幅大于5%，并且收盘价大于20MA，开盘价小于20MA
    public static void strategyUpDown(List<KlineInfo> klineInfos, Map<Date, Double> avgMap5, Map<Date, Double> avgMap10, Map<Date, Double> avgMap20) {
        System.out.println("左边是上涨百分点，上面是下跌的百分点\n" +
                "例如：[16][9],表示左边序列16（上涨16个点以上买入），右序列为9（下跌9个点以上卖出），总收益为179.11%");
        double p = 0.01;//初始值
        double q = 0.005;//每次递增的值
        double paramM = p;

        double[][] mns = new double[30][30];
        boolean[][] top20 = new boolean[30][30];
        for (int m = 0; m < 30; m++) {
            double paramN = p;
            for (int n = 0; n < 30; n++) {
                mns[m][n] = dealStrategyUpDown(klineInfos, paramM, paramN, avgMap5, avgMap10, avgMap20);
                paramN += q;
            }
            paramM += q;
        }
        List<Matrix> matrixList = MatrixUtil.dealMatrix(mns);
        MatrixUtil.dealTop20(top20, matrixList);
        Matrix matrix = MatrixUtil.queryMaxMatrix(mns);
        Integer maxM = matrix.getM();
        Integer maxN = matrix.getN();
        MatrixUtil.print(mns);

        System.out.println(String.format("最大收益：%-8.4f\t买入上涨幅度：%-8.4f\t卖出下跌幅度：%-8.4f", dealStrategyUpDown(klineInfos, (maxM + 1) * p, (maxN + 1) * p, avgMap5, avgMap10, avgMap20), (maxM + 1) * p, (maxN + 1) * p));
        KlineInfoUtil.printBuySell(klineInfos);
    }


    public static double dealStrategyUpDown(List<KlineInfo> klineInfos, double paramM, double paramN, Map<Date, Double> avgMap5, Map<Date, Double> avgMap10, Map<Date, Double> avgMap20) {
        KlineInfoUtil.emptyIncomeRateAndBuySellStatus(klineInfos);
        List<KlineInfo> result1 = new ArrayList<>();
        Map<Date, Double> incomeMap = new HashMap<>();
        boolean positionStatus = false;
        for (int i = 0; i < klineInfos.size(); i++) {
            KlineInfo klineInfo = klineInfos.get(i);
            //建仓
            //  if (klineInfo.dealGain() >= paramM && klineInfo.getClose() > avgMap5.get(klineInfo.getDate())) {
            if (!positionStatus) {
                if (klineInfo.getGain() >= paramM) {
                    buyOpen(result1, klineInfo);
                    positionStatus = !positionStatus;
                }
            }

            //平仓
            if (klineInfo.getGain() <= -paramN) {
                if (positionStatus) {
                    klineInfo.setBuySellStatus(KLineConstant.BUYSELLSTATUS_SELL);
                    klineInfo.setIncomeRate(new BigDecimal(klineInfo.getClose()).subtract(new BigDecimal(result1.get(result1.size() - 1).getClose())).divide(new BigDecimal(klineInfo.getClose()), 6, BigDecimal.ROUND_DOWN).doubleValue());
                    result1.add(klineInfo);
                    positionStatus = !positionStatus;
                }
            }
        }
        KlineInfoUtil.dealSumIncomeRateList(klineInfos, null);
        double result = KlineInfoUtil.getSumIncomeRateRealList(klineInfos);
        return result;
    }

    public static void strategyAvgAll(List<KlineInfo> klineInfos) {
        int ma = 1;
        int count = 120;
        List<Double> incomeList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Map<Date, Double> avgMap = KlineInfoUtil.getAvg(klineInfos, i);
            List<KlineInfo> result = dealStrategyAvg(klineInfos, avgMap);

            KlineInfoUtil.dealSumIncomeRateList(result, null);
            double income = KlineInfoUtil.getSumIncomeRateRealList(result);
            incomeList.add(income);
        }
        KlineInfoUtil.printIncomeList(incomeList);
    }

    public static List<KlineInfo> dealStrategyAvg(List<KlineInfo> klineInfos, Map<Date, Double> upAvgMap) {
        KlineInfoUtil.emptyIncomeRateAndBuySellStatus(klineInfos);
        List<KlineInfo> result1 = new ArrayList<>();
        Map<Date, Double> incomeMap = new HashMap<>();
        boolean positionStatus = false;
        for (int i = 0; i < klineInfos.size(); i++) {
            KlineInfo klineInfo = klineInfos.get(i);
            if ("0.0".equals(upAvgMap.get(klineInfo.getDate()).toString())) continue;
            //建仓
            if (!positionStatus) {
                if (klineInfo.getClose() > upAvgMap.get(klineInfo.getDate())) {
                    buyOpen(result1, klineInfo);
                    positionStatus = !positionStatus;
                }
            } else {
                //平仓
                if (klineInfo.getClose() < upAvgMap.get(klineInfo.getDate())) {
                    sellClose(result1, klineInfo);
                    positionStatus = !positionStatus;
                }
            }


        }
        return result1;
    }


    /**
     * 做多策略
     */
    public static List<KlineInfo> strategyTwoAvg(List<KlineInfo> klineInfos, Integer upMaNum, Integer downMaNum, Strategy strategy) {
        Map<Date, Double> upAvgMap = KlineInfoUtil.getAvg(klineInfos, upMaNum);
        Map<Date, Double> downAvgMap = KlineInfoUtil.getAvg(klineInfos, downMaNum);
        List<KlineInfo> result = dealStrategyTwoAvg(klineInfos, upAvgMap, downAvgMap, strategy);
        return result;
    }

    /**
     * 处理做多策略
     *
     * @param klineInfos
     * @param upAvgMap
     * @param downAvgMap
     * @return
     */
    private static List<KlineInfo> dealStrategyTwoAvg(List<KlineInfo> klineInfos, Map<Date, Double> upAvgMap, Map<Date, Double> downAvgMap, Strategy strategy) {
        double volFactory = 2.5;
        double upperLeadGain = 0.007;
        Double downRate = null;
        Double nextGain = null;
        Double towKlineGain = null;
        Double upperLeadGainB = null;
        Double gain = null;
        Double gainB = null;
        Double overGain = null;
        Integer klineCount = null;
        if (!StrUtil.isBlank(strategy.getParam())) {
            try {
                JSONObject param = JSONObject.parseObject(strategy.getParam());
                downRate = param.getDouble("downRate");
                nextGain = param.getDouble("nextGain");
                towKlineGain = param.getDouble("towKlineGain");
                upperLeadGain = param.getDouble("upperLeadGain");
                upperLeadGainB = param.getDouble("upperLeadGainB");
                gain = param.getDouble("gain");
                gainB = param.getDouble("gainB");
                overGain = param.getDouble("overGain");
                klineCount = param.getInteger("klineCount");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        KlineInfoUtil.emptyIncomeRateAndBuySellStatus(klineInfos);
        List<KlineInfo> result1 = new ArrayList<>();
        boolean positionStatus = false;
        String openKey = strategy.getSymbol() + strategy.getContractType() + "buy";
        for (int i = 0; i < klineInfos.size(); i++) {
            if (i < 2) continue;
            KlineInfo klineInfo = klineInfos.get(i);
            if ("0.0".equals(upAvgMap.get(klineInfo.getDate()).toString())) continue;
            //debug到具体某一根K线
            if (klineInfo.getDate().getTime() == DateUtil.toDate("2019-10-07 08:00:00", DateUtil.TIME_PATTERN_DISPLAY).getTime()) {
                System.out.println(JSONObject.toJSONString(klineInfo));
            }
            if ("EOS".equals(strategy.getSymbol())) {
                if (!positionStatus) {  //如果空仓,则看是否需要建仓
                    //买入条件：

                    //1、当前收盘K线的长期均线值 小于 短期均线的值
                    if (upAvgMap.get(klineInfo.getDate()) > downAvgMap.get(klineInfo.getDate())) continue;

                    //2、当前收盘K线的长期均线的值  大于   上一根收盘K线的长期均线的值       即：长期均线往上走
                    if (upAvgMap.get(klineInfos.get(i - 1).getDate()) > upAvgMap.get(klineInfo.getDate())) continue;

                    //3、当前收盘K线的收盘价高于短期均线的收盘价
                    if (klineInfo.getClose() < downAvgMap.get(klineInfo.getDate())) continue;

                    //4、短期均线翻上
                    if (downAvgMap.get(klineInfos.get(i - 1).getDate()) > downAvgMap.get(klineInfo.getDate())) continue;

                    //4、当前收盘的K线或上一根收盘的K线不能收上引线，振幅不能大于振幅因子。如果大于，则当前K线涨幅不能小于0.0072且当前K线量能是上一根K线量能的3倍以上
                    if (KlineInfoUtil.isUpperLead(klineInfo, Double.valueOf(strategy.getUpperLeadFactor())) || KlineInfoUtil.isUpperLead(klineInfos.get(i - 1), Double.valueOf(strategy.getUpperLeadFactor()))) {
                        if (!(klineInfo.getGain() > upperLeadGain && (klineInfo.getVolume() - klineInfos.get(i - 1).getVolume()) / klineInfos.get(i - 1).getVolume() > volFactory)) {
                            continue;
                        }
                    }


                    //6、当前收盘K线涨幅大于涨幅因子
                    if (klineInfo.getGain() < Double.valueOf(strategy.getGainFactor())) {
                        continue;
                    }

                    //8、下跌那根K线的量不能是最近三根K线的最大量
                    if (KlineInfoUtil.bigVolDown(klineInfo, klineInfos.get(i - 1), klineInfos.get(i - 2))) continue;


                    //10、最近三根K线中不能有两根以上的K线下跌或小于均值，
                    // 除非：当前K线的收盘价大于前面两根K线的收盘价，且当前K线的涨幅是前面两根K线涨幅总和绝对值的两倍以上，且当前K线的量能是前一根K线量能的3倍以上
                    if (KlineInfoUtil.twoDown(klineInfo, klineInfos.get(i - 1), klineInfos.get(i - 2), downAvgMap)) {
                        if (klineInfo.getClose() > klineInfos.get(i - 1).getClose() && klineInfo.getClose() > klineInfos.get(i - 2).getClose()
                                && BigDecimal.valueOf(klineInfos.get(i - 1).getGain() + klineInfos.get(i - 2).getGain()).compareTo(BigDecimal.valueOf(0)) != 0
                                && BigDecimal.valueOf(klineInfo.getGain()).divide(BigDecimal.valueOf(klineInfos.get(i - 1).getGain()).add(BigDecimal.valueOf(klineInfos.get(i - 2).getGain())).abs(), 4, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.valueOf(2)) > 0
                                && klineInfo.getVolume() / klineInfos.get(i - 1).getVolume() > volFactory
                        ) {
                        } else {
                            continue;
                        }

                    }

                    buyOpen(result1, klineInfo);
                    positionStatus = !positionStatus;
                } else {
                    //卖出条件：
                    //**保护价格，当价格跌破开仓多少止损**
                    double startPrice = result1.get(result1.size() - 1).getClose();//建仓价格
                    double protectionPrice = new BigDecimal(startPrice).subtract(new BigDecimal(startPrice).multiply(new BigDecimal(strategy.getProtectionDownFactor()))).doubleValue();
                    if (klineInfo.getLow() < protectionPrice) {
                        klineInfo.setClose(protectionPrice);
                        sellClose(result1, klineInfo);
                        positionStatus = !positionStatus;
                    } else {
                        // 1、当前收盘K线的收盘价 小于 当前收盘K线的短期均线的值
                        if (klineInfo.getClose() > downAvgMap.get(klineInfo.getDate())) continue;
                        sellClose(result1, klineInfo);
                        positionStatus = !positionStatus;
                    }
                }
            } else {//BTC做多策略  获取其他测试
                if (!positionStatus) {
                    //买入条件：

                    //当前k线收盘价必须大于今日的开盘价
                    /* if(!KlineInfoUtil.overDailyOpen(klineInfos,i,klineInfo))continue;*/

                    //当最近100根K线内出现跌幅超过0.023，则不操作
                    if (overGain(klineInfos, i, overGain, klineCount)) continue;

                    //1、当前收盘K线的长期均线值 小于 短期均线的值 (短期均线站上长期均线)
                    if (upAvgMap.get(klineInfo.getDate()) > downAvgMap.get(klineInfo.getDate())) continue;

                    //2、当前收盘K线的长期均线的值  大于  上一根收盘K线的长期均线的值（即：长期均线往上走）
                    if (upAvgMap.get(klineInfo.getDate()) < upAvgMap.get(klineInfos.get(i - 1).getDate())) continue;

                    //3、当前收盘K线的收盘价高于短期均线的收盘价 (收盘价站上短期均线)
                    if (klineInfo.getClose() < downAvgMap.get(klineInfo.getDate())) continue;

                    //4、当前收盘K线不能收长的上引线
                    if (KlineInfoUtil.isUpperLead(klineInfo, Double.valueOf(strategy.getUpperLeadFactor()))) continue;

                    //5、当前收盘K线的上一个K线不能收上引线
                    if (KlineInfoUtil.isUpperLead(klineInfos.get(i - 1), Double.valueOf(strategy.getUpperLeadFactor())))
                        continue;

                    //6、当前收盘K线涨幅大于gainFactor
                    if (klineInfo.getGain() < Double.valueOf(strategy.getGainFactor())) {
                        if (!(klineInfo.getGain() > gainB &&
                                klineInfos.get(i - 1).getGain() > 0 && klineInfos.get(i - 2).getGain() > 0 &&
                                klineInfos.get(i - 1).getGain() + klineInfos.get(i - 2).getGain() > 0.0015)) {
                            continue;
                        }
                    }

                    //7、最近5根收盘K线不能出现大于-0.0078个点的跌幅
                    if (klineInfos.get(i).getGain() < downRate ||
                            klineInfos.get(i - 1).getGain() < downRate ||
                            klineInfos.get(i - 2).getGain() < downRate ||
                            klineInfos.get(i - 3).getGain() < downRate ||
                            klineInfos.get(i - 4).getGain() < downRate
                    ) {
                        continue;
                    }

                    //8、前两根K线不能全部下跌并且下跌的总和不能大于0.0082
                    if (klineInfos.get(i - 1).getGain() < 0 && klineInfos.get(i - 2).getGain() < 0 &&
                            (klineInfos.get(i - 1).getGain() + klineInfos.get(i - 2).getGain()) < towKlineGain) {
                        continue;
                    }

                    //9、K线涨幅大于0.014则不建仓
                    if (klineInfo.getGain() > gain) {
                        continue;
                    }

                    buyOpen(result1, klineInfo);
                    openMap.put(openKey, klineInfo.getDate().getTime() + "");
                    positionStatus = !positionStatus;
                } else {
                    //卖出条件：

                    //当跌幅超过0.023，则平多
                    if (overGain(klineInfos, i, overGain, klineCount)) {
                        sellClose(result1, klineInfo);
                        positionStatus = !positionStatus;
                        continue;
                    }


                    //**保护伞，当价格跌破开仓多少止损**
                    double startPrice = result1.get(result1.size() - 1).getClose();//建仓价格
                    double protectionPrice = new BigDecimal(startPrice).subtract(new BigDecimal(startPrice).multiply(new BigDecimal(strategy.getProtectionDownFactor()))).doubleValue();
                    if (klineInfo.getLow() < protectionPrice) {
                        klineInfo.setClose(protectionPrice);
                        sellClose(result1, klineInfo);
                        positionStatus = !positionStatus;
                    } else {
                        //1、当前收盘K线的收盘价 小于 当前收盘K线的短期均线的值
                        //如果买入后的第一根k线收跌则卖出 (klineInfo.getGain()<nextGain  && ((Long.valueOf(openMap.get(openKey)) +15*60*1000)>=klineInfo.getDate().getTime()))
                        //2、如果收盘K线收上引线，并且涨幅为负
                        //3、如果收盘K线收上引线，并且涨幅大于1.1个点
                        if (klineInfo.getClose() < downAvgMap.get(klineInfo.getDate()) ||
                                (klineInfo.getUpLeadGain() >= upperLeadGain && klineInfo.getGain() <= 0) ||
                                klineInfo.getUpLeadGain() >= upperLeadGainB) {
                            sellClose(result1, klineInfo);
                            positionStatus = !positionStatus;
                        }

                    }
                }
            }
        }
        return result1;
    }

    /**
     * 两条均线做空策略
     */
    public static List<KlineInfo> strategySellTwoAvg(List<KlineInfo> klineInfos, int upMa, int downMa, Strategy strategy) {
        Map<Date, Double> upAvgMap = KlineInfoUtil.getAvg(klineInfos, upMa);
        Map<Date, Double> downAvgMap = KlineInfoUtil.getAvg(klineInfos, downMa);
        List<KlineInfo> result = new ArrayList<>();
        if (strategy.getSymbol().toUpperCase().equals("EOS")) {
            result = dealStrategySellTwoAvgEOS(klineInfos, upAvgMap, downAvgMap, strategy);
        } else if (strategy.getSymbol().toUpperCase().equals("BTC")) {
            result = dealStrategySellTwoAvgBTC(klineInfos, upAvgMap, downAvgMap, strategy);
        }
        return result;
    }

    /**
     * 处理两条均线做空策略的详情:BTC
     */
    private static List<KlineInfo> dealStrategySellTwoAvgBTC(List<KlineInfo> klineInfos, Map<Date, Double> upAvgMap, Map<Date, Double> downAvgMap, Strategy strategy) {
        KlineInfoUtil.emptyIncomeRateAndBuySellStatus(klineInfos);
        List<KlineInfo> result1 = new ArrayList<>();
        boolean positionStatus = false;

        Double overGain = null;
        Integer threeRaiseNum = 20;
        Integer klineCount = 0;
        if (!StrUtil.isBlank(strategy.getParam())) {
            try {
                JSONObject param = JSONObject.parseObject(strategy.getParam());
                overGain = param.getDouble("overGain");
                klineCount = param.getInteger("klineCount");
                threeRaiseNum = param.getInteger("threeRaiseNum");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < klineInfos.size(); i++) {
            if (i < 2) continue;
            KlineInfo klineInfo = klineInfos.get(i);

            //debug到具体某一根K线
            if (klineInfo.getDate().getTime() == DateUtil.toDate("2019-07-11 13:45:00", DateUtil.TIME_PATTERN_DISPLAY).getTime()) {
                System.out.println(JSONObject.toJSONString(klineInfo));
            }
            if ("0.0".equals(upAvgMap.get(klineInfo.getDate()).toString())) continue;
            if (!positionStatus) {  //如果空仓,则看是否需要建仓
                //买入条件：

                //当最近20跟K线出现连续的三根k线上涨,则不建仓
                /*     if(KlineInfoUtil.isThreeRaiseNum(klineInfos,threeRaiseNum,i)) continue;*/

                //如果最近200根k线出现涨幅大于3.2个点，则不建仓
                if (overGain(klineInfos, i, overGain, klineCount)) continue;

                //1、当前收盘K线的长期均线值 大于 短期均线的值      即：长期均线在短期均线的上面
                if (upAvgMap.get(klineInfo.getDate()) < downAvgMap.get(klineInfo.getDate())) continue;

                //2、当前收盘K线的长期均线的值  小于  上一根收盘K线的长期均线的值       即：长期均线往下走
                if (upAvgMap.get(klineInfo.getDate()) > upAvgMap.get(klineInfos.get(i - 1).getDate())) continue;

                //3、当前收盘K线的收盘价低于短期均线的收盘价     即价格跌破短期均线
                if (klineInfo.getClose() > downAvgMap.get(klineInfo.getDate())) continue;

                //4、短期均线翻下
                if (downAvgMap.get(klineInfo.getDate()) > downAvgMap.get(klineInfos.get(i - 1).getDate()))
                    continue;

                //5、当前收盘K线不能收长的下引线
                if (KlineInfoUtil.isDownLead(klineInfo, Double.valueOf(strategy.getUpperLeadFactor())))
                    continue;

                //6、当前收盘K线的上一个K线不能收下引线
                if (KlineInfoUtil.isDownLead(klineInfos.get(i - 1), Double.valueOf(strategy.getUpperLeadFactor())))
                    continue;

                //7、最近20根收盘K线涨幅小于1个点
                if (KlineInfoUtil.overGainFactory(klineInfos, i, threeRaiseNum, Double.valueOf(strategy.getGainFactor())))
                    continue;

                sellOpen(result1, klineInfo);
                positionStatus = !positionStatus;
            } else {
                //卖出条件：

                //当最近20跟K线出现连续的三根k线上涨,则平空
                       /* if(KlineInfoUtil.isThreeRaiseNum(klineInfos,threeRaiseNum,i)){
                            buyClose(result1, klineInfo);
                            positionStatus = !positionStatus;
                            continue;
                        }*/

                    /*    if (KlineInfoUtil.overGainFactory(klineInfos,i,threeRaiseNum,Double.valueOf(strategy.getGainFactor()))){
                            buyClose(result1, klineInfo);
                            positionStatus = !positionStatus;
                            continue;
                        }*/

                //如果最近200根k线出现涨幅大于3.2个点,则清仓
                if (overGain(klineInfos, i, overGain, klineCount)) {
                    buyClose(result1, klineInfo);
                    positionStatus = !positionStatus;
                    continue;
                }
                //**保护价格，当价格跌破开仓多少止损**
                double startPrice = result1.get(result1.size() - 1).getClose();//建仓价格
                double protectionPrice = new BigDecimal(startPrice).add(new BigDecimal(startPrice).multiply(new BigDecimal(strategy.getProtectionDownFactor()))).doubleValue();
                if (klineInfo.getHigh() > protectionPrice) {
                    klineInfo.setClose(protectionPrice);
                    buyClose(result1, klineInfo);
                    positionStatus = !positionStatus;
                } else {
                    // 1、当前收盘K线的收盘价 高于 当前收盘K线的短期均线的值
                    if (klineInfo.getClose() < downAvgMap.get(klineInfo.getDate())) continue;
                    buyClose(result1, klineInfo);
                    positionStatus = !positionStatus;
                }
            }
        }
        return result1;
    }

    /**
     * 处理两条均线做空策略的详情:EOS
     */
    private static List<KlineInfo> dealStrategySellTwoAvgEOS(List<KlineInfo> klineInfos, Map<Date, Double> upAvgMap, Map<Date, Double> downAvgMap, Strategy strategy) {
        KlineInfoUtil.emptyIncomeRateAndBuySellStatus(klineInfos);
        List<KlineInfo> result1 = new ArrayList<>();
        boolean positionStatus = false;

        Double overGain = null;
        Integer klineCount = 0;
        if (!StrUtil.isBlank(strategy.getParam())) {
            try {
                JSONObject param = JSONObject.parseObject(strategy.getParam());
                overGain = param.getDouble("overGain");
                klineCount = param.getInteger("klineCount");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < klineInfos.size(); i++) {
            if (i < 2) continue;
            KlineInfo klineInfo = klineInfos.get(i);

            //debug到具体某一根K线
            if (klineInfo.getDate().getTime() == DateUtil.toDate("2019-07-11 13:45:00", DateUtil.TIME_PATTERN_DISPLAY).getTime()) {
                System.out.println(JSONObject.toJSONString(klineInfo));
            }
            if ("0.0".equals(upAvgMap.get(klineInfo.getDate()).toString())) continue;
            if (!positionStatus) {  //如果空仓,则看是否需要建仓
                //买入条件：

                //如果最近200根k线出现涨幅大于3.2个点，则不建仓
                if (overGain(klineInfos, i, overGain, klineCount)) continue;

                //1、当前收盘K线的长期均线值 大于 短期均线的值      即：长期均线在短期均线的上面
                if (upAvgMap.get(klineInfo.getDate()) < downAvgMap.get(klineInfo.getDate())) continue;

                //2、当前收盘K线的长期均线的值  小于  上一根收盘K线的长期均线的值       即：长期均线往下走
                if (upAvgMap.get(klineInfo.getDate()) > upAvgMap.get(klineInfos.get(i - 1).getDate())) continue;

                //3、当前收盘K线的收盘价低于短期均线的收盘价     即价格跌破短期均线
                if (klineInfo.getClose() > downAvgMap.get(klineInfo.getDate())) continue;

                //4、短期均线翻下
                if (downAvgMap.get(klineInfo.getDate()) > downAvgMap.get(klineInfos.get(i - 1).getDate()))
                    continue;

                //5、当前收盘K线不能收长的下引线
                if (KlineInfoUtil.isDownLead(klineInfo, Double.valueOf(strategy.getUpperLeadFactor())))
                    continue;

                //6、当前收盘K线的上一个K线不能收下引线
                if (KlineInfoUtil.isDownLead(klineInfos.get(i - 1), Double.valueOf(strategy.getUpperLeadFactor())))
                    continue;

                sellOpen(result1, klineInfo);
                positionStatus = !positionStatus;
            } else {
                //卖出条件：

                //如果最近200根k线出现涨幅大于3.2个点,则清仓
                if (overGain(klineInfos, i, overGain, klineCount)) {
                    buyClose(result1, klineInfo);
                    positionStatus = !positionStatus;
                    continue;
                }
                //**保护价格，当价格跌破开仓多少止损**
                double startPrice = result1.get(result1.size() - 1).getClose();//建仓价格
                double protectionPrice = new BigDecimal(startPrice).add(new BigDecimal(startPrice).multiply(new BigDecimal(strategy.getProtectionDownFactor()))).doubleValue();
                if (klineInfo.getHigh() > protectionPrice) {
                    klineInfo.setClose(protectionPrice);
                    buyClose(result1, klineInfo);
                    positionStatus = !positionStatus;
                } else {
                    // 1、当前收盘K线的收盘价 高于 当前收盘K线的短期均线的值
                    if (klineInfo.getClose() < downAvgMap.get(klineInfo.getDate())) continue;
                    buyClose(result1, klineInfo);
                    positionStatus = !positionStatus;
                }
            }
        }
        return result1;
    }

    /**
     * 当超过一定的涨幅则不考虑开空
     *
     * @param klineInfos
     * @param i
     * @return
     */
    private static boolean overGain(List<KlineInfo> klineInfos, int i, double overGain, Integer klineCount) {
        for (int m = 0; m < klineCount; m++) {
            if (i - m < 0) break;
            if (overGain > 0) {
                if (klineInfos.get(i - m).getGain() >= overGain) {
                    return true;
                }
            } else {
                if (klineInfos.get(i - m).getGain() <= overGain) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * 卖出平仓
     *
     * @param result1
     * @param klineInfo
     * @return
     */
    private static void sellClose(List<KlineInfo> result1, KlineInfo klineInfo) {
        klineInfo.setBuySellStatus(KLineConstant.BUYSELLSTATUS_SELL);
        klineInfo.setIncomeRate(new BigDecimal(klineInfo.getClose()).subtract(new BigDecimal(result1.get(result1.size() - 1).getClose())).divide(new BigDecimal(result1.get(result1.size() - 1).getClose()), 6, BigDecimal.ROUND_DOWN).doubleValue());
        result1.add(klineInfo);
    }

    /**
     * 买入开仓
     *
     * @param result1
     * @param klineInfo
     * @return
     */
    private static void buyOpen(List<KlineInfo> result1, KlineInfo klineInfo) {
        klineInfo.setBuySellStatus(KLineConstant.BUYSELLSTATUS_BUY);
        result1.add(klineInfo);
    }

    /**
     * 卖出开仓
     *
     * @param result1
     * @param klineInfo
     * @return
     */
    private static void sellOpen(List<KlineInfo> result1, KlineInfo klineInfo) {
        klineInfo.setBuySellStatus(KLineConstant.BUYSELLSTATUS_SELL_OPEN);
        result1.add(klineInfo);
    }

    /**
     * 买入平仓
     *
     * @param result1
     * @param klineInfo
     * @return
     */
    private static void buyClose(List<KlineInfo> result1, KlineInfo klineInfo) {
        klineInfo.setBuySellStatus(KLineConstant.BUYSELLSTATUS_BUY_CLOSE);
        KlineInfo klineInfoPrevious = result1.get(result1.size() - 1);
        klineInfo.setIncomeRate(new BigDecimal(klineInfoPrevious.getClose()).subtract(new BigDecimal(klineInfo.getClose())).divide(new BigDecimal(klineInfoPrevious.getClose()), 6, BigDecimal.ROUND_DOWN).doubleValue());
        result1.add(klineInfo);
    }

    /**
     * 关键K线策略
     *
     * @param klineInfos
     * @param upMaNum
     * @param downMaNum
     * @param symbol
     * @param upperLeadFactor
     * @param gainFactor
     * @param protectionDownFactor
     * @param volFactory
     * @param upperLeadGain
     * @return
     */
    public static List<KlineInfo> strategykeyKline(List<KlineInfo> klineInfos, Integer upMaNum, Integer downMaNum, String symbol, double upperLeadFactor, double gainFactor, double protectionDownFactor, double volFactory, double upperLeadGain) {
        Map<Date, Double> upAvgMap = KlineInfoUtil.getAvg(klineInfos, upMaNum);
        Map<Date, Double> downAvgMap = KlineInfoUtil.getAvg(klineInfos, downMaNum);
        List<KlineInfo> result = dealStrategykeyKline(klineInfos, upMaNum, upAvgMap, downAvgMap, symbol, upperLeadFactor, gainFactor, protectionDownFactor, volFactory, upperLeadGain);
        return result;
    }

    /**
     * 处理关键K线的策略
     *
     * @param klineInfos
     * @param upMaNum
     * @param upAvgMap
     * @param downAvgMap
     * @param symbol
     * @param upperLeadFactor
     * @param gainFactor
     * @param protectionDownFactor
     * @param volFactory
     * @param upperLeadGain
     * @return
     */
    private static List<KlineInfo> dealStrategykeyKline(List<KlineInfo> klineInfos, int upMaNum, Map<Date, Double> upAvgMap, Map<Date, Double> downAvgMap,
                                                        String symbol, double upperLeadFactor, double gainFactor, double protectionDownFactor, double volFactory, double upperLeadGain) {
        KlineInfoUtil.emptyIncomeRateAndBuySellStatus(klineInfos);
        List<KlineInfo> result1 = new ArrayList<>();
        boolean positionStatus = false;
        for (int i = 0; i < klineInfos.size(); i++) {
            if (i < 2) continue;
            KlineInfo klineInfo = klineInfos.get(i);
            if (klineInfo.getDate().getTime() == DateUtil.toDate("2019-08-18 18:00:00", DateUtil.TIME_PATTERN_DISPLAY).getTime()) {
                System.out.println(JSONObject.toJSONString(klineInfo));
            }
            if ("0.0".equals(upAvgMap.get(klineInfo.getDate()).toString())) continue;
            if ("EOS".equals(symbol)) {
                if (!positionStatus) {  //如果空仓,则看是否需要建仓
                    //买入条件：
                    //10MA短期均线翻上，切价格站上该均线
                    if (downAvgMap.get(klineInfos.get(i - 1).getDate()) > downAvgMap.get(klineInfo.getDate())
                            && klineInfo.getClose() < downAvgMap.get(klineInfo.getDate())
                    ) continue;

                    if (klineInfo.getGain() < gainFactor) continue;
                    if (klineInfo.getVolume() / klineInfos.get(i - 1).getVolume() < volFactory) continue;

                    double maxHight = 0;
                    for (int j = i - upMaNum + 1; j < i; j++) {
                        if (maxHight < klineInfos.get(j).getHigh()) {
                            maxHight = klineInfos.get(j).getHigh();
                        }
                    }
                    if (klineInfo.getClose() < maxHight) continue;

                    buyOpen(result1, klineInfo);
                    positionStatus = !positionStatus;
                } else {
                    //卖出条件：
                    //**保护价格，当价格跌破开仓多少止损**
                    double startPrice = result1.get(result1.size() - 1).getClose();//建仓价格
                    double protectionPrice = new BigDecimal(startPrice).subtract(new BigDecimal(startPrice).multiply(new BigDecimal(protectionDownFactor))).doubleValue();
                    if (klineInfo.getLow() < protectionPrice) {
                        klineInfo.setClose(protectionPrice);
                        sellClose(result1, klineInfo);
                        positionStatus = !positionStatus;
                    } else {
                        // 1、当前收盘K线的收盘价 小于 当前收盘K线的短期均线的值
                        if (klineInfo.getClose() > downAvgMap.get(klineInfo.getDate())) continue;
                        sellClose(result1, klineInfo);
                        positionStatus = !positionStatus;
                    }
                }
            }
        }
        return result1;
    }
}
