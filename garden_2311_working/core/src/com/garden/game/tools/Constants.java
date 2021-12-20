package com.garden.game.tools;

import jdk.internal.net.http.common.FlowTube;

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

    public static final float PLANT_OFFSET_X = 0.2f;
    public static final float PLANT_OFFSET_Y = 0.3f;

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
    public static final String DEAD_STATE = "Dead";

    // Not actually a constant. Is changed if Construction is learned.
    public static Map<Integer, Integer> idPriceMap;
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
        idPriceMap.put(COFFEE, 50);

        // -------- More Fruits -------- //
        idPriceMap.put(ORANGE, 30);
        idPriceMap.put(LEMON, 30);
        idPriceMap.put(STRAWBERRY, 40);
        idPriceMap.put(GRAPES, 40);
        idPriceMap.put(AVOCADO, 45);
        idPriceMap.put(MELON, 50);
        idPriceMap.put(PINEAPPLE, 50);

        // -------- More Flowers -------- //
        idPriceMap.put(SUNFLOWER, 45);
        idPriceMap.put(ROSE, 65);
        idPriceMap.put(TULIP, 85);

    }

    private static final float ProfitRate = 0.3f;
    public static final Map<Integer, Float> idProfitMap;
    static {
        idProfitMap = new HashMap<Integer, Float>();
        // -------- Start -------- //
        idProfitMap.put(CUCUMBER, idPriceMap.get(CUCUMBER) * ProfitRate);
        idProfitMap.put(RICE, idPriceMap.get(RICE) * ProfitRate);

        // -------- Basic Plants -------- //
        idProfitMap.put(TOMATO, idPriceMap.get(TOMATO) * ProfitRate);
        idProfitMap.put(POTATO, idPriceMap.get(POTATO) * ProfitRate);
        idProfitMap.put(TURNIP, idPriceMap.get(TURNIP) * ProfitRate);
        idProfitMap.put(WHEAT, idPriceMap.get(WHEAT) * ProfitRate);
        idProfitMap.put(CORN, idPriceMap.get(CORN) * ProfitRate);
        idProfitMap.put(CASSAVA, idPriceMap.get(CASSAVA) * ProfitRate);
        idProfitMap.put(EGGPLANT, idPriceMap.get(EGGPLANT) * ProfitRate);
        idProfitMap.put(COFFEE, idPriceMap.get(COFFEE) * ProfitRate);

        // -------- More Fruits -------- //
        idProfitMap.put(ORANGE, idPriceMap.get(ORANGE) * ProfitRate);
        idProfitMap.put(LEMON, idPriceMap.get(LEMON) * ProfitRate);
        idProfitMap.put(STRAWBERRY, idPriceMap.get(STRAWBERRY) * ProfitRate);
        idProfitMap.put(GRAPES, idPriceMap.get(GRAPES) * ProfitRate);
        idProfitMap.put(AVOCADO, idPriceMap.get(AVOCADO) * ProfitRate);
        idProfitMap.put(MELON, idPriceMap.get(MELON) * ProfitRate);
        idProfitMap.put(PINEAPPLE, idPriceMap.get(PINEAPPLE) * ProfitRate);

        // -------- More Flowers -------- //
        idProfitMap.put(SUNFLOWER, idPriceMap.get(SUNFLOWER) * ProfitRate);
        idProfitMap.put(ROSE, idPriceMap.get(ROSE) * ProfitRate);
        idProfitMap.put(TULIP, idPriceMap.get(TULIP) * ProfitRate);

        // -------- Bonus Plants -------- //
    }

    public static final Map<Integer, Integer> idWaterLossMap;
    static {
        idWaterLossMap = new HashMap<Integer, Integer>();
        // -------- Start -------- //
        idWaterLossMap.put(CUCUMBER, 1);
        idWaterLossMap.put(RICE, 1);

        // -------- Basic Plants -------- //
        idWaterLossMap.put(TOMATO, 1);
        idWaterLossMap.put(POTATO, 1);
        idWaterLossMap.put(TURNIP, 1);
        idWaterLossMap.put(WHEAT, 2);
        idWaterLossMap.put(CORN, 2);
        idWaterLossMap.put(CASSAVA, 2);
        idWaterLossMap.put(EGGPLANT, 2);
        idWaterLossMap.put(COFFEE, 3);

        // -------- More Fruits -------- //
        idWaterLossMap.put(ORANGE, 2);
        idWaterLossMap.put(LEMON, 2);
        idWaterLossMap.put(STRAWBERRY, 2);
        idWaterLossMap.put(GRAPES, 3);
        idWaterLossMap.put(AVOCADO, 3);
        idWaterLossMap.put(MELON, 3);
        idWaterLossMap.put(PINEAPPLE, 3);

        // -------- More Flowers -------- //
        idWaterLossMap.put(SUNFLOWER, 2);
        idWaterLossMap.put(ROSE, 2);
        idWaterLossMap.put(TULIP, 3);

        // -------- Bonus Plants -------- //

    }

    public static final Map<Integer, Boolean> idIsFlowerMap;
    static {
        idIsFlowerMap = new HashMap<Integer, Boolean>();
        idIsFlowerMap.put(SUNFLOWER, true);
        idIsFlowerMap.put(ROSE, true);
        idIsFlowerMap.put(TULIP, true);
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
    public static final int MAX_WET_SEASONS_DAYS = 15, MAX_DRY_SEASONS_DAYS = 7;
    public static final int MIN_WET_SEASONS_DAYS = 7, MIN_DRY_SEASONS_DAYS = 2;

    /* Quest stuff */
    public static final int KEEP_HEALTHY_QUEST_ID = 0;
    public static final int HARVEST_QUEST_ID = 1;
    public static final int FLOWER_QUEST_ID = 2;



}


