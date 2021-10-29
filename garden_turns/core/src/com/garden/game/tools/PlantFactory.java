package com.garden.game.tools;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.garden.game.world.Grass;
import com.garden.game.world.Plant;

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
    }

    public Plant createPlant(Integer plantID, int x, int y) {
        return createActorMap.get(Constants.GRASS).create(x, y);
    }
}
