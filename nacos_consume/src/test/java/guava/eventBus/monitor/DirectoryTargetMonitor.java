package guava.eventBus.monitor;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;

/**
 * @Description: DirectoryTargetMonitor
 * @Author LeeYoung
 * @Date: 2021/5/8
 */
public class DirectoryTargetMonitor implements TargetMonitor{
    private final static Logger LOGGER = LoggerFactory.getLogger(DirectoryTargetMonitor.class);

    private WatchService watchService;

    private EventBus eventBus;

    private final Path path;

    public DirectoryTargetMonitor(final EventBus eventBus,final String targetPath){
        this(eventBus,targetPath,"");
    }

    public DirectoryTargetMonitor(final EventBus eventBus,final String targetPath,String... more){
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath,more);
    }

    @Override
    public void startMonitor() throws Exception {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.path.register(watchService,StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
    }

    @Override
    public void stopMonitor() throws Exception {

    }
}
