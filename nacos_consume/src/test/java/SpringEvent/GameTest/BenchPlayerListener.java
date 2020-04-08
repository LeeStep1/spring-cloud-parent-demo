package SpringEvent.GameTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description: 替补球员监听类
 * @author: liyang
 * @date: 2020-04-08
 **/
@Component
public class BenchPlayerListener implements ApplicationListener<GameEvent> {

    /**
     * 替补球员球员类
     */
    @Autowired
    BenchPlayer benchPlayer;

    /**
     * 替补球员监听比赛事件
     * @param event
     */
    @Override
    public void onApplicationEvent(GameEvent event) {
        Game game = event.game;

        //比赛开始替补球员下场
        if(game.getGameFlag() == 1){
            benchPlayer.playerOut();
        }

        //中场休息替补球员上场
        if(game.getGameFlag() == 2){
            benchPlayer.playerGoOn();
        }

        //中场休息结束替补球员下场
        if(game.getGameFlag() == 3){
            benchPlayer.playerOut();
        }

        //比赛结束替补球员下场
        if(game.getGameFlag() == 4){
            benchPlayer.playerOut();
        }

    }


}
