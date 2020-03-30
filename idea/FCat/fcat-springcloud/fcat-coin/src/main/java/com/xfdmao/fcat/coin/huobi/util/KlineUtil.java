package com.xfdmao.fcat.coin.huobi.util;

import com.xfdmao.fcat.coin.huobi.globle.response.Kline;
import org.apache.commons.collections.set.PredicatedSortedSet;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2019/7/29.
 */
public class KlineUtil {
    public static void sort(List<Kline> klineList){
        Collections.sort(klineList, new Comparator<Kline>() {
            @Override
            public int compare(Kline o1, Kline o2) {
                if(o1.getId()>o2.getId()){
                    return -1;
                }else if(o1.getId()<o2.getId()){
                    return 1;
                }
                return 0;
            }
        });
    }

    public static double queryAvg(List<Kline> historyKline) {
        double avg = 0;
        BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<historyKline.size();i++){
            Kline kline = historyKline.get(i);
            sum = sum.add(new BigDecimal(kline.getClose()));
        }
        avg = sum.divide(new BigDecimal(historyKline.size()),4,BigDecimal.ROUND_DOWN).doubleValue();
        return avg;
    }

    public static List<com.xfdmao.fcat.coin.entity.Kline> toTableKline(List<Kline> klines) {
        List<com.xfdmao.fcat.coin.entity.Kline> klineList = new ArrayList<>();
        for(Kline kline:klines){
            com.xfdmao.fcat.coin.entity.Kline kline1 = new com.xfdmao.fcat.coin.entity.Kline();
            kline1.setDateId(kline.getId());
            kline1.setOpen(kline.getOpen());
            kline1.setClose(kline.getClose());
            kline1.setHigh(kline.getHigh());
            kline1.setLow(kline.getLow());
            kline1.setVol(kline.getVol());
            kline1.setKlineDate(new Date(kline.getId()*1000));
            klineList.add(kline1);
        }
        return klineList;
    }

    /**
     * 判断K线是否长的下引线
     * @param kline
     * @return
     */
    public static boolean isDownLead(Kline kline,double leadFactor) {
        double min =kline.getClose();//收盘和开盘最小
        if(kline.getClose()>kline.getOpen()) min = kline.getOpen();
        if(new BigDecimal(min).subtract(new BigDecimal(kline.getLow())).divide(new BigDecimal(kline.getLow()),4,BigDecimal.ROUND_DOWN).doubleValue() > leadFactor){
            return true;
        }
        return false;
    }

    /**
     * 上引线的定义：(最高价-收盘价)/(收盘价-最低价) >  p
     * @param kline
     * @param leadFactor
     * @return
     */
    public static boolean isUpperLead(Kline kline,double leadFactor) {
        double max = kline.getClose();//收盘和开盘最大值
        if(kline.getClose()<kline.getOpen()) max = kline.getOpen();
        if(new BigDecimal(kline.getHigh()).subtract(new BigDecimal(max)).divide(new BigDecimal(kline.getHigh()),4,BigDecimal.ROUND_DOWN).doubleValue() > leadFactor){
            return true;
        }
        return false;
    }

    /**
     * 获取K线的涨幅
     * @param kline
     * @return
     */
    public static double getGain(Kline kline) {
        return new BigDecimal(kline.getClose()).subtract(new BigDecimal(kline.getOpen())).divide(new BigDecimal(kline.getOpen()),6,BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 判断三根K线，下跌的那个K线的量是否是最大量
     * @param kline
     * @param kline1
     * @param kline2
     * @return
     */
    public static boolean bigVolDown(Kline kline, Kline kline1, Kline kline2) {
        if(KlineUtil.getGain(kline)<0){
            if(kline.getVol()>kline1.getVol() && kline.getVol()> kline2.getVol()){
                return true;
            }
        }
        if(KlineUtil.getGain(kline1)<0){
            if(kline1.getVol()>kline.getVol() && kline1.getVol()> kline2.getVol()){
                return true;
            }
        }
        if(KlineUtil.getGain(kline2)<0){
            if(kline2.getVol()>kline1.getVol() && kline2.getVol()> kline.getVol()){
                return true;
            }
        }
        return false;
    }

    /**
     * 三根K线中是否有两根K线以上下跌 或者两根K线以上收盘价低于短期均线
     * @param kline
     * @param kline1
     * @param kline2
     * @param downAvg
     * @param downAvgPrevious
     * @param downAvgThird
     * @return
     */
    public static boolean twoDown(Kline kline, Kline kline1, Kline kline2, double downAvg, double downAvgPrevious, double downAvgThird) {
        int i=0;
        if(KlineUtil.getGain(kline)<0){
            i++;
        }
        if(KlineUtil.getGain(kline1)<0){
            i++;
        }
        if(KlineUtil.getGain(kline2)<0){
            i++;
        }

/*        int j=0;
        if(kline.getClose()<downAvg){
            j++;
        }
        if(kline1.getClose()<downAvgPrevious){
            j++;
        }
        if(kline2.getClose()<downAvgThird){
            j++;
        }*/
        double sumGain = KlineUtil.getGain(kline) + KlineUtil.getGain(kline1) + KlineUtil.getGain(kline2);
        return i>=2?true:sumGain<0?true:false;
    }

    /**
     *   最近三根K线中不能有两根以上的K线下跌
     * @param kline
     * @param kline1
     * @param kline2
     * @param downAvg
     * @param downAvgPrevious
     * @param downAvgThird
     * @return
     */
    public static boolean twoUp(Kline kline, Kline kline1, Kline kline2, double downAvg, double downAvgPrevious, double downAvgThird) {
        int i=0;
        if(KlineUtil.getGain(kline)>0){
            i++;
        }
        if(KlineUtil.getGain(kline1)>0){
            i++;
        }
        if(KlineUtil.getGain(kline2)>0){
            i++;
        }

        int j=0;
        if(kline.getClose()>downAvg){
            j++;
        }
        if(kline1.getClose()>downAvgPrevious){
            j++;
        }
        if(kline2.getClose()>downAvgThird){
            j++;
        }
        return i>=2?true:j>=2?true:false;
    }

    /**
     * 获取上引线的跌幅，绝对值
     * @param kline
     * @return
     */
    public static double getUpLeadGain(Kline kline) {
        double max = kline.getClose();//收盘和开盘最大值
        if (kline.getClose() < kline.getOpen()) max = kline.getOpen();
        return BigDecimal.valueOf(kline.getHigh()).subtract(BigDecimal.valueOf(max)).divide(BigDecimal.valueOf(kline.getHigh()), 4, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 当超过一定的涨幅则不考虑开空
     * @param klines
     * @return
     */
    public static boolean overGain(List<Kline> klines,double overGain,int klineCount) {
        for(int m=0;m<klineCount;m++){
            if(m>klines.size()-1)break;
            if(overGain>0){
                if(KlineUtil.getGain(klines.get(m))>=overGain){
                    return true;
                }
            }else{
                if(KlineUtil.getGain(klines.get(m))<=overGain){
                    return true;
                }
            }

        }
        return false;
    }

    public static boolean nearOverGainFactor(List<Kline> klines, int num,double gainFactor) {
        boolean result = false;
        for(int i=0;i<=num;i++){
            Kline kline = klines.get(i);
            if(KlineUtil.getGain(kline)>=gainFactor){
                result =true;
                break;
            }
        }
        return result;
    }
}
