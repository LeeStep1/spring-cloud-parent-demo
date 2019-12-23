package com.bit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * Desc
 *,exclude={DataSourceAutoConfiguration.class}
 * @author mifei
 * @date 2019-03-08
 */
@SpringBootApplication(scanBasePackages = "com.bit")
@EnableTransactionManagement
@MapperScan("com.bit.module.*.dao")
@ServletComponentScan
@EnableAsync
@EnableScheduling
public class CommunityWxappApplication {
    public static void main(String[] args) {
        
        SpringApplication.run(CommunityWxappApplication.class, args);
    }
}