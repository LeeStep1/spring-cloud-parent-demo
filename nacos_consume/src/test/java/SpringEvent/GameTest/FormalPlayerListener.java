package SpringEvent.GameTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description:正式球员监听比赛事件
 * @author: liyang
 * @date: 2020-04-08
 **/
@Component
public class FormalPlayerListener implements ApplicationListener<GameEvent> {

    /**
     * 正式球员球员类
     */
    @Autowired
    FormalPlayer formalPlayer;

    /**
     * 正式球员监听比赛事件
     * @param event
     */
    @Override
    public void onApplicationEvent(GameEvent event) {
        Game game = event.game;

        //比赛开始正式球员上场
        if(game.getGameFlag() == 1){
            formalPlayer.playerGoOn();
        }

        //中场休息正式球员下场
        if(game.getGameFlag() == 2){
            formalPlayer.playerOut();
        }

        //中场休息结束正式球员上场
        if(game.getGameFlag() == 3){
            formalPlayer.playerGoOn();
        }

        //比赛结束正式球员下场
        if(game.getGameFlag() == 4){
            formalPlayer.playerOut();
        }

    }
}
