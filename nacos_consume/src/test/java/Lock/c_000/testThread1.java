package Lock.c_000;

/**
 * @description:
 * @author: liyang
 * @date: 2020-03-16
 **/
public class testThread1 implements Runnable {
    Thread t2;
    public testThread1(Thread t2) {
        this.t2 =t2;
    }

    @Override
    public void run() {
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("t1" + Thread.currentThread().getName() + "   " + i);
        }


    }


}
