package com.nacosDemo.until;

import org.apache.poi.ss.formula.functions.T;

interface Ball{
    public void play();
}

class BallClass{
    public int a = 0;

    private String p = "父类的私有属性";

    public String getP(){
        return p;
    }

    public void play(){
        System.out.println("球玩...");
    };
}

class BasketBallClass extends BallClass {

    public int a = 1;
    
    @Override
    public void play() {
        System.out.println("篮球玩......");
    }

    public void go(){
        System.out.println("打篮球.....");
    }
}

interface Car{
    public void playCar();
}

class BenCar implements Car{
    @Override
    public void playCar() {
        System.out.println("奔驰车.....");
    }
}

class Toyota implements Car{

    @Override
    public void playCar() {
        System.out.println("丰田车........");
    }
}

class FootBall implements Ball{

    @Override
    public void play() {
        System.out.println("踢足球");
    }

    public void go(){
        System.out.println("go 方法....");
    }
}

class BasketBall implements Ball{

    @Override
    public void play() {
        System.out.println("打篮球");
    }
}


class Fatory{
    public Fatory(){};
    public static <T>T getInstance(String name){
        T b = null;
        try {
            b = (T) Class.forName(name).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}



public class ZZTest {

    public static void main(String[] args) {
//        FootBall fb = Fatory.getInstance(FootBall.class.getName());
//        BasketBall fb2 = Fatory.getInstance(BasketBall.class.getName());
//        fb.play();
//
//        fb2.play();

        BasketBallClass ball = new BasketBallClass();

        int i = ball.a;

        String s = ball.getP();

        System.out.println("s   " + s);

        System.out.println("aaa   " + i);

        ball.play();
    }
}
