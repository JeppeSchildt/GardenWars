package com.garden.game.Skills;

import com.garden.game.player.Player;

public class Water extends Skill{
    public Water(int turns, Player player) {
        super(turns, player);
        hasTurnWork = true;
    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.maxWater += 50;
        player.waterPerTurn = 20;
    }
}
