package guava.base;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class StopWatchTest {

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
