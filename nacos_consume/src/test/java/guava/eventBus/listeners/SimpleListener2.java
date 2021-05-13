package guava.eventBus.listeners;

import com.google.common.eventbus.Subscribe;
import guava.eventBus.events.People;
import guava.eventBus.events.Resident;

public class SimpleListener2 {

    /**
     * event bus 的监听器
     * 注意事项 ：1、如果想要注册到消息总线中，方法头部必须加入 google中的 @Subscribe 注解
     *          2、监听器想要起作用，必须注册到消息总线（eventBus）中 eventBus.register(new SimpleListener())
     *          3、入参代表监听的消息类型  **必须为封装类型**
     *          4、入参有且只有一种且不能改变
     *          5、如果注册的监听器类中有多个相同类型的监听方法，则消息放入消息总线时，会被多个订阅方法监听到
     *          6、event为子类对象时，父子监听器可以同时接收
     *          7、event为父类对象时，子类监听器无法接收
     *          8、EventBus 为同步阻塞试消息推送 如果想要用异步消息，利用 AsyncEventBus
     * @param event
     */
    @Subscribe
    public void simpleListenerString1(String event) throws InterruptedException {
        System.out.printf(Thread.currentThread().getName() + "  1监听器1：收到监听的String类型的消息 {%s}",event);
    }

    @Subscribe
    public void simpleListenerString2(String event){
        System.out.println();
        System.out.printf(Thread.currentThread().getName() + "   2监听器2：收到监听的String类型的消息 {%s}",event);
    }

    @Subscribe
    public void simpleListenerInt1(Integer event){
        System.out.println();
        System.out.printf("3监听器3：收到监听的int 类型的消息 {%d}",event);
    }

    @Subscribe
    public void listenerResident(Resident resident){
        System.out.println();
        System.out.printf("监听器3：收到监听的Resident 类型的消息 名字是 %s",resident.getName());
    }

    @Subscribe
    public void listenerPeople(People people){
        System.out.println();
        System.out.printf("监听器4：收到监听的People 类型的消息 名字是 %s",people.getName());
    }
}
