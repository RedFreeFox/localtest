package com.xfdmao.fcat.coin.base.util;

import com.xfdmao.fcat.coin.base.entity.KlineInfo;
import com.xfdmao.fcat.coin.constant.CoinConstant;
import com.xfdmao.fcat.coin.entity.Kline;
import com.xfdmao.fcat.common.util.DateUtil;
import com.xfdmao.fcat.common.util.FileUtil;
import com.xfdmao.fcat.coin.base.constant.KLineConstant;
import com.xfdmao.fcat.common.util.StrUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cissa on 2019/7/27.
 */
public class KlineInfoUtil {
    /**
     * 清空K线列表中的收益率和买卖状态
     *
     * @param klineInfos
     */
    public static void emptyIncomeRateAndBuySellStatus(List<KlineInfo> klineInfos) {
        for (int i = 0; i < klineInfos.size(); i++) {
            KlineInfo klineInfo = klineInfos.get(i);
            klineInfo.setIncomeRate(0);
            klineInfo.setBuySellStatus(null);
        }
    }

    public static void printIncomeList(List<Double> incomeList) {
        for (int i = 0; i < incomeList.size(); i++) {
            System.out.println(String.format("ma%d:%.4f", (i + 1), incomeList.get(i)));
        }
    }

    /**
     * 打印K线列表
     *
     * @param klineInfos
     */
    public static void print(List<KlineInfo> klineInfos, Map<Date, Double> avg) {
        System.out.println("总数：" + klineInfos.size());

        for (int i = 0; i < klineInfos.size(); i++) {
            KlineInfo klineInfo = klineInfos.get(i);

            System.out.println(String.format("时间：%s" +
                            "\t\t收盘价：%.4f" +
                            "\t\t涨幅：%-8.4f" +
                            "\t\t%dMA：%-8.4f" +
                            "\t\t收益：%-8.4f",
                    DateUtil.formatDate(klineInfo.getDate(), DateUtil.TIME_PATTERN_DISPLAY), klineInfo.getClose(),
                    klineInfo.getGain() * 100,
                    20, avg.get(klineInfo.getDate()),
                    klineInfo.getIncomeRate() * 100
            ));
        }
        System.out.println("总收益：" + getSumIncomeRateRealList(klineInfos));
    }

    public static void print(List<KlineInfo> klineInfos, int upMa, Map<Date, Double> upAvgMap, int downMa, Map<Date, Double> downAvgMap) {
        System.out.println("总数：" + klineInfos.size());

        for (int i = 0; i < klineInfos.size(); i++) {
            KlineInfo klineInfo = klineInfos.get(i);

            System.out.println(String.format("时间：%s" +
                            "\t\t收盘价：%.4f" +
                            "\t\t涨幅：%-8.4f" +
                            "\t\tMA%d：%-8.4f" +
                            "\t\tMA%d：%-8.4f" +
                            "\t\t买/卖：%s" +
                            "\t\t收益：%-8.4f",
                    DateUtil.formatDate(klineInfo.getDate(), DateUtil.TIME_PATTERN_DISPLAY), klineInfo.getClose(),
                    klineInfo.getGain() * 100,
                    upMa,
                    upAvgMap.get(klineInfo.getDate()),
                    downMa,
                    downAvgMap.get(klineInfo.getDate()),
                    klineInfo.getBuySellStatus(),
                    klineInfo.getIncomeRate() * 100
            ));
        }
        System.out.println("总收益：" + getSumIncomeRateRealList(klineInfos) * 100);
    }

    /**
     * 根据买卖的K线，计算收益率，净收益率
     *
     * @param klineInfos
     * @Param buyType 0-等额，1-复利，默认为等额
     */
    public static void dealSumIncomeRateList(List<KlineInfo> klineInfos, String buyType) {
        double taker = CoinConstant.takerFee;
        BigDecimal startVol = new BigDecimal(1);
        for (int i = 0; i < klineInfos.size(); i++) {
            KlineInfo klineInfo = klineInfos.get(i);
            BigDecimal sum = new BigDecimal(1);
            if ("1".equals(buyType)) {
                sum = sum.multiply(new BigDecimal(klineInfo.getIncomeRate()).add(startVol)).subtract(sum.multiply(new BigDecimal(taker)));
            } else {
                sum = startVol.multiply(new BigDecimal(klineInfo.getIncomeRate()).add(sum)).subtract(sum.multiply(new BigDecimal(taker)));
            }
            BigDecimal sumIncome = sum.divide(startVol, 6, BigDecimal.ROUND_DOWN).subtract(startVol);
            klineInfo.setIncomeRate(sumIncome.doubleValue());
        }
    }

    /**
     * 打印K线列表
     *
     * @param klineInfos
     */
    public static void print(List<KlineInfo> klineInfos) {
        System.out.println("总数：" + klineInfos.size());
        for (int i = 0; i < klineInfos.size(); i++) {
            System.out.println(klineInfos.get(i));
        }
    }

    /**
     * 打印K线列表,只打印买卖的K线
     *
     * @param klineInfos
     */
    public static void printBuySell(List<KlineInfo> klineInfos) {
        System.out.println(String.format("%-12s\t%-8s\t%-8s\t%-8s\t%-8s", "日期", "收盘价", "涨幅", "买入/卖出", "收益率"));
        for (int i = 0; i < klineInfos.size(); i++) {
            if (klineInfos.get(i).getBuySellStatus() != null) {
                KlineInfo klineInfo = klineInfos.get(i);
                System.out.println(String.format("%-12s\t%-8.4f\t%-8.4f\t%-8s\t%-8.4f",
                        DateUtil.formatDate(klineInfo.getDate(), DateUtil.TIME_PATTERN_DAY_SLASH),
                        klineInfo.getClose(),
                        klineInfo.getGain(), klineInfo.getBuySellStatus() == KLineConstant.BUYSELLSTATUS_BUY ? "买入" : "卖出",
                        klineInfo.getIncomeRate() * 100));
            }
        }
    }

    /**
     * 将文件转换为List对象
     *
     * @param filePath
     * @return
     */
    public static List<KlineInfo> getKLines(String filePath) {
        File file = new File(filePath);
        List<String> eosDatass = FileUtil.readFileLines(file.getAbsolutePath());
        List<KlineInfo> klineInfos = new ArrayList<>();
        for (int i = 0; i < eosDatass.size(); i++) {
            if (i == 0) continue;
            String line = eosDatass.get(i);
            String[] lineArr = line.split("\t");
            KlineInfo klineInfo = new KlineInfo();
            klineInfo.setDate(DateUtil.toDate(lineArr[0], DateUtil.TIME_PATTERN_DAY_SLASH));
            //date	open	high	low	close	volume
            klineInfo.setOpen(new Double(lineArr[1]));
            klineInfo.setHigh(new Double(lineArr[2]));
            klineInfo.setLow(new Double(lineArr[3]));
            klineInfo.setClose(new Double(lineArr[4]));
            klineInfo.setVolume(new Double(lineArr[5]));
            klineInfos.add(klineInfo);
        }
        return klineInfos;
    }

    /**
     * 返回每日的平均值
     *
     * @param klineInfos
     * @param avgNum
     * @return
     */
    public static Map<Date, Double> getAvg(List<KlineInfo> klineInfos, int avgNum) {
        Map<Date, Double> avg = new HashMap<>();
        for (int i = 0; i < klineInfos.size(); i++) {
            if (i < avgNum - 1) {
                avg.put(klineInfos.get(i).getDate(), 0d);
                continue;
            }
            BigDecimal sum = new BigDecimal(0);
            for (int j = i; j > i - avgNum; j--) {
                sum = sum.add(new BigDecimal(klineInfos.get(j).getClose()));
            }
            avg.put(klineInfos.get(i).getDate(), sum.divide(new BigDecimal(avgNum), 4, BigDecimal.ROUND_DOWN).doubleValue());
        }
        return avg;
    }

    /**
     * 返回每日的涨幅
     *
     * @param klineInfos
     * @return
     */
    public static void dealGain(List<KlineInfo> klineInfos) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < klineInfos.size(); i++) {
            KlineInfo klineInfo = klineInfos.get(i);
            result.add(new BigDecimal(klineInfo.getClose()).subtract(new BigDecimal(klineInfo.getOpen())).divide(new BigDecimal(klineInfo.getOpen()), 6, BigDecimal.ROUND_DOWN).doubleValue());
            klineInfo.setGain(result.get(i));
        }
    }

    /**
     * 将收益的K线对象列表转换成日期与计算收益的K线的Map映射
     *
     * @param klineInfos
     * @return
     */
    public static Map<Date, KlineInfo> getKLineMap(List<KlineInfo> klineInfos) {
        Map<Date, KlineInfo> kLineMap = new HashMap<>();
        for (int i = 0; i < klineInfos.size(); i++) {
            KlineInfo klineInfo = klineInfos.get(i);
            kLineMap.put(klineInfo.getDate(), klineInfo);
        }
        return kLineMap;
    }


    /**
     * 将火币返回的K线对象转换成代码要计算收益的K线对象列表
     *
     * @param kLineList
     * @return
     */
    public static List<KlineInfo> getKLines(List<Kline> kLineList) {
        List<KlineInfo> klineInfos = new ArrayList<>();
        for (Kline kline : kLineList) {
            KlineInfo klineInfo = new KlineInfo();
            try {
                BeanUtils.copyProperties(klineInfo, kline);
                klineInfo.setDate(kline.getKlineDate());
                klineInfo.setVolume(kline.getVol());
                klineInfos.add(klineInfo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        initData(klineInfos);
        return klineInfos;
    }

    /**
     * 判断K线是否长的下引线
     *
     * @param klineInfo
     * @return
     */
    public static boolean isDownLead(KlineInfo klineInfo, double leadFactor) {
        double downLead = getDownLeadGain(klineInfo);
        if (downLead > leadFactor) {
            return true;
        }
        return false;
    }

    /**
     * 判断K线是否长的上引线
     *
     * @param klineInfo
     * @return
     */
    public static boolean isUpperLead(KlineInfo klineInfo, double upperLeadFactor) {

        double upperLeadGain = getUpperLeadGain(klineInfo);
        if (upperLeadGain > upperLeadFactor) {
            return true;
        }
        return false;
    }

    /**
     * 判断三根K线，下跌的那个K线的量是否是最大量
     *
     * @param klineInfo
     * @param klineInfo1
     * @param klineInfo2
     * @return
     */
    public static boolean bigVolDown(KlineInfo klineInfo, KlineInfo klineInfo1, KlineInfo klineInfo2) {
        if (klineInfo.getGain() < 0) {
            if (klineInfo.getVolume() > klineInfo1.getVolume() && klineInfo.getVolume() > klineInfo2.getVolume()) {
                return true;
            }
        }
        if (klineInfo1.getGain() < 0) {
            if (klineInfo1.getVolume() > klineInfo.getVolume() && klineInfo1.getVolume() > klineInfo2.getVolume()) {
                return true;
            }
        }
        if (klineInfo2.getGain() < 0) {
            if (klineInfo2.getVolume() > klineInfo1.getVolume() && klineInfo2.getVolume() > klineInfo.getVolume()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 三根K线中是否有两根K线以上下跌 或者两根K线以上收盘价低于短期均线
     *
     * @param klineInfo
     * @param klineInfo1
     * @param klineInfo2
     * @param downAvg
     * @return
     */
    public static boolean twoDown(KlineInfo klineInfo, KlineInfo klineInfo1, KlineInfo klineInfo2, Map<Date, Double> downAvg) {
        int i = 0;
        if (klineInfo.getGain() < 0) {
            i++;
        }
        if (klineInfo1.getGain() < 0) {
            i++;
        }
        if (klineInfo2.getGain() < 0) {
            i++;
        }

        int j = 0;
/*        if(klineInfo.getClose()<downAvg.get(klineInfo.getDate())){
            j++;
        }
        if(klineInfo1.getClose()<downAvg.get(klineInfo1.getDate())){
            j++;
        }
        if(klineInfo2.getClose()<downAvg.get(klineInfo2.getDate())){
            j++;
        }*/

        double sumGain = klineInfo.getGain() + klineInfo1.getGain() + klineInfo2.getGain();

        if (i >= 2 || j >= 2 || sumGain < 0) {
            return true;
        }
        return false;
    }

    /**
     * 三根K线中是否有两根K线以上上涨 或者两根K线以上收盘价高于短期均线
     *
     * @param klineInfo
     * @param klineInfo1
     * @param klineInfo2
     * @param downAvg
     * @return
     */
    public static boolean twoUp(KlineInfo klineInfo, KlineInfo klineInfo1, KlineInfo klineInfo2, Map<Date, Double> downAvg) {
        int i = 0;
        if (klineInfo.getGain() > 0) {
            i++;
        }
        if (klineInfo1.getGain() > 0) {
            i++;
        }
        if (klineInfo2.getGain() > 0) {
            i++;
        }

        int j = 0;
        if (klineInfo.getClose() > downAvg.get(klineInfo.getDate())) {
            j++;
        }
        if (klineInfo1.getClose() > downAvg.get(klineInfo1.getDate())) {
            j++;
        }
        if (klineInfo2.getClose() > downAvg.get(klineInfo2.getDate())) {
            j++;
        }

        if (i >= 2 || j >= 2) {
            return true;
        }
        return false;
    }

    public static double getAmplitude(KlineInfo klineInfo) {
        return new BigDecimal(klineInfo.getHigh()).subtract(new BigDecimal(klineInfo.getLow())).divide(new BigDecimal(klineInfo.getLow()), 6, BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static double getSumIncomeRateRealList(List<KlineInfo> klineInfos) {
        double result = 0;
        for (KlineInfo klineInfo : klineInfos) {
            result += klineInfo.getIncomeRate();
        }
        return BigDecimal.valueOf(result).setScale(6, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 初始化涨幅，上引线的跌幅，下引线的涨幅
     *
     * @param klineInfos
     */
    public static void initData(List<KlineInfo> klineInfos) {
        for (int i = 0; i < klineInfos.size(); i++) {
            KlineInfo klineInfo = klineInfos.get(i);
            double gain = new BigDecimal(klineInfo.getClose()).subtract(new BigDecimal(klineInfo.getOpen())).divide(new BigDecimal(klineInfo.getOpen()), 6, BigDecimal.ROUND_DOWN).doubleValue();
            klineInfo.setGain(gain);

            double upperLeadGain = getUpperLeadGain(klineInfo);
            klineInfo.setUpLeadGain(upperLeadGain);

            double downLead = getDownLeadGain(klineInfo);
            klineInfo.setDownLeadGain(downLead);

            if (i > 0) {
                if (!BigDecimal.valueOf(klineInfos.get(i - 1).getVolume()).equals(BigDecimal.valueOf(0.0))) {
                    klineInfo.setVolRate(BigDecimal.valueOf(klineInfo.getVolume()).divide(BigDecimal.valueOf(klineInfos.get(i - 1).getVolume()), 6, BigDecimal.ROUND_DOWN).doubleValue());
                }
            }
        }
    }

    /**
     * 获取下引线的涨幅
     *
     * @param klineInfo
     * @return
     */
    private static double getDownLeadGain(KlineInfo klineInfo) {
        double min = klineInfo.getClose();//收盘和开盘最小
        if (klineInfo.getClose() > klineInfo.getOpen()) min = klineInfo.getOpen();
        return new BigDecimal(min).subtract(new BigDecimal(klineInfo.getLow())).divide(new BigDecimal(klineInfo.getLow()), 4, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 通过K线获取上引线的跌幅，为正值
     *
     * @param klineInfo
     * @return
     */
    private static double getUpperLeadGain(KlineInfo klineInfo) {
        double max = klineInfo.getClose();//收盘和开盘最大值
        if (klineInfo.getClose() < klineInfo.getOpen()) max = klineInfo.getOpen();
        return new BigDecimal(klineInfo.getHigh()).subtract(new BigDecimal(max)).divide(new BigDecimal(klineInfo.getHigh()), 4, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 判断最近的N跟K线是否出现连续的三根K线上涨
     *
     * @param klineInfos
     * @param threeRaiseNum
     * @return
     */
    public static boolean isThreeRaiseNum(List<KlineInfo> klineInfos, int threeRaiseNum, int i) {
        boolean result = false;
        int start = i - threeRaiseNum;
        if (start < 2) start = 2;
        for (int j = start; j < i + 1; j++) {
            if (klineInfos.get(j).getGain() > 0 && klineInfos.get(j - 1).getGain() > 0 && klineInfos.get(j - 2).getGain() > 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean overGainFactory(List<KlineInfo> klineInfos, int i, Integer threeRaiseNum,double gainFactory) {
        boolean result = false;
        int start = i - threeRaiseNum;
        for (int j = start; j < i + 1; j++) {
            if (klineInfos.get(j).getGain()>=gainFactory) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean overDailyOpen(List<KlineInfo> klineInfos,int i,KlineInfo klineInfo) {
        boolean result = false;
        for(int j=i;j>=0;j--){
            if(DateUtil.formatDate(klineInfos.get(j).getDate(),DateUtil.TIME_PATTERN_DISPLAY).contains("07:45")){
                if(klineInfos.get(j).getClose()<=klineInfo.getClose()){
                    result = true;
                }
                break;
            }
        }
        return result;
    }
}
