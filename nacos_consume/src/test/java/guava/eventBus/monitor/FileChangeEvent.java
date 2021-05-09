package guava.eventBus.monitor;

import lombok.Data;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * 文件事件
 * @Author LeeYoung
 * @Date 2021/5/9
 **/
@Data
public class FileChangeEvent {

    /**
     * 路径事件监听
     */
    private final Path path;

    /**
     * 文件事件监听
     */
    private final WatchEvent.Kind<?> kind;

    public FileChangeEvent(Path path, WatchEvent.Kind<?> kind) {
        this.path = path;
        this.kind = kind;
    }
}
