package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.garden.game.GardenGame;
import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;

import java.util.ArrayList;

// Character controlled by player.
public class Unit extends Actor {
    GardenGame app;
    String assetName;

    public int maxX, minX, maxY, minY, direc;
    public ArrayList<Animation<TextureRegion>> walkAnimations, stopAnimations, bucketAnimations, wateringAnimations;
    public Animation<TextureRegion> activeAnimation;
    public float elapsedTime, animationTime;
    TextureRegion drawThis;
    public float velocity = 100;
    public boolean bucket;


    public Unit(GardenGame app, String assetName) {
        this.app = app;
        this.assetName = assetName;

        this.walkAnimations = app.assets.walkAnimations;
        this.stopAnimations = app.assets.stopAnimations;
        this.bucketAnimations = app.assets.bucketAnimations;
        this.wateringAnimations = app.assets.wateringAnimations;

        maxX = Constants.MAP_WIDTH_TILES; // Hardcoded...
        maxY = Constants.MAP_HEIGHT_TILES;
        minX = 0;
        minY = 0;
        activeAnimation = stopAnimations.get(0);

        // Position character in middle of map.
        setX(Constants.MAP_WIDTH_TILES/2 * Constants.TILE_WIDTH);
        setY(Constants.MAP_HEIGHT_TILES/2 * Constants.TILE_HEIGHT);

    }

    public void move(float x, float y) {
        selectAnimation(x, y);

    }
    public boolean canMove(int x, int y) {

        // Check for bounds of map.
        boolean canMove = x < maxX && y < maxY && x >= minX && y >= minY;

        // Check for water.

        canMove = canMove && !(app.gameScreen.world.isWaterTile(x,y));
        canMove = canMove && !(app.gameScreen.world.isNoAccessTile("Fence Layer", x,y));
        canMove = canMove && !(app.gameScreen.world.isNoAccessTile("Buildings Layer", x,y));
        canMove = canMove && !(app.gameScreen.world.isNoAccessTile("Trees Layer", x,y));
        //canMove = canMove && (app.gameScreen.world.fenceLayer.getCell(x, y) == null);

        return canMove;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();

        batch.draw(drawThis, getX(), getY());

    }

    @Override
    public void act(float delta) {
        super.act(delta);

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

            if(canMove((int) x/Constants.TILE_WIDTH, (int) y/Constants.TILE_HEIGHT))
                setPosition(x, y);
        }

        if(!canMove((int) getX()/Constants.TILE_WIDTH, (int) getY()/Constants.TILE_HEIGHT)) {
            //clearActions();

            //activeAnimation = stopAnimations.get(direc);
        }

        drawThis = activeAnimation.getKeyFrame(elapsedTime, true);
    }

    public void selectAnimation(float x, float y) {

        // Find a cleaner mapping to right animation. Consider also rotating sprite...
        Vector2 route = new Vector2(x-getX(), y-getY());

        float angle = route.angleDeg();
        if(angle <= 45 || angle > 315) {
            activeAnimation = walkAnimations.get(Constants.RIGHT);
            direc = Constants.RIGHT;
        } else if (45 < angle && angle <= 135) {
            activeAnimation = walkAnimations.get(Constants.UP);
            direc = Constants.UP;
        } else if (135 < angle && angle < 225) {
            activeAnimation = walkAnimations.get(Constants.LEFT);
            direc = Constants.LEFT;
        } else {
            activeAnimation = walkAnimations.get(Constants.DOWN);
            direc = Constants.DOWN;
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
                app.gameScreen.world.grassLayer.setCell((int) x/Constants.TILE_WIDTH, (int) y/Constants.TILE_HEIGHT, plant.getCell());
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

    // Go to plant and give it water.
    public void gotoAndWater(final float x, final float y, final Plant plant) {
        clearActions();
        selectAnimation(x, y);
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x, y);
        float duration = (float) Math.sqrt(Math.pow(x-getX(), 2) + Math.pow(y-getY(), 2))/100f;
        moveToAction.setDuration(duration);

    }

    // Go to lake and get some water
    public void gotoAndGetMoreWater() {
        bucket = true;
        animationTime = 0.f;
        clearActions();
        float x = 17*Constants.TILE_WIDTH;
        float y = 12*Constants.TILE_HEIGHT;
        selectAnimation(x, y);
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x, y);
        float duration = (float) Math.sqrt(Math.pow(x-getX(), 2) + Math.pow(y-getY(), 2))/100f;
        moveToAction.setDuration(duration);


        /*RunnableAction getMoreWater = new RunnableAction();
        /*getMoreWater.setRunnable(new Runnable() {

            @Override
            public void run() {
                activeAnimation=bucketAnimations.get(direc);

            }
        });*/

        /*Action getMoreWater = new Action() {
            @Override
            public boolean act(float delta) {
                if(bucketAnimations.get(direc).isAnimationFinished(animationTime)) {
                    animationTime = 0f;
                    bucket = false;
                    return true;
                }
                if(bucket) {
                    animationTime += delta;
                    activeAnimation = bucketAnimations.get(direc);

                    drawThis = bucketAnimations.get(direc).getKeyFrame(animationTime, false);

                }
                return false;
            }
        };*/

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

    public void setDirec(int dir) {
        direc = dir;
    }
}

