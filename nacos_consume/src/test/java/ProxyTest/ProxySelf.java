package ProxyTest;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-27
 **/
public class ProxySelf implements BallFactory {

    private BastetBallFactory bastetBallFactory;

    public ProxySelf(BastetBallFactory bastetBallFactory){
        this.bastetBallFactory = bastetBallFactory;
    }

    @Override
    public void makeBall() {
        System.out.println("联系供货商....");
        System.out.println("自己卖篮球....");
        System.out.println("提供售后.....");
    }
}
