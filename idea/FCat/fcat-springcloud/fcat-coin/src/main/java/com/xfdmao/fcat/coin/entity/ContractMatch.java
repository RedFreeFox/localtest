package com.xfdmao.fcat.coin.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import javax.persistence.*;

@Table(name = "contract_match")
public class ContractMatch {
    @Id
    private Long id;

    /**
     * 成交ID，不唯一，可能重复
     */
    @Column(name = "match_id")
    private Long matchId;

    /**
     * 订单ID
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 品种代码
     */
    private String symbol;

    /**
     * 合约类型	当周:this_week, 次周:next_week, 季度:quarter
     */
    @Column(name = "contract_type")
    private String contractType;

    /**
     * 合约代码
     */
    @Column(name = "contract_code")
    private String contractCode;

    /**
     * buy:买 sell:卖	
     */
    private String direction;

    /**
     * open:开 close:平
     */
    private String offset;

    /**
     * 成交数量
     */
    @Column(name = "trade_volume")
    private Double tradeVolume;

    /**
     * 成交价格
     */
    @Column(name = "trade_price")
    private Double tradePrice;

    /**
     * 成交总金额
     */
    @Column(name = "trade_turnover")
    private Double tradeTurnover;

    /**
     * 成交时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 平仓盈亏
     */
    @Column(name = "offset_profitloss")
    private Double offsetProfitloss;

    /**
     * 成交手续费
     */
    @Column(name = "traded_fee")
    private Double tradedFee;

    /**
     * taker或maker
     */
    private String role;

    /**
     * 记录创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    public ContractMatch() {
    }

    public ContractMatch(JSONObject json) {
        this.contractCode = json.getString("contract_code");
        this.contractType = json.getString("contract_type");
        this.createDate = new Date(json.getLong("create_date"));
        this.direction = json.getString("direction");
        this.matchId = json.getLong("match_id");
        this.offset = json.getString("offset");
        this.offsetProfitloss = json.getDoubleValue("offset_profitloss");
        this.orderId = json.getLong("match_id");
        this.role = json.getString("role");
        this.symbol = json.getString("symbol");
        this.tradedFee = json.getDoubleValue("traded_fee");
        this.tradePrice = json.getDoubleValue("trade_price");
        this.tradeTurnover = json.getDoubleValue("trade_turnover");
        this.tradeVolume = json.getDoubleValue("trade_volume");
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
     * 获取成交ID，不唯一，可能重复
     *
     * @return match_id - 成交ID，不唯一，可能重复
     */
    public Long getMatchId() {
        return matchId;
    }

    /**
     * 设置成交ID，不唯一，可能重复
     *
     * @param matchId 成交ID，不唯一，可能重复
     */
    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取品种代码
     *
     * @return symbol - 品种代码
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 设置品种代码
     *
     * @param symbol 品种代码
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 获取合约类型	当周:this_week, 次周:next_week, 季度:quarter
     *
     * @return contract_type - 合约类型	当周:this_week, 次周:next_week, 季度:quarter
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * 设置合约类型	当周:this_week, 次周:next_week, 季度:quarter
     *
     * @param contractType 合约类型	当周:this_week, 次周:next_week, 季度:quarter
     */
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    /**
     * 获取合约代码
     *
     * @return contract_code - 合约代码
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * 设置合约代码
     *
     * @param contractCode 合约代码
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * 获取buy:买 sell:卖	
     *
     * @return direction - buy:买 sell:卖	
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 设置buy:买 sell:卖	
     *
     * @param direction buy:买 sell:卖	
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * 获取open:开 close:平
     *
     * @return offset - open:开 close:平
     */
    public String getOffset() {
        return offset;
    }

    /**
     * 设置open:开 close:平
     *
     * @param offset open:开 close:平
     */
    public void setOffset(String offset) {
        this.offset = offset;
    }

    /**
     * 获取成交数量
     *
     * @return trade_volume - 成交数量
     */
    public Double getTradeVolume() {
        return tradeVolume;
    }

    /**
     * 设置成交数量
     *
     * @param tradeVolume 成交数量
     */
    public void setTradeVolume(Double tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    /**
     * 获取成交价格
     *
     * @return trade_price - 成交价格
     */
    public Double getTradePrice() {
        return tradePrice;
    }

    /**
     * 设置成交价格
     *
     * @param tradePrice 成交价格
     */
    public void setTradePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }

    /**
     * 获取成交总金额
     *
     * @return trade_turnover - 成交总金额
     */
    public Double getTradeTurnover() {
        return tradeTurnover;
    }

    /**
     * 设置成交总金额
     *
     * @param tradeTurnover 成交总金额
     */
    public void setTradeTurnover(Double tradeTurnover) {
        this.tradeTurnover = tradeTurnover;
    }

    /**
     * 获取成交时间
     *
     * @return create_date - 成交时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置成交时间
     *
     * @param createDate 成交时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取平仓盈亏
     *
     * @return offset_profitloss - 平仓盈亏
     */
    public Double getOffsetProfitloss() {
        return offsetProfitloss;
    }

    /**
     * 设置平仓盈亏
     *
     * @param offsetProfitloss 平仓盈亏
     */
    public void setOffsetProfitloss(Double offsetProfitloss) {
        this.offsetProfitloss = offsetProfitloss;
    }

    /**
     * 获取成交手续费
     *
     * @return traded_fee - 成交手续费
     */
    public Double getTradedFee() {
        return tradedFee;
    }

    /**
     * 设置成交手续费
     *
     * @param tradedFee 成交手续费
     */
    public void setTradedFee(Double tradedFee) {
        this.tradedFee = tradedFee;
    }

    /**
     * 获取taker或maker
     *
     * @return role - taker或maker
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置taker或maker
     *
     * @param role taker或maker
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 获取记录创建时间
     *
     * @return create_time - 记录创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置记录创建时间
     *
     * @param createTime 记录创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}