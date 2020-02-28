package ProxyTest;

import org.junit.Test;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-27
 **/
public class TestProxy {

    @Test
    public void staticProxy() {
        BastetBallFactory bastetBallFactory = new BastetBallFactory();

        ProxySelf proxySelf = new ProxySelf(bastetBallFactory);
        proxySelf.makeBall();
    }

    @Test
    public void dynamicProxy(){
//        BastetBallFactory bastetBallFactory = new BastetBallFactory();
//        ProxySelfDynamic proxySelfDynamic = new ProxySelfDynamic(bastetBallFactory);
//        BallFactory ballFactory = (BallFactory) proxySelfDynamic.proxyFactory();
////        BastetBallFactory ball = (BastetBallFactory)o;
////        System.out.println(o);
//        ballFactory.makeBall();
//
//        System.out.println("============================================");
//
//        FootBallFactory footBallFactory = new FootBallFactory();
//        ProxySelfDynamic proxySelfDynamic1 = new ProxySelfDynamic(footBallFactory);
//        BallFactory ball = (BallFactory) proxySelfDynamic1.proxyFactory();
//        ball.makeBall();
//
//
//        System.out.println("============================================");

        RedDisk redDisk = new RedDisk();
        ProxySelfDynamic p = new ProxySelfDynamic(redDisk);
        DiskFactory o = (DiskFactory) p.proxyFactory();
        o.makeDisk();
        OutPutFIleUtils.outPutFIle(redDisk.getClass(),o.getClass().getSimpleName());
    }


}
