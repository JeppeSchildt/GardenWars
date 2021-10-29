package com.garden.game.tools;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String GRASS = "GRASS";
    public static final Integer GRASS_PRICE = 10;
    public static final Map<String, Integer> idPriceMap;
    static {
         idPriceMap = new HashMap<String, Integer>();
         idPriceMap.put(GRASS, GRASS_PRICE);
    }
}
