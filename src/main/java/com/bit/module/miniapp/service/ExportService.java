package com.bit.module.miniapp.service;

import java.util.List;

/**
 * @Description:  导出服务
 * @Author: liyujun
 * @Date: 2020-01-13
 **/
public interface ExportService {


    void sendPriceMail(Long projectPriceId, List<String>ccAddress, String toAdress);
}
