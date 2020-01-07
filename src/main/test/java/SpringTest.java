import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bit.QuotationApplication;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.*;
import com.bit.module.miniapp.service.WxElevatorService;
import com.bit.module.miniapp.vo.ExcelVo;
import com.bit.module.miniapp.vo.ProjectEleOptionsVo;
import com.bit.utils.ConvertMoneyUtil;
import com.bit.utils.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-02
 **/

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = QuotationApplication.class)
@WebAppConfiguration
public class SpringTest {

    @Autowired
    private ElevatorRelaOptionsDao elevatorRelaOptionsDao;

    @Autowired
    private ProjectEleOrderDao projectEleOrderDao;

    @Autowired
    private ProjectEleOrderBaseInfoDao projectEleOrderBaseInfoDao;

    @Autowired
    private ProjectPriceDao projectPriceDao;

    @Autowired
    private ProjectEleOptionsDao projectEleOptionsDao;

    @Autowired
    private  WxElevatorService wxElevatorService;


    @Autowired
    private ProjectDao projectDao;


    @Value("${upload.imagesPath}")
    private  String path;


    /**
     * @description:  easyExle
     * @author liyujun
     * @date 2020-01-02
     * @return : void
     */
    @Test
    public void Test(){
        Map cod=new HashMap();
        cod.put("version_id",49);
        cod.put("project_id",13);

        //订单数据
        List<ProjectEleOrder> orderList= projectEleOrderDao.selectByMap(cod);
        List<ExcelVo>listVo=new ArrayList<ExcelVo>();
        List<OpenOption>optionList=new ArrayList<>();
        int i=1;
        for (ProjectEleOrder c:orderList) {
            ExcelVo vo = new ExcelVo();
            vo.setElevatorName(c.getElevatorTypeName());
            vo.setNums(c.getNum());
            vo.setInstallPrice(c.getInstallPrice());
            vo.setSingleTotalPrice(c.getSingleTotalPrice());
            vo.setTotalPrice(c.getTotalPrice());
            vo.setCid(i);
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
                }
            });

            List<ProjectEleOptionsVo > optins = projectEleOptionsDao.findOptionByOrder(c.getId(), 3);
            listVo.add(vo);
     /* 选装项      if (optins.size() > 0) {
                ExcelVo voOption1 = new ExcelVo();
                voOption1.setElevatorName(c.getElevatorTypeName() + "非标项");//非标项单独一行
                listVo.add(voOption1);
                optins.forEach(op1 -> {
                    ExcelVo voOption = new ExcelVo();
                    voOption.setElevatorName(op1.getGroupName()+"_"+op1.getOptionName());
                    listVo.add(voOption);
                });

            }*/

            i++;
        }
        ExcelVo  rs=new ExcelVo();
        if(orderList.size()>0){
            ProjectPrice aa = projectPriceDao.selectById(orderList.get(0).getVersionId());
            rs.setElevatorName("总价");
            rs.setTotalPrice(aa.getTotalPrice());
            rs.setInstallPrice( ConvertMoneyUtil.convert(Double.parseDouble(orderList.get(0).getTotalPrice())));
        }else{
            rs.setElevatorName("总价");
            rs.setTotalPrice("0");
            rs.setInstallPrice( "零");
        }
        listVo.add(rs);


        System.getProperty("file.separator");
        export(listVo);

    }


    public void export(List<ExcelVo> clsList) {

     // Map aa=  projectDao.getPriceInfo(null);
        String filename= UUIDUtil.getUUID();
        String  path="D:\\fileUpload\\"+filename+".xls";
       // File aa=new File("D:/test/1/exportCls.xls");

        File aa=new File(path);
        if(!aa.getParentFile().exists()){
            aa.getParentFile().mkdirs();
        }
        try {
            aa.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* if(!aa.exists()){
         finally {
            }
        }*/

        try(OutputStream out=new FileOutputStream(aa)) {
            ExcelWriter writer=new ExcelWriter(out, ExcelTypeEnum.XLSX);

            if(!clsList.isEmpty()) {

                Sheet sheet=new Sheet(1,0,clsList.get(0).getClass());

                sheet.setSheetName("价格表测试");
                writer.write(clsList, sheet);
            }
            writer.finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
       // aa.delete();
        //aa.getParentFile().delete();  删除上一级
    }

    @Test
    public void Test11(){

        Map aa=  projectDao.getPriceInfo(null);
        String a="111";


        Map totalMap=projectDao.getPriceInfo(null);
        if(totalMap!=null){


            if(totalMap.containsKey("installPrice")){
                System.out.println("----------------"+String.valueOf(totalMap.get("installPrice")));
               // projectPriceDetailVO.setInstallPrice(String.valueOf(totalMap.get("installPrice")));
            }
            if(totalMap.containsKey("transportPrice")){
               // projectPriceDetailVO.setTransportPrice(String.valueOf(totalMap.get("transportPrice")));
            }
        }
    }


    @Test
    public void testMail(){

       // wxElevatorService.sendPriceMail(49L);
        System.out.println("__________________________________________");
    }
}
