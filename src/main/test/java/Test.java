import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.bit.module.miniapp.vo.ExcelVo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-02
 **/
public class Test {


    @org.junit.Test
    public void testExcel(){

    //准备数据
        List<ExcelVo> clsList=new ArrayList<ExcelVo>();
        for(int i=0;i<3;i++) {
            ExcelVo cls=new ExcelVo();
            cls.setCid(i);
            cls.setElevatorName("java"+i);
            clsList.add(cls);
        }
        export(clsList);

    }


    public void export(List<ExcelVo> clsList) {

        File aa=new File("D:/exportCls.xls");
        if(!aa.exists()){
            try {
                aa.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
            }
        }

        try(OutputStream out=new FileOutputStream(aa)) {
            ExcelWriter writer=new ExcelWriter(out, ExcelTypeEnum.XLSX);

            if(!clsList.isEmpty()) {

                 Sheet sheet=new Sheet(1,0,clsList.get(0).getClass());

                 sheet.setSheetName("导出班级测试");
                 writer.write(clsList, sheet);
                 }
                 writer.finish();

                 } catch (Exception e) {
                 e.printStackTrace();
                 }


    }

}
