package com.bit.listen;

import com.alibaba.fastjson.JSON;
import com.bit.common.consts.Const;
import com.bit.module.manager.bean.PublicWxInfo;
import com.bit.utils.CacheUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:  监听微信小程序整体的服务token
 * @Author: liyujun
 * @Date: 2019-11-13
 **/

//@Component
@Log
public class WxTokenListener {


    /**
     * 服务号APPID
     */
    @Value("${publicApp.appid}")
    private String publicAppid;

    /**
     * 服务号SecretId
     */
    @Value("${publicApp.secret}")
    private String publicSecret;

    /**
     * 获取微信tokenURL
     */
    private final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";


    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private CacheUtil cacheUtil;

    @EventListener
    @Async
    public void onApplicationEvent(ApplicationReadyEvent event) {

        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        if(event!=null){

          //  logger.info("-------------服务启动成功执行此方法-----------------");
            if(restTemplate==null){
                restTemplate=applicationContext.getBean(RestTemplate.class);
            }
            if(cacheUtil==null){
                cacheUtil=applicationContext.getBean(CacheUtil.class);
            }

            String params[]={publicAppid,publicSecret};
            String targetUrl= MessageFormat.format(tokenUrl,params);
            String ss = restTemplate.getForObject(targetUrl,String.class);
            PublicWxInfo wx = JSON.parseObject(ss,PublicWxInfo.class);
            String accessToken = wx.getAccess_token();
            log.info("服务启动成功执行此方法"+accessToken);
            cacheUtil.set(Const.WX_ACC_TOKEN,wx.getAccess_token(),Long.valueOf(wx.getExpires_in())-1800);

        }

        //logger.error("-------------服务监听执行失败-----------------");
    }

}
