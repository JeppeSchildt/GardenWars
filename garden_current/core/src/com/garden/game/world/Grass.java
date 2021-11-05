package com.garden.game.world;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.garden.game.tools.Constants;


import java.util.HashMap;
import java.util.Map;

public class Grass extends Plant {
    TiledMapTileLayer.Cell cell;

    public Grass(int x, int y, TiledMapTileLayer.Cell cell) {
        super(x, y);
        this.cell = cell;
        typeID = Constants.GRASS;
        price = Constants.GRASS_PRICE;
        water = 2;

        initWaterStateMap();


    }

    public void initWaterStateMap() {
        waterStateMap = new HashMap<>();
        waterStateMap.put(PlantState.SEED, new Vector2(0,2));
        waterStateMap.put(PlantState.SMALL, new Vector2(2,4));
        waterStateMap.put(PlantState.THRIVING, new Vector2(5,20));
        waterStateMap.put(PlantState.THRIVING, new Vector2(2,5));
        waterStateMap.put(PlantState.DEAD, new Vector2(0,0));
    }

    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }

    @Override
    public void changeState() {
        super.changeState();
        if(water < waterStateMap.get(state).x) {
            state = state.prevState();
        } else if (water > waterStateMap.get(state).y) {
            state = state.nextState();
        }

    }

    @Override
    public void nextTurn() {
        water--;
        changeState();
    }
}
