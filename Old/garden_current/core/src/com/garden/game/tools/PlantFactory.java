package com.garden.game.tools;

import com.garden.game.world.plants.Grass;
import com.garden.game.world.plants.Plant;
import com.garden.game.world.plants.Turnip;

import java.util.HashMap;
import java.util.Map;

interface IFCreatePlant {
    Plant create(int x, int y);
}

public class PlantFactory {
    private final Assets assets;
    Map<Integer, IFCreatePlant> createActorMap;

    public PlantFactory(final Assets assets) {
        this.assets = assets;
        createActorMap = new HashMap<Integer, IFCreatePlant>();
        initMap();
    }

    private void initMap() {
        if(createActorMap == null) {
            createActorMap = new HashMap<Integer, IFCreatePlant>();
        }
        createActorMap.put(Constants.GRASS, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {
                return new Grass(x, y, assets.grassCell);
            }
        });

        createActorMap.put(Constants.TURNIP, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {
                return new Turnip(x, y, assets.plantTextures[Constants.TURNIP], assets.grassCell);
            }
        });
    }

    public Plant createPlant(int plantID, int x, int y) {
        return createActorMap.get(plantID).create(x, y);
    }
}
