package com.garden.game.Skills;

import com.badlogic.gdx.math.Vector2;
import com.garden.game.player.Player;
import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;

import java.util.Iterator;
import java.util.Map;

public class Irrigation extends Skill {
    public Irrigation(int turns, Player player) {
        super(turns, player);
        name = "Irrigation";
        hasTurnWork = true;
        adjacent.add(Constants.AUTO_HARVEST);
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

            if(plant.getState() != Plant.PlantState.HEALTHY) {
                //plant.setState(Plant.PlantState.HEALTHY);
                // Penal user somehow. Or just think about it very powerful now.
                plant.water += plant.waterStateMap.get(Plant.PlantState.HEALTHY).x;
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
