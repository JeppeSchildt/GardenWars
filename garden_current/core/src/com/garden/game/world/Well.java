package com.garden.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Well extends Actor {

    Sprite sprite;
    public Well(float x, float y, Sprite sprite) {
        super.setPosition(x, y);
        this.sprite = sprite;
        sprite.setPosition(x*32, y*32);

    }
}
