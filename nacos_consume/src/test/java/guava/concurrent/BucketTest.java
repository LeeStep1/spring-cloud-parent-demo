package guava.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

public class BucketTest {

    public static void main(String[] args) {
        final Bucket bucket = new Bucket();
        final AtomicInteger DATA_CREATOR = new AtomicInteger(0);

        //5个线程去拿数据  消费者
        IntStream.range(0,5).forEach(i->{
            new Thread(()->{
                for (;;){
                    int data = DATA_CREATOR.getAndIncrement();
                    bucket.submit(data);
                    try {
                        TimeUnit.MILLISECONDS.sleep(200L);
                    } catch (Exception e) {
                        if(e instanceof IllegalStateException){
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }).start();
        });
        //25个/S


        //submit data 208 / W
        IntStream.range(0,5).forEach(i->{
            new Thread(()->{
                for (;;){
                    bucket.takeThenConsume(x-> System.out.println(currentThread() + " w " + x));
                }
            }).start();
        });
    }
}
