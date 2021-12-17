package com.garden.game.world;

import com.garden.game.GardenGame;
import com.garden.game.tools.Constants;

// Journalist class
public class Journalist extends Unit {
    String journalist = "journalist";
    public Journalist(GardenGame app) {
        super(app);
        this.walkAnimations = app.assets.journalistWalkAnimations;
        this.stopAnimations = app.assets.journalistStopAnimations;
        activeAnimation = stopAnimations.get(0);


    }

    public void setInitialPosition() {
        direc = Constants.LEFT;
        setX(31*Constants.TILE_WIDTH);
        setY(22*Constants.TILE_HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        drawThis = activeAnimation.getKeyFrame(elapsedTime);
    }
}
