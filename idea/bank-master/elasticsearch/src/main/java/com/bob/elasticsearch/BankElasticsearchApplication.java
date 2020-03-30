package com.bob.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author wb-jjb318191
 * @create 2019-04-17 14:34
 */
@SpringBootApplication
@EnableElasticsearchRepositories("com.bob.elasticsearch.repository")
public class BankElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankElasticsearchApplication.class, args);
    }

}
