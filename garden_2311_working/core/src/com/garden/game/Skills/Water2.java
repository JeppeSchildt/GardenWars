package com.garden.game.Skills;

import com.garden.game.player.Player;

public class Water2 extends Skill{
    public Water2(int turns, Player player) {
        super(turns, player);
        hasTurnWork = true;

    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.maxWater += 100;
        player.waterPerTurn = 50;
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }
}