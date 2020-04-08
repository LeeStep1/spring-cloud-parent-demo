package SpringEvent.GameTest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liyang
 * @date: 2020-04-08
 **/
@Component
@Data
public class Game {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 比赛标记 1、比赛开始  2、中场休息 3、中场休息结束  4、比赛结束
     */
    private int gameFlag;

    /**
     * 比赛开始
     */
    public void gameStart(){
        System.out.println("上半场比赛正式开始..........");
        gameFlag = 1;
        applicationEventPublisher.publishEvent(new GameStartEvent(applicationContext,this));
    };

    /**
     * 中场休息
     */
    public void halfTimeStart(){
        System.out.println();
        System.out.println("进入中场休息..........");
        gameFlag = 2;
        applicationEventPublisher.publishEvent(new HalfTimeStartEvent(applicationContext,this));
    };

    /**
     * 中场休息结束
     */
    public void halfTimeEnd(){
        System.out.println();
        System.out.println("下半场比赛正式开始..........");
        gameFlag = 3;
        applicationEventPublisher.publishEvent(new HalfTimeEndEvent(applicationContext,this));
    };

    /**
     * 比赛结束
     */
    public void gameEnd(){
        System.out.println();
        System.out.println("比赛结束..........");
        gameFlag = 4;
        applicationEventPublisher.publishEvent(new GameEndEvent(applicationContext,this));
    };


}
