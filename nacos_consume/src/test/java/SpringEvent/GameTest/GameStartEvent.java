package SpringEvent.GameTest;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 比赛开始事件
 * @author: liyang
 * @date: 2020-04-08
 **/
public class GameStartEvent extends GameEvent {

    public GameStartEvent(Object source,Game game) {
        super(source);
        this.game = game;
    }
}
