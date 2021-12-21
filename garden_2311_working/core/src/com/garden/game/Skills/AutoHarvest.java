package com.garden.game.Skills;

import com.badlogic.gdx.math.Vector2;
import com.garden.game.player.Player;
import com.garden.game.world.Plant;

import java.util.Iterator;
import java.util.Map;

public class AutoHarvest extends Skill{
    public AutoHarvest(int turns, Player player) {
        super(turns, player);
        name = "Auto Harvest";
        hasTurnWork = true;
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

    @Override
    public void turnWork() {
        super.turnWork();
        Iterator<Map.Entry<Vector2, Plant>> entryIt = player.getPlants_().entrySet().iterator();

        while (entryIt.hasNext()) {
            Map.Entry<Vector2, Plant> entry = entryIt.next();
            Plant plant = entry.getValue();
            if (plant.getState() == Plant.PlantState.HEALTHY && !plant.stopAutoHarvest) {
                player.money += plant.harvest();
            }
        }
    }
}


