package com.garden.game.player;

import com.badlogic.gdx.math.Vector2;
import com.garden.game.GardenGame;
import com.garden.game.Skills.Skill;
import com.garden.game.Skills.SkillTree;
import com.garden.game.tools.Constants;
import com.garden.game.world.World;
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
    public int money, water, maxWater, points, maxPoint, waterPerTurn;
    public int waterSize;
    private ArrayList<Integer> availablePlants;
    public ArrayList<Quest> quests;
    private boolean gotWater;

    private ArrayList<Plant> plants;
    private Map<Vector2, Plant> plants_; // Use map data structure to store plants? Pros: position encoded and used for indexing. Cons: bad for iterating.
    public SkillTree skillTree;

    public Player(GardenGame app) {
        this.app = app;
        unit = new Unit(this.app, "character000");
        skillTree = new SkillTree(this);
        plants = new ArrayList<>();
        plants_ = new HashMap<>();
        availablePlants = new ArrayList<>();
        availablePlants.add(Constants.RICE);
        availablePlants.add(Constants.CUCUMBER);

        initQuests();
        // Skal fejnes igen - ER her kun fo viso
        water = 10;
        maxWater = 500;
        waterPerTurn = 100;
        waterSize = 20;
        maxPoint = 1000;
    }

    private void initQuests() {
        quests = new ArrayList<>();
        quests.add(new KeepHealthyQuest(this));
    }

    public void makePlantAvailable(int plantID) {
        availablePlants.add(plantID);
    }

    public ArrayList<Integer> getAvailablePlants() {
        return availablePlants;
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
        return Constants.idPriceMap.get(id) <= money;
    }

    // Player can plant at x,y if sufficient funds, no plant there already and tile is not water.
    public boolean canPlant(int id, int x, int y) {
        return (Constants.idPriceMap.get(id) <= money) &&
                (plants_.get(new Vector2(x, y)) == null) &&
                !(app.gameScreen.world.isWaterTile(x/Constants.TILE_WIDTH,y/Constants.TILE_HEIGHT)) &&
                !(app.gameScreen.world.isNoAccessTile("Road Layer", x/Constants.TILE_WIDTH,y/Constants.TILE_HEIGHT)) &&
                !(app.gameScreen.world.isNoAccessTile("Buildings Layer", x/Constants.TILE_WIDTH,y/Constants.TILE_HEIGHT)) &&
                !(app.gameScreen.world.isNoAccessTile("Trees Layer", x/Constants.TILE_WIDTH,y/Constants.TILE_HEIGHT));
    }

    public Plant getPlantAtPosition(int x, int y) {
        return plants_.get(new Vector2(x, y));
    }

    public void plant(int x, int y, Plant plant) {
        money -= plant.getPrice();
        addPlant(plant);
        unit.gotoAndPlant(x, y, plant);
    }

    public boolean canWater(int x, int y) {
        if (water != 0){
            if(plants_.get(new Vector2(x,y)) != null && money >= 2) {

                return true;
            }
        }

        return false;
    }

    public void water(int x, int y, int amount) {
        //money -= 2;
        plants_.get(new Vector2(x,y)).water(amount);
        water -= waterSize;
    }

    public void getMoreWater(){
        // Tile 17, 12
        // You get water once per round
        if(!gotWater && !app.drySeason) {
            unit.gotoAndGetMoreWater();
            water += waterPerTurn;
            gotWater = true;
        }
    }

    public int getWater() {
        return water;
    }

    /**
     * Harvest a plant
     *
     * @param x
     * @param y
     */
    public void harvest(int x, int y) {
        Plant p = plants_.get(new Vector2(x, y));
        if(p != null) {
            unit.setPosition(x, y);
            money += p.harvest();
        }
    }

    public void nextTurn() {

        gotWater = false;
        skillWork();
        skillTree.nextTurn();

        Iterator<Map.Entry<Vector2, Plant>> entryIt = getPlants_().entrySet().iterator();

        while (entryIt.hasNext()) {
            Map.Entry<Vector2, Plant> entry = entryIt.next();
            Plant plant = entry.getValue();
            plant.nextTurn();
            if (plant.getState() == Plant.PlantState.DEAD) {
                // Remove grass from improvement layer.
                app.gameScreen.world.grassLayer.setCell((int) plant.getX() / 32, (int) plant.getY() / 32, app.assets.grassCell);

                entryIt.remove();
            } else {
                money += plant.profit;
            }
            for(Quest q : quests) {
                q.checkPlant(plant);
            }
        }
        for(int i = 0; i < quests.size(); i++) {
            Quest q = quests.get(i);
            q.nextTurn();
            if(q.isCompleted) {
                quests.set(i, new KeepHealthyQuest(this));
            }
        }

    }

    private void skillWork() {
        // For available skills
        // Let them do their turn work if any
        for(Skill skill : skillTree.skills) {
            if(skill.learned && skill.hasTurnWork) {
                skill.turnWork();
            }
        }
    }


}
