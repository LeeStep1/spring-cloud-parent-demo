package SpringEvent;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liyang
 * @date: 2020-04-08
 **/
@Component
public class BenchPlayer {
    public void playerGoOn(){
        System.out.println("替补球员上场........");
    }

    public void playerOut(){
        System.out.println("替补球员下场........");
    }
}
