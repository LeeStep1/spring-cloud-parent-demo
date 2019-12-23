package com.bit.filter;

import com.bit.base.vo.BaseVo;
import com.bit.common.consts.Const;
import com.bit.common.consts.RedisKey;
import com.bit.common.wxenum.ResultCode;
import com.bit.common.informationEnum.TidUrlEnum;
import com.bit.utils.CacheUtil;
import com.bit.utils.RedisKeyUtil;
import com.bit.utils.RequestThreadBinder;
import com.bit.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import net.sf.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


//@Slf4j
//@WebFilter(urlPatterns = {"/*"}, filterName = "tokenAuthorFilter")
public class TokenFilter implements Filter {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Value("${filter.noFilterUrl}")
    private String[] noFilterUrl;

    @Value("${filter.authUrl}")
    private String[] authUrl;

    @Value("${token.expireTime}")
    private long expireTime;



    @Autowired
    private CacheUtil cacheUtil;


    private final String tokenName = "at";

    private final String terminalName = "tid";

    //private final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //log.info("----------------过滤器初始化------------------------");
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        settCors(request,response);
        //如果是OPTIONS请求就return 往后执行会到业务代码中 他不带参数会产生异常
        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (isInclude(request.getServletPath(),noFilterUrl)){
            //白名单不拦截
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            String token = "";
            String terminalId = getTerminalId(request);
            if (StringUtil.isEmpty(terminalId)) {
                //判断是否为空
                responseOutWithJson(response, new BaseVo(HttpStatus.UNAUTHORIZED.value(), "接入端为空", null));
                return;
            }
            token = request.getHeader(tokenName);
            if (StringUtil.isNotEmpty(token)) {
                    String key= RedisKeyUtil.getRedisKey(RedisKey.LOGIN_TOKEN,terminalId,token);
                    if( cacheUtil.hasKey(key)) {
                        //判断是否需要token
                         if(isInclude(request.getServletPath(),authUrl)){
                             //公共的服务接口要暴露
                             String user = (String)cacheUtil.get(key);
                             RequestThreadBinder.bindUser(user);
                             filterChain.doFilter(servletRequest, servletResponse);
                             return;
                         }
                        //接入端与请求地址匹配(居民端)
                        if(String.valueOf(TidUrlEnum.TERMINALURL_RESIDENT.getTid()).equals(terminalId)){
                            String ss = request.getServletPath();
                            String url = ss.substring(1,ss.indexOf("/",2));
                            if(!TidUrlEnum.TERMINALURL_RESIDENT.getUrl().equals(url)){
                                responseOutWithJson(response, new BaseVo(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getInfo(), null));
                                return;
                            }
                        }else if(String.valueOf(TidUrlEnum.TERMINALURL_MANAGER.getTid()).equals(terminalId)){
                            long l = cacheUtil.getExpire(token);
                            if (l < expireTime) {
                                // 更新过期时间
                                cacheUtil.expire(token, Const.TOKEN_EXPIRE_SECONDS);
                            }
                            String ss = request.getServletPath();
                            String url = ss.substring(1,ss.indexOf("/",2));
                            if(!TidUrlEnum.TERMINALURL_MANAGER.getUrl().equals(url)){
                                responseOutWithJson(response, new BaseVo(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getInfo(), null));
                                return;
                            }
                        }else {
                            responseOutWithJson(response, new BaseVo(ResultCode.PARAMETER_ERROR.getCode(), ResultCode.PARAMETER_ERROR.getInfo(), null));
                            return;
                        }
                        String user = (String)cacheUtil.get(key);
                        RequestThreadBinder.bindUser(user);
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    } else {
                        responseOutWithJson(response, new BaseVo(ResultCode.UNAUTH.getCode(), "无效token", null));
                        return;
                    }

            }else{
                responseOutWithJson(response, new BaseVo(ResultCode.UNAUTH.getCode(), "无token", null));
                return;
            }

        }
    }

    private String getTerminalId(HttpServletRequest request) {
        String terminalId = request.getHeader(terminalName);
        if (terminalId == null) {
            terminalId = request.getParameter(terminalName);
        }
        return terminalId;
    }


    @Override
    public void destroy() {

       // log.info("--------------过滤器销毁--------------");
    }

    /**
     * 是否需要过滤
     *
     * @param url
     * @return
     */
    private boolean isInclude(String url,String [] targetUrl) {
        for (String pattern : targetUrl) {
            if (url.indexOf(pattern) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 以JSON格式输出
     *
     * @param response
     */
    private void responseOutWithJson(HttpServletResponse response,
                                       Object responseObject) {
        //将实体对象转换为JSON Object转换
        JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private void settCors(  HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header,at,tid");
    }
}
