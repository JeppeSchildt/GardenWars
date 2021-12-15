package com.garden.game.player;

import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;

public class FlowerQuest extends Quest {
    private static int nCompleted = 0;
    private int nPlants;
    private int nCurrentTurns = 0;
    private int plantType;
    public static boolean isAvailable = false;

    public FlowerQuest(Player player) {
        super(player);
        questID = Constants.FLOWER_QUEST_ID;
        selectNumber();
        selectPlantType();
    }

    /* Determine size of square */
    public void selectNumber() {
        if(nCompleted == 0) {
            nPlants = 2;
        } else {
            nPlants = Math.min(4, nCompleted*2+1);
        }
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(!isAvailable) {
            if(player.getAvailablePlants().contains(Constants.ROSE)) {
                isAvailable = true;
                selectNumber();
                selectPlantType();
            }

        }
        if(isCompleted) {
            onCompleted();
        }
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        nCompleted += 1;
        player.points += 10;
        description += ": completed";
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void checkPlant(Plant plant) {
        super.checkPlant(plant);
        for(int i = 0; i < nPlants; i++) {
            for(int j = 0; j < nPlants; j++) {
                Plant p = player.getPlantAtPosition(plant.getX()+(i*Constants.TILE_WIDTH), plant.getY()+(j*Constants.TILE_HEIGHT));
                if(p == null || p.getTypeID() != plantType || !(p.getState() == Plant.PlantState.SMALL || p.getState() == Plant.PlantState.HEALTHY)) {
                    return;
                }
            }
        }
        isCompleted = true;
    }

    @Override
    public void initDescription() {
        super.initDescription();
        String plantName = Constants.idNameMap.get(plantType);
        description = "Plant a " + nPlants + "x" + nPlants + " square of " + plantName + "s. Bring them all to the Small or Healthy state.";
    }

    /**
     * Select plant that should be kept healthy in quest among the plants available to player.
     */
    @Override
    public void selectPlantType() {
        int[] flowers = {Constants.ROSE, Constants.TULIP, Constants.SUNFLOWER};
        int offset = random.nextInt(3);
        int i = 0;
        while(i < 3) {
            plantType = flowers[(offset+i) % 3];
            if(player.getAvailablePlants().contains(plantType)) {
                initDescription();
                return;
            }
            i++;
        }
        description = "Study More Flowers to access this quest";
    }
}
