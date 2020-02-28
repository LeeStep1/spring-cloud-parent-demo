package InClassTest;

import com.sun.org.apache.xpath.internal.operations.String;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-26
 **/
public class InServiceImpl implements InService {
    @Override
    public java.lang.String initString() {
        System.out.println("内部实例初始化.....");
        java.lang.String s = "Name";
        return  s;
    }
}
