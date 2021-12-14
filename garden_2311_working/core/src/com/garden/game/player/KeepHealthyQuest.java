package com.garden.game.player;

import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;

public class KeepHealthyQuest extends Quest {
    static int nCompleted = 0;
    int nPlants;
    int nTurns;
    int nCurrentTurns = 0;
    int plantType;
    int healthyPlants;

    public KeepHealthyQuest(Player player) {
        super(player);
        selectNumber();
        selectPlantType();
        initDescription();

    }

    /**
     * Select plant that should be kept healthy in quest among the plants available to player.
     */
    public void selectPlantType() {
        do {
            plantType = random.nextInt(Constants.N_PLANTS);
        }while(!player.getAvailablePlants().contains(plantType));
    }

    public void selectNumber() {
        if(nCompleted == 0) {
            nPlants = 2;
            nTurns = 2;
        } else {
            nPlants = nCompleted*2;
            nTurns = nCompleted*2;
        }
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(healthyPlants == nPlants) {
            nCurrentTurns +=1;
            if(nCurrentTurns == nTurns) {
                isCompleted = true;
                onCompleted();
            }
        } else {
            nCurrentTurns = 0;
        }
        healthyPlants = 0;
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        nCompleted += 1;
        player.points += 10;
        description += ": completed";
        System.out.println("HDELLO FROM IONcal");
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    // Check if correct plant and healthy.
    @Override
    public void checkPlant(Plant plant) {
        super.checkPlant(plant);
        if(plant.getTypeID() == plantType) {
            if(plant.getState() == Plant.PlantState.HEALTHY) {
                healthyPlants++;
            }

        }
    }

    @Override
    public void initDescription() {
        super.initDescription();
        String plantName = Constants.idNameMap.get(plantType);
        description = "Keep " + nPlants + " " + plantName+  " in the Healthy state for " + nTurns + " turns";
    }
}
