package guava.concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/**
 * 限流桶
 * @Author LeeYoung
 **/
public class RateLimiterTest {

    //create(double permitsPerSecond)  QPS 每秒几个 请求 0.5d  2S一个
    private final static RateLimiter limiter = RateLimiter.create(0.5d);

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0,10).forEach(i->{
            service.submit(RateLimiterTest::testLimiter);
        });
    }

    private static void testLimiter(){
        System.out.println(currentThread() + " wait  " + limiter.acquire());
    }
}
