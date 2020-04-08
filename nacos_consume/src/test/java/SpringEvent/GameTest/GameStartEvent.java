package SpringEvent;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 比赛开始事件
 * @author: liyang
 * @date: 2020-04-08
 **/
public class GameStartEvent extends ApplicationEvent {

    Game game;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public GameStartEvent(Object source) {
        super(source);
    }

    public GameStartEvent(Object source,Game game) {
        super(source);
        this.game = game;
    }
}
