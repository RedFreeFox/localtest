package com.xfdmao.fcat.coin.entity;

import javax.persistence.*;

public class Token {
    @Id
    private Integer id;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "api_secret")
    private String apiSecret;

    private String username;

    private String remark;

    /**
     * 测试使能：0-否，1-是
     */
    @Column(name = "test_enable")
    private Boolean testEnable;

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
     * @return api_key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return api_secret
     */
    public String getApiSecret() {
        return apiSecret;
    }

    /**
     * @param apiSecret
     */
    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
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
     * 获取测试使能：0-否，1-是
     *
     * @return test_enable - 测试使能：0-否，1-是
     */
    public Boolean getTestEnable() {
        return testEnable;
    }

    /**
     * 设置测试使能：0-否，1-是
     *
     * @param testEnable 测试使能：0-否，1-是
     */
    public void setTestEnable(Boolean testEnable) {
        this.testEnable = testEnable;
    }
}