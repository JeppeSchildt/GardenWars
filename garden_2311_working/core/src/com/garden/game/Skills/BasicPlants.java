package com.garden.game.Skills;

import com.garden.game.player.Player;
import com.garden.game.tools.Constants;
import jdk.vm.ci.meta.Constant;

public class BasicPlants extends Skill {
    public BasicPlants(int turns, Player player) {
        super(turns, player);
    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.makePlantAvailable(Constants.TOMATO);
        player.makePlantAvailable(Constants.WHEAT);
        player.makePlantAvailable(Constants.POTATO);
        player.makePlantAvailable(Constants.CORN);
        player.makePlantAvailable(Constants.CASSAVA);
        player.makePlantAvailable(Constants.COFFEE);
        player.makePlantAvailable(Constants.EGGPLANT);
        player.makePlantAvailable(Constants.TURNIP);
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }

}
