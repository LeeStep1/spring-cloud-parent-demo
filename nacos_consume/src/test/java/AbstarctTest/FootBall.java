package AbstarctTest;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-25
 **/
public class FootBall extends AbstractBall {
    @Override
    void initBall() {
        System.out.println("找个足球.....");
    }

    @Override
    void pepleTogether() {
        System.out.println("用足球把人聚在一起.....");
        System.out.println("大家一起玩足球.....");
    }
}
