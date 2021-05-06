package guava.eventBus.bus;

import com.google.common.eventbus.EventBus;
import guava.eventBus.events.People;
import guava.eventBus.events.Resident;
import guava.eventBus.listeners.SimpleListener;
import org.junit.Test;

public class SimpleBus {

    @Test
    public void simpleBus1(){
        EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());
        eventBus.post("假装一个消息...");

        eventBus.post(1500);
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
