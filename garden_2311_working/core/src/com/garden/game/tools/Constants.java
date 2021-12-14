package com.garden.game.tools;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    // Size of tiles
    public static int TILE_WIDTH = 32;
    public static int TILE_HEIGHT = 32;
    public static int MAP_WIDTH_TILES = 32;
    public static int MAP_HEIGHT_TILES = 32;

    // Plant ids. Some of them are completely random. Some them come from position in a spritesheet.
    public static final int GRASS = 500;
    public static final int TURNIP = 0;
    public static final int ROSE = 1;
    public static final int CUCUMBER = 2;
    public static final int TULIP = 3;
    public static final int TOMATO = 4;
    public static final int MELON = 5;
    public static final int EGGPLANT = 6;
    public static final int LEMON = 7;
    public static final int PINEAPPLE = 8;
    public static final int RICE = 9;
    public static final int WHEAT = 10;
    public static final int GRAPES = 11;
    public static final int STRAWBERRY = 12;
    public static final int CASSAVA = 13;
    public static final int POTATO = 14;
    public static final int COFFEE = 15;
    public static final int ORANGE = 16;
    public static final int AVOCADO = 17;
    public static final int CORN = 18;
    public static final int SUNFLOWER = 19;

    public static final Map<Integer, String> idNameMap;
    static {
        idNameMap = new HashMap<Integer, String>();
        idNameMap.put(TURNIP, "Turnip");
        idNameMap.put(ROSE, "Rose");
        idNameMap.put(CUCUMBER, "Cucumber");
        idNameMap.put(TULIP, "Tulip");
        idNameMap.put(TOMATO, "Tomato");
        idNameMap.put(MELON, "Melon");
        idNameMap.put(EGGPLANT, "Eggplant");
        idNameMap.put(LEMON, "Lemon");
        idNameMap.put(PINEAPPLE, "Pineapple");
        idNameMap.put(RICE, "Rice");
        idNameMap.put(WHEAT, "Wheat");
        idNameMap.put(GRAPES, "Grapes");
        idNameMap.put(STRAWBERRY, "Strawberry");
        idNameMap.put(CASSAVA, "Cassava");
        idNameMap.put(POTATO, "Potato");
        idNameMap.put(COFFEE, "Coffee");
        idNameMap.put(ORANGE, "Orange");
        idNameMap.put(AVOCADO, "Avocado");
        idNameMap.put(CORN, "Corn");
        idNameMap.put(SUNFLOWER, "Sunflower");

    }


    // Indices of sprites associated with state of plant.
    public static final int SPRITE_HEALTHY = 0;
    public static final int SPRITE_SMALL = 1;
    public static final int SPRITE_WITHERING = 2;
    public static final int SPRITE_SEED = 5;
    public static final int SPRITE_DEAD = -1;

    // Names of states.
    public static final String HEALTHY_STATE = "Healthy";
    public static final String SMALL_STATE = "Small";
    public static final String WITHERING_STATE = "Withering";
    public static final String SEED_STATE = "Seed";
    public static final String DEAD_STATE = "";

    public static final Integer GRASS_PRICE = 10;
    public static final Integer TURNIP_PRICE = 10;
    public static final Map<Integer, Integer> idPriceMap;
    static {
        idPriceMap = new HashMap<Integer, Integer>();
        // -------- Crops ------- //
        idPriceMap.put(CUCUMBER, 2);
        idPriceMap.put(TOMATO, 7);
        idPriceMap.put(TURNIP, 5);
        idPriceMap.put(WHEAT, 3);
        idPriceMap.put(POTATO, 2);
        idPriceMap.put(RICE, 3);
        idPriceMap.put(CORN, 4);

        // -------- Fruits ------- //
        idPriceMap.put(MELON, 10);
        idPriceMap.put(LEMON, 5);
        idPriceMap.put(ORANGE, 7);
        idPriceMap.put(PINEAPPLE, 10);
        idPriceMap.put(GRAPES, 4);
        idPriceMap.put(STRAWBERRY, 5);

        // -------- Flower ------- //
        idPriceMap.put(SUNFLOWER, 7);
        idPriceMap.put(ROSE, 10);
        idPriceMap.put(TULIP, 5);

        // -------- Bonus Plants -------- /
        idPriceMap.put(COFFEE, 10);
        idPriceMap.put(AVOCADO, 7);
        idPriceMap.put(EGGPLANT, 5);
        idPriceMap.put(CASSAVA, 4);
    }

    public static final Map<Integer, Integer> idWaterLossMap;
    static {
        idWaterLossMap = new HashMap<Integer, Integer>();
        // -------- Crops ------- //
        idWaterLossMap.put(CUCUMBER, 2);
        idWaterLossMap.put(TOMATO, 7);
        idWaterLossMap.put(WHEAT, 3);
        idWaterLossMap.put(POTATO, 2);
        idWaterLossMap.put(RICE, 3);
        idWaterLossMap.put(CORN, 4);
        idWaterLossMap.put(TURNIP, 5);

        // -------- Fruits ------- //
        idWaterLossMap.put(MELON, 10);
        idWaterLossMap.put(LEMON, 5);
        idWaterLossMap.put(ORANGE, 7);
        idWaterLossMap.put(PINEAPPLE, 10);
        idWaterLossMap.put(GRAPES, 4);
        idWaterLossMap.put(STRAWBERRY, 5);

        // -------- Flower ------- //
        idWaterLossMap.put(SUNFLOWER, 7);
        idWaterLossMap.put(ROSE, 10);
        idWaterLossMap.put(TULIP, 5);

        // -------- Bonus Plants -------- /
        idWaterLossMap.put(COFFEE, 10);
        idWaterLossMap.put(AVOCADO, 7);
        idWaterLossMap.put(EGGPLANT, 5);
        idWaterLossMap.put(CASSAVA, 4);

    }

    public static final Map<Integer, Integer> idProfitMap;
    static {
        idProfitMap = new HashMap<Integer, Integer>();
        // -------- Crops ------- //
        idProfitMap.put(CUCUMBER, 2);
        idProfitMap.put(TOMATO, 7);
        idProfitMap.put(WHEAT, 3);
        idProfitMap.put(POTATO, 2);
        idProfitMap.put(RICE, 3);
        idProfitMap.put(CORN, 4);
        idProfitMap.put(TURNIP, 5);

        // -------- Fruits ------- //
        idProfitMap.put(MELON, 10);
        idProfitMap.put(LEMON, 5);
        idProfitMap.put(ORANGE, 7);
        idProfitMap.put(PINEAPPLE, 10);
        idProfitMap.put(GRAPES, 4);
        idProfitMap.put(STRAWBERRY, 5);

        // -------- Flower ------- //
        idProfitMap.put(SUNFLOWER, 7);
        idProfitMap.put(ROSE, 10);
        idProfitMap.put(TULIP, 5);

        // -------- Bonus Plants -------- /
        idProfitMap.put(COFFEE, 10);
        idProfitMap.put(AVOCADO, 7);
        idProfitMap.put(EGGPLANT, 5);
        idProfitMap.put(CASSAVA, 4);
    }


    public static final int BASIC_PLANTS = 0;
    public static final int FERTILIZER_1 = 1;
    public static final int MORE_FLOWERS = 2;
    public static final int MORE_FRUITS = 3;
    public static final int FERTILIZER_2 = 4;
    public static final int GENERAL = 5;
    public static final int CONSTRUCTION = 6;
    public static final int COMMUNICATION = 7;
    public static final int WATER_1 = 8;
    public static final int WATER_2 = 9;
    public static final int IRRIGATION = 10;
    public static final int AUTO_HARVEST = 11;

    public static final int DOWN = 0, RIGHT = 1, UP = 2, LEFT = 3;

    public static final float FRONT_PORCH_X = 546.128f, FRONT_PORCH_Y = 797.53f;

    // -------- Dry Season Event -------- //
    public static final int MAX_WET_SEASONS_DAYS = 40, MAX_DRY_SEASONS_DAYS = 10;
    public static final int MIN_WET_SEASONS_DAYS = 15, MIN_DRY_SEASONS_DAYS = 5;


}


 /*
- Rose
- Cucumber
- Tulip
- Tomato
- Melon
- Eggplant
- Lemon
- Pineapple
- Rice
- Wheat
- Grapes
- Straberry
- Cassava
- Potato
- Coffee
- Orange
- Avocado
- Corn
- Sunflower*/