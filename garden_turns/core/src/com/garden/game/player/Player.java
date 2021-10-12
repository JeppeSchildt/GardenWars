package com.garden.game.player;

import com.garden.game.GardenGame;
import com.garden.game.world.Unit;
import com.garden.game.world.World;

import java.util.ArrayList;

/*
    Make an endTurn() method that makes all plants spin.
    Store stats here: money, score etc.
    Plants.
 */
public class Player {
    GardenGame app;
    public Unit unit;
    public int dkk;


    public Player(GardenGame app) {
        this.app = app;
        //world = this.app.gameScreen.world;
        unit = new Unit(this.app, "character000");
    }
}
