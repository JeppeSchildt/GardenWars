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
import sun.java2d.SunGraphics2D;

public class SkillTreeScreen implements Screen {

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


    private int basicPlantsX = 300, basicPlantsY = 450;
    private int fertilizerX = 250, fertilizerY = 350;

    Skin skin;
    public SkillTreeScreen(GardenGame app) {
        this.app = app;

        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport(camera));

        initStage();

        hudColor = new Color(1, 0, 0, 0.5f);
        shapeRenderer = new ShapeRenderer();
    }

    private void initStage() {

        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();

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

        moreFlowers = new TextButton("More Flowers",skin);
        moreFlowers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        moreFruits = new TextButton("More Fruits",skin);
        moreFruits.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        construction = new TextButton("Construction",skin);
        construction.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        communication = new TextButton("Communication",skin);
        communication.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        water = new TextButton("Water",skin);
        water.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        fertilizerPlus = new TextButton("Fertilizer++",skin);
        fertilizerPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        waterPlus = new TextButton("Water++",skin);
        waterPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        autoHarvest = new TextButton("Auto harvest",skin);
        autoHarvest.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        irrigation = new TextButton("Irrigation",skin);
        irrigation.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });


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

/*
       // table.add(title).center();
        // table.row();
        //table.add(imgEmptyArrow).right();
        //table.add(basicPlants).left();
        //table.row();
        table.add(imgEmptyArrow).right();
       // table.add(imgDownArrow).center();
        table.row();
        table.add(fertilizer).colspan(3).center();
        table.add(general).colspan(3).center();
        table.row();
        //table.add(imgLeftArrow).right();
        //table.add(imgDownArrow).center();
        //table.add(imgRightArrow).left();
        table.add(imgEmptyArrow).right();
        table.row();
        table.add(moreFruits).center();
        table.add(moreFlowers).center();
        table.add(imgEmptyArrow).right();
        table.add(construction).center();
        table.add(communication).center();
        table.add(water).center();
        table.row();
        table.add(imgEmptyArrow).center();
        table.row();
        table.add(fertilizerPlus).colspan(2).center();
        table.add(imgEmptyArrow).center();
        table.add(imgEmptyArrow).center();
        table.add(imgEmptyArrow).center();
        table.add(waterPlus).right();
        table.row();
        table.add(imgEmptyArrow).center();
        table.row();
        table.add(imgEmptyArrow).center();
        table.add(imgEmptyArrow).center();
        table.add(imgEmptyArrow).center();
        table.add(imgEmptyArrow).center();
        table.add(imgEmptyArrow).center();
        table.add(irrigation).center();
        table.row();
        table.add(imgEmptyArrow).center();
        table.row();
        table.add(imgEmptyArrow).center();
        table.add(imgEmptyArrow).center();
        table.add(autoHarvest).center();

*/



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
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(basicPlantsX + 55, basicPlantsY, fertilizerX + 50, fertilizerY+24);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(hudColor); // Red line
        shapeRenderer.line(basicPlantsX + 55, basicPlantsY, 400, 400);
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
