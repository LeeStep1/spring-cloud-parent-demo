package guava.eventBus.monitor;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件事件监听器
 * @Author LeeYoung
 * @Date 2021/5/9
 **/
public class FileChangeListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileChangeListener.class);

    @Subscribe
    public void onChange(FileChangeEvent fileChangeEvent){
        LOGGER.info("路径 {} 下发生了 {} 事件",fileChangeEvent.getPath(),fileChangeEvent.getKind());
    }
}
