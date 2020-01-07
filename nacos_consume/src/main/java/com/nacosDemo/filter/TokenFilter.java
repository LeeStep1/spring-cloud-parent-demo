package com.nacosDemo.filter;

import com.nacosDemo.until.CacheUtil;
import com.nacosDemo.until.RequestThread;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * @description:
 * @author: liyang
 * @date: 2020-01-03
 **/
@WebFilter(urlPatterns = {"/*"}, filterName = "tokenAuthorFilter")
public class TokenFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----------------conusme过滤器初始化------------------------");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uuid = request.getHeader("testUuid");
        RequestThread.bindThread(uuid);
//        System.out.println(Thread.currentThread().getName() + "  插入 UUID.........." + uuid);
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
