package SpringEvent;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @description: 事件源 (事件的属性)
 * @author: liyang
 * @date: 2020-04-08
 **/
@Component
public class SendMailSource{

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ApplicationContext applicationContext;

    public void mailEvent(){
        SendMailEvent event = new SendMailEvent(applicationContext, 1, "小李");
        System.out.println(" 发送邮件事件..........");

        applicationEventPublisher.publishEvent(event);
    }
}
