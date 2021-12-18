package com.garden.game.Skills;

import com.garden.game.player.Player;
import com.garden.game.tools.Constants;

public class MoreFruits extends Skill{
    public MoreFruits(int turns, Player player) {
        super(turns, player);
        name = "More Fruits";
        adjacent.add(Constants.FERTILIZER_2);
    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.makePlantAvailable(Constants.ORANGE);
        player.makePlantAvailable(Constants.LEMON);
        player.makePlantAvailable(Constants.STRAWBERRY);
        player.makePlantAvailable(Constants.GRAPES);
        player.makePlantAvailable(Constants.AVOCADO);
        player.makePlantAvailable(Constants.MELON);
        player.makePlantAvailable(Constants.PINEAPPLE);




    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }
}

