package com.garden.game.Skills;

import com.garden.game.player.Player;

// Give more points when quests are completed.
public class Communication extends Skill {
    public Communication(int turns, Player player) {
        super(turns, player);
        name = "Communication";
    }

    @Override
    public void skillLearned() {
        super.skillLearned();
        player.questPointFactor = 1.5f;
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }

}

