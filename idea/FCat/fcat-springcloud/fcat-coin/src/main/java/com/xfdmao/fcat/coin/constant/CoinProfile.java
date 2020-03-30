package com.xfdmao.fcat.coin.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by fier on 2018/09/20
 */
@Component
public class CoinProfile {

    @Value("${spring.profiles.active}")
    public static String active;

    /**
     * // 合约接口地址 "https://api.hbdm.com";//火币api接口地址https://api.hbdm.com;
     */
    @Value("${huobi.contract.url_prex}")
    public static String url_prex;

}
