package guava.eventBus.monitor;


import com.google.common.eventbus.EventBus;

public class MonitorClient {

    public static void main(String[] args) throws Exception {
        final EventBus eventBus = new EventBus() ;
        eventBus.register(new FileChangeListener());
        TargetMonitor monitor = new DirectoryTargetMonitor(eventBus,"D:\\monitorEventBus");
        monitor.startMonitor();
    }
}
