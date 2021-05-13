package guava.base;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class StopWatchTest {

    /**
     * stopwatch:
     *          1、计时器
     *          2、stopwatch.stop() 默认自动转换时间
     *          3、elapsed(TimeUnit)  强制用某个时间单位计时
     * @Author LeeYoung
     **/
    @Test
    public void stopWatch() throws InterruptedException {
        orderProcess("0318");
    }

    private void orderProcess(String orderId) throws InterruptedException {
        System.out.printf("the order start %s\n", orderId);
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.SECONDS.sleep(1);
        System.out.printf("the order %s end take %s\n",orderId,stopwatch.stop());

        //毫秒
//        System.out.printf("the order %s end take %s\n",orderId,stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));


    }
}
