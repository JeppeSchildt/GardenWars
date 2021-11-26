package com.garden.game.Skills;

import com.badlogic.gdx.math.Vector2;
import com.garden.game.player.Player;
import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;

import java.util.Iterator;
import java.util.Map;

public class Fertilizer extends Skill {
        public Fertilizer(int turns, Player player) {
            super(turns, player);
            hasTurnWork = true;
        }

        @Override
        public void skillLearned(){
            super.skillLearned();
            player.makePlantAvailable(Constants.RICE);
        }

        // Visit plants and make them lose less water pr. turn. More resilient.
        @Override
        public void turnWork() {
            Iterator<Map.Entry<Vector2, Plant>> entryIt = player.getPlants_().entrySet().iterator();

            while (entryIt.hasNext()) {
                Map.Entry<Vector2, Plant> entry = entryIt.next();
                Plant plant = entry.getValue();

                if(!plant.isFertilizer1) {
                    plant.isFertilizer1 = true;
                    plant.setWaterLoss(plant.getWaterLoss()-1f);

                }
            }
    }



}
