package guava.eventBus.bus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import guava.eventBus.events.People;
import guava.eventBus.events.Resident;
import guava.eventBus.listeners.SimpleListener;
import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleBus {

    /**
     * 同步消息
     * @Author LeeYoung
     * @Date 2021/5/10
     **/
    @Test
    public void simpleBus1(){
        EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());
        eventBus.post("假装一个消息...");

        eventBus.post(1500);
    }

    /**
     * 异步Event
     * @Author LeeYoung
     * @Date 2021/5/10
     **/
    @Test
    public void simpleAsyncBus() throws InterruptedException {
        AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(3));
        asyncEventBus.register(new SimpleListener());
        asyncEventBus.post("假装一个消息...");

        asyncEventBus.post(1500);

        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void simpleBus2(){
        EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());
        Resident resident = new Resident();
        resident.setName("小李");
        eventBus.post(resident);
    }

    @Test
    public void simpleBus3(){
        EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());
        People people = new People();
        people.setName("小李");
        eventBus.post(people);
    }
}
