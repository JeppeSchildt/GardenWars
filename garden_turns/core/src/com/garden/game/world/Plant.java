package com.garden.game.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.*;

public class Plant extends Actor {
    int water;
    String typeID;
    TiledMapTileLayer.Cell cell;
    Sprite sprite;
    Integer price;

    // Move to constants?
    enum States {
        SEED,
        SHOOT,
        GROWING,
        THRIVING,
        WITHERING,
        DEAD
    }
    States state;

    // Two different constructors. For convenience. Maybe it's not necessary.
    public Plant(int x, int y) {
        setPosition(x, y);
        state = States.SEED;
    }

    Plant(int x, int y, String assetName) {
        setPosition(x, y);
        state = States.SEED;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x*32, y*32); // 32 is length of a tile...
    }

    public void changeState(States newState) {
        state = newState;
    }

    public String getTypeID() {
        return typeID;
    }

    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
