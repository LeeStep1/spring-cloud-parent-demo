package ProxyTest;

import java.io.Serializable;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-27
 **/
public class BastetBallFactory implements BallFactory{
    @Override
    public void makeBall() {
        System.out.println("生产篮球.......");
    }
}
