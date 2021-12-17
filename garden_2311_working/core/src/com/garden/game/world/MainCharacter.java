package com.garden.game.world;

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
}
