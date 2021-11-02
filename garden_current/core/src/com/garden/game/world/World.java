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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.player.Player;
import com.garden.game.tools.Constants;

public class World extends Stage {
    private final GardenGame app;
    public OrthographicCamera worldCamera;
    public TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    public TiledMapTileLayer soilLayer, improvementLayer;
    private int[] mapLayerIndices;
    public Player user;
    Sprite spritePlayer, spriteHighlight;
    public MapInput mapInput;
    public int worldWidth, worldHeight, tileSize;
    public int hoveredX, hoveredY;
    public int turnNumber;

    private int maxGold = 9999;

    public World(GardenGame app) {
        this.app = app;

        worldCamera = new OrthographicCamera();
        // Do this to print map properly.
        worldCamera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        setViewport(new ScreenViewport(worldCamera));
        mapInput = new MapInput(app, this);
        user = new Player(app);
    }


    public void init(String map) {
        tiledMap = app.assets.get(map, TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        soilLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Soil Layer");
        improvementLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Improvement Layer");
        MapLayers mapLayers = tiledMap.getLayers();
        mapLayerIndices = new int[] {mapLayers.getIndex("Soil Layer"), mapLayers.getIndex("Improvement Layer")};

        tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);
        worldWidth = tiledMap.getProperties().get("width", Integer.class);
        worldHeight = tiledMap.getProperties().get("height", Integer.class);

        addActor(user.unit);

        spritePlayer = app.assets.textureAtlas.createSprite("character000");
        spriteHighlight = app.assets.textureAtlas.createSprite("border_tile");

    }



    public void update(float delta) {
        mapInput.update(delta);
    }

    public void render() {
        // Draw red around the edge of world
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldCamera.update();
        tiledMapRenderer.setView(worldCamera);
        tiledMapRenderer.render(mapLayerIndices);


        // Fixate sprites when moving camera. Consider fixing camera to main character.
        app.batch.setProjectionMatrix(worldCamera.combined);

        app.batch.begin();
        for ( Plant plant : user.getPlants() ) {
            if( plant.getSprite() != null ) {
                plant.getSprite().draw(app.batch);
            }
        }
        app.batch.draw(spriteHighlight, hoveredX*tileSize, hoveredY*tileSize);
        act(Gdx.graphics.getDeltaTime());
        draw();
        app.batch.end();

    }

    public void endTurn() {
        turnNumber++;


        if (user.dkk <= maxGold)
            user.dkk += 200;
        else
            user.dkk = maxGold;

        app.score = user.dkk;

        if (turnNumber == 5)
            app.setScreen(app.gameOverScreen);

    }

}
