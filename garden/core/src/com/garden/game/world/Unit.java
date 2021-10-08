package com.garden.game.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.garden.game.GardenGame;

import java.awt.*;

// turn in to interface? actor?? plants and character implement this??
public class Unit {
    GardenGame app;
    Point position;
    String assetName;

    public Unit(GardenGame app, String assetName) {
        this.app = app;
        this.assetName = assetName;
        position = new Point(0,0);
    }

    void move(double x, double y) {
        position.setLocation(x, y);
    }
}
