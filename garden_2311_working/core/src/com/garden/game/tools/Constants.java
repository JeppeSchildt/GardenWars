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

    public static final int N_PLANTS = 20;

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
        // -------- Start -------- //
        idPriceMap.put(CUCUMBER, 20);
        idPriceMap.put(RICE, 25);

        // -------- Basic Plants -------- //
        idPriceMap.put(TOMATO, 20);
        idPriceMap.put(POTATO, 20);
        idPriceMap.put(TURNIP, 25);
        idPriceMap.put(WHEAT, 30);
        idPriceMap.put(CORN, 30);
        idPriceMap.put(CASSAVA, 40);
        idPriceMap.put(EGGPLANT, 40);
        idPriceMap.put(COFFEE, 55);

        // -------- More Fruits -------- //
        idPriceMap.put(ORANGE, 35);
        idPriceMap.put(LEMON, 35);
        idPriceMap.put(STRAWBERRY, 45);
        idPriceMap.put(GRAPES, 45);
        idPriceMap.put(AVOCADO, 65);
        idPriceMap.put(MELON, 75);
        idPriceMap.put(PINEAPPLE, 100);

        // -------- More Flowers -------- //
        idPriceMap.put(SUNFLOWER, 55);
        idPriceMap.put(ROSE, 200);
        idPriceMap.put(TULIP, 350);

        // -------- Bonus Plants -------- //

    }

    public static final Map<Integer, Integer> idWaterLossMap;
    static {
        idWaterLossMap = new HashMap<Integer, Integer>();
        // -------- Start -------- //
        idWaterLossMap.put(CUCUMBER, 1);
        idWaterLossMap.put(RICE, 1);

        // -------- Basic Plants -------- //
        idWaterLossMap.put(TOMATO, 2);
        idWaterLossMap.put(POTATO, 2);
        idWaterLossMap.put(TURNIP, 2);
        idWaterLossMap.put(WHEAT, 3);
        idWaterLossMap.put(CORN, 3);
        idWaterLossMap.put(CASSAVA, 3);
        idWaterLossMap.put(EGGPLANT, 3);
        idWaterLossMap.put(COFFEE, 4);

        // -------- More Fruits -------- //
        idWaterLossMap.put(ORANGE, 3);
        idWaterLossMap.put(LEMON, 3);
        idWaterLossMap.put(STRAWBERRY, 4);
        idWaterLossMap.put(GRAPES, 4);
        idWaterLossMap.put(AVOCADO, 4);
        idWaterLossMap.put(MELON, 5);
        idWaterLossMap.put(PINEAPPLE, 5);

        // -------- More Flowers -------- //
        idWaterLossMap.put(SUNFLOWER, 5);
        idWaterLossMap.put(ROSE, 5);
        idWaterLossMap.put(TULIP, 5);

        // -------- Bonus Plants -------- //

    }

    private static final int ProfitRate = 2;
    public static final Map<Integer, Integer> idProfitMap;
    static {
        idProfitMap = new HashMap<Integer, Integer>();
        // -------- Start -------- //
        idProfitMap.put(CUCUMBER, 20 * ProfitRate);
        idProfitMap.put(RICE, 25 * ProfitRate);

        // -------- Basic Plants -------- //
        idProfitMap.put(TOMATO, 20 * ProfitRate);
        idProfitMap.put(POTATO, 20 * ProfitRate);
        idProfitMap.put(TURNIP, 25 * ProfitRate);
        idProfitMap.put(WHEAT, 30 * ProfitRate);
        idProfitMap.put(CORN, 30 * ProfitRate);
        idProfitMap.put(CASSAVA, 40 * ProfitRate);
        idProfitMap.put(EGGPLANT, 40 * ProfitRate);
        idProfitMap.put(COFFEE, 55 * ProfitRate);

        // -------- More Fruits -------- //
        idProfitMap.put(ORANGE, 35 * ProfitRate);
        idProfitMap.put(LEMON, 35 * ProfitRate);
        idProfitMap.put(STRAWBERRY, 45 * ProfitRate);
        idProfitMap.put(GRAPES, 45 * ProfitRate);
        idProfitMap.put(AVOCADO, 65 * ProfitRate);
        idProfitMap.put(MELON, 75 * ProfitRate);
        idProfitMap.put(PINEAPPLE, 100 * ProfitRate);

        // -------- More Flowers -------- //
        idProfitMap.put(SUNFLOWER, 55 * ProfitRate);
        idProfitMap.put(ROSE, 200 * ProfitRate);
        idProfitMap.put(TULIP, 350 * ProfitRate);

        // -------- Bonus Plants -------- //
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
    public static final int MAX_WET_SEASONS_DAYS = 5, MAX_DRY_SEASONS_DAYS = 3;
    public static final int MIN_WET_SEASONS_DAYS = 3, MIN_DRY_SEASONS_DAYS = 2;

    


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