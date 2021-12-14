package com.garden.game.player;

import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;

public class KeepHealthyQuest extends Quest {
    static int nCompleted = 0;
    int nPlants;
    int plantType;
    int healthyPlants;

    public KeepHealthyQuest(Player player) {
        super(player);
        selectNumberPlants();
        selectPlantType();

    }

    /**
     * Select plant that should be kept healthy in quest among the plants available to player.
     */
    public void selectPlantType() {
        do {
            plantType = random.nextInt(Constants.N_PLANTS);
        }while(!player.getAvailablePlants().contains(plantType));
    }

    public void selectNumberPlants() {
        if(nCompleted == 0) {
            nPlants = 2;
        } else {
            nPlants = nCompleted*2;
        }
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        int healthyPlants_ = 0;
        //for(Plant p : player.getPlants_().)
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }
}
