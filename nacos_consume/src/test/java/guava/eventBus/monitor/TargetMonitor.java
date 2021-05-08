package guava.eventBus.monitor;

public interface TargetMonitor {

    /**
     * 开始监听
     * @throws Exception
     */
    void startMonitor() throws Exception;

    /**
     * 停止监听
     * @throws Exception
     */
    void stopMonitor() throws Exception;
}
