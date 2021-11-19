package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.garden.game.GardenGame;
import com.garden.game.world.plants.Plant;

import java.util.ArrayList;

// turn in to interface? actor?? plants and character implement this??
public class Unit extends Actor {
    GardenGame app;
    String assetName;
    //Sprite sprite;
    public int maxX, minX, maxY, minY, direc;
    public ArrayList<Animation<TextureRegion>> walkAnimations, stopAnimations;
    public Animation<TextureRegion> activeAnimation;
    public float elapsedTime;
    TextureRegion drawThis;
    private final int DOWN = 0, RIGHT = 1, UP = 2, LEFT = 3;
    float velocity = 100;

    public Unit(GardenGame app, String assetName) {
        this.app = app;
        this.assetName = assetName;
        //this.sprite = app.assets.textureAtlas.createSprite("character000");
        this.walkAnimations = app.assets.walkAnimations;
        this.stopAnimations = app.assets.stopAnimations;
        maxX = 32; // Hardcoded...
        maxY = 32;
        minX = 0;
        minY = 0;
        activeAnimation = stopAnimations.get(0);
        setX(16*32);
        setY(16*32);

    }

    public void move(float x, float y) {
        //position.setLocation(x, y);
        selectAnimation(x, y);

    }
    public boolean canMove(int x, int y) {

        // Check for bounds of map.
        boolean canMove = x < maxX && y < maxY && x >= minX && y >= minY;

        // Check for water.

        canMove = canMove && (app.gameScreen.world.waterLayer.getCell(x, y) == null);

        return canMove;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();
        if(app.gameScreen.world.mapInput.walking) {
            float x = getX();
            float y = getY();

            // app.gameScreen.world.mapInput.... lol
            if (app.gameScreen.world.mapInput.keyPressed[Input.Keys.UP] || app.gameScreen.world.mapInput.keyPressed[Input.Keys.W]) {
                y += velocity * Gdx.graphics.getDeltaTime();
            }
            if (app.gameScreen.world.mapInput.keyPressed[Input.Keys.DOWN] || app.gameScreen.world.mapInput.keyPressed[Input.Keys.S]) {
                y -= velocity * Gdx.graphics.getDeltaTime();
            }

            if (app.gameScreen.world.mapInput.keyPressed[Input.Keys.RIGHT] || app.gameScreen.world.mapInput.keyPressed[Input.Keys.D]) {
                x += velocity * Gdx.graphics.getDeltaTime();
            }
            if (app.gameScreen.world.mapInput.keyPressed[Input.Keys.LEFT] || app.gameScreen.world.mapInput.keyPressed[Input.Keys.A]) {
                x -= velocity * Gdx.graphics.getDeltaTime();
            }

            if(canMove((int) x/32, (int) y/32))
                setPosition(x, y);
        }
        batch.draw(drawThis, getX(), getY());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!canMove((int) getX()/32, (int) getY()/32)) {
            clearActions();
            activeAnimation = stopAnimations.get(direc);
        }
        drawThis = activeAnimation.getKeyFrame(elapsedTime, true);
    }

    public void selectAnimation(float x, float y) {

        // Find a cleaner mapping to right animation. Consider also rotating sprite...
        Vector2 route = new Vector2(x-getX(), y-getY());

        float angle = route.angleDeg();
        if(angle <= 45 || angle > 315) {
            activeAnimation = walkAnimations.get(RIGHT);
            direc = RIGHT;
        } else if (45 < angle && angle <= 135) {
            activeAnimation = walkAnimations.get(UP);
            direc = UP;
        } else if (135 < angle && angle < 225) {
            activeAnimation = walkAnimations.get(LEFT);
            direc = LEFT;
        } else {
            activeAnimation = walkAnimations.get(DOWN);
            direc = DOWN;
        }
    }

    @Override
    public void setPosition(float x, float y) {

        selectAnimation(x, y);

        clearActions();
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x, y);
        float duration = (float) Math.sqrt(Math.pow(x-getX(), 2) + Math.pow(y-getY(), 2))/100f;
        moveToAction.setDuration(duration);

        RunnableAction stop = new RunnableAction();
        stop.setRunnable(new Runnable() {

            @Override
            public void run() {
                activeAnimation=stopAnimations.get(direc);

            }
        });
        SequenceAction sequence = new SequenceAction(moveToAction, stop);

        addAction(sequence);

    }

    public void gotoAndPlant(final float x, final float y, final Plant plant) {
        clearActions();
        selectAnimation(x, y);
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x, y);
        float duration = (float) Math.sqrt(Math.pow(x-getX(), 2) + Math.pow(y-getY(), 2))/100f;
        moveToAction.setDuration(duration);
        RunnableAction run = new RunnableAction();
        run.setRunnable(new Runnable() {
            @Override
            public void run() {
                //app.gameScreen.world.improvementLayer.setCell((int) x/32, (int) y/32, plant.getCell());
                plant.setActiveAnimation();
            }
        });

        RunnableAction stop = new RunnableAction();
        stop.setRunnable(new Runnable() {
            @Override
            public void run() {
                activeAnimation=stopAnimations.get(direc);
            }
        });

        SequenceAction sequence = new SequenceAction(moveToAction, run, stop);

        addAction(sequence);
    }

}

