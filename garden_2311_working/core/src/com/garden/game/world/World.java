package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.player.Player;
import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;

import java.util.Map;

public class World extends Stage {
    private final GardenGame app;
    public OrthographicCamera worldCamera;
    public TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    public TiledMapTileLayer soilLayer, improvementLayer, grassLayer, waterLayer, noWaterLayer;
    private int[] mapLayerIndices, mapLayerIndicesDry, activeIndices;
    public Player player;
    Sprite spriteHighlight;

    public MapInput mapInput;

    public int worldWidth, worldHeight, tileSize;
    public int hoveredX, hoveredY;
    public int turnNumber;

    public int dayCount, weekCount, monthCount;

    private int maxGold = 9999;

    public World(GardenGame app) {
        this.app = app;

        worldCamera = new OrthographicCamera();
        // Do this to print map properly.
        worldCamera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        setViewport(new ScreenViewport(worldCamera));
        worldCamera.zoom = 0.6302493f;
        mapInput = new MapInput(app, this);
        player = new Player(app);
    }

    public void init(String map) {
        tiledMap = app.assets.get(map, TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //soilLayer = (TiledMapTileLayer) tiledMap.getLayers().get("GrassLayer");
        improvementLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Trees Layer");
        improvementLayer = (TiledMapTileLayer) tiledMap.getLayers().get("TreesDead Layer");

        improvementLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Buildings Layer");

        improvementLayer = (TiledMapTileLayer) tiledMap.getLayers().get("WaterPlants Layer");
        waterLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Water Layer");
        noWaterLayer = (TiledMapTileLayer) tiledMap.getLayers().get("NoWater Layer");

        improvementLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Road Layer");

        improvementLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Improvement Layer");
        grassLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Grass Layer");

        MapLayers mapLayers = tiledMap.getLayers();
        mapLayerIndices = new int[] {mapLayers.getIndex("Grass Layer"), mapLayers.getIndex("Improvement Layer"), mapLayers.getIndex("Road Layer"), mapLayers.getIndex("Water Layer"), mapLayers.getIndex("WaterPlants Layer"), mapLayers.getIndex("Buildings Layer"), mapLayers.getIndex("Trees Layer")};

        mapLayerIndicesDry = new int[] {mapLayers.getIndex("Grass Layer"), mapLayers.getIndex("Improvement Layer"), mapLayers.getIndex("Road Layer"), mapLayers.getIndex("NoWater Layer"), mapLayers.getIndex("Buildings Layer"), mapLayers.getIndex("TreesDead Layer")};


        activeIndices = mapLayerIndicesDry;
        tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);
        worldWidth = tiledMap.getProperties().get("width", Integer.class);
        worldHeight = tiledMap.getProperties().get("height", Integer.class);



        addActor(player.unit);

        spriteHighlight = app.assets.textureAtlas.createSprite("highlight_test");
    }


    public void update(float delta) {
        mapInput.update(delta);
        spriteHighlight.setPosition(hoveredX * Constants.TILE_WIDTH, hoveredY * Constants.TILE_HEIGHT);
    }

    public void render() {
        // Draw red around the edge of world
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldCamera.update();
        tiledMapRenderer.setView(worldCamera);


        if (app.drySeason)
            tiledMapRenderer.render(mapLayerIndicesDry);
        else tiledMapRenderer.render(mapLayerIndices);


        // Fixate sprites when moving camera. Consider fixing camera to main character.
        app.batch.setProjectionMatrix(worldCamera.combined);

        app.batch.begin();  // Batch ended in GameScreens render
        act(Gdx.graphics.getDeltaTime());
        //renderBubble("TEST");
        draw();

        //for ( Plant plant : user.getPlants() ) {
        for (Map.Entry<Vector2, Plant> entry : player.getPlants_().entrySet()) {
            Plant plant = entry.getValue();
            plant.draw(app.batch, 1);
        }

        spriteHighlight.draw(app.batch);

        //well.sprite.draw(app.batch);

    }

    public void nextTurn() {
        turnNumber++;
        //startEvent("magazine"); //remove
        int profit = 0;

        player.nextTurn();


        if (dayCount == 7){
            //app.setScreen(app.weekDayScreen);
            if (player.money <= maxGold)
                player.money += 100;
        }

        player.money += profit;
        app.score = player.money;

        weekCount();
    }

    private void weekCount(){
        dayCount++;
        if (dayCount == 8){
            dayCount = 1;
            weekCount++;
        }
        //Month end
        if (weekCount == 4){
            weekCount = 0;
            monthCount++;
            //startEvent("magazine");
        }

    }

    public boolean isWaterTile(int x, int y) {
        return waterLayer.getCell(x, y) != null;
    }

}
