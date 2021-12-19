package com.garden.game.Skills;

import com.garden.game.player.Player;

public class Communication extends Skill {
    public Communication(int turns, Player player) {
        super(turns, player);
        hasTurnWork = true;
        name = "Communication";
    }

    @Override
    public void skillLearned() {
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

