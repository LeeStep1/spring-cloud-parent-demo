package ProxyTest;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-27
 **/
public class FootBallFactory implements BallFactory {
    @Override
    public void makeBall() {
        System.out.println("制造足球.......");
    }
}
