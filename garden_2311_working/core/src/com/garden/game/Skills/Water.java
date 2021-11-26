package com.garden.game.Skills;

import com.garden.game.player.Player;
import com.garden.game.tools.Constants;

public class Water extends Skill{
    public Water(int turns, Player player) {
        super(turns, player);
        hasTurnWork = true;
        adjacent.add(Constants.WATER_2);
    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.maxWater += 50;
        player.waterPerTurn = 20;
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }
}
