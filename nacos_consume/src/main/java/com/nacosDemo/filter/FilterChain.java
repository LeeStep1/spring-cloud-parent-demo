package com.nacosDemo.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;


@Configuration
public class FilterChain  {

    @Bean
    public Filter tokenFilter1(){
        return new TokenFilter();
    }

    @Bean
    public Filter tokenFilter2(){
        return new TokenFilter2();
    }

    @Bean
    public FilterRegistrationBean registrationBean(){
        FilterRegistrationBean f = new FilterRegistrationBean<>();
        f.setFilter(tokenFilter1());
        f.setFilter(tokenFilter2());
        return f;
    }
}
