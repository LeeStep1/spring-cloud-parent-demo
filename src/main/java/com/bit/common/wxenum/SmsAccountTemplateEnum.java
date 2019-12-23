package com.bit.common.wxenum;

import com.bit.base.exception.BusinessException;
import com.bit.common.consts.RedisKey;
import com.bit.utils.RedisKeyUtil;

/**
 * @Description:  各业务的短信模板key值
 * @Author: liyujun
 * @Date: 2019-09-19
 **/
public enum SmsAccountTemplateEnum {

    /**
     * 账号短信注册业务
     */
    SMS_ACCOUNT_REGISTER (1,"tempForReg", RedisKey.SMS_REGISTER_TOTAL, RedisKey.SMS_REGISTER_VERIFICATION_CODE),
    /**
     * 账号短信密码找回业务
     */
    SMS_ACCOUNT_RESET_PASSWORD (2,"tempForPassBack", RedisKey.SMS_BACKPWD_TOTAL, RedisKey.SMS_BACKPWD_VERIFICATION_CODE);
    /**
     * 业务类型
     */
    private  int smsTemplateType;

    /**
     * 短信模板yml 中对应的key
     */
    private  String smsTemplateKey;

    /**
     * 短信发出后验证码控制的今日累计的发送次数的redis中key值
     */
    private RedisKey smslimitKey;

    /**
     * 短信发出后验证码所存储在redis中的key值
     */
    private RedisKey smsSendTemplate;


    SmsAccountTemplateEnum(int smsTemplateType, String smsTemplateKey , RedisKey smslimitKey, RedisKey smsSendTemplate){

        this.smsTemplateType=smsTemplateType;
        this.smsTemplateKey=smsTemplateKey;
        this.smslimitKey=smslimitKey;
        this.smsSendTemplate=smsSendTemplate;

    }


    public String getSmsSendTemplate(String...parmas) {
       return RedisKeyUtil.getRedisKey(this.smsSendTemplate,parmas);
    }


    public String getSmslimitKey(String...parmas) {
        return RedisKeyUtil.getRedisKey(this.smslimitKey,parmas);
    }



    public String getSmsTemplateKey(){
         return this.smsTemplateKey;
    }

    public int getSmsTemplateType(){
        return this.smsTemplateType;
    }

    /**
     * @description:  根据操作的类型得出相应的枚举类
     * @author liyujun
     * @date 2019-09-19
     * @param smsTemplateType : 业务的类型
     * @return : com.bit.common.SysEnum.SmsAccountTemplateEnum
     */
    public static SmsAccountTemplateEnum getSmsAccountTemplateEnum(int smsTemplateType ) {

        SmsAccountTemplateEnum a=null;
        for(SmsAccountTemplateEnum i:SmsAccountTemplateEnum.values()){
            if(i.smsTemplateType==smsTemplateType){
                a=i;
                break;
            }
        }
        if(a==null){
                throw new BusinessException("无此操作模板");
        }
        return  a;
    }
}
