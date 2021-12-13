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
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,5));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.TURNIP, x, y, Constants.TURNIP + "", Constants.idWaterLossMap.get(Constants.TURNIP), Constants.idProfitMap.get(Constants.TURNIP), Constants.idPriceMap.get(Constants.TURNIP), assets.plantTextures[Constants.TURNIP], waterStateMap);



            }
        });

        createActorMap.put(Constants.ROSE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,5));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.ROSE, x, y, Constants.ROSE + "", Constants.idWaterLossMap.get(Constants.ROSE), Constants.idProfitMap.get(Constants.ROSE), Constants.idPriceMap.get(Constants.ROSE), assets.plantTextures[Constants.ROSE], waterStateMap);

            }
        });

        createActorMap.put(Constants.CUCUMBER, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.CUCUMBER, x, y, Constants.CUCUMBER + "", Constants.idWaterLossMap.get(Constants.CUCUMBER), Constants.idProfitMap.get(Constants.CUCUMBER), Constants.idPriceMap.get(Constants.CUCUMBER),  assets.plantTextures[Constants.CUCUMBER], waterStateMap);

            }
        });
        createActorMap.put(Constants.TULIP, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.TULIP, x, y, Constants.TULIP + "", Constants.idWaterLossMap.get(Constants.TULIP), Constants.idProfitMap.get(Constants.TULIP), Constants.idPriceMap.get(Constants.TULIP), assets.plantTextures[Constants.TULIP], waterStateMap);

            }
        });

        createActorMap.put(Constants.TOMATO, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.TOMATO, x, y, Constants.TOMATO + "", Constants.idWaterLossMap.get(Constants.TOMATO), Constants.idProfitMap.get(Constants.TOMATO), Constants.idPriceMap.get(Constants.TOMATO), assets.plantTextures[Constants.TOMATO], waterStateMap);

            }
        });

        createActorMap.put(Constants.MELON, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.MELON, x, y, Constants.MELON + "", Constants.idWaterLossMap.get(Constants.MELON), Constants.idProfitMap.get(Constants.MELON), Constants.idPriceMap.get(Constants.MELON), assets.plantTextures[Constants.MELON], waterStateMap);

            }
        });

        createActorMap.put(Constants.EGGPLANT, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.EGGPLANT, x, y, Constants.EGGPLANT + "", Constants.idWaterLossMap.get(Constants.EGGPLANT), Constants.idProfitMap.get(Constants.EGGPLANT), Constants.idPriceMap.get(Constants.EGGPLANT), assets.plantTextures[Constants.EGGPLANT], waterStateMap);

            }
        });

        createActorMap.put(Constants.LEMON, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.LEMON, x, y, Constants.LEMON + "", Constants.idWaterLossMap.get(Constants.LEMON), Constants.idProfitMap.get(Constants.LEMON), Constants.idPriceMap.get(Constants.LEMON), assets.plantTextures[Constants.LEMON], waterStateMap);

            }
        });

        createActorMap.put(Constants.PINEAPPLE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.PINEAPPLE, x, y, Constants.PINEAPPLE + "", Constants.idWaterLossMap.get(Constants.PINEAPPLE), Constants.idProfitMap.get(Constants.PINEAPPLE), Constants.idPriceMap.get(Constants.PINEAPPLE), assets.plantTextures[Constants.PINEAPPLE], waterStateMap);

            }
        });


        createActorMap.put(Constants.RICE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.RICE, x, y, Constants.RICE + "", Constants.idWaterLossMap.get(Constants.RICE), Constants.idProfitMap.get(Constants.RICE), Constants.idPriceMap.get(Constants.RICE), assets.plantTextures[Constants.RICE], waterStateMap);

            }
        });


        createActorMap.put(Constants.WHEAT, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.WHEAT, x, y, Constants.WHEAT + "", Constants.idWaterLossMap.get(Constants.WHEAT), Constants.idProfitMap.get(Constants.WHEAT), Constants.idPriceMap.get(Constants.WHEAT), assets.plantTextures[Constants.WHEAT], waterStateMap);

            }
        });

        createActorMap.put(Constants.GRAPES, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.GRAPES, x, y, Constants.GRAPES + "", Constants.idWaterLossMap.get(Constants.GRAPES), Constants.idProfitMap.get(Constants.GRAPES), Constants.idPriceMap.get(Constants.GRAPES), assets.plantTextures[Constants.GRAPES], waterStateMap);

            }
        });

        createActorMap.put(Constants.STRAWBERRY, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.STRAWBERRY, x, y, Constants.STRAWBERRY + "", Constants.idWaterLossMap.get(Constants.STRAWBERRY), Constants.idProfitMap.get(Constants.STRAWBERRY), Constants.idPriceMap.get(Constants.STRAWBERRY), assets.plantTextures[Constants.STRAWBERRY], waterStateMap);

            }
        });

        createActorMap.put(Constants.CASSAVA, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.CASSAVA, x, y, Constants.CASSAVA + "", Constants.idWaterLossMap.get(Constants.CASSAVA), Constants.idProfitMap.get(Constants.CASSAVA), Constants.idPriceMap.get(Constants.CASSAVA), assets.plantTextures[Constants.CASSAVA], waterStateMap);

            }
        });

        createActorMap.put(Constants.POTATO, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.POTATO, x, y, Constants.POTATO + "", Constants.idWaterLossMap.get(Constants.POTATO), Constants.idProfitMap.get(Constants.POTATO), Constants.idPriceMap.get(Constants.POTATO), assets.plantTextures[Constants.POTATO], waterStateMap);

            }
        });

        createActorMap.put(Constants.COFFEE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.COFFEE, x, y, Constants.COFFEE + "", Constants.idWaterLossMap.get(Constants.COFFEE), Constants.idProfitMap.get(Constants.COFFEE), Constants.idPriceMap.get(Constants.COFFEE), assets.plantTextures[Constants.COFFEE], waterStateMap);

            }
        });

        createActorMap.put(Constants.ORANGE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.ORANGE, x, y, Constants.ORANGE + "", Constants.idWaterLossMap.get(Constants.ORANGE), Constants.idProfitMap.get(Constants.ORANGE), Constants.idPriceMap.get(Constants.ORANGE), assets.plantTextures[Constants.ORANGE], waterStateMap);

            }
        });

        createActorMap.put(Constants.AVOCADO, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.AVOCADO, x, y, Constants.AVOCADO + "", Constants.idWaterLossMap.get(Constants.AVOCADO), Constants.idProfitMap.get(Constants.AVOCADO), Constants.idPriceMap.get(Constants.AVOCADO), assets.plantTextures[Constants.AVOCADO], waterStateMap);

            }
        });

        createActorMap.put(Constants.CORN, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.CORN, x, y, Constants.CORN + "", Constants.idWaterLossMap.get(Constants.CORN), Constants.idProfitMap.get(Constants.CORN), Constants.idPriceMap.get(Constants.CORN), assets.plantTextures[Constants.CORN], waterStateMap);

            }
        });

        createActorMap.put(Constants.SUNFLOWER, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Vector2> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, new Vector2(0,2));
                waterStateMap.put(Plant.PlantState.SMALL, new Vector2(2,5));
                waterStateMap.put(Plant.PlantState.HEALTHY, new Vector2(5,2000));
                waterStateMap.put(Plant.PlantState.WITHERING, new Vector2(0,4));
                waterStateMap.put(Plant.PlantState.DEAD, new Vector2(0,0));

                return new Plant(Constants.SUNFLOWER, x, y, Constants.SUNFLOWER + "", Constants.idWaterLossMap.get(Constants.SUNFLOWER), Constants.idProfitMap.get(Constants.SUNFLOWER), Constants.idPriceMap.get(Constants.SUNFLOWER), assets.plantTextures[Constants.SUNFLOWER], waterStateMap);

            }
        });


    }

    public Plant createPlant(int plantID, int x, int y) {
        return createActorMap.get(plantID).create(x, y);
    }
}
