package SpringEvent;

import SpringEvent.GameTest.Game;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: liyang
 * @date: 2020-04-08
 **/
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigStart.class);
        SendMailSource bean = applicationContext.getBean(SendMailSource.class);
        bean.mailEvent();


    }
}
