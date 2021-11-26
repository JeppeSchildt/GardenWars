package com.garden.game.Skills;

import com.garden.game.player.Player;
import com.garden.game.tools.Constants;

public class MoreFruits extends Skill{
    public MoreFruits(int turns, Player player) {
        super(turns, player);
    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.makePlantAvailable(Constants.MELON);
        player.makePlantAvailable(Constants.LEMON);
        player.makePlantAvailable(Constants.PINEAPPLE);
        player.makePlantAvailable(Constants.AVOCADO);
        player.makePlantAvailable(Constants.STRAWBERRY);
        player.makePlantAvailable(Constants.ORANGE);
        player.makePlantAvailable(Constants.GRAPES);

    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }
}

