package com.bit.utils;


import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bit.base.exception.BusinessException;
import com.bit.common.wx.WxLoginRs;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.text.MessageFormat;
import java.util.Arrays;



/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-10-15
 **/
@Component
@Slf4j
public class WxUserComponent {

    /**登录授权**/
    private final  String  Jscode2sessionUrl="https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";


    /**
     * 居民端appid
     */
    @Value("${wxapp.customer.appid}")
    private String customerAppid;


    /**
     * 居民端secret
     */
    @Value("${wxapp.customer.secret}")
    private String customerSecret;


    /**
     * 跳转工具类
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过不同的端口
     * @param code
     * @param tid
     * @return
     */
    public WxLoginRs getSessionKeyAndOpenId(String code,Integer tid){
        String appid = null;
        String secret = null;
        /*if(tid.equals(TERMINALURL_MANAGER.getTid())){
            appid = adminAppid;
            secret= adminSecret;
        }else if(tid.equals(TERMINALURL_RESIDENT.getTid())) {
            appid = residnetAppid;
            secret= residnetSecret;
        }*/
         String params[]={customerAppid,customerSecret,code};
         String targetUrl=MessageFormat.format(Jscode2sessionUrl,params);
         log.info("请求微信人员登录接口：{}",targetUrl);
         String ss = restTemplate.getForObject(targetUrl,String.class);
         WxLoginRs rs = JSON.parseObject(ss,WxLoginRs.class);

         if(rs!=null){
             if(rs.getErrcode()!=null &&!rs.getErrcode().equals(0)){
                 log.error("调用微信登录结构错误，错误信息为：{}{}",rs.getErrcode(),rs.getErrmsg());
                 throw new BusinessException(rs.getErrmsg());
             }
         }else{
             log.error("调用微信登录结构错误失败",rs.getErrcode(),rs.getErrmsg());
             throw new BusinessException("调用失败");
         }

        return rs;
    }

    public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
                log.error("解析人员信息失败：{}",e.getMessage());
                throw  new BusinessException("解析人员信息失败");
        }
        return null;
    }



}
