package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import com.garden.game.GardenGame;

import java.awt.*;
import java.util.ArrayList;

// turn in to interface? actor?? plants and character implement this??
public class Unit extends Actor {
    GardenGame app;
    Point position; // Tile position... not really used now
    Vector2 pos;
    float angle_old;
    String assetName;
    Sprite sprite;
    public int maxX, minX, maxY, minY;
    public ArrayList<Animation<TextureRegion>> walkAnimations;
    public Animation<TextureRegion> activeAnimation;
    public float elapsedTime;
    TextureRegion drawThis;
    private final int DOWN = 0, RIGHT = 1, UP = 2, LEFT = 3;


    public Unit(GardenGame app, String assetName) {
        this.app = app;
        this.assetName = assetName;
        position = new Point(0,0);
        pos = new Vector2();
        this.sprite = app.assets.textureAtlas.createSprite("character000");
        this.walkAnimations = app.assets.walkAnimations;
        maxX = 32; // Hardcoded...
        maxY = 32;
        minX = 0;
        minY = 0;
        angle_old = 0;
        activeAnimation = walkAnimations.get(0);
        setPosition(0,0);

    }

    public void move(double x, double y) {
        position.setLocation(x, y);
    }
    public boolean canMove(int x, int y) {
        return (x < maxX && y < maxY && x >= minX && y >= minY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();
        //sprite.draw(batch);
        //batch.draw(activeAnimation.getKeyFrame(elapsedTime, true), getX(), getY());
        batch.draw(drawThis, getX(), getY());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        drawThis = activeAnimation.getKeyFrame(elapsedTime, true);
    }

    @Override
    public void setPosition(float x, float y) {
        pos.set(x,y);
        //System.out.println("angle - angle_old: " + String.valueOf(pos.angleDeg() - angle_old));
        //System.out.println(pos.angleDeg());
        //angle_old = pos.angleDeg();
        //System.out.println(getX() + " " + x);

        // Find a cleaner mapping to right animation.
        Vector2 route = new Vector2(x-getX(), y-getY());
        float distance = route.len();
        //System.out.println("length: " + distance);
        //System.out.println("angle: " + route.angleDeg());
        float angle = route.angleDeg();
        if(angle <= 45 || angle > 315) {
            activeAnimation = walkAnimations.get(RIGHT);
        } else if (45 < angle && angle <= 135) {
            activeAnimation = walkAnimations.get(UP);
        } else if (135 < angle && angle < 225) {
            activeAnimation = walkAnimations.get(LEFT);
        } else {
            activeAnimation = walkAnimations.get(DOWN);
        }

        /*if(y >= x) {
            if(y <= getY()) {
                activeAnimation = walkAnimations.get(DOWN);
            }
            else {
                activeAnimation = walkAnimations.get(UP);
            }
        } else {
            if(x < getX()) {
                activeAnimation = walkAnimations.get(LEFT);
            } else {
                activeAnimation = walkAnimations.get(RIGHT);
            }
        }*/

        clearActions();
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x, y);
        float duration = (float) Math.sqrt(Math.pow(x-getX(),2) + Math.pow(y-getY(), 2))/100f;
        moveToAction.setDuration(duration);
        addAction(moveToAction);
        //System.out.println(getActions());
    }

}


// Do this in unit
                /*MoveToAction moveToAction = new MoveToAction();
                moveToAction.setPosition(position.x, position.y);

                float duration = (float) Math.sqrt(position.x*position.x+position.y*position.y)/75f;

                moveToAction.setDuration(duration);
                world.user.unit.addAction(moveToAction);*/


// sprite.setPosition(x,y);
        //System.out.println("angle: " + String.valueOf(pos.angleDeg() - angle_old));
              //  angle_old = pos.angleDeg();