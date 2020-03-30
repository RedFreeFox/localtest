package com.xfdmao.fcat.coin.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "btc_address")
public class BtcAddress {
    @Id
    private Integer id;

    private String balance;

    private Integer addresses;

    private Long coins;

    private Long usd;

    @Column(name = "addresses_total_rate")
    private Double addressesTotalRate;

    @Column(name = "coins_rate")
    private Double coinsRate;

    @Column(name = "addresses_rate")
    private Double addressesRate;

    @Column(name = "coins_total_rate")
    private Double coinsTotalRate;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @param balance
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /**
     * @return addresses
     */
    public Integer getAddresses() {
        return addresses;
    }

    /**
     * @param addresses
     */
    public void setAddresses(Integer addresses) {
        this.addresses = addresses;
    }

    /**
     * @return coins
     */
    public Long getCoins() {
        return coins;
    }

    /**
     * @param coins
     */
    public void setCoins(Long coins) {
        this.coins = coins;
    }

    /**
     * @return usd
     */
    public Long getUsd() {
        return usd;
    }

    /**
     * @param usd
     */
    public void setUsd(Long usd) {
        this.usd = usd;
    }

    /**
     * @return addresses_total_rate
     */
    public Double getAddressesTotalRate() {
        return addressesTotalRate;
    }

    /**
     * @param addressesTotalRate
     */
    public void setAddressesTotalRate(Double addressesTotalRate) {
        this.addressesTotalRate = addressesTotalRate;
    }

    /**
     * @return coins_rate
     */
    public Double getCoinsRate() {
        return coinsRate;
    }

    /**
     * @param coinsRate
     */
    public void setCoinsRate(Double coinsRate) {
        this.coinsRate = coinsRate;
    }

    /**
     * @return addresses_rate
     */
    public Double getAddressesRate() {
        return addressesRate;
    }

    /**
     * @param addressesRate
     */
    public void setAddressesRate(Double addressesRate) {
        this.addressesRate = addressesRate;
    }

    /**
     * @return coins_total_rate
     */
    public Double getCoinsTotalRate() {
        return coinsTotalRate;
    }

    /**
     * @param coinsTotalRate
     */
    public void setCoinsTotalRate(Double coinsTotalRate) {
        this.coinsTotalRate = coinsTotalRate;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}