package SpringEvent;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 中场休息事件开始
 * @author: liyang
 * @date: 2020-04-08
 **/
public class HalfTimeStartEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public HalfTimeStartEvent(Object source) {
        super(source);
    }
}
