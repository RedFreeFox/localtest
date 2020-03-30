package com.xfdmao.fcat.coin.base.entity;

import com.xfdmao.fcat.common.util.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * Created by cissa on 2019/7/27.
 */
public class KlineInfo implements  Cloneable{
    private Date date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    /**
     * 上引线跌幅
     */
    private double upLeadGain;

    /**
     * 下引线涨幅
     */
    private double downLeadGain;

    /**
     * 当前K线相比上一根K线的量能比
     */
    private double volRate;


    /**
     * k线涨幅
     */
    private double gain;




    /**
     * 买卖状态
     */
    private String buySellStatus;
    /**
     * 收益率
     */
    private double incomeRate;

    public double getUpLeadGain() {
        return upLeadGain;
    }

    public void setUpLeadGain(double upLeadGain) {
        this.upLeadGain = upLeadGain;
    }

    public double getDownLeadGain() {
        return downLeadGain;
    }

    public void setDownLeadGain(double downLeadGain) {
        this.downLeadGain = downLeadGain;
    }

    public double getVolRate() {
        return volRate;
    }

    public void setVolRate(double volRate) {
        this.volRate = volRate;
    }

    public double getIncomeRate() {
        return incomeRate;
    }

    public void setIncomeRate(double incomeRate) {
        this.incomeRate = incomeRate;
    }

    public String getBuySellStatus() {
        return buySellStatus;
    }

    public void setBuySellStatus(String buySellStatus) {
        this.buySellStatus = buySellStatus;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "KlineInfo{" +
                "date=" + DateUtil.formatDate(date,DateUtil.TIME_PATTERN_DAY_SLASH) +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", gain=" + gain +
                ", buySellStatus='" + buySellStatus + '\'' +
                ", incomeRate=" + incomeRate +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
