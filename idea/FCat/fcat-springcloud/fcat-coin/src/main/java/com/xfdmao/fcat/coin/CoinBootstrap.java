package com.xfdmao.fcat.coin;

import com.xfdmao.fcat.coin.controller.KlineController;
import com.xfdmao.fcat.coin.controller.TransactionController;
import com.xfdmao.fcat.coin.huobi.websocket.Client;
import com.xfdmao.fcat.coin.huobi.websocket.WebSocketAccountsAndOrders;
import org.apache.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fier on 2018/09/20
 */
@SpringBootApplication
@ServletComponentScan("com.xfdmao.fcat.coin.config.druid")
@EnableAsync
public class CoinBootstrap {
    private static Logger logger = Logger.getLogger(CoinBootstrap.class);

    String protocol = "wss://";
    String host = "api.btcgateway.pro";
    String port ="80";
    String aO = "/ws";
    String market = "/notification";
    String accessKey = "";
    String secretKey = "";

    @Autowired
    Client client;

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private KlineController klineController;


    @PostConstruct
    private void init() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
/*                try {

                URI uri = new URI(protocol + host + ":" + port + market);
                System.out.println(protocol + host + ":" + port + market + "  1");
                System.out.println(uri.getHost() + uri.getPath());
              // TODO 合约的websocket暂时不启用
              WebSocketClient ws = new WebSocketAccountsAndOrders(uri, accessKey, secretKey);
                client.connect(ws);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }*/
        });
        executorService.execute(() -> transactionController.towAvg());
        executorService.execute(() -> klineController.queryHistoryKlineAndSave());
        executorService.execute(() -> klineController.saveTwoMaStrategy());
        executorService.execute(() -> klineController.saveTwoMaStrategyBest());

    }
    public static void main(String[] args) {
        new SpringApplicationBuilder(CoinBootstrap.class).web(true).run(args);    }
}
 