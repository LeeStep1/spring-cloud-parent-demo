package SpringEvent.GameTest;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 中场休息事件开始
 * @author: liyang
 * @date: 2020-04-08
 **/
public class HalfTimeStartEvent extends GameEvent {
    public HalfTimeStartEvent(Object source,Game game) {
        super(source);
        this.game = game;
    }
}
