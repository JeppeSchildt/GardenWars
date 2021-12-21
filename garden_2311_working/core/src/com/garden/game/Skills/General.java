package com.garden.game.Skills;

import com.badlogic.gdx.math.Vector2;
import com.garden.game.player.Player;
import com.garden.game.tools.Constants;
import com.garden.game.world.Plant;

import java.util.Iterator;
import java.util.Map;

public class General extends Skill {
    public General(int turns, Player player) {
        super(turns, player);
        name = "General";
        adjacent.add(Constants.CONSTRUCTION);
        adjacent.add(Constants.COMMUNICATION);
        adjacent.add(Constants.WATER_1);
    }

    // Visit plants and make them more profitable.
    @Override
    public void skillLearned() {
        super.skillLearned();
        for (Map.Entry<Integer, Float> entry : Constants.idProfitMap.entrySet()) {
            entry.setValue(entry.getValue()+2f);
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
