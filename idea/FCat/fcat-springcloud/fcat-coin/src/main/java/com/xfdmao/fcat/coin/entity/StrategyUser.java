package com.xfdmao.fcat.coin.entity;

import javax.persistence.*;

@Table(name = "strategy_user")
public class StrategyUser {
    @Id
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 每次买卖的张数
     */
    private String volume;

    /**
     * 开启使能，0-否，1-是
     */
    private Boolean enable;

    private String remark;

    @Column(name = "strategy_id")
    private Long strategyId;

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
     * 获取账号
     *
     * @return username - 账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置账号
     *
     * @param username 账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取每次买卖的张数
     *
     * @return volume - 每次买卖的张数
     */
    public String getVolume() {
        return volume;
    }

    /**
     * 设置每次买卖的张数
     *
     * @param volume 每次买卖的张数
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * 获取开启使能，0-否，1-是
     *
     * @return enable - 开启使能，0-否，1-是
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 设置开启使能，0-否，1-是
     *
     * @param enable 开启使能，0-否，1-是
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
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

    /**
     * @return strategy_id
     */
    public Long getStrategyId() {
        return strategyId;
    }

    /**
     * @param strategyId
     */
    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }
}