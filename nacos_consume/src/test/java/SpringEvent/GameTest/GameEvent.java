package SpringEvent.GameTest;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @description:
 * @author: liyang
 * @date: 2020-04-08
 **/
public class GameEvent extends ApplicationEvent {
    Game game;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public GameEvent(Object source) {
        super(source);
    }
}
