package com.nacosDemo.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import java.util.ArrayList;
import java.util.List;


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
        List<String> a = new ArrayList<>();
        a.add("/*");
        f.setUrlPatterns(a);
        f.setOrder(1);
//        @Order(1)
//                @WebFilter(urlPatterns = {}, filterName = "tokenAuthorFilter")

        return f;
    }

    @Bean
    public FilterRegistrationBean registrationBean2(){
        FilterRegistrationBean f = new FilterRegistrationBean<>();
        f.setFilter(tokenFilter2());
        List<String> a = new ArrayList<>();
        a.add("/*");
        f.setUrlPatterns(a);
        f.setOrder(2);
//        @Order(1)
//                @WebFilter(urlPatterns = {}, filterName = "tokenAuthorFilter")

        return f;
    }

}
