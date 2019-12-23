package com.bit.common.wx;

import lombok.Data;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-10-15
 **/
@Data
public class WxLoginRs extends WxBaseRs{

    private String openid;

    private String sessionKey;

    private String encryptedData;

    private String iv;

    private String  unionid;
}
