package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Pool;
import com.garden.game.GardenGame;
import com.garden.game.tools.Constants;

import java.util.ArrayList;

public class MainCharacter extends Unit{

    public Pool<TemporalAction> getWaterTemporalPool, waterTemporalPool;
    public MainCharacter(GardenGame app) {
        super(app);
        this.walkAnimations = app.assets.walkAnimations;
        this.stopAnimations = app.assets.stopAnimations;
        this.bucketAnimations = app.assets.bucketAnimations;
        this.wateringAnimations = app.assets.wateringAnimations;
        activeAnimation = stopAnimations.get(0);
    }

    @Override
    public void initPools() {
        super.initPools();
        getWaterTemporalPool = new Pool<TemporalAction>() {
            @Override
            protected TemporalAction newObject() {
                return new TemporalAction() {
                    @Override
                    protected void update(float percent) {
                        activeAnimation = bucketAnimations.get(direc);
                        if(isComplete()) {
                            activeAnimation = stopAnimations.get(direc);
                            setPlayerMovLocked(false);
                        }
                    }
                };
            }
        };

        waterTemporalPool = new Pool<TemporalAction>() {
            @Override
            protected TemporalAction newObject() {
                return new TemporalAction() {
                    @Override
                    protected void update(float percent) {
                        activeAnimation = wateringAnimations.get(direc);
                        if(isComplete()) {
                            activeAnimation = stopAnimations.get(direc);
                            setPlayerMovLocked(false);
                        }
                    }
                };
            }
        };
    }


    // Go to plant and give it water.
    public void gotoAndWater(final float x, final float y) {
        clearActions();
        selectAnimation(x, y);
        //MoveToAction moveToAction = new MoveToAction();
        MoveToAction moveToAction = moveToActionPool.obtain();
        moveToAction.setPosition(x, y);
        float duration = (float) Math.sqrt(Math.pow(x-getX(), 2) + Math.pow(y-getY(), 2))/100f;
        moveToAction.setDuration(duration);

        TemporalAction waterPlant = waterTemporalPool.obtain();

        waterPlant.setDuration(0.8f);

        SequenceAction sequence = new SequenceAction(moveToAction, waterPlant);
        addAction(sequence);

    }

    // Go to lake and get some water
    public void gotoAndGetMoreWater() {
        clearActions();
        // Some fixed location above the lake.
        float x = 17* Constants.TILE_WIDTH;
        float y = 12*Constants.TILE_HEIGHT;
        selectAnimation(x, y);

        // Get MoveToAction from pool and set position and duration
        MoveToAction moveToAction = moveToActionPool.obtain();
        moveToAction.setPosition(x, y);
        float duration = (float) Math.sqrt(Math.pow(x-getX(), 2) + Math.pow(y-getY(), 2))/100f;
        moveToAction.setDuration(duration);

        // Get get more water action from pool and set duration
        TemporalAction getMoreWater = getWaterTemporalPool.obtain();
        getMoreWater.setDuration(0.5f);

        // Create sequence action
        SequenceAction sequence = new SequenceAction(moveToAction, getMoreWater);

        addAction(sequence);


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

        drawThis = activeAnimation.getKeyFrame(elapsedTime);
    }
}
