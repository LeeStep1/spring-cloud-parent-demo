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

    private volatile boolean start = false;

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
        System.out.println("试试 ");
        LOGGER.info("路径[{}]正在被监控......",path);

        this.start = true;
        while (start) {
            WatchKey watchKey = null;

            try {

                //监听路径发生事件，没发生一直阻塞
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event -> {

                    //获取事件类型接口(文件 创建  删除  修改)
                    WatchEvent.Kind<?> kind = event.kind();

                    //获取事件路径
                    Path path = (Path)event.context();

                    //NIO中获取文件全路径名
                    Path child = DirectoryTargetMonitor.this.path.resolve(path);

                    //推送监听器
                    eventBus.post(new FileChangeEvent(child,kind));
                });
            }catch (Exception e){

                //此异常为 take() 时的中断阻塞异常 出现异常 停止
                this.start = false;
            }finally {
                //中断后继续捕获  使得发生一次事件监听结束后可继续监听其余事件
                if(watchKey != null)
                    watchKey.reset();
            }

        }
    }

    @Override
    public void stopMonitor() throws Exception {
        LOGGER.info("路径 [{}] 监听 停止 ",path);
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
        LOGGER.info("路径 [{}] 监听 已经停了 ",path);
    }
}
