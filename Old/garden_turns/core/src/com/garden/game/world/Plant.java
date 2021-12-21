package com.garden.game.world;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.*;

public class Plant extends Actor {
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
