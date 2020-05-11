package com.lee.user.filter;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description:
 * @author: liyang
 * @date: 2020-01-03
 **/
@Order(1)
@WebFilter(urlPatterns = {"/*"}, filterName = "tokenAuthorFilter")
public class TokenFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----------------conusme过滤器初始化------------------------");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("请求方法......."+request.getMethod());
        String uuid = request.getHeader("testUuid");
//        RequestThread.bindThread(uuid);
        System.out.println(111111+ "    filter，request 链条.....");
//        System.out.println(Thread.currentThread().getName() + "  插入 UUID.........." + uuid);
        filterChain.doFilter(servletRequest,servletResponse);

        System.out.println("1 的返回链条....");
        return;
    }

//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String uuid = request.getHeader("testUuid");
//        RequestThread.bindThread(uuid);
//        System.out.println(1111111111+ "    filter");
////        System.out.println(Thread.currentThread().getName() + "  插入 UUID.........." + uuid);
//        filterChain.doFilter(servletRequest,servletResponse);
//        return;
//    }

    @Override
    public void destroy() {

    }
}
