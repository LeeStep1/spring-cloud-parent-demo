package AbstarctTest;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-25
 **/
public class BasketBall extends AbstractBall{
    @Override
    void initBall() {
        System.out.println("找个篮球");
    }

    @Override
    void pepleTogether() {
        System.out.println("用篮球把人聚在一起");
        System.out.println("大家一起玩篮球");
    }

    void newMethod(){
        super.newMethod();
        System.out.println("這是BasketBall自己的方法");
    }
}
