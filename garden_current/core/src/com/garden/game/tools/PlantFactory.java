package com.garden.game.tools;

import com.badlogic.gdx.math.Vector2;
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

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,4));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(4,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                /*Plant turnip = new Plant(x, y, assets.plantTextures[Constants.TURNIP]);
                turnip.setWaterStateMap(waterStateMap);
                turnip.setProfit(2);
                turnip.setWaterLoss(1);
                return turnip;*/
                return new Plant(x, y, "Turnip", 1, 2, assets.plantTextures[Constants.TURNIP], waterStateMap);

            }
        });

        createActorMap.put(Constants.ROSE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                /*Plant turnip = new Plant(x, y, assets.plantTextures[Constants.TURNIP]);
                turnip.setWaterStateMap(waterStateMap);
                turnip.setProfit(2);
                turnip.setWaterLoss(1);
                return turnip;*/
                return new Plant(x, y, "Rose", 2, 3, assets.plantTextures[Constants.ROSE], waterStateMap);

            }
        });
    }

    public Plant createPlant(int plantID, int x, int y) {
        return createActorMap.get(plantID).create(x, y);
    }
}
