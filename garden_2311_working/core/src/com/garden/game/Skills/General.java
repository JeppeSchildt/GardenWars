package com.garden.game.Skills;

import com.badlogic.gdx.math.Vector2;
import com.garden.game.player.Player;
import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;

import java.util.Iterator;
import java.util.Map;

public class General extends Skill {
    public General(int turns, Player player) {
        super(turns, player);
        hasTurnWork = true;
        adjacent.add(Constants.CONSTRUCTION);
        adjacent.add(Constants.COMMUNICATION);
        adjacent.add(Constants.WATER_1);
    }

    @Override
    public void skillLearned() {
        super.skillLearned();
    }

    // Visit plants and make them more profitable.
    @Override
    public void turnWork() {
        Iterator<Map.Entry<Vector2, Plant>> entryIt = player.getPlants_().entrySet().iterator();

        while (entryIt.hasNext()) {
            Map.Entry<Vector2, Plant> entry = entryIt.next();
            Plant plant = entry.getValue();

            if (!plant.isGeneral) {
                plant.profit += 2;

            }
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
