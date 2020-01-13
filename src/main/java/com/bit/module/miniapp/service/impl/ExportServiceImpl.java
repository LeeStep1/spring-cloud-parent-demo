package com.bit.module.miniapp.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.base.vo.SuccessVo;
import com.bit.common.informationEnum.StandardEnum;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.*;
import com.bit.module.manager.service.Impl.EquationServiceImpl;
import com.bit.module.miniapp.service.ExportService;
import com.bit.module.miniapp.vo.ExcelVo;
import com.bit.module.miniapp.vo.ProjectEleOptionsVo;
import com.bit.utils.ConvertMoneyUtil;
import com.bit.utils.MailUtil;
import com.bit.utils.UUIDUtil;
import lombok.extern.java.Log;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-13
 **/
@Service
@Log
public class ExportServiceImpl  extends BaseService implements ExportService {
    @Autowired
    private OptionsDao optionsDao;


    @Autowired
    private ProjectEleOptionsDao projectEleOptionsDao;


    @Autowired
    private ProjectEleOrderBaseInfoDao projectEleOrderBaseInfoDao;



    @Autowired
    private ProjectEleOrderDao projectEleOrderDao;

    @Autowired
    private ProjectPriceDao projectPriceDao;



    @Autowired
    private ProjectEleNonstandardDao projectEleNonstandardDao;

    @Autowired
    private ProjectDao  projectDao;


    @Value("${upload.imagesPath}")
    private String filePath;


    /**
     * 生成报价单发送邮件
     *
     * @param projectPriceId (id)
     * @return
     */

    @Async
    @Override
    public void sendPriceMail(Long projectPriceId, List<String>ccAddress, String toAdress){
        if(StringUtils.isEmpty(toAdress)){
            throw new BusinessException("无邮件地址");
        }else{
            Map cod = new HashMap();
            cod.put("version_id", projectPriceId);
            Project j=new Project();

            ProjectPrice p = projectPriceDao.selectById(projectPriceId);
            if (p != null) {
                cod.put("project_id", p.getProjectId());
                j=projectDao.selectById(p.getProjectId());
            }else{
                throw new BusinessException("无此项目数据");
            }
            //订单数据
            List<ProjectEleOrder> orderList = projectEleOrderDao.selectByMap(cod);
            List<ExcelVo> listVo = new ArrayList<ExcelVo>();

            int i = 1;
            for (ProjectEleOrder c : orderList) {
                ExcelVo vo = new ExcelVo();
                vo.setElevatorName(c.getElevatorTypeName());
                vo.setNums(c.getNum());
                vo.setInstallPrice(c.getInstallPrice());
                vo.setSingleTotalPrice(c.getSingleTotalPrice());
                vo.setTotalPrice(c.getTotalPrice());
                vo.setCid(i);
                //项目名称
                if(i==1){
                    vo.setProjectName(j.getProjectName());
                }
                Map codss = new HashMap();
                codss.put("order_id", c.getId());
                List<ProjectEleOrderBaseInfo> list = projectEleOrderBaseInfoDao.selectByMap(codss);
                list.forEach(cc -> {

                    if (cc.getParamKey().equals("速度")) {
                        vo.setSpeed(cc.getInfoValue());
                    } else if (cc.getParamKey().equals("载重")) {
                        vo.setWeight(cc.getInfoValue());
                    } else if (cc.getParamKey().equals("层站")) {
                        vo.setFloors(cc.getInfoValue());
                    }else if(cc.getParamKey().equals("角度")){
                        vo.setFloors(cc.getInfoValue()+"°");
                    }else if(cc.getParamKey().equals("梯级宽度")){
                        vo.setWeight(cc.getInfoValue());
                    }
                });
                //结束
                listVo.add(vo);
                int indexOder=listVo.size();
                //
                i++;
                //选装项
                List<ProjectEleOptionsVo> optins = projectEleOptionsDao.findOptionByOrder(c.getId(), null);

                if(optins.size()>0&&optins.size()>1){
                    for(int ii=0;ii<optins.size();ii++){
                        if(ii==0){
                            vo.setOptions(optins.get(ii).getOptionName());
                        }else{
                            ExcelVo voOption = new ExcelVo();
                            voOption.setOptions(optins.get(ii).getOptionName());
                            listVo.add(voOption);
                        }
                    }
                }else if(optins.size()==1){
                    vo.setOptions(optins.get(0).getOptionName());
                }
                int endIndex=listVo.size();
                //选装项结束

                //非标项
                if(c.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())){
                    Map cods=new HashMap();
                    cods.put("order_id",c.getId());
                    List<ProjectEleNonstandard>listNon= projectEleNonstandardDao.selectByMap(cods);

                    if(listNon.size()==1){
                        JSONObject object= JSON.parseObject(listNon.get(0).getContent());
                        vo.setNonStandard( object.get("input").toString()+object.get("auto").toString());
                    }else  if(listNon.size()>1){
                        vo.setNonStandard(listNon.get(0).getContent());
                        for(int ii=1;ii<listNon.size();ii++){
                            if((endIndex-1+ii)<=endIndex){
                                listVo.get(endIndex-1+ii).setNonStandard(listNon.get(ii).getContent());
                            }else{
                                ExcelVo voOption = new ExcelVo();
                                voOption.setNonStandard(listNon.get(ii).getContent());
                                listVo.add(voOption);
                            }

                        }
                    }
                }


            }
            ExcelVo rs = new ExcelVo();
            if (orderList.size() > 0) {
                ProjectPrice aa = projectPriceDao.selectById(orderList.get(0).getVersionId());
                rs.setElevatorName("总价");
                rs.setTotalPrice(aa.getTotalPrice());
                rs.setInstallPrice(ConvertMoneyUtil.convert(Double.parseDouble(orderList.get(0).getTotalPrice())));
            } else {
                rs.setElevatorName("总价");
                rs.setTotalPrice("0");
                rs.setInstallPrice("零");
            }
            listVo.add(rs);
            export(listVo, "电梯价格单",ccAddress,toAdress,j.getProjectName()+"项目报价");
        }


    }



    public void export(List<ExcelVo> clsList, String sheetName,List<String>ccAdress,String toAdress,String title) {
        String filename = UUIDUtil.getUUID();
        String path = filePath+  System.getProperty("file.separator")+filename+ System.getProperty("file.separator")+title+".xls";
        File aa = new File(path);
        if (!aa.getParentFile().exists()) {
            aa.getParentFile().mkdirs();
        }
        try {
            aa.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (OutputStream out = new FileOutputStream(aa)) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);

            if (!clsList.isEmpty()) {
                Sheet sheet = new Sheet(1, 0, clsList.get(0).getClass());
                sheet.setSheetName(sheetName);
                sheet.setAutoWidth(Boolean.TRUE);
                writer.write(clsList, sheet);
            }
            writer.finish();
            EmailInfo emailInfo = new EmailInfo();
            List<String> toList = new ArrayList<String>();
            if(StringUtils.isNotEmpty(toAdress)){
                toList.add(toAdress);
            }else{
                log.info("------------当前用户邮件为空-------------");
                throw new BusinessException("当前用户邮件为空");
            }

            emailInfo.setToAddress(toList);
            List<EmailAttachment> attachments = new ArrayList<>();
            EmailAttachment emailAttachment = new EmailAttachment();
            emailAttachment.setPath(path);
            emailAttachment.setName(title+".xls");
            attachments.add(emailAttachment);
            //标题
           // emailInfo.setSubject("电梯报价报价");
            emailInfo.setSubject(title);
            //内容
            emailInfo.setContent("内容：<h1>电梯报价,请查收附件</h1>");
            emailInfo.setAttachments(attachments);
            if(CollectionUtils.isNotEmpty(ccAdress)){
                emailInfo.setCcAddress(ccAdress);
            }
            MailUtil.send(emailInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("发送失败{}" + e.getMessage());
        } finally {
            aa.delete();
            aa.getParentFile().delete();
        }
    }
}
