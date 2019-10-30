package com.demo.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: liyang
 * @date: 2019-09-26
 **/
@Component
public class Filter extends ZuulFilter {
    @Override
    public String filterType() {
        RequestContext ctx =  RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();

        String token = request.getHeader("at");

        System.out.println(token);
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx =  RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();

        return null;
    }

    public static void main(String[] args) {

        //线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        //速率是每秒只有3个许可
        final RateLimiter rateLimiter = RateLimiter.create(30.0);

        for (int i = 0; i<100; i++) {
            final int no = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取许可
                        rateLimiter.acquire();

                        System.out.println("Accessing:" + no + ",time:"+ new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            //执行线程
            exec.execute(runnable);
        }
        //退出线程池
        exec.shutdown();

    }
}
