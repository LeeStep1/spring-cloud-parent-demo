package InClassTest;

import org.junit.Test;
import org.springframework.beans.BeansException;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-26
 **/
public class OutClass {

    @Test
    public void test(){
        String name = "1";
        getImpl(name, new InService() {
            @Override
            public String initString() {
                System.out.println("复写方法执行....");
                String jj = "ssss";
                return jj;
            }
        });
    }

    void getImpl(String name ,InService inService){
        name = "外部初始化........";
        System.out.println(name);
        inService.initString();

    }
}
