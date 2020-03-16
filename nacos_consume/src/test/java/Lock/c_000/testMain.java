package Lock.c_000;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * @description:
 * @author: liyang
 * @date: 2020-03-16
 **/
public class testMain {
    private Thread t2;

    @Test
    public void demo1(){
        Thread t2;
        Thread t1;
        t2 = new Thread(new testThread2());

        t1 = new Thread(new testThread1(t2));


        t1.start();
        t2.start();
    }


    /**
     * 当第一个线程跑到 5 的时候 第二个线程触发  等第二个线程跑完  第一个线程继续
     */


    @Test
    public void demo2(){

        CountDownLatch cc = new CountDownLatch(1);

        Object lockt1 = new Object();
        Object lockt2 = new Object();
        Thread t2 = new Thread(()->{
            synchronized (lockt1){
                System.out.println("t2  " + Thread.currentThread().getName());
//            try {
//                lockt2.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
                for (int i = 0; i < 10; i++) {

                    System.out.println("t2  " + i);
                }
                lockt1.notify();
            }

        });

        Thread t1 = new Thread(()->{
            synchronized (lockt1){
                System.out.println("t1  " + Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    if(i == 5){
                        try {
//                            lockt2.notify();
                            t2.start();
                            lockt1.wait();

                            System.out.println("t2  " + Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("t1  " + i);
                }
                cc.countDown();
            }

        });
//        t2.start();

        t1.start();

        try {
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void demo3(){

        CountDownLatch cc = new CountDownLatch(1);

        Thread t2 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println( "t2   "+ i);
            }
        });
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                if(i == 5){
                    try {
                        t2.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("t1  " + i);

            }
            cc.countDown();
        });


        t1.start();
        t2.start();

        try {
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    /**
     *  A1B2C3D4E5...... 轮训输出
     */

    static Thread t5 = null;
    static Thread t4 = null;
    @Test
    public void demo4(){
        char[] char1 = new char[]{'A','B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        char[] char2 = new char[]{'1','2', '3', '4', '5', '6', '7', '8', '9'};

        CountDownLatch cc = new CountDownLatch(1);

        t5 = new Thread(()->{
            for (char i : char2){
                LockSupport.park();
                System.out.print(i);
                LockSupport.unpark(t4);

            }
            cc.countDown();
        });


        t4 = new Thread(()->{
            for (char i: char1){
                System.out.print(i);
                LockSupport.unpark(t5);
                LockSupport.park();
            }
        });

        t4.start();
        t5.start();

        try {
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static Thread t6 = null;
    static Thread t7 = null;

    @Test
    public void demo5() throws InterruptedException {
        char[] char1 = new char[]{'A','B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        char[] char2 = new char[]{'1','2', '3', '4', '5', '6', '7', '8', '9'};

        CountDownLatch cc = new CountDownLatch(1);
        Object obj1 = new Object();
        Object obj2 = new Object();
        t6 = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " t6");
            synchronized (obj1){
                for(char i : char1){
                    System.out.print(i);
                    obj1.notify();
                    try {
                        obj1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        t7 = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " t7 ");
            t6.start();
            synchronized (obj1){
                for(char i : char2){
                    try {
                        obj1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print(i);
                    obj1.notify();
                }

                obj1.notify();
            }

            cc.countDown();

        });

        t7.start();

        try {
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
