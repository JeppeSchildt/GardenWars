package com.garden.game.Skills;

import com.garden.game.player.Player;

public class Construction extends Skill {
    public Construction(int turns, Player player) {
        super(turns, player);

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
