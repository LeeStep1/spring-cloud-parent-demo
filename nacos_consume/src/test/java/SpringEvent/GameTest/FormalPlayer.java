package SpringEvent;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liyang
 * @date: 2020-04-08
 **/
@Component
public class FormalPlayer {

    public void playerGoOn(){
        System.out.println("正式球员上场........");
    }

    public void playerOut(){
        System.out.println("正式球员下场........");
    }
}
