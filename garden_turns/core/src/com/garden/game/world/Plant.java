package com.garden.game.world;

import java.awt.*;

public class Plant {
    Point position;
    enum States {
        SEED,
        SHOOT,
        GROWING,
        THRIVING,
        WITHERING
    }

    Plant(int x, int y) {
        position = new Point(x,y);
    }
}
