package com.garden.game.world;

import com.garden.game.GardenGame;

// Journalist class
public class Journalist extends Unit {

    public Journalist(GardenGame app) {
        super(app);
        this.walkAnimations = app.assets.journalistWalkAnimations;
        this.stopAnimations = app.assets.journalistStopAnimations;
        activeAnimation = stopAnimations.get(0);
    }


}
