package com.xfdmao.fcat.coin.huobi.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xfdmao.fcat.coin.base.entity.KlineInfo;
import com.xfdmao.fcat.coin.entity.Strategy;
import com.xfdmao.fcat.coin.entity.StrategyUser;
import com.xfdmao.fcat.coin.entity.Token;
import com.xfdmao.fcat.coin.huobi.contract.api.HbdmClient;
import com.xfdmao.fcat.coin.huobi.contract.api.HbdmRestApiV1;
import com.xfdmao.fcat.coin.huobi.contract.api.IHbdmRestApi;
import com.xfdmao.fcat.coin.huobi.globle.response.Kline;
import com.xfdmao.fcat.coin.po.PositionInfo;
import org.apache.http.HttpException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangfei on 2019/7/29.
 */
public class StrategyUtil {
    private static Logger logger = Logger.getLogger(StrategyUtil.class);
    /**
     * 各合约对应的最新的止损
     * key:用户名+symbol+contractType
     * value:止损K线对应的时间
     */
    private static Map<String, String> stopLossMap = new HashMap<>();

    /**
     * 查询是否持仓
     *
     * @param positionInfoList
     * @param symbol
     * @param contractType
     * @return
     */
    private static boolean queryPositionFlag(List<PositionInfo> positionInfoList, String symbol, String contractType, String direction) {
        if (positionInfoList == null || positionInfoList.size() == 0) {
            return false;
        } else {
            for (int i = 0; i < positionInfoList.size(); i++) {
                PositionInfo positionInfo = positionInfoList.get(i);
                if (symbol.equals(positionInfo.getSymbol()) && contractType.equals(positionInfo.getContract_type()) && direction.equals(positionInfo.getDirection())) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 查询持仓列表
     *
     * @param futurePostV1
     * @param symbol
     * @throws HttpException
     * @throws IOException
     */
    private static List<PositionInfo> queryPositionInfoList(IHbdmRestApi futurePostV1, String symbol) throws HttpException, IOException {
        List<PositionInfo> positionInfoList = new ArrayList<>();//持仓列表
        String positionInfo = futurePostV1.futureContractPositionInfo(symbol);
        logger.debug("获取用户持仓信息" + positionInfo);
        JSONObject jsonObject = JSONObject.parseObject(positionInfo);
        String status = jsonObject.getString("status");
        if ("ok".equals(status)) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            positionInfoList = jsonArray.toJavaList(PositionInfo.class);
        }
        return positionInfoList;
    }

    /**
     * 两条均线策略BTC:做多
     */
    public static void towMaBTC(String url_prex, Strategy strategy, StrategyUser strategyUser, Token token) throws Exception {
        logger.debug(JSONObject.toJSONString(strategy));
        String symbol = strategy.getSymbol();//交易对
        String contractType = strategy.getContractType();//合约类型
        int upMa = Integer.valueOf(strategy.getUpMa());//长期均线
        int downMa = Integer.valueOf(strategy.getDownMa());//短期均线
        String period = strategy.getPeriod();//时间周期
        int leverRate = Integer.valueOf(strategy.getLeverRate());//合约倍数
        long volume = Long.valueOf(strategyUser.getVolume());//每次买卖的张数
        double upperLeadFactor = Double.valueOf(strategy.getUpperLeadFactor());  //上引线的判断因子
        double gainFactor = Double.valueOf(strategy.getGainFactor());//涨幅因子
        double protectionDownFactor = Double.valueOf(strategy.getProtectionDownFactor());//保护价，当下跌幅度超过该跌幅，自动止损
        List<PositionInfo> positionInfoList;//所有持仓列表
        boolean positionFlag;//当前交易对是否持仓
        long available;//可平仓数量
        String direction = "buy";//做空sell，做多buy
        double downRate = -0.0078;//最近5根收盘K线不能出现大于0.0078个点的跌幅
        double towKlineGain = -0.0082;//前两根K线不能全部下跌并且下跌的总和不能大于0.0082
        double overGain = -0.023;
        int klineCount = 100;

        /**
         * get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
         */
        logger.debug(symbol + " 季度合约开始");
        String api_key = token.getApiKey(); // huobi申请的apiKey,API调试过程中有问题
        String secret_key = token.getApiSecret(); // huobi申请的secretKey
        IHbdmRestApi futureGetV1 = new HbdmRestApiV1(url_prex);
        IHbdmRestApi futurePostV1 = new HbdmRestApiV1(url_prex, api_key, secret_key);

        //撤销所有持仓
        String contractCancelall = futurePostV1.futureContractCancelall(symbol);
        logger.debug("合约全部撤单:" + contractCancelall);

        //获取持仓信息
        positionInfoList = queryPositionInfoList(futurePostV1, symbol);
        positionFlag = queryPositionFlag(positionInfoList, symbol, contractType, direction);
        logger.debug(symbol + " positionFlag：" + positionFlag);
        available = queryAvailable(positionInfoList, symbol, contractType, direction);
        logger.debug(symbol + " 可平仓数量：" + available);

        // 获取K线数据，最新的那条K线是未收盘的K线
        String historyKline = futureGetV1.futureMarketHistoryKline(symbol + "_CQ", period, (klineCount + 2) + "");
        JSONObject jsonObject = JSONObject.parseObject(historyKline);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Kline> klines = jsonArray.toJavaList(Kline.class);
        KlineUtil.sort(klines);
        logger.debug("已收盘的K线：" + JSONObject.toJSONString(klines));

        //当前收盘K线的长期均线的值
        double upAvg = getFirstAvg(upMa, klines);
        //当前收盘K线的短期均线的值
        double downAvg = getFirstAvg(downMa, klines);
        //上一根收盘K线的长期均线的值
        double upAvgPrevious = getPreviousAvg(upMa, klines);
        //上一根收盘K线的短期均线的值
        double downAvgPrevious = getPreviousAvg(downMa, klines);

        double startPrice = getStartPrice(positionInfoList, symbol, contractType, direction);
        double protectionPrice = new BigDecimal(startPrice).subtract(new BigDecimal(startPrice).multiply(new BigDecimal(protectionDownFactor))).doubleValue();

        if (positionFlag) {//当前已经建仓，需要判断是否需要平仓
            //4、当跌幅超过0.023，则平多
            if(KlineUtil.overGain(klines,overGain,klineCount)){
                sellClose(symbol, contractType, leverRate, available, futurePostV1);
                return;
            }

            //**保护伞，当价格跌破开仓均价**
            if (klines.get(0).getLow() < protectionPrice) {
                sellClose(symbol, contractType, leverRate, available, futurePostV1);
                stopLossMap.put(strategyUser.getUsername() + symbol + contractType + direction, klines.get(0).getId() + "");
                return;
            }

            // 卖出条件：
            // 1、当前收盘K线的收盘价 小于 当前收盘K线的短期均线的值
            //2、如果收盘K线收上引线，并且涨幅为负
            //3、如果收盘K线收上引线，并且涨幅大于1.1个点
            if (klines.get(1).getClose() < downAvg ||
                    (KlineUtil.getUpLeadGain(klines.get(1)) >= 0.003 && KlineUtil.getGain(klines.get(1)) <= 0)  ||
                    KlineUtil.getUpLeadGain(klines.get(1)) >= 0.011) {
                sellClose(symbol, contractType, leverRate, available, futurePostV1);
                return;
            }

        }
        if (!positionFlag || available < volume) {  //当前是空仓，需要判断是否需要建仓
            //买入条件：

            //10、当最近100根K线内出现跌幅超过0.023，则不操作
            if(KlineUtil.overGain(klines,overGain,klineCount))return;

            //如果当前k线没有触发过止损
            if ((klines.get(0).getId() + "").equals(stopLossMap.get(strategyUser.getUsername() + symbol + contractType + direction)))
                return;

            //1、当前收盘K线的长期均线值 小于 短期均线的值 (短期均线站上长期均线)
            if (upAvg > downAvg) return;

            //2、当前收盘K线的长期均线的值  大于  上一根收盘K线的长期均线的值（即：长期均线往上走）
            if (upAvg < upAvgPrevious) return;

            //3、当前收盘K线的收盘价高于短期均线的收盘价 (收盘价站上短期均线)
            if (klines.get(1).getClose() < downAvg) return;

            //4、当前收盘K线不能收长的上引线
            if (KlineUtil.isUpperLead(klines.get(1), upperLeadFactor)) return;

            //5、当前收盘K线的上一个K线不能收上引线
            if (KlineUtil.isUpperLead(klines.get(2), upperLeadFactor)) return;

            //6、当前收盘K线涨幅大于gainFactor
            if (KlineUtil.getGain(klines.get(1)) < gainFactor) {
                if (!(KlineUtil.getGain(klines.get(1)) > 0.003 &&
                        KlineUtil.getGain(klines.get(2)) > 0 && KlineUtil.getGain(klines.get(3)) > 0 &&
                        KlineUtil.getGain(klines.get(2)) + KlineUtil.getGain(klines.get(3)) > 0.0015)) {
                    return;
                }
            }

            //7、最近5根收盘K线不能出现大于0.0078个点的跌幅
            if (KlineUtil.getGain(klines.get(1)) < downRate ||
                    KlineUtil.getGain(klines.get(2)) < downRate ||
                    KlineUtil.getGain(klines.get(3)) < downRate ||
                    KlineUtil.getGain(klines.get(4)) < downRate ||
                    KlineUtil.getGain(klines.get(5)) < downRate
            ) {
                return;
            }

            //8、前两根K线不能全部下跌并且下跌的总和不能大于0.0082
            if (KlineUtil.getGain(klines.get(2)) < 0 && KlineUtil.getGain(klines.get(3)) < 0 &&
                    (KlineUtil.getGain(klines.get(2)) + KlineUtil.getGain(klines.get(3))) < towKlineGain) {
                return;
            }

            //9、K线涨幅大于0.014则不建仓
            if(KlineUtil.getGain(klines.get(1))>0.014){
                return;
            }

            buyOpen(symbol, contractType, leverRate, volume, available, futurePostV1);
        }
        logger.debug(symbol + " 季度合约结束");
    }

    /**
     * 上一根收盘K线的ma均线的值
     *
     * @param ma
     * @param klines
     * @return
     */
    private static double getPreviousAvg(int ma, List<Kline> klines) {
        List<Kline> klinesUpPrevious = new ArrayList<>();
        for (int i = 0; i < ma; i++) {
            klinesUpPrevious.add(klines.get(i + 2));
        }
        return KlineUtil.queryAvg(klinesUpPrevious);
    }

    /**
     * 获取第一根收盘K线ma均线的值
     *
     * @param ma
     * @param klines
     * @return
     */
    private static double getFirstAvg(int ma, List<Kline> klines) {
        List<Kline> klinesUp = new ArrayList<>();
        for (int i = 0; i < ma; i++) {
            klinesUp.add(klines.get(i + 1));
        }
        return KlineUtil.queryAvg(klinesUp);
    }

    /**
     * 获取持仓价格
     *
     * @param symbol
     * @param contractType
     * @return
     */
    private static double getStartPrice(List<PositionInfo> positionInfoList, String symbol, String contractType, String direction) {
        double startPrice = 0;
        for (int i = 0; i < positionInfoList.size(); i++) {
            PositionInfo positionInfo = positionInfoList.get(i);
            if (symbol.equals(positionInfo.getSymbol()) && contractType.equals(positionInfo.getContract_type()) && direction.equals(positionInfo.getDirection())) {
                startPrice = positionInfo.getCost_hold();
                break;
            }
        }
        return startPrice;
    }

    /**
     * 两天均线策略EOS,做多
     */
    public static void towMaEOS(String url_prex, Strategy strategy, StrategyUser strategyUser, Token token) throws Exception {
        logger.debug(JSONObject.toJSONString(strategy));
        String symbol = strategy.getSymbol();//交易对
        String contractType = strategy.getContractType();//合约类型
        int upMa = Integer.valueOf(strategy.getUpMa());//长期均线
        int downMa = Integer.valueOf(strategy.getDownMa());//短期均线
        String period = strategy.getPeriod();//时间周期
        int leverRate = Integer.valueOf(strategy.getLeverRate());//合约倍数
        long volume = Long.valueOf(strategyUser.getVolume());//每次买卖的张数
        double upperLeadFactor = Double.valueOf(strategy.getUpperLeadFactor());  //上引线的判断因子
        double gainFactor = Double.valueOf(strategy.getGainFactor());//涨幅因子
        double protectionDownFactor = Double.valueOf(strategy.getProtectionDownFactor());//保护价，当下跌幅度超过该跌幅，自动止损
        List<PositionInfo> positionInfoList;//所有持仓列表
        boolean positionFlag;//当前交易对是否持仓
        long available = 0;//可平仓数量
        String direction = "buy";//做空sell，做多buy

        /**
         * get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
         */
        logger.debug(symbol + "季度合约开始");
        String api_key = token.getApiKey(); // huobi申请的apiKey,API调试过程中有问题
        String secret_key = token.getApiSecret(); // huobi申请的secretKey
        IHbdmRestApi futureGetV1 = new HbdmRestApiV1(url_prex);
        IHbdmRestApi futurePostV1 = new HbdmRestApiV1(url_prex, api_key, secret_key);

        //撤销所有持仓
        String contractCancelall = futurePostV1.futureContractCancelall(symbol);
        logger.debug("合约全部撤单:" + contractCancelall);

        //获取持仓信息
        positionInfoList = queryPositionInfoList(futurePostV1, symbol);
        positionFlag = queryPositionFlag(positionInfoList, symbol, contractType, direction);
        logger.debug(symbol + " positionFlag：" + positionFlag);
        available = queryAvailable(positionInfoList, symbol, contractType, direction);
        logger.debug(symbol + " 可平仓数量：" + available);

        // 获取K线数据
        String historyKline = futureGetV1.futureMarketHistoryKline(symbol + "_CQ", period, (upMa + 3) + "");
        JSONObject jsonObject = JSONObject.parseObject(historyKline);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Kline> klines = jsonArray.toJavaList(Kline.class);
        KlineUtil.sort(klines);
        logger.debug("已收盘的K线：" + JSONObject.toJSONString(klines));

        double upAvg = getFirstAvg(upMa, klines);

        //当前收盘K线的短期均线的值
        double downAvg = getFirstAvg(downMa, klines);

        double upAvgPrevious = getPreviousAvg(upMa, klines);

        double downAvgPrevious = getPreviousAvg(downMa, klines);

        List<Kline> klinesDownThird = new ArrayList<>();
        for (int i = 0; i < downMa; i++) {
            klinesDownThird.add(klines.get(i + 3));
        }
        double downAvgThird = KlineUtil.queryAvg(klinesDownThird);  //上上一根收盘K线的短期均线的值


        double startPrice = getStartPrice(positionInfoList, symbol, contractType, direction);
        double protectionPrice = new BigDecimal(startPrice).subtract(new BigDecimal(startPrice).multiply(new BigDecimal(protectionDownFactor))).doubleValue();

        //如果建仓了，则查看是否需要清仓
        if (positionFlag) {
            //**保护伞，当价格跌破开仓均价**
            if (klines.get(0).getLow() < protectionPrice) {
                sellClose(symbol, contractType, leverRate, available, futurePostV1);
                stopLossMap.put(strategyUser.getUsername() + symbol + contractType + direction, klines.get(0).getId() + "");
                return;
            }

            // 卖出条件：
            // 1、当前收盘K线的收盘价 小于 当前收盘K线的短期均线的值
            if (downAvg > klines.get(1).getClose()) {
                sellClose(symbol, contractType, leverRate, available, futurePostV1);
                return;
            }
        }
        if (!positionFlag || available < volume) {//如果空仓，则考虑建仓
            //买入条件：

            //如果当前k线没有触发过止损
            if ((klines.get(0).getId() + "").equals(stopLossMap.get(strategyUser.getUsername() + symbol + contractType + direction)))
                return;

            // 1、当前收盘K线的长期均线值 小于 短期均线的值
            if (upAvg > downAvg) return;

            //2、当前收盘K线的长期均线的值  大于  当前收盘K线的长期均线的值       即：长期均线网上走
            if (upAvg < upAvgPrevious) return;

            //3、当前收盘K线的收盘价高于短期均线的收盘价
            if (klines.get(1).getClose() < downAvg) return;

/*            //4、当前收盘K线不能收长的上引线
            if(KlineUtil.isUpperLead(klines.get(1),upperLeadFactor))return;

            //5、当前收盘K线的上一个K线不能收上引线
            if(KlineUtil.isUpperLead(klines.get(2),upperLeadFactor))return;*/

            //4、当前收盘的K线或上一根收盘的K线不能收上引线，振幅不能大于振幅因子。如果大于，则当前K线涨幅不能小于0.0072且当前K线量能是上一根K线量能的2.5倍以上
            if (KlineUtil.isUpperLead(klines.get(1), upperLeadFactor) || KlineUtil.isUpperLead(klines.get(2), upperLeadFactor)) {
                if (!(KlineUtil.getGain(klines.get(1)) > 0.0072 && (klines.get(1).getVol() - klines.get(2).getVol()) / klines.get(2).getVol() > 2.5)) {
                    return;
                }
            }


            //6、当前收盘K线涨幅大于涨幅影子
            if (KlineUtil.getGain(klines.get(1)) < gainFactor) return;

            //8、下跌那根K线的量不能是最近三根K线的最大量
            if (KlineUtil.bigVolDown(klines.get(1), klines.get(2), klines.get(3))) return;

            //9、短期均线翻上
            if (downAvg < downAvgPrevious) return;

            //10、最近三根K线中不能有两根以上的K线下跌
            if (KlineUtil.twoDown(klines.get(1), klines.get(2), klines.get(3), downAvg, downAvgPrevious, downAvgThird))
                return;

            buyOpen(symbol, contractType, leverRate, volume, available, futurePostV1);
        }
        logger.debug(symbol + " 季度合约结束");
    }

    /**
     * 均线策略：做空BTC
     */
    public static void towMaBTCSell(String url_prex, Strategy strategy, StrategyUser strategyUser, Token token) throws Exception {
        logger.debug(JSONObject.toJSONString(strategy));
        String symbol = strategy.getSymbol();//交易对
        String contractType = strategy.getContractType();//合约类型
        int upMa = Integer.valueOf(strategy.getUpMa());//长期均线
        int downMa = Integer.valueOf(strategy.getDownMa());//短期均线
        String period = strategy.getPeriod();//时间周期
        int leverRate = Integer.valueOf(strategy.getLeverRate());//合约倍数
        long volume = Long.valueOf(strategyUser.getVolume());//每次买卖的张数
        double leadFactor = Double.valueOf(strategy.getUpperLeadFactor());  //上引线的判断因子
        double gainFactor = Double.valueOf(strategy.getGainFactor());//涨幅因子
        double protectionDownFactor = Double.valueOf(strategy.getProtectionDownFactor());//保护价，当下跌幅度超过该跌幅，自动止损
        List<PositionInfo> positionInfoList;//所有持仓列表
        boolean positionFlag;//当前交易对是否持仓
        long available = 0;//可平仓数量
        String direction = "sell";//做空sell，做多buy
        double overGain = 0.032;
        int klineCount = 200;

        /**
         * get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
         */
        logger.debug(symbol + "季度合约开始 做空");
        String api_key = token.getApiKey(); // huobi申请的apiKey,API调试过程中有问题
        String secret_key = token.getApiSecret(); // huobi申请的secretKey
        IHbdmRestApi futureGetV1 = new HbdmRestApiV1(url_prex);
        IHbdmRestApi futurePostV1 = new HbdmRestApiV1(url_prex, api_key, secret_key);

        //撤销所有持仓
        String contractCancelall = futurePostV1.futureContractCancelall(symbol);
        logger.debug("合约全部撤单:" + contractCancelall);

        //获取持仓信息
        positionInfoList = queryPositionInfoList(futurePostV1, symbol);
        positionFlag = queryPositionFlag(positionInfoList, symbol, contractType, direction);
        logger.debug(symbol + " positionFlag：" + positionFlag);
        available = queryAvailable(positionInfoList, symbol, contractType, direction);
        logger.debug(symbol + " 可平仓数量：" + available);

        // 获取K线数据
        String historyKline = futureGetV1.futureMarketHistoryKline(symbol + "_CQ", period, (klineCount + 3) + "");
        JSONObject jsonObject = JSONObject.parseObject(historyKline);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Kline> klines = jsonArray.toJavaList(Kline.class);
        KlineUtil.sort(klines);
        logger.debug("已收盘的K线：" + JSONObject.toJSONString(klines));

        double upAvg = getFirstAvg(upMa, klines);

        //当前收盘K线的短期均线的值
        double downAvg = getFirstAvg(downMa, klines);

        double upAvgPrevious = getPreviousAvg(upMa, klines);

        double downAvgPrevious = getPreviousAvg(downMa, klines);

        List<Kline> klinesDownThird = new ArrayList<>();
        for (int i = 0; i < downMa; i++) {
            klinesDownThird.add(klines.get(i + 3));
        }
        double downAvgThird = KlineUtil.queryAvg(klinesDownThird);  //上上一根收盘K线的短期均线的值


        double startPrice = getStartPrice(positionInfoList, symbol, contractType, direction);
        double protectionPrice = new BigDecimal(startPrice).add(new BigDecimal(startPrice).multiply(new BigDecimal(protectionDownFactor))).doubleValue();

        //如果建仓了，则查看是否需要清仓
        if (positionFlag) {

            //如果最近200根k线出现涨幅大于3.2个点，则清仓
            if(KlineUtil.overGain(klines,overGain,klineCount)){
                buyClose(symbol, contractType, leverRate, available, futurePostV1);
                stopLossMap.put(strategyUser.getUsername() + symbol + contractType + direction, klines.get(0).getId() + "");
                return;
            }


            //**保护伞，当价格上涨开仓均价多少止损**
            if (klines.get(0).getHigh() > protectionPrice) {
                buyClose(symbol, contractType, leverRate, available, futurePostV1);
                stopLossMap.put(strategyUser.getUsername() + symbol + contractType + direction, klines.get(0).getId() + "");
                return;
            }

            // 卖出条件：
            // 1、当前收盘K线的收盘价 大于 当前收盘K线的短期均线的值
            if (klines.get(1).getClose() > downAvg) {
                buyClose(symbol, contractType, leverRate, available, futurePostV1);
                return;
            }
        }
        if (!positionFlag || available < volume) {//如果空仓，则考虑建仓
            //买入条件：
            //如果最近200根k线出现涨幅大于3.2个点，则不建仓
            if(KlineUtil.overGain(klines,overGain,klineCount))return;

            //如果当前k线没有触发过止损
            if ((klines.get(0).getId() + "").equals(stopLossMap.get(strategyUser.getUsername() + symbol + contractType + direction)))
                return;

            // 1、当前收盘K线的长期均线值 大于 短期均线的值
            if (upAvg < downAvg) return;

            //2、当前收盘K线的长期均线的值  小于  上一根收盘K线的长期均线的值       即：长期均线往下走
            if (upAvg > upAvgPrevious) return;

            //3、当前收盘K线的收盘价低于短期均线的收盘价
            if (klines.get(1).getClose() > downAvg) return;

            //4、短期均线翻下
            if (downAvg > downAvgPrevious) return;

            //5、当前收盘K线不能收长的下引线
            if (KlineUtil.isDownLead(klines.get(1), leadFactor)) return;

            //6、当前收盘K线的上一个K线不能收下引线
            if (KlineUtil.isDownLead(klines.get(2), leadFactor)) return;

            //7、最近20根收盘K线涨幅小于1个点
            if (KlineUtil.nearOverGainFactor(klines,20,Double.valueOf(strategy.getGainFactor()))) return;

            sellOpen(symbol, contractType, leverRate, volume, available, futurePostV1);
        }
        logger.debug(symbol + " 季度合约做空结束");
    }


    /**
     * 均线策略：做空EOS
     */
    public static void towMaEOSSell(String url_prex, Strategy strategy, StrategyUser strategyUser, Token token) throws Exception {
        logger.debug(JSONObject.toJSONString(strategy));
        String symbol = strategy.getSymbol();//交易对
        String contractType = strategy.getContractType();//合约类型
        int upMa = Integer.valueOf(strategy.getUpMa());//长期均线
        int downMa = Integer.valueOf(strategy.getDownMa());//短期均线
        String period = strategy.getPeriod();//时间周期
        int leverRate = Integer.valueOf(strategy.getLeverRate());//合约倍数
        long volume = Long.valueOf(strategyUser.getVolume());//每次买卖的张数
        double leadFactor = Double.valueOf(strategy.getUpperLeadFactor());  //上引线的判断因子
        double gainFactor = Double.valueOf(strategy.getGainFactor());//涨幅因子
        double protectionDownFactor = Double.valueOf(strategy.getProtectionDownFactor());//保护价，当下跌幅度超过该跌幅，自动止损
        List<PositionInfo> positionInfoList;//所有持仓列表
        boolean positionFlag;//当前交易对是否持仓
        long available = 0;//可平仓数量
        String direction = "sell";//做空sell，做多buy
        double overGain = 0.032;
        int klineCount = 200;

        /**
         * get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
         */
        logger.debug(symbol + "季度合约开始 做空");
        String api_key = token.getApiKey(); // huobi申请的apiKey,API调试过程中有问题
        String secret_key = token.getApiSecret(); // huobi申请的secretKey
        IHbdmRestApi futureGetV1 = new HbdmRestApiV1(url_prex);
        IHbdmRestApi futurePostV1 = new HbdmRestApiV1(url_prex, api_key, secret_key);

        //撤销所有持仓
        String contractCancelall = futurePostV1.futureContractCancelall(symbol);
        logger.debug("合约全部撤单:" + contractCancelall);

        //获取持仓信息
        positionInfoList = queryPositionInfoList(futurePostV1, symbol);
        positionFlag = queryPositionFlag(positionInfoList, symbol, contractType, direction);
        logger.debug(symbol + " positionFlag：" + positionFlag);
        available = queryAvailable(positionInfoList, symbol, contractType, direction);
        logger.debug(symbol + " 可平仓数量：" + available);

        // 获取K线数据
        String historyKline = futureGetV1.futureMarketHistoryKline(symbol + "_CQ", period, (klineCount + 3) + "");
        JSONObject jsonObject = JSONObject.parseObject(historyKline);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Kline> klines = jsonArray.toJavaList(Kline.class);
        KlineUtil.sort(klines);
        logger.debug("已收盘的K线：" + JSONObject.toJSONString(klines));

        double upAvg = getFirstAvg(upMa, klines);

        //当前收盘K线的短期均线的值
        double downAvg = getFirstAvg(downMa, klines);

        double upAvgPrevious = getPreviousAvg(upMa, klines);

        double downAvgPrevious = getPreviousAvg(downMa, klines);

        List<Kline> klinesDownThird = new ArrayList<>();
        for (int i = 0; i < downMa; i++) {
            klinesDownThird.add(klines.get(i + 3));
        }
        double downAvgThird = KlineUtil.queryAvg(klinesDownThird);  //上上一根收盘K线的短期均线的值


        double startPrice = getStartPrice(positionInfoList, symbol, contractType, direction);
        double protectionPrice = new BigDecimal(startPrice).add(new BigDecimal(startPrice).multiply(new BigDecimal(protectionDownFactor))).doubleValue();

        //如果建仓了，则查看是否需要清仓
        if (positionFlag) {

            //如果最近200根k线出现涨幅大于3.2个点，则清仓
            if(KlineUtil.overGain(klines,overGain,klineCount)){
                buyClose(symbol, contractType, leverRate, available, futurePostV1);
                stopLossMap.put(strategyUser.getUsername() + symbol + contractType + direction, klines.get(0).getId() + "");
                return;
            }


            //**保护伞，当价格上涨开仓均价多少止损**
            if (klines.get(0).getHigh() > protectionPrice) {
                buyClose(symbol, contractType, leverRate, available, futurePostV1);
                stopLossMap.put(strategyUser.getUsername() + symbol + contractType + direction, klines.get(0).getId() + "");
                return;
            }

            // 卖出条件：
            // 1、当前收盘K线的收盘价 大于 当前收盘K线的短期均线的值
            if (klines.get(1).getClose() > downAvg) {
                buyClose(symbol, contractType, leverRate, available, futurePostV1);
                return;
            }
        }
        if (!positionFlag || available < volume) {//如果空仓，则考虑建仓
            //买入条件：
            //如果最近200根k线出现涨幅大于3.2个点，则不建仓
            if(KlineUtil.overGain(klines,overGain,klineCount))return;

            //如果当前k线没有触发过止损
            if ((klines.get(0).getId() + "").equals(stopLossMap.get(strategyUser.getUsername() + symbol + contractType + direction)))
                return;

            // 1、当前收盘K线的长期均线值 大于 短期均线的值
            if (upAvg < downAvg) return;

            //2、当前收盘K线的长期均线的值  小于  上一根收盘K线的长期均线的值       即：长期均线往下走
            if (upAvg > upAvgPrevious) return;

            //3、当前收盘K线的收盘价低于短期均线的收盘价
            if (klines.get(1).getClose() > downAvg) return;

            //4、短期均线翻下
            if (downAvg > downAvgPrevious) return;

            //5、当前收盘K线不能收长的下引线
            if (KlineUtil.isDownLead(klines.get(1), leadFactor)) return;

            //6、当前收盘K线的上一个K线不能收下引线
            if (KlineUtil.isDownLead(klines.get(2), leadFactor)) return;

            //7、当前收盘K线涨幅小于gainFactor
            if (KlineUtil.getGain(klines.get(1)) > gainFactor) return;

            sellOpen(symbol, contractType, leverRate, volume, available, futurePostV1);
        }
        logger.debug(symbol + " 季度合约做空结束");
    }


    /**
     * 查询可用的平仓数量
     *
     * @param positionInfoList
     * @param symbol
     * @param contractType
     * @return
     */
    private static long queryAvailable(List<PositionInfo> positionInfoList, String symbol, String contractType, String direction) {
        long result = 0;
        if (positionInfoList == null || positionInfoList.size() == 0) {
        } else {
            for (int i = 0; i < positionInfoList.size(); i++) {
                PositionInfo positionInfo = positionInfoList.get(i);
                if (symbol.equals(positionInfo.getSymbol()) && contractType.equals(positionInfo.getContract_type()) && direction.equals(positionInfo.getDirection())) {
                    result = new Double(positionInfo.getAvailable()).longValue();
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 卖出平多
     *
     * @param symbol
     * @param contractType
     * @param leverRate
     * @param volume
     * @param futurePostV1
     * @throws HttpException
     * @throws IOException
     */
    private static boolean sellClose(String symbol, String contractType, int leverRate, long volume, IHbdmRestApi futurePostV1) throws HttpException, IOException {
        String contractOrder = futurePostV1.futureContractOrder(symbol, contractType, "", "", "", volume + "",
                "sell", "close", leverRate + "", "optimal_5");
        logger.debug("合约下单（卖出平仓）：" + contractOrder);
        String status = JSONObject.parseObject(contractOrder).getString("status");
        if ("ok".equals(status)) {
            return true;
        }
        return false;
    }

    /**
     * 买入开多
     *
     * @param symbol
     * @param contractType
     * @param leverRate
     * @param volume
     * @param futurePostV1
     * @throws HttpException
     * @throws IOException
     */
    private static boolean buyOpen(String symbol, String contractType, int leverRate, long volume, long available, IHbdmRestApi futurePostV1) throws HttpException, IOException {
        String contractOrder = futurePostV1.futureContractOrder(symbol, contractType, "", "", "", (volume - available) + "",
                "buy", "open", leverRate + "", "optimal_5");
        logger.debug("合约下单（买入开仓）：" + contractOrder);
        String status = JSONObject.parseObject(contractOrder).getString("status");
        if ("ok".equals(status)) {
            return true;
        }
        return false;
    }

    /**
     * 买入平空
     *
     * @param symbol
     * @param contractType
     * @param leverRate
     * @param volume
     * @param futurePostV1
     * @throws HttpException
     * @throws IOException
     */
    private static boolean buyClose(String symbol, String contractType, int leverRate, long volume, IHbdmRestApi futurePostV1) throws HttpException, IOException {
        String contractOrder = futurePostV1.futureContractOrder(symbol, contractType, "", "", "", volume + "",
                "buy", "close", leverRate + "", "optimal_5");
        logger.debug("合约下单（卖出平仓）：" + contractOrder);
        String status = JSONObject.parseObject(contractOrder).getString("status");
        if ("ok".equals(status)) {
            return true;
        }
        return false;
    }

    /**
     * 卖出开空
     *
     * @param symbol
     * @param contractType
     * @param leverRate
     * @param volume
     * @param futurePostV1
     * @throws HttpException
     * @throws IOException
     */
    private static boolean sellOpen(String symbol, String contractType, int leverRate, long volume, long available, IHbdmRestApi futurePostV1) throws HttpException, IOException {
        String contractOrder = futurePostV1.futureContractOrder(symbol, contractType, "", "", "", (volume - available) + "",
                "sell", "open", leverRate + "", "optimal_5");
        logger.debug("合约下单（买入开仓）：" + contractOrder);
        String status = JSONObject.parseObject(contractOrder).getString("status");
        if ("ok".equals(status)) {
            return true;
        }
        return false;
    }
    //String historyKline = "{\"ch\":\"market.EOS_CQ.kline.15min\",\"data\":[{\"amount\":382169.471940235656770057931306090711111403,\"close\":4.286,\"count\":583,\"high\":4.29,\"id\":1564904700,\"low\":4.28,\"open\":4.285,\"vol\":163722},{\"amount\":220190.6898975105501997895662511313499575096,\"close\":4.291,\"count\":617,\"high\":4.293,\"id\":1564905600,\"low\":4.28,\"open\":4.286,\"vol\":94422},{\"amount\":178794.8194331697251014720621310775276335884,\"close\":4.295,\"count\":559,\"high\":4.298,\"id\":1564906500,\"low\":4.286,\"open\":4.29,\"vol\":76758},{\"amount\":171205.867803322848592736113210613714635838,\"close\":4.286,\"count\":432,\"high\":4.299,\"id\":1564907400,\"low\":4.285,\"open\":4.295,\"vol\":73490},{\"amount\":173342.653702344131851797417955897327912695,\"close\":4.284,\"count\":462,\"high\":4.291,\"id\":1564908300,\"low\":4.282,\"open\":4.286,\"vol\":74286},{\"amount\":312558.7474654158044716508837418404487176062,\"close\":4.277,\"count\":618,\"high\":4.289,\"id\":1564909200,\"low\":4.268,\"open\":4.284,\"vol\":133692},{\"amount\":431689.901488770264254092822825030043036895,\"close\":4.297,\"count\":978,\"high\":4.298,\"id\":1564910100,\"low\":4.274,\"open\":4.276,\"vol\":185050},{\"amount\":180946.2981945981365484500954667490000856264,\"close\":4.296,\"count\":558,\"high\":4.299,\"id\":1564911000,\"low\":4.292,\"open\":4.297,\"vol\":77730},{\"amount\":559110.3711547472623413183415709224696295332,\"close\":4.316,\"count\":1356,\"high\":4.319,\"id\":1564911900,\"low\":4.296,\"open\":4.296,\"vol\":241062},{\"amount\":342814.9564590040495336313822891340540920798,\"close\":4.31,\"count\":855,\"high\":4.323,\"id\":1564912800,\"low\":4.305,\"open\":4.316,\"vol\":147814},{\"amount\":149103.6472795905315489187659468704132426592,\"close\":4.31,\"count\":542,\"high\":4.313,\"id\":1564913700,\"low\":4.302,\"open\":4.309,\"vol\":64220},{\"amount\":104578.6539162161458470045092694235955702902,\"close\":4.313,\"count\":375,\"high\":4.314,\"id\":1564914600,\"low\":4.306,\"open\":4.31,\"vol\":45072},{\"amount\":333519.5635517334579104489243813371630665344,\"close\":4.316,\"count\":754,\"high\":4.322,\"id\":1564915500,\"low\":4.314,\"open\":4.314,\"vol\":144008},{\"amount\":148644.4362925731121239801074614503576409678,\"close\":4.311,\"count\":293,\"high\":4.317,\"id\":1564916400,\"low\":4.308,\"open\":4.316,\"vol\":64098},{\"amount\":187463.884682096196248867755344251036854571,\"close\":4.31,\"count\":420,\"high\":4.315,\"id\":1564917300,\"low\":4.31,\"open\":4.31,\"vol\":80824},{\"amount\":186811.9407241345795244450389536299064291494,\"close\":4.309,\"count\":421,\"high\":4.312,\"id\":1564918200,\"low\":4.305,\"open\":4.31,\"vol\":80472},{\"amount\":334263.3053058908357757121042484544247683206,\"close\":4.295,\"count\":751,\"high\":4.309,\"id\":1564919100,\"low\":4.288,\"open\":4.309,\"vol\":143562},{\"amount\":230319.77344801926511362883618817795665119,\"close\":4.3,\"count\":549,\"high\":4.307,\"id\":1564920000,\"low\":4.29,\"open\":4.295,\"vol\":98988},{\"amount\":237824.3912045684394305666646034695599239868,\"close\":4.303,\"count\":522,\"high\":4.308,\"id\":1564920900,\"low\":4.293,\"open\":4.3,\"vol\":102290},{\"amount\":302121.8996595844641817032055512599783326152,\"close\":4.285,\"count\":721,\"high\":4.305,\"id\":1564921800,\"low\":4.283,\"open\":4.304,\"vol\":129708},{\"amount\":599892.614993395176868265173629929821493314,\"close\":4.283,\"count\":1468,\"high\":4.29,\"id\":1564922700,\"low\":4.268,\"open\":4.284,\"vol\":256728},{\"amount\":508338.8985930415209957628556604063406442408,\"close\":4.315,\"count\":1165,\"high\":4.323,\"id\":1564923600,\"low\":4.281,\"open\":4.283,\"vol\":218750},{\"amount\":807819.5888652550528579138140263493654164682,\"close\":4.315,\"count\":1828,\"high\":4.33,\"id\":1564924500,\"low\":4.308,\"open\":4.315,\"vol\":348900},{\"amount\":310391.2023106259222845791359366433318322258,\"close\":4.309,\"count\":851,\"high\":4.318,\"id\":1564925400,\"low\":4.3,\"open\":4.314,\"vol\":133732},{\"amount\":214033.2904142646458226069917091866045679942,\"close\":4.305,\"count\":614,\"high\":4.31,\"id\":1564926300,\"low\":4.298,\"open\":4.309,\"vol\":92090},{\"amount\":287822.4150706289839504836339104269387728732,\"close\":4.314,\"count\":674,\"high\":4.316,\"id\":1564927200,\"low\":4.306,\"open\":4.306,\"vol\":124096},{\"amount\":157829.76314319673911160170150377424355577,\"close\":4.308,\"count\":455,\"high\":4.317,\"id\":1564928100,\"low\":4.302,\"open\":4.313,\"vol\":68016},{\"amount\":1195600.327911595262169399318602060210034025,\"close\":4.345,\"count\":2255,\"high\":4.371,\"id\":1564929000,\"low\":4.304,\"open\":4.306,\"vol\":519092},{\"amount\":972920.3749399025247389931883532085251128138,\"close\":4.341,\"count\":1896,\"high\":4.357,\"id\":1564929900,\"low\":4.33,\"open\":4.346,\"vol\":422798},{\"amount\":1147912.3754071293319026734977389996120650962,\"close\":4.345,\"count\":2241,\"high\":4.36,\"id\":1564930800,\"low\":4.336,\"open\":4.341,\"vol\":499070},{\"amount\":690716.466600929368866864055449507233199385,\"close\":4.337,\"count\":1335,\"high\":4.346,\"id\":1564931700,\"low\":4.329,\"open\":4.345,\"vol\":299552},{\"amount\":1421680.5593113641030151384044276433821128298,\"close\":4.331,\"count\":3219,\"high\":4.361,\"id\":1564932600,\"low\":4.328,\"open\":4.338,\"vol\":617828},{\"amount\":467699.7360601674144623877176780555084248836,\"close\":4.344,\"count\":1033,\"high\":4.348,\"id\":1564933500,\"low\":4.332,\"open\":4.332,\"vol\":202998}],\"status\":\"ok\",\"ts\":1564934097157}";
}
