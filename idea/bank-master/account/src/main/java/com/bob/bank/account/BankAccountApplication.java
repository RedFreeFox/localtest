package com.bob.bank.account;

import com.bob.bank.client.base.BaseMapper;
import com.bob.bank.client.config.MybatisConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration;
import org.springframework.cloud.config.client.ConfigClientAutoConfiguration;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * SpringCloudConfig Client的配置信息都在{@link ConfigClientAutoConfiguration} 里面
 * 通过 spring.factories文件引入
 *
 * 默认初始化时通过 {@link ConfigServicePropertySourceLocator#locate(Environment)} 向Server发送请求
 * 请求发送到
 *
 * @author wb-jjb318191
 * @RequestMapping("/{name}/{profiles}/{label:.*}") {@link EnvironmentController#labelled()} 中
 *
 * 容器数据刷新配置类: {@link RefreshAutoConfiguration}
 *
 *
 * SpringCloudConfig 请求 /refresh 时刷新容器内Environment信息的流程:
 * >> {@link RefreshEndpoint#refresh()}
 * >> {@link ContextRefresher#addConfigFilesToEnvironment}
 * >> {@link SpringApplicationBuilder#run(String...)}
 * >> {@link SpringApplication#run(String...)}
 * >> {@link SpringApplication#prepareContext}
 * >> {@link SpringApplication#applyInitializers(ConfigurableApplicationContext)}
 * >> {@link PropertySourceBootstrapConfiguration#initialize(ConfigurableApplicationContext)}
 * >> {@link ConfigServicePropertySourceLocator#locate(Environment)}
 * @create 2018-10-19 12:10
 */
@EnableEurekaClient
@Import({MybatisConfiguration.class})
@EnableFeignClients("com.bob.bank.client.export.user")
@MapperScan(basePackages = "com.bob.bank.account.mapper", markerInterface = BaseMapper.class)
@SpringBootApplication(scanBasePackages = {"com.bob.bank.account", "com.bob.bank.client.export.user"})
public class BankAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountApplication.class, args);
    }

}
