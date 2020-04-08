package SpringEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liyang
 * @date: 2020-04-08
 **/
public class SendMailEvent extends ApplicationEvent {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SendMailEvent(Object source) {
        super(source);
    }

    public SendMailEvent(Object source, int id, String name) {
        super(source);
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;

}
