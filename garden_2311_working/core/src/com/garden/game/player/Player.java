package com.garden.game.player;

import com.badlogic.gdx.math.Vector2;
import com.garden.game.GardenGame;
import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;
import com.garden.game.world.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    public int dkk, water, maxWater, point, maxPoint;
    private int waterSize;

    private ArrayList<Plant> plants;
    private Map<Vector2, Plant> plants_; // Use map data structure to store plants? Pros: position encoded and used for indexing. Cons: bad for iterating.

    public Player(GardenGame app) {
        this.app = app;
        unit = new Unit(this.app, "character000");
        plants = new ArrayList<>();
        plants_ = new HashMap<>();


        // Skal fejnes igen - ER her kun fo viso
        water = 10;
        maxWater = 100;

        waterSize = 2;

        maxPoint = 1000;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }
    public Map<Vector2, Plant> getPlants_() { return  plants_; }

    public void addPlant(Plant plant) {
        plants_.put(new Vector2(plant.getX(), plant.getY()), plant);
    }
    public void removePlant(Plant plant) {
        plants_.remove(new Vector2(plant.getX(), plant.getY()));
    }

    public boolean canBuy(int id) {
        return Constants.idPriceMap.get(id) <= dkk;
    }
    public boolean canPlant(int id, int x, int y) {
        return (Constants.idPriceMap.get(id) <= dkk) && (plants_.get(new Vector2(x, y)) == null);
    }

    public Plant getPlantAtPosition(int x, int y) {
        return plants_.get(new Vector2(x, y));
    }

    public void plant(int x, int y, Plant plant) {
        dkk -= plant.getPrice();
        addPlant(plant);
        unit.gotoAndPlant(x, y, plant);
    }

    public boolean canWater(int x, int y) {
        if (water != 0){
            if(plants_.get(new Vector2(x,y)) != null && dkk >= 2) {

                return true;
            }
        }

        return false;
    }

    public void water(int x, int y, int amount) {
        dkk -= 2;
        plants_.get(new Vector2(x,y)).water(amount);
        water -= waterSize;
    }

    public void getMoreWater(int x, int y){

        unit.gotoAndGetMoreWater(x, y, water);
        water += waterSize;
    }

    public int getWater() {
        return water;
    }

    public void nextTurn() {
        Iterator<Map.Entry<Vector2, Plant>> entryIt = getPlants_().entrySet().iterator();

        while (entryIt.hasNext()) {
            Map.Entry<Vector2, Plant> entry = entryIt.next();
            Plant plant = entry.getValue();
            plant.nextTurn();

            if (plant.getState() == Plant.PlantState.DEAD) {
                // Remove grass from improvement layer.
                app.gameScreen.world.improvementLayer.setCell((int) plant.getX() / 32, (int) plant.getY() / 32, plant.getCell());

                entryIt.remove();
            } else {
                dkk += plant.profit;
            }
        }

    }






}
