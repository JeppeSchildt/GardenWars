package com.garden.game.player;

import com.garden.game.tools.Constants;
import com.garden.game.world.Plant;

public class KeepHealthyQuest extends Quest {
    private static int nCompleted = 0;
    private int nPlants;
    private int nTurns;
    private int nCurrentTurns = 0;
    private int plantType;
    private int healthyPlants;

    public KeepHealthyQuest(Player player) {
        super(player);
        questID = Constants.KEEP_HEALTHY_QUEST_ID;
        selectNumber();
        selectPlantType();
        initDescription();
        //description = "";

    }

    /**
     * Select plant that should be kept healthy in quest among the plants available to player.
     */
    @Override
    public void selectPlantType() {
        plantType = player.getAvailablePlants().get(random.nextInt(player.getAvailablePlants().size()));
    }

    public void selectNumber() {
        if(nCompleted == 0) {
            nPlants = 2;
            nTurns = 2;
        } else {
            nPlants = Math.min(nCompleted*2+1, 20);
            //nPlants = nCompleted*2+1;
            nTurns = Math.min(nCompleted*2+1, 15);
            //nTurns = nCompleted*2+1;
        }
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(isCompleted) {
            return;
        }

        if(healthyPlants >= nPlants) {
            nCurrentTurns +=1;
            if(nCurrentTurns >= nTurns) {
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
        description = "Keep " + nPlants + " " + plantName +  " crops in the Healthy state for " + nTurns + " consecutive turns";
    }
}
