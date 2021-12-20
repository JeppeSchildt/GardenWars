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
import com.garden.game.player.Quest;
import com.garden.game.tools.Constants;

import java.util.Map;
import java.util.Random;

public class World extends Stage {
    private final GardenGame app;
    public OrthographicCamera worldCamera;
    public TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    public TiledMapTileLayer soilLayer, improvementLayer, grassLayer, waterLayer, noWaterLayer, fenceLayer, buildingsLayer, treesLayer;
    private int[] mapLayerIndices, mapLayerIndicesDry, activeIndices;
    public Player player;
    private Sprite spriteHighlight;
    public MapInput mapInput;
    public int worldWidth, worldHeight, tileSize;
    public int hoveredX, hoveredY;
    public int turnNumber;
    public int dayCount, weekCount, monthCount;
    public MapLayers mapLayers;
    private int maxGold = 9999, salary = 50;
    public Boss boss;

    public int DrySeasonCount_RandomNumber, WetSeasonCount_RandomNumber;
    public int lengthForDrySeason, lengthForWetSeason;
    public boolean drySeason, isStartDrySeason = false, isStartWetSeason = false, isBossEvent = false;

    public World(GardenGame app) {
        this.app = app;

        worldCamera = new OrthographicCamera();
        // Do this to print map properly.
        worldCamera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        setViewport(new ScreenViewport(worldCamera));
        worldCamera.zoom = 0.6302493f;
        mapInput = new MapInput(app, this);
        player = new Player(app);
        turnNumber = 1;
        dayCount = 1;
        boss = new Boss(app);

        // Dry season event
        drySeason = false;
    }

    public void init(String map) {
        tiledMap = app.assets.get(map, TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        waterLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Water Layer");
        grassLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Grass Layer");

        mapLayers = tiledMap.getLayers();
        mapLayerIndices = new int[] {mapLayers.getIndex("Grass Layer"), mapLayers.getIndex("Improvement Layer"), mapLayers.getIndex("Road Layer"), mapLayers.getIndex("Water Layer"), mapLayers.getIndex("WaterPlants Layer"), mapLayers.getIndex("Fence Layer"), mapLayers.getIndex("Buildings Layer"), mapLayers.getIndex("Trees Layer")};

        mapLayerIndicesDry = new int[] {mapLayers.getIndex("Grass Layer"), mapLayers.getIndex("Improvement Layer"), mapLayers.getIndex("Road Layer"), mapLayers.getIndex("NoWater Layer"), mapLayers.getIndex("Fence Layer"), mapLayers.getIndex("Buildings Layer"), mapLayers.getIndex("TreesDead Layer")};

        activeIndices = mapLayerIndicesDry;
        tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);
        worldWidth = tiledMap.getProperties().get("width", Integer.class);
        worldHeight = tiledMap.getProperties().get("height", Integer.class);

        resetMap();

        addActor(player.unit);

        spriteHighlight = app.assets.textureAtlas.createSprite("highlight_test");
    }


    public void update(float delta) {
        mapInput.update(delta);
        spriteHighlight.setPosition(hoveredX * Constants.TILE_WIDTH, hoveredY * Constants.TILE_HEIGHT);
    }

    public void render() {
        // Draw same color as grass around the edge of world
        Gdx.gl.glClearColor(0.184f, 0.505f, 0.211f,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldCamera.update();
        tiledMapRenderer.setView(worldCamera);

        if (drySeason) {
            tiledMapRenderer.render(mapLayerIndicesDry);
        } else {
            tiledMapRenderer.render(mapLayerIndices);
        }

        // Fixate sprites when moving camera. Consider fixing camera to main character.
        app.batch.setProjectionMatrix(worldCamera.combined);

        app.batch.begin();  // Batch ended in GameScreens render
        act(Gdx.graphics.getDeltaTime());
        //renderBubble("TEST");
        //for ( Plant plant : user.getPlants() ) {

        for (Map.Entry<Vector2, Plant> entry : player.getPlants_().entrySet()) {
            Plant plant = entry.getValue();
            plant.draw(app.batch, 1);
        }

        draw();

        spriteHighlight.draw(app.batch);
    }

    public void nextTurn() {
        turnNumber++;
        //startEvent("magazine"); //remove
        int profit = 0;

        // Making sure boss doesn't show up unexpectedly...?
        if(isBossEvent) {
            isBossEvent = false;
            boss.remove();
        }

        player.nextTurn();

        if (dayCount == 7){
            //app.setScreen(app.weekDayScreen);
            if (player.money <= maxGold)
                player.money += salary;
        }

        player.money += profit;
        app.score = player.money;

        weekCount();
        drySeasonEvent();
        checkPoints();

    }

    private void checkPoints() {
        player.checkPoints();
        if(player.gameWon) {
            bossGameWon();
        }
    }

    private void weekCount(){
        dayCount++;
        if (dayCount == 8){
            enterBoss();
            setNewQuests();
            dayCount = 1;
            weekCount++;
        }
        //Month end
        if (weekCount == 4){
            weekCount = 0;
            monthCount++;
        }

    }

    // Player can't walk/plant on certain layers.
    public boolean isNoAccessTile(String layerName, int x, int y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) mapLayers.get(layerName);
        return layer.getCell(x,y) != null;
    }

    public boolean isWaterTile(int x, int y) {
        return waterLayer.getCell(x, y) != null;
    }

    private void resetMap(){
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) mapLayers.get("Grass Layer");
        for (int i = 0; i < Constants.MAP_WIDTH_TILES; i++){

            for(int j = 0; j < Constants.MAP_HEIGHT_TILES; j++){
                if (tileLayer.getCell(i,j) != null){
                    tileLayer.setCell(i,j, app.assets.grassCell);
                }
            }
        }
    }

    public void drySeasonEvent(){

        if (lengthForWetSeason == DrySeasonCount_RandomNumber){
            // Make Map DrySeason
            drySeason = true;
            app.sound.Chance_InGameMusic();
            lengthForDrySeason = 0;
        }

        if (drySeason){
            if (!isStartWetSeason){
                isStartWetSeason = true;
                WetSeasonCount_RandomNumber = new Random().nextInt(Constants.MAX_DRY_SEASONS_DAYS) + Constants.MIN_DRY_SEASONS_DAYS;
            }
            if (lengthForDrySeason == WetSeasonCount_RandomNumber){
                // Make Map WetSeason
                drySeason = false;
                app.sound.Chance_InGameMusic();
                lengthForWetSeason = 0;

                isStartDrySeason = false;
                isStartWetSeason = false;
            }
            lengthForDrySeason++;
        }
        lengthForWetSeason++;
    }

    public void enterBoss() {
        boss.weeklyCheck();
        isBossEvent = true;
        addActor(boss);
        boss.enterBoss(player.unit.getX()+50, player.unit.getY());
    }

    public void leaveBoss() {
        boss.leave();
    }

    public void introBoss() {
        isBossEvent = true;
        addActor(boss);
        boss.intro(player.unit.getX()+50, player.unit.getY());
    }

    // If quest is completed give a new quest.
    private void setNewQuests() {
        for(Quest q : player.quests) {
            if(q.isCompleted) {
                q.onCompleted();
                player.setNewQuest(q.questID);
            }
        }
    }
    private void bossGameWon() {
        isBossEvent = true;
        boss.remove();
        addActor(boss);
        boss.gameWon(player.unit.getX(), player.unit.getY());
    }

}
