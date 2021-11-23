package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;


public class SkillTreeScreen implements Screen {

    public World world;
    private GardenGame app;
    private Stage stage;
    private Table table;
    private Texture textureRightArrow, textureLeftArrow, textureDownArrow, textureEmptyArrow;
    private Image imgRightArrow, imgLeftArrow, imgDownArrow, imgEmptyArrow;
    private TextButton basicPlants, fertilizer, fertilizerPlus, general, moreFruits, moreFlowers,
            construction, communication, water, waterPlus, irrigation, autoHarvest,
            settingsButton, quitButton, playButton, resetButton;

    private ShapeRenderer shapeRenderer;
    private final Color hudColor;

    private int offsetX = 20, offsetY = 80;
    private int basicPlantsX = 250, basicPlantsY = 600;
    private int fertilizerX = basicPlantsX + offsetX, fertilizerY = basicPlantsY - offsetY;
    private int moreFruitsX = basicPlantsX + 55 , moreFruitsY = fertilizerY - offsetY;
    private int moreFlowersX = fertilizerX + offsetX - 100, moreFlowersY = moreFruitsY;
    private int fertilizerPlusX = basicPlantsX + 10,  fertilizerPlusY = moreFruitsY - offsetY;
    //tree 2
    private int generalX = fertilizerX + 400,  generalY = basicPlantsY;
    private int constructionX = moreFlowersX + 350 , constructionY = generalY - offsetY;
    private int communicationX = constructionX + 110 , communicationY = constructionY;
    private  int waterX = communicationX + 135, waterY = constructionY;
    private  int waterPlusX = waterX  , waterPlusY = constructionY - offsetY;
    private  int irrigationX = waterX , irrigationY = waterPlusY - offsetY;
    private  float autoHarvestX = (float) ((fertilizerPlusX + irrigationX) *0.5) -50, autoHarvestY = fertilizerPlusY - 2* offsetY;







    Skin skin;
    public SkillTreeScreen(GardenGame app) {
        this.app = app;

        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport(camera));

        initStage();

        hudColor = Color.BLACK;
        shapeRenderer = new ShapeRenderer();
    }

    private void initStage() {


        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();

        world = new World(app);
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("uiskin.json"));


        Label title = new Label("Skill Tree", app.assets.largeTextStyle);
        title.setFontScale(5);

        //ImageButton playButton = new ImageButton(app.assets.goldIcon);
        playButton = new TextButton("Resume",skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resumeGame();
            }
        });



        textureRightArrow = new Texture(Gdx.files.internal("arrows/RightArrow.png"));
        imgRightArrow = new Image(textureRightArrow);

        textureLeftArrow = new Texture(Gdx.files.internal("arrows/LeftArrow.png"));
        imgLeftArrow = new Image(textureLeftArrow);

        textureDownArrow = new Texture(Gdx.files.internal("arrows/DownArrow.png"));
        imgDownArrow = new Image(textureDownArrow);

        textureEmptyArrow = new Texture(Gdx.files.internal("arrows/EmptyArrow.png"));
        imgEmptyArrow = new Image(textureEmptyArrow);

        basicPlants = new TextButton("Basic Plants",skin);
        basicPlants.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        basicPlants.setPosition(basicPlantsX, basicPlantsY);
        stage.addActor(basicPlants);

        fertilizer = new TextButton("Fertilizer",skin);
        fertilizer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        fertilizer.setPosition(fertilizerX, fertilizerY);
        stage.addActor(fertilizer);

        general = new TextButton("General",skin);
        general.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        general.setPosition(generalX, generalY);
        stage.addActor(general);

        moreFlowers = new TextButton("More Flowers",skin);
        moreFlowers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        moreFlowers.setPosition(moreFlowersX, moreFlowersY);
        stage.addActor(moreFlowers);

        moreFruits = new TextButton("More Fruits",skin);
        moreFruits.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        moreFruits.setPosition(moreFruitsX, moreFruitsY);
        stage.addActor(moreFruits);

        construction = new TextButton("Construction",skin);
        construction.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        construction.setPosition(constructionX, constructionY);
        stage.addActor(construction);

        communication = new TextButton("Communication",skin);
        communication.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        communication.setPosition(communicationX, communicationY);
        stage.addActor(communication);

        water = new TextButton("Water",skin);
        water.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                world.user.maxWater += 100;
            }
        });
        water.setPosition(waterX, waterY);
        stage.addActor(water);

        fertilizerPlus = new TextButton("Fertilizer++",skin);
        fertilizerPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        fertilizerPlus.setPosition(fertilizerPlusX, fertilizerPlusY);
        stage.addActor(fertilizerPlus);

        waterPlus = new TextButton("Water++",skin);
        waterPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        waterPlus.setPosition(waterPlusX, waterPlusY);
        stage.addActor(waterPlus);

        autoHarvest = new TextButton("Auto harvest",skin);
        autoHarvest.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        autoHarvest.setPosition(autoHarvestX, autoHarvestY);
        stage.addActor(autoHarvest);

        irrigation = new TextButton("Irrigation",skin);
        irrigation.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        irrigation.setPosition(irrigationX, irrigationY);
        stage.addActor(irrigation);


        resetButton = new TextButton("Restart",skin);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });




        settingsButton = new TextButton("Preferences",skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                preferences();

            }
        });

        quitButton = new TextButton("Back to menu",skin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMenue();
            }
        });

    }


    private void resumeGame(){
        app.sound.buttonMenueSound();
        app.setScreen(app.gameScreen);
    }
    private void backToMenue(){
        //Gdx.app.exit();
        app.sound.buttonMenueSound();
        app.preferencesBool = false;
        app.currentGameBool = true;

        app.sound.Chance_Music();
        app.setScreen(app.titleScreen);
    }



    private void preferences(){
        app.sound.buttonMenueSound();
        if(app.preferencesScreen == null) {
            app.preferencesScreen = new PreferencesScreen(app);
        }
        app.setScreen(app.preferencesScreen);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) backToMenue();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) resumeGame();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        drawMenu();
    }

    // https://stackoverflow.com/questions/14700577/drawing-transparent-shaperenderer-in-libgdx
    public void drawMenu(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glLineWidth (4);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(basicPlantsX + 55, basicPlantsY, basicPlantsX + 55, fertilizerY+27);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(basicPlantsX + 55, fertilizerY, moreFruitsX + 55, moreFruitsY+27);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(basicPlantsX + 55, fertilizerY, moreFlowersX + 55, moreFruitsY+27);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(moreFruitsX + 55, moreFruitsY, basicPlantsX + 55, fertilizerPlusY+27);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(moreFlowersX + 55, moreFruitsY, basicPlantsX + 55, fertilizerPlusY+27);
        shapeRenderer.end();



        // second tree

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(generalX + 35, generalY, constructionX + 55, constructionY+27);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(generalX + 35, generalY, communicationX + 55, communicationY+27);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(generalX + 35, generalY, waterX + 30, waterY+27);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(waterX + 30, waterY, waterPlusX + 30, waterPlusY+27);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(waterPlusX + 30, waterPlusY, irrigationX + 30, irrigationY+27);
        shapeRenderer.end();


        //autoHarvest

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(basicPlantsX + 55, fertilizerPlusY, autoHarvestX + 55, autoHarvestY+27);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(irrigationX + 30, irrigationY, autoHarvestX + 55, autoHarvestY+27);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(constructionX + 55, constructionY, autoHarvestX + 55, autoHarvestY+27);
        shapeRenderer.end();




    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
