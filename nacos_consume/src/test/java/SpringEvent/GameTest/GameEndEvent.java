package SpringEvent.GameTest;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 比赛结束事件
 * @author: liyang
 * @date: 2020-04-08
 **/
public class GameEndEvent extends GameEvent {

    public GameEndEvent(Object source,Game game) {
        super(source);
        this.game = game;
    }
}
