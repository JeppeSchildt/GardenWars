package com.garden.game.player;

import com.garden.game.world.Plant;

import java.util.Random;

public class Quest {
    public boolean isCompleted;
    public int difficulty;
    public String description;
    public Player player;
    public Random random;
    public int questID;
    public float points;

    public Quest(Player player){
        this.player = player;
        difficulty = 1;
        random = new Random(System.currentTimeMillis());
    };
    public void nextTurn(){};
    public void onCompleted(){};
    public String getDescription() {
        return description;
    };

    public void updateDescComplete() {
        description += ": completed";
    }

    public void checkPlant(Plant plant){};
    public void initDescription(){};
    public void selectPlantType(){};

}
