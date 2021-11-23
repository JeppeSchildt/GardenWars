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
         idPriceMap.put(GRASS, 10);
        idPriceMap.put(TURNIP, 10);

    }
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