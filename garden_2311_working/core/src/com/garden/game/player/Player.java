package com.garden.game.player;

import com.badlogic.gdx.math.Vector2;
import com.garden.game.GardenGame;
import com.garden.game.Skills.Skill;
import com.garden.game.Skills.SkillTree;
import com.garden.game.tools.Constants;
import com.garden.game.world.MainCharacter;
import com.garden.game.world.Plant;

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
    public MainCharacter unit;
    public int money, water, maxWater, maxPoint, waterPerTurn, nHarvested, waterSize;
    public float points, questPointFactor = 1f;
    private ArrayList<Integer> availablePlants;
    public ArrayList<Quest> quests;
    private boolean gotWater, isMovementLocked;

    private ArrayList<Plant> plants;
    private Map<Vector2, Plant> plants_; // Use map data structure to store plants? Pros: position encoded and used for indexing. Cons: bad for iterating.
    public SkillTree skillTree;

    public Player(GardenGame app) {
        this.app = app;
        unit = new MainCharacter(this.app);
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
        nHarvested = 0;

    }

    private void initQuests() {
        quests = new ArrayList<>();
        quests.add(0, new KeepHealthyQuest(this));
        quests.add(1, new FlowerQuest(this));
        quests.add(2, new HarvestQuest(this));
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
                (plants_.get(new Vector2((float) (x+Constants.PLANT_OFFSET_X)*Constants.TILE_WIDTH, (float) (y+Constants.PLANT_OFFSET_Y)*Constants.TILE_HEIGHT)) == null) &&
                !(app.gameScreen.world.isWaterTile(x,y)) &&
                !(app.gameScreen.world.isNoAccessTile("Road Layer", x,y)) &&
                !(app.gameScreen.world.isNoAccessTile("Buildings Layer", x,y)) &&
                !(app.gameScreen.world.isNoAccessTile("Trees Layer", x,y));
    }

    public Plant getPlantAtPosition(float x, float y) {
        return plants_.get(new Vector2(x, y));
    }

    // Add plant to map. We do not check any conditions here...??
    public void plant(float x, float y, Plant plant) {
        money -= plant.getPrice(); // Construction bonus set when skill construction learned.
        addPlant(plant);
        unit.gotoAndPlant(x, y, plant);
        setMovementLocked(true);
    }

    public boolean canWater(float x, float y) {
        if (water != 0){
            if(plants_.get(new Vector2(x,y)) != null) {
                return true;
            }
        }

        return false;
    }

    // Water plant at given position.
    public void water(float x, float y) {
        Plant p = plants_.get(new Vector2(x,y));
        if(p == null) { return; }
        if(water-waterSize < 0) { return; }

        p.water(waterSize);
        water -= waterSize;
        unit.gotoAndWater(x,y);
        setMovementLocked(true);
    }

    public void getMoreWater(){
        // Tile 17, 12
        // You get water once per round
        if(!gotWater && !app.gameScreen.world.drySeason) {
            unit.gotoAndGetMoreWater();
            water += waterPerTurn;
            gotWater = true;
            setMovementLocked(true);
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
    public void harvest(float x, float y) {
        Plant p = plants_.get(new Vector2(x, y));
        if(p != null) {
            setMovementLocked(true);
            unit.setPosition(x, y);
            money += p.harvest();
            nHarvested++;
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

            switch (plant.getState()) {
                case DEAD:
                    // Remove grass from improvement layer.
                    app.gameScreen.world.grassLayer.setCell((int) plant.getX() / 32, (int) plant.getY() / 32, app.assets.grassCell);
                    entryIt.remove();
                    break;
                case HEALTHY:
                    points += plant.profit*0.001;
                    break;
            }

            // Check each plant with each quest.
            for(Quest q : quests) {
                q.checkPlant(plant);
            }
        }
        updateQuests();

        // Reset number of harvested plants per turn.
        nHarvested = 0;
    }

    // Call nextTurn on each quests. Checks if conditions for completion are met.
    public void updateQuests() {
        for(Quest q : quests) {
            q.nextTurn();
        }
    }

    public void setNewQuest(int i) {
        switch (i) {
            case 0:
                quests.set(i, new KeepHealthyQuest(this));
                break;
            case 1:
                quests.set(i, new FlowerQuest(this));
                break;
            case 2:
                quests.set(i, new HarvestQuest(this));
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

    // Disable movements for player.
    public void setMovementLocked(boolean locked) {
        isMovementLocked = locked;
    }

    public boolean isMovementLocked() {
        return isMovementLocked;
    }
}
