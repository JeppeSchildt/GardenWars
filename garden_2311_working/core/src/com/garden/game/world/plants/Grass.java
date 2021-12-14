package com.garden.game.world.plants;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.garden.game.tools.Constants;


import java.util.HashMap;

public class Grass extends Plant {

    public Grass(int x, int y, TiledMapTileLayer.Cell cell) {
        super(x, y);
        this.cell = cell;
        typeID = Constants.GRASS;
        price = Constants.GRASS_PRICE;
        water = 2;
        profit = 2;

        initWaterStateMap();
    }

    public void initWaterStateMap() {
        waterStateMap = new HashMap<>();
        waterStateMap.put(PlantState.SEED, new Vector2(0,2));
        waterStateMap.put(PlantState.SMALL, new Vector2(2,4));
        waterStateMap.put(PlantState.HEALTHY, new Vector2(4,2000));
        waterStateMap.put(PlantState.WITHERING, new Vector2(0,4));
        waterStateMap.put(PlantState.DEAD, new Vector2(0,0));
    }

    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }

    // if, if, if, if, if.....
    @Override
    public void changeState() {
        if(water <= waterStateMap.get(state).x) {
            state = state.prevState();
            profit -= 2;
        } else if (water > waterStateMap.get(state).y) {
            state = state.nextState();
            profit += 2;
        }

        if(state == PlantState.DEAD) {
            activeSprite = null;
            cell = null;
        }

    }

    @Override
    public void nextTurn() {
        water--;
        changeState();
    }
}
