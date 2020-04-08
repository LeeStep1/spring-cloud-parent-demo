package SpringEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liyang
 * @date: 2020-04-08
 **/
@Component
public class SendMailListener implements ApplicationListener<SendMailEvent> {
    @Override
    public void onApplicationEvent(SendMailEvent event) {
        System.out.println("我监听到了邮件的发送，发送人ID 是" + event.getId() + " ，发送的内容是..." + event.getName());
    }
}
