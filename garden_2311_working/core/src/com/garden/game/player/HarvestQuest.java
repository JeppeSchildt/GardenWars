package com.garden.game.player;

import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;

public class HarvestQuest extends Quest{
    private static int nCompleted = 0;
    private int nPlants;
    private int nTurns;
    private int nCurrentTurns = 0;
    private int plantType;

    public HarvestQuest(Player player) {
        super(player);
        questID = Constants.HARVEST_QUEST_ID;
        selectNumber();
        selectPlantType();
        initDescription();
        //description = "";
    }

    public void selectNumber() {
        if(nCompleted == 0) {
            nPlants = 2;
            nTurns = 2;
        } else {
            //nPlants = nCompleted*2+1;
            //nTurns = nCompleted*2;
            nPlants = Math.min(nCompleted*2+1, 20);
            nTurns = Math.min(nCompleted*2, 15);

        }
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(player.nHarvested >= nPlants) {
            nCurrentTurns += 1;
            if(nCurrentTurns >= nTurns) {
                isCompleted = true;
                onCompleted();
            }
        } else {
            nCurrentTurns = 0;
        }
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

    // Does not do anything for this quest.
    @Override
    public void checkPlant(Plant plant) {
        super.checkPlant(plant);
    }

    @Override
    public void initDescription() {
        super.initDescription();
        description = "Harvest " + nPlants +  " plants each turn for " + nTurns + " consecutive turns";
    }
}
