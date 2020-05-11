package Lock.ProductConsumeTest;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @description:
 * @author: liyang
 * @date: 2020-03-16
 **/
public class TestDemo {
    //创建一个容器
    ArrayList<Object> list = new ArrayList<>();

    public void add(Object obj){
        list.add(obj);
    }

    public void remove(){
        list.remove(0);
    }

    public int size(){
        return list.size();
    }

    //生产者
    public void product(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                synchronized (this){
                    for (int j = 0; j < 20; j++) {
                        if(this.size() < 20){
                            list.add(1);
                            System.out.println(Thread.currentThread().getName() + "   生产了一个面包  一共有 " + this.size() + "个" );
                            this.notify();
                            try {
                                this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }).start();
        }
    }

    public void consume(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                synchronized (this){
                    for (int j = 0; j < 50; j++) {
                        if(this.size()!=0){
                            this.remove();
                            System.out.println(Thread.currentThread().getName() + " 消费一个.... 还剩  " + this.size() + "个");
                        }else {
                            this.notify();
                            try {
                                this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }).start();
        }
    }

    @Test
    public void  test(){
        this.size();
        this.product();
        this.consume();

        while (true){

        }
    }

}
