package com.garden.game.Skills;

import com.garden.game.player.Player;
import com.garden.game.tools.Constants;

import java.util.Map;

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
        for (Map.Entry<Integer, Integer> entry : Constants.idPriceMap.entrySet()) {
            entry.setValue(entry.getValue()-10);
        }
    }


    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();
        }
    }


}
