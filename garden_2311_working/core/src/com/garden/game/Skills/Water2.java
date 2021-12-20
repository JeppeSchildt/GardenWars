package com.garden.game.Skills;

import com.garden.game.player.Player;
import com.garden.game.tools.Constants;

public class Water2 extends Skill{
    public Water2(int turns, Player player) {
        super(turns, player);
        name = "Water 2";
        hasTurnWork = true;
        adjacent.add(Constants.IRRIGATION);

    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.maxWater += 100;
        player.waterPerTurn *= 2;
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }
}