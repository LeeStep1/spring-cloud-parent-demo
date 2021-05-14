package guava.concurrent;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

/**
 * 漏桶
 * @Author LeeYoung
 **/
public class Bucket {

    //桶
    private final ConcurrentLinkedQueue<Integer> container = new ConcurrentLinkedQueue<>();

    //桶的上限
    private final static int BUCKET_LIMIT = 1000;

    //桶的漏斗
    private final RateLimiter limiter = RateLimiter.create(10);

    //监控是否超过上限
    private final Monitor offerMonitor = new Monitor();

    //下游消费监控
    private final Monitor pollMonitor = new Monitor();

    public void submit(Integer data){
        //判断桶的是否满了
        if(offerMonitor.enterIf(offerMonitor.newGuard(()->container.size() < BUCKET_LIMIT))){
            try {
                container.offer(data);
                System.out.println(currentThread() + " submit data " + data + ", current size: " + container.size());
            }finally {
                offerMonitor.leave();
            }
        }else {

            //桶满了降级
            throw new IllegalStateException("The bucket is full");
        }
    }

    /**
     * 下游消费
     * @param consumer
     * @Author LeeYoung
     **/
    public void takeThenConsume(Consumer<Integer> consumer){
        if(pollMonitor.enterIf(pollMonitor.newGuard(()->!container.isEmpty()))){
            try {
                System.out.println(currentThread() + " wait " + limiter.acquire());

                //消费。
                consumer.accept(container.poll());
            }finally {
                //释放
                pollMonitor.leave();
            }
        }
    }
}
