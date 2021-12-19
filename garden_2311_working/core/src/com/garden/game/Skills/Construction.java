package com.garden.game.Skills;

import com.garden.game.player.Player;
import com.garden.game.tools.Constants;

// Make plants cheaper.
public class Construction extends Skill {
    public Construction(int turns, Player player) {
        super(turns, player);
        adjacent.add(Constants.AUTO_HARVEST);
        name = "Construction";

    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.constructionBonus = 50; // Deducted from price of plants.
    }


    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }


}
