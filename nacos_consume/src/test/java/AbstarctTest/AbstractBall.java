package AbstarctTest;

import service.IService;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-25
 **/
public abstract class AbstractBall  {

    abstract void initBall();

    abstract void pepleTogether();

    public void play(){
        initBall();
        pepleTogether();
    }

    void newMethod(){
        System.out.println("這是父類自己的方法");
    }



}
