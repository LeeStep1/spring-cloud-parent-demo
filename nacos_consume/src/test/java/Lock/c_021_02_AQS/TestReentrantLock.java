package Lock.c_021_02_AQS;

import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

    private static volatile int i = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        for (int j = 0; j < 10; j++) {
            new Thread(()->{
                try {
                    lock.lock();
                    i++;
                    System.out.println(Thread.currentThread().getName() + "  "  + i);
                }finally {
                    lock.unlock();
                }

            }).start();



            //synchronized (TestReentrantLock.class) {
        }


        //}

        while (true){}

        //synchronized 程序员的丽春院 JUC
    }
}
