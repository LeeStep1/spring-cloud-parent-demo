package SpringEvent;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 中场休息结束事件
 * @author: liyang
 * @date: 2020-04-08
 **/
public class HalfTimeEndEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public HalfTimeEndEvent(Object source) {
        super(source);
    }
}
