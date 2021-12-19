package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.Pool;
import com.garden.game.GardenGame;
import com.garden.game.tools.Constants;

import java.util.ArrayList;

// Class for characters.
public class Unit extends Actor {
    GardenGame app;

    public int maxX, minX, maxY, minY, direc;
    public ArrayList<Animation<TextureRegion>> walkAnimations, stopAnimations, bucketAnimations, wateringAnimations;
    public Animation<TextureRegion> activeAnimation;
    public float elapsedTime;
    public TextureRegion drawThis;
    public float velocity = 100;
    public Pool<MoveToAction> moveToActionPool;
    public Pool<RunnableAction> stopActionPool;

    public Unit(GardenGame app) {
        this.app = app;

        maxX = Constants.MAP_WIDTH_TILES; // Hardcoded...
        maxY = Constants.MAP_HEIGHT_TILES;
        minX = 0;
        minY = 0;
        // Position character in middle of map.
        setX(Constants.MAP_WIDTH_TILES/2 * Constants.TILE_WIDTH);
        setY(Constants.MAP_HEIGHT_TILES/2 * Constants.TILE_HEIGHT);

        initPools();

    }

    // Create Pools of actions instead of newing them all the time.
    public void initPools() {
        moveToActionPool = new Pool<MoveToAction>(){
            protected MoveToAction newObject(){
                return new MoveToAction();
            }
        };


        stopActionPool = new Pool<RunnableAction>() {
            @Override
            protected RunnableAction newObject() {
                return new RunnableAction() {
                    @Override
                    public void run() {
                        setPlayerMovLocked(false);
                        activeAnimation=stopAnimations.get(direc);
                    }
                };
            }
        };
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


        return canMove;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();

        batch.draw(drawThis, getX(), getY());

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

    // Add a MoveToAction to given position.
    @Override
    public void setPosition(float x, float y) {

        selectAnimation(x, y);

        clearActions();
        //MoveToAction moveToAction = new MoveToAction();
        MoveToAction moveToAction = moveToActionPool.obtain();
        moveToAction.setPosition(x, y);
        float duration = (float) Math.sqrt(Math.pow(x-getX(), 2) + Math.pow(y-getY(), 2))/100f;
        moveToAction.setDuration(duration);

        RunnableAction stop = stopActionPool.obtain();
        SequenceAction sequence = new SequenceAction(moveToAction, stop);

        addAction(sequence);

    }

    // Go to x, y and remove actor from whatever stage it appears in.
    public void goSomewhereRemove(float x, float y) {
        selectAnimation(x, y);

        clearActions();
        //MoveToAction moveToAction = new MoveToAction();
        MoveToAction moveToAction = moveToActionPool.obtain();
        moveToAction.setPosition(x, y);
        float duration = (float) Math.sqrt(Math.pow(x-getX(), 2) + Math.pow(y-getY(), 2))/100f;
        moveToAction.setDuration(duration);

        RunnableAction stop = stopActionPool.obtain();


        RunnableAction remove = new RunnableAction() {
            @Override
            public void run() {
                remove();
            }
        };

        SequenceAction sequence = new SequenceAction(moveToAction, stop, remove);
        addAction(sequence);
    }



    public void setDirec(int dir) {
        direc = dir;
    }


    public void setPlayerMovLocked(boolean b) {
        app.gameScreen.world.player.setMovementLocked(b);
    }

}

