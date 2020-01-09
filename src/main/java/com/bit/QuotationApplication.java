package com.bit;

import org.apache.catalina.webresources.StandardRoot;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
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
@EnableCaching  //开启缓存
public class QuotationApplication {
    public static void main(String[] args) {
        
        SpringApplication.run(QuotationApplication.class, args);
    }

}