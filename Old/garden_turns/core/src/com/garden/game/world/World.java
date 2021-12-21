package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.player.Player;

public class World extends Stage {
    private final GardenGame app;
    public OrthographicCamera worldCamera;
    public TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    TiledMapTileLayer tileLayer1, tileLayer2, improvementLayer;
    private int[] mapLayerIndices;
    private int[] improvementLayerIndex;
    TiledMapTileLayer.Cell cellGrass;
    public Player user;
    Sprite spritePlayer, spriteHighlight, spriteGrass;
    public MapInput mapInput;
    public int worldWidth, worldHeight, tileSize;
    public int hoveredX, hoveredY;
    public int turnNumber;
    public Stage worldStage;


    public World(GardenGame app) {
        this.app = app;

        worldCamera = new OrthographicCamera();
        // Do this to print map properly.
        worldCamera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        setViewport(new ScreenViewport(worldCamera));
        mapInput = new MapInput(app, this);
        user = new Player(app);
        //init();
    }

    // Den skal lave så det virker.... ctor/world skal ikke også init
    public void init() {
        //tiledMap = new TmxMapLoader().load("map3.tmx");
        tiledMap = app.assets.get("map6.tmx", TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tileLayer1 = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");
        tileLayer2 = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 2");
        MapLayers mapLayers = tiledMap.getLayers();
        mapLayerIndices = new int[] {mapLayers.getIndex("Tile Layer 1")};
        improvementLayerIndex = new int[] { 1 };

        improvementLayer = new TiledMapTileLayer(32,32,32,32);
        //highlightedTile = (TiledMapTile) app.textureAtlas.createSprite("highlight_tile");

        tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);
        worldWidth = tiledMap.getProperties().get("width", Integer.class);
        worldHeight = tiledMap.getProperties().get("height", Integer.class);

        //user = new Player(app);
        addActor(user.unit);

        spritePlayer = app.assets.textureAtlas.createSprite("character000");
        spriteHighlight = app.assets.textureAtlas.createSprite("border_tile");
        spriteGrass = app.assets.textureAtlas.createSprite("grass");
        cellGrass = tileLayer2.getCell(0, 0);
        //cellGrass = app.assets.tileSet.getTile(0);
        //cellGrass = app.assets.textureAtlas.createSprite("grass");
        //mapInput = new MapInput(app, this);
        //Gdx.input.setInputProcessor(mapInput);
    }

    public void update(float delta) {

        mapInput.update(delta);
        tileLayer1.setCell(0,0, cellGrass);
    }

    public void render() {
        // Draw red around the edge of world
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldCamera.update();
        tiledMapRenderer.setView(worldCamera);
        tiledMapRenderer.render(mapLayerIndices);


        // Fix sprites when moving camera.
        app.batch.setProjectionMatrix(worldCamera.combined);
        /*
        app.batch.begin();

        // The tileSize division and then multiplication seems... odd, but do this
        // to position sprite/highlight same place whenever there is a click/hover
        // within some tile.
        app.batch.draw(spritePlayer, user.unit.position.x*tileSize, user.unit.position.y*tileSize);
        // Consider positioning character in middle of tile.
        app.batch.draw(spriteHighlight, hoveredX*tileSize, hoveredY*tileSize);
        app.batch.end();*/

        app.batch.begin();
        app.batch.draw(spriteHighlight, hoveredX*tileSize, hoveredY*tileSize);
        act(Gdx.graphics.getDeltaTime());
        draw();
        app.batch.end();

    }

    public void endTurn() {
        turnNumber++;

        user.dkk += 20;
    }
}
