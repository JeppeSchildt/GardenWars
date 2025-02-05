package com.garden.game.Skills;


import com.badlogic.gdx.math.Vector2;
import com.garden.game.player.Player;
import com.garden.game.tools.Constants;
import com.garden.game.world.Plant;

import java.util.Iterator;
import java.util.Map;

public class Fertilizer2 extends Skill {
    public Fertilizer2(int turns, Player player) {
        super(turns, player);
        name = "Fertilizer 2";
        incoming = 0;
        adjacent.add(Constants.AUTO_HARVEST);
        hasTurnWork = true;
    }

    @Override
    public void skillLearned(){
        super.skillLearned();
    }

    // Visit plants and make them lose less water pr. turn. More resilient.
    @Override
    public void turnWork() {
        Iterator<Map.Entry<Vector2, Plant>> entryIt = player.getPlants_().entrySet().iterator();

        while (entryIt.hasNext()) {
            Map.Entry<Vector2, Plant> entry = entryIt.next();
            Plant plant = entry.getValue();

            if(!plant.isFertilizer2) {
                plant.isFertilizer2 = true;
                plant.setWaterLoss(plant.getWaterLoss()-0.75f);
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
