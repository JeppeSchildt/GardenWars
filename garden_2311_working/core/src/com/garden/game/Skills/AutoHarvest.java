package com.garden.game.Skills;

import com.garden.game.player.Player;

public class AutoHarvest extends Skill{
    public AutoHarvest(int turns, Player player) {
        super(turns, player);
        name = "Auto Harvest";

    }

    @Override
    public void skillLearned(){
        super.skillLearned();
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }

}


