package com.nacosDemo.filter;

import com.nacosDemo.until.RequestThread;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description:
 * @author: liyang
 * @date: 2020-01-03
 **/
@Order(2)
@WebFilter(urlPatterns = {"/*"}, filterName = "tokenAuthorFilter2")
public class TokenFilter2 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----------------conusme过滤器初始化------------------------");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, javax.servlet.FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uuid = request.getHeader("testUuid");
        RequestThread.bindThread(uuid);
        System.out.println(22222222+ "    filter");
//        System.out.println(Thread.currentThread().getName() + "  插入 UUID.........." + uuid);
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String uuid = request.getHeader("testUuid");
//        RequestThread.bindThread(uuid);
//        System.out.println(22222222+ "    filter");
////        System.out.println(Thread.currentThread().getName() + "  插入 UUID.........." + uuid);
//        filterChain.doFilter(servletRequest,servletResponse);
//        return;
//    }

    @Override
    public void destroy() {

    }
}
