package com.garden.game.tools;

import com.badlogic.gdx.math.Vector2;
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

        createActorMap.put(Constants.RICE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 1f);
                waterStateMap.put(Plant.PlantState.SMALL, 3f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 2f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);

                return new Plant(Constants.RICE, x, y, assets.plantTextures[Constants.RICE], waterStateMap, assets.dirtCell);

            }
        });


        createActorMap.put(Constants.CUCUMBER, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 2f);
                waterStateMap.put(Plant.PlantState.SMALL, 5f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 3f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);

                return new Plant(Constants.CUCUMBER, x, y, assets.plantTextures[Constants.CUCUMBER], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.TOMATO, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 1.5f);
                waterStateMap.put(Plant.PlantState.SMALL, 5.2f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 3.5f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);

                return new Plant(Constants.TOMATO, x, y, assets.plantTextures[Constants.TOMATO], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.POTATO, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 1.2f);
                waterStateMap.put(Plant.PlantState.SMALL, 2.5f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 2f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.POTATO, x, y, assets.plantTextures[Constants.POTATO], waterStateMap, assets.dirtCell);

            }
        });


        createActorMap.put(Constants.TURNIP, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 1f);
                waterStateMap.put(Plant.PlantState.SMALL, 4f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 4f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);

                return new Plant(Constants.TURNIP, x, y, assets.plantTextures[Constants.TURNIP], waterStateMap, assets.dirtCell);
            }
        });

        createActorMap.put(Constants.WHEAT, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 2f);
                waterStateMap.put(Plant.PlantState.SMALL, 4.5f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 3f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.WHEAT, x, y, assets.plantTextures[Constants.WHEAT], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.ROSE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 2.5f);
                waterStateMap.put(Plant.PlantState.SMALL, 5f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 5f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);

                return new Plant(Constants.ROSE, x, y, assets.plantTextures[Constants.ROSE], waterStateMap, assets.dirtCell);

            }
        });



        createActorMap.put(Constants.TULIP, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 2f);
                waterStateMap.put(Plant.PlantState.SMALL, 7f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 4f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);

                return new Plant(Constants.TULIP, x, y, assets.plantTextures[Constants.TULIP], waterStateMap, assets.dirtCell);

            }
        });



        createActorMap.put(Constants.MELON, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 1.5f);
                waterStateMap.put(Plant.PlantState.SMALL, 6f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 5f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.MELON, x, y, assets.plantTextures[Constants.MELON], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.EGGPLANT, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 2f);
                waterStateMap.put(Plant.PlantState.SMALL, 4f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 2f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.EGGPLANT, x, y, assets.plantTextures[Constants.EGGPLANT], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.LEMON, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 3f);
                waterStateMap.put(Plant.PlantState.SMALL, 6f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 3f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.LEMON, x, y, assets.plantTextures[Constants.LEMON], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.PINEAPPLE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 3.1f);
                waterStateMap.put(Plant.PlantState.SMALL, 5.1312f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 3.1f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.PINEAPPLE, x, y, assets.plantTextures[Constants.PINEAPPLE], waterStateMap, assets.dirtCell);

            }
        });






        createActorMap.put(Constants.GRAPES, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 2.7f);
                waterStateMap.put(Plant.PlantState.SMALL, 6.6f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 4f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.GRAPES, x, y, assets.plantTextures[Constants.GRAPES], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.STRAWBERRY, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 1.7f);
                waterStateMap.put(Plant.PlantState.SMALL, 6f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 3f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.STRAWBERRY, x, y, assets.plantTextures[Constants.STRAWBERRY], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.CASSAVA, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 1.3f);
                waterStateMap.put(Plant.PlantState.SMALL, 5f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 2f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.CASSAVA, x, y, assets.plantTextures[Constants.CASSAVA], waterStateMap, assets.dirtCell);

            }
        });



        createActorMap.put(Constants.COFFEE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 2.1f);
                waterStateMap.put(Plant.PlantState.SMALL, 7.5f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 5f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.COFFEE, x, y, assets.plantTextures[Constants.COFFEE], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.ORANGE, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 3f);
                waterStateMap.put(Plant.PlantState.SMALL, 6f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 4.5f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.ORANGE, x, y, assets.plantTextures[Constants.ORANGE], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.AVOCADO, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 3.5f);
                waterStateMap.put(Plant.PlantState.SMALL, 8f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 3f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.AVOCADO, x, y, assets.plantTextures[Constants.AVOCADO], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.CORN, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 1.5f);
                waterStateMap.put(Plant.PlantState.SMALL, 3f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 2f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.CORN, x, y, assets.plantTextures[Constants.CORN], waterStateMap, assets.dirtCell);

            }
        });

        createActorMap.put(Constants.SUNFLOWER, new IFCreatePlant() {
            @Override
            public Plant create(int x, int y) {

                Map<Plant.PlantState, Float> waterStateMap = new HashMap<>();
                waterStateMap.put(Plant.PlantState.SEED, 3.6f);
                waterStateMap.put(Plant.PlantState.SMALL, 4.6f);
                waterStateMap.put(Plant.PlantState.HEALTHY, 2000f);
                waterStateMap.put(Plant.PlantState.WITHERING, 3f);
                waterStateMap.put(Plant.PlantState.DEAD, 0f);


                return new Plant(Constants.SUNFLOWER, x, y, assets.plantTextures[Constants.SUNFLOWER], waterStateMap, assets.dirtCell);

            }
        });


    }

    public Plant createPlant(int plantID, int x, int y) {
        return createActorMap.get(plantID).create(x, y);
    }
}
