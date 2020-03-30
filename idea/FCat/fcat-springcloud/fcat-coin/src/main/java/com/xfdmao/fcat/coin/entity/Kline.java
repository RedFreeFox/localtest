package com.xfdmao.fcat.coin.entity;

import javax.persistence.*;
import java.util.Date;

public class Kline {
    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 成交量(张)，买卖双边成交量之和
     */
    private Double vol;

    /**
     * 成交笔数
     */
    private Double count;

    /**
     * 开盘价
     */
    private Double open;

    /**
     * 收盘价,当K线为最晚的一根时，是最新成交价
     */
    private Double close;

    /**
     * 最低价
     */
    private Double low;

    /**
     * 最高价
     */
    private Double high;

    /**
     * 成交量(币), 即 sum(每一笔成交量(张)*单张合约面值/该笔成交价)
     */
    private Double amount;

    /**
     * 币名，大写
     */
    private String symbol;

    /**
     * 合约类型: （this_week:当周 next_week:下周 quarter:季度）
     */
    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "date_id")
    private Long dateId;

    @Column(name = "period")
    private String period;


    @Column(name = "kline_date")
    private Date klineDate;


    public Date getKlineDate() {
        return klineDate;
    }

    public void setKlineDate(Date klineDate) {
        this.klineDate = klineDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * 获取id
     *
     * @return id - id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取成交量(张)，买卖双边成交量之和
     *
     * @return vol - 成交量(张)，买卖双边成交量之和
     */
    public Double getVol() {
        return vol;
    }

    /**
     * 设置成交量(张)，买卖双边成交量之和
     *
     * @param vol 成交量(张)，买卖双边成交量之和
     */
    public void setVol(Double vol) {
        this.vol = vol;
    }

    /**
     * 获取成交笔数
     *
     * @return count - 成交笔数
     */
    public Double getCount() {
        return count;
    }

    /**
     * 设置成交笔数
     *
     * @param count 成交笔数
     */
    public void setCount(Double count) {
        this.count = count;
    }

    /**
     * 获取开盘价
     *
     * @return open - 开盘价
     */
    public Double getOpen() {
        return open;
    }

    /**
     * 设置开盘价
     *
     * @param open 开盘价
     */
    public void setOpen(Double open) {
        this.open = open;
    }

    /**
     * 获取收盘价,当K线为最晚的一根时，是最新成交价
     *
     * @return close - 收盘价,当K线为最晚的一根时，是最新成交价
     */
    public Double getClose() {
        return close;
    }

    /**
     * 设置收盘价,当K线为最晚的一根时，是最新成交价
     *
     * @param close 收盘价,当K线为最晚的一根时，是最新成交价
     */
    public void setClose(Double close) {
        this.close = close;
    }

    /**
     * 获取最低价
     *
     * @return low - 最低价
     */
    public Double getLow() {
        return low;
    }

    /**
     * 设置最低价
     *
     * @param low 最低价
     */
    public void setLow(Double low) {
        this.low = low;
    }

    /**
     * 获取最高价
     *
     * @return high - 最高价
     */
    public Double getHigh() {
        return high;
    }

    /**
     * 设置最高价
     *
     * @param high 最高价
     */
    public void setHigh(Double high) {
        this.high = high;
    }

    /**
     * 获取成交量(币), 即 sum(每一笔成交量(张)*单张合约面值/该笔成交价)
     *
     * @return amount - 成交量(币), 即 sum(每一笔成交量(张)*单张合约面值/该笔成交价)
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 设置成交量(币), 即 sum(每一笔成交量(张)*单张合约面值/该笔成交价)
     *
     * @param amount 成交量(币), 即 sum(每一笔成交量(张)*单张合约面值/该笔成交价)
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取币名，大写
     *
     * @return symbol - 币名，大写
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 设置币名，大写
     *
     * @param symbol 币名，大写
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 获取合约类型: （this_week:当周 next_week:下周 quarter:季度）
     *
     * @return contract_type - 合约类型: （this_week:当周 next_week:下周 quarter:季度）
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * 设置合约类型: （this_week:当周 next_week:下周 quarter:季度）
     *
     * @param contractType 合约类型: （this_week:当周 next_week:下周 quarter:季度）
     */
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }
}