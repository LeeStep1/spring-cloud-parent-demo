package defaultInteface;

/**
 * @description:
 * @author: liyang
 * @date: 2020-03-03
 **/
public class Test {

    @org.junit.Test
    public void test(){
        ServiceA servceA  = new ServiceAImp2();
        servceA.a();
        servceA.b();
    }
}
