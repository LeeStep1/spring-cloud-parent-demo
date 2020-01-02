package com.bit.module.miniapp.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-02
 **/
@Data
public class ExcelVo extends BaseRowModel {

    @ExcelProperty(value="梯号",index=0)
    private Integer cid;

    @ExcelProperty(value="品牌型号",index=1)
    private String elevatorName;


    @ExcelProperty(value="数量",index=2)
    private String nums;


    @ExcelProperty(value="速度(m/s)",index=3)
    private String speed;


    @ExcelProperty(value="载重（kg）",index=4)
    private String weight;

    @ExcelProperty(value="停靠层淑",index=5)
    private String floors;


    @ExcelProperty(value="设备单价(元)",index=6)
    private String singleTotalPrice;

    @ExcelProperty(value="安装单价(元)",index=7)
    private String installPrice;

    @ExcelProperty(value="总价(元)",index=8)
    private String totalPrice;


}
