package com.bit.module.miniapp.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @Description:  常规直梯导出
 * @Author: liyujun
 * @Date: 2020-01-02
 **/
@Data
public class ExcelVo extends BaseRowModel {


    @ExcelProperty(value="项目名称",index=0)
    private String projectName;

    @ExcelProperty(value="梯号",index=1)
    private Integer cid;

    @ExcelProperty(value="品牌型号",index=2)
    private String elevatorName;


    @ExcelProperty(value="数量",index=3)
    private String nums;


    @ExcelProperty(value="速度(m/s)",index=5)
    private String speed;


    @ExcelProperty(value="载重（KG）/梯阶宽度（MM）",index=5)
    private String weight;

    @ExcelProperty(value="层站/倾斜度",index=6)
    private String floors;


    @ExcelProperty(value="功能选项",index=7)
    private String options;

    @ExcelProperty(value="非标项",index=8)
    private String nonStandard;


    @ExcelProperty(value="设备单价(元)",index=9)
    private String unitPrice;

    @ExcelProperty(value="安装单价(元)",index=10)
    private String installPrice;

    @ExcelProperty(value="运输单价(元)",index=11)
    private String transportPrice;

    @ExcelProperty(value="单台总价(元)",index=12)
    private String singleTotalPrice;


    @ExcelProperty(value="合价(元)",index=13)
    private String totalPrice;


}
