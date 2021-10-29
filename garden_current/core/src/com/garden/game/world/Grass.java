package com.garden.game.world;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.garden.game.tools.Constants;

public class Grass extends Plant {
    TiledMapTileLayer.Cell cell;

    public Grass(int x, int y, TiledMapTileLayer.Cell cell) {
        super(x, y);
        this.cell = cell;
        typeID = Constants.GRASS;
        price = Constants.GRASS_PRICE;

    }

    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }
}
