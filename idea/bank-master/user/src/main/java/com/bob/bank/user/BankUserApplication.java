package com.bob.bank.user;

import com.bob.bank.client.base.BaseMapper;
import com.bob.bank.client.config.MybatisConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * @author wb-jjb318191
 * @create 2018-10-19 11:40
 */
@EnableEurekaClient
@Import(MybatisConfiguration.class)
@EnableFeignClients("com.bob.bank.client.export.account")
@MapperScan(basePackages = "com.bob.bank.user.mapper", markerInterface = BaseMapper.class)
@SpringBootApplication(scanBasePackages = {"com.bob.bank.client.export.account", "com.bob.bank.user"})
public class BankUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankUserApplication.class, args);
    }

}
