package com.garden.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.player.Player;
import com.garden.game.screens.GameScreen;
import com.garden.game.world.plants.Plant;

import java.util.Iterator;
import java.util.Map;

public class World extends Stage {
    private final GardenGame app;
    public OrthographicCamera worldCamera;
    public TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    public TiledMapTileLayer soilLayer, improvementLayer, grassLayer, waterLayer, noWaterLayer;
    private int[] mapLayerIndices, mapLayerIndicesDry, activeIndices;
    public Player user;
    Sprite spriteHighlight;

    public MapInput mapInput;

    public int worldWidth, worldHeight, tileSize;
    public int hoveredX, hoveredY;
    public int turnNumber;

    public int dayCount, weekCount, monthCount;
    private Well well;

    private int maxGold = 9999;

    public World(GardenGame app) {
        this.app = app;

        worldCamera = new OrthographicCamera();
        // Do this to print map properly.
        worldCamera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        setViewport(new ScreenViewport(worldCamera));
        mapInput = new MapInput(app, this);
        user = new Player(app);
        //Texture t = app.assets.textureAtlas.createSprite("well");
    }

    public void init(String map) {
        tiledMap = app.assets.get(map, TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //soilLayer = (TiledMapTileLayer) tiledMap.getLayers().get("GrassLayer");
        improvementLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Improvement Layer");

        waterLayer = (TiledMapTileLayer) tiledMap.getLayers().get("WaterLayer");
        noWaterLayer = (TiledMapTileLayer) tiledMap.getLayers().get("NoWaterLayer");

        grassLayer = (TiledMapTileLayer) tiledMap.getLayers().get("GrassLayer");

        MapLayers mapLayers = tiledMap.getLayers();
        mapLayerIndices = new int[] {mapLayers.getIndex("GrassLayer"),mapLayers.getIndex("Improvement Layer"), mapLayers.getIndex("WaterLayer")};

        mapLayerIndicesDry = new int[] {mapLayers.getIndex("GrassLayer"),mapLayers.getIndex("Improvement Layer"), mapLayers.getIndex("NoWaterLayer")};

        activeIndices = mapLayerIndicesDry;
        tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);
        worldWidth = tiledMap.getProperties().get("width", Integer.class);
        worldHeight = tiledMap.getProperties().get("height", Integer.class);

        well = new Well(16, 16, app.assets.textureAtlas.createSprite("well"));
        addActor(well);

        addActor(user.unit);

        spriteHighlight = app.assets.textureAtlas.createSprite("border_tile");
    }


    public void update(float delta) {
        mapInput.update(delta);
        spriteHighlight.setPosition(hoveredX*32, hoveredY*32);
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
        for (Map.Entry<Vector2, Plant> entry : user.getPlants_().entrySet()) {
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
        //for ( Plant plant : user.getPlants() ) {
        //for (Map.Entry<Vector2, Plant> entry : user.getPlants_().entrySet()) {
        Iterator<Map.Entry<Vector2, Plant>> entryIt = user.getPlants_().entrySet().iterator();
        while (entryIt.hasNext()) {
            Map.Entry<Vector2, Plant> entry = entryIt.next();
            Plant plant = entry.getValue();
            plant.nextTurn();

            if (plant.getState() == Plant.PlantState.DEAD) {
                // Remove grass from improvement layer.
                app.gameScreen.world.improvementLayer.setCell((int) plant.getX() / 32, (int) plant.getY() / 32, plant.getCell());

                entryIt.remove();
            } else {
                profit += plant.profit;
            }
        }

        if (dayCount == 7){
            //app.setScreen(app.weekDayScreen);
            if (user.dkk <= maxGold)
                user.dkk += 100;
        }

        user.dkk += profit;
        app.score = user.dkk;

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

}
