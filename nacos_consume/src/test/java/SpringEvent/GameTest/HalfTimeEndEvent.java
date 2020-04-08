package SpringEvent.GameTest;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 中场休息结束事件
 * @author: liyang
 * @date: 2020-04-08
 **/
public class HalfTimeEndEvent extends GameEvent {


    public HalfTimeEndEvent(Object source,Game game) {
        super(source);
        this.game = game;
    }
}
