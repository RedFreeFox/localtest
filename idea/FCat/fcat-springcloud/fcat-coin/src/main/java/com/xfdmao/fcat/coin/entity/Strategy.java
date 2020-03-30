package com.xfdmao.fcat.coin.entity;

import javax.persistence.*;

public class Strategy {
    @Id
    private Long id;

    /**
     * 交易对
     */
    private String symbol;

    /**
     * 合约类型
     */
    @Column(name = "contract_type")
    private String contractType;

    /**
     * 长期均线
     */
    @Column(name = "up_ma")
    private String upMa;

    /**
     * 短期均线
     */
    @Column(name = "down_ma")
    private String downMa;

    /**
     * 时间周期
     */
    private String period;

    /**
     * 合约倍数
     */
    @Column(name = "lever_rate")
    private String leverRate;

    /**
     * 上引线的判断因子
     */
    @Column(name = "upper_lead_factor")
    private String upperLeadFactor;

    /**
     * 涨幅因子
     */
    @Column(name = "gain_factor")
    private String gainFactor;

    /**
     * 保护价，当下跌幅度超过该跌幅，自动止损
     */
    @Column(name = "protection_down_factor")
    private String protectionDownFactor;

    /**
     * 做多：buy，做空：sell
     */
    @Column(name = "buy_sell")
    private String buySell;

    private String remark;

    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取交易对
     *
     * @return symbol - 交易对
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 设置交易对
     *
     * @param symbol 交易对
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 获取合约类型
     *
     * @return contract_type - 合约类型
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * 设置合约类型
     *
     * @param contractType 合约类型
     */
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    /**
     * 获取长期均线
     *
     * @return up_ma - 长期均线
     */
    public String getUpMa() {
        return upMa;
    }

    /**
     * 设置长期均线
     *
     * @param upMa 长期均线
     */
    public void setUpMa(String upMa) {
        this.upMa = upMa;
    }

    /**
     * 获取短期均线
     *
     * @return down_ma - 短期均线
     */
    public String getDownMa() {
        return downMa;
    }

    /**
     * 设置短期均线
     *
     * @param downMa 短期均线
     */
    public void setDownMa(String downMa) {
        this.downMa = downMa;
    }

    /**
     * 获取时间周期
     *
     * @return period - 时间周期
     */
    public String getPeriod() {
        return period;
    }

    /**
     * 设置时间周期
     *
     * @param period 时间周期
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * 获取合约倍数
     *
     * @return lever_rate - 合约倍数
     */
    public String getLeverRate() {
        return leverRate;
    }

    /**
     * 设置合约倍数
     *
     * @param leverRate 合约倍数
     */
    public void setLeverRate(String leverRate) {
        this.leverRate = leverRate;
    }

    /**
     * 获取上引线的判断因子
     *
     * @return upper_lead_factor - 上引线的判断因子
     */
    public String getUpperLeadFactor() {
        return upperLeadFactor;
    }

    /**
     * 设置上引线的判断因子
     *
     * @param upperLeadFactor 上引线的判断因子
     */
    public void setUpperLeadFactor(String upperLeadFactor) {
        this.upperLeadFactor = upperLeadFactor;
    }

    /**
     * 获取涨幅因子
     *
     * @return gain_factor - 涨幅因子
     */
    public String getGainFactor() {
        return gainFactor;
    }

    /**
     * 设置涨幅因子
     *
     * @param gainFactor 涨幅因子
     */
    public void setGainFactor(String gainFactor) {
        this.gainFactor = gainFactor;
    }

    /**
     * 获取保护价，当下跌幅度超过该跌幅，自动止损
     *
     * @return protection_down_factor - 保护价，当下跌幅度超过该跌幅，自动止损
     */
    public String getProtectionDownFactor() {
        return protectionDownFactor;
    }

    /**
     * 设置保护价，当下跌幅度超过该跌幅，自动止损
     *
     * @param protectionDownFactor 保护价，当下跌幅度超过该跌幅，自动止损
     */
    public void setProtectionDownFactor(String protectionDownFactor) {
        this.protectionDownFactor = protectionDownFactor;
    }

    /**
     * 获取做多：buy，做空：sell
     *
     * @return buy_sell - 做多：buy，做空：sell
     */
    public String getBuySell() {
        return buySell;
    }

    /**
     * 设置做多：buy，做空：sell
     *
     * @param buySell 做多：buy，做空：sell
     */
    public void setBuySell(String buySell) {
        this.buySell = buySell;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}