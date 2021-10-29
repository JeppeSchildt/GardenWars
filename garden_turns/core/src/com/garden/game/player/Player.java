package com.garden.game.player;

import com.garden.game.GardenGame;
import com.garden.game.world.Plant;
import com.garden.game.world.Unit;
import com.garden.game.world.World;

import java.util.ArrayList;

/*
    Make an endTurn() method that makes all plants spin.
    Store stats here: money, score etc.
    Plants.

    Go to tile then display whatever is planted, we have app here so use tilelayer. Add walkaction and then add tile
    /improvement.
 */
public class Player {
    GardenGame app;
    public Unit unit;
    public int dkk;

    private ArrayList<Plant> plants;

    public Player(GardenGame app) {
        this.app = app;
        //world = this.app.gameScreen.world;
        unit = new Unit(this.app, "character000");
        plants = new ArrayList<>();
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public boolean canPlant(int cost) {
        return cost <= dkk;

    }
    public void plant(int x, int y) {

    }


}
