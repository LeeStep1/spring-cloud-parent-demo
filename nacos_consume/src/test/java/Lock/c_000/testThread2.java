package Lock.c_000;

/**
 * @description:
 * @author: liyang
 * @date: 2020-03-16
 **/
public class testThread2 implements Runnable {

    @Override
    public void run() {
        Thread.yield();
        for (int i = 0; i < 10; i++) {
//
            System.out.println("t2" + Thread.currentThread().getName() + "   " + i);
        }

    }
}
