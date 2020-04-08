package SpringEvent;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 比赛结束事件
 * @author: liyang
 * @date: 2020-04-08
 **/
public class GameEndEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public GameEndEvent(Object source) {
        super(source);
    }
}
