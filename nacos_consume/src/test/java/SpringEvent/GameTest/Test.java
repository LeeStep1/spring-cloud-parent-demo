package SpringEvent.GameTest;

import SpringEvent.ConfigStart;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: liyang
 * @date: 2020-04-08
 **/
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigStart.class);

        Game bean = applicationContext.getBean(Game.class);
        bean.gameStart();
        System.out.print("比赛中");
        for (int i = 0; i < 4; i++) {
            try {
                System.out.print(".");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        bean.halfTimeStart();
        for (int i = 0; i < 4; i++) {
            try {
                System.out.print(".");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        bean.halfTimeEnd();
        System.out.print("比赛中");
        for (int i = 0; i < 4; i++) {
            try {
                System.out.print(".");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        bean.gameEnd();
    }
}
