package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.garden.game.GardenGame;
import com.garden.game.player.Player;

public class World {
    private final GardenGame app;
    public OrthographicCamera worldCamera;
    public TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    TiledMapTileLayer tileLayer;
    TiledMapTile highlightedTile;
    TiledMapTileLayer.Cell highlightedCell;
    public Player user;
    Sprite spritePlayer, spriteHighlight;
    public MapInput mapInput;
    int worldWidth, worldHeight, tileSize;
    public int hoveredX, hoveredY;
    public int turnNumber;


    public World(GardenGame app) {
        this.app = app;

        worldCamera = new OrthographicCamera();
        // Do this to print map properly.
        worldCamera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        worldCamera.update();

        //tiledMap = new TmxMapLoader().load("map3.tmx");
        tiledMap = app.assets.get("map3.tmx", TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");
        //highlightedTile = (TiledMapTile) app.textureAtlas.createSprite("highlight_tile");

        tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);
        worldWidth = tiledMap.getProperties().get("width", Integer.class);
        worldHeight = tiledMap.getProperties().get("height", Integer.class);

        user = new Player(app);
        //spritePlayer = app.textureAtlas.createSprite("character000");
        //spriteHighlight = app.textureAtlas.createSprite("border_tile");
        spritePlayer = app.assets.textureAtlas.createSprite("character000");
        spriteHighlight = app.assets.textureAtlas.createSprite("border_tile");
        mapInput = new MapInput(app, this);
        Gdx.input.setInputProcessor(mapInput);

    }

    public void update(float delta) {
        mapInput.update(delta);
    }

    public void render() {
        // Draw red around the edge of world
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldCamera.update();
        tiledMapRenderer.setView(worldCamera);
        tiledMapRenderer.render();

        // Fix sprites when moving camera.
        app.batch.setProjectionMatrix(worldCamera.combined);

        app.batch.begin();

        // The tileSize division and then multiplication seems... odd, but do this
        // to position sprite/highlight same place whenever there is a click/hover
        // within some tile.
        app.batch.draw(spritePlayer, user.unit.position.x*tileSize, user.unit.position.y*tileSize);
        // Consider positioning character in middle of tile.
        app.batch.draw(spriteHighlight, hoveredX*tileSize, hoveredY*tileSize);
        app.batch.end();

    }

    public void endTurn() {
        turnNumber++;
        user.dkk += 20;
    }
}
