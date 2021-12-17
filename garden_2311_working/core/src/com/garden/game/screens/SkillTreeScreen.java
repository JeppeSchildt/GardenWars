package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.Skills.SkillTree;
import com.garden.game.tools.Constants;
import com.garden.game.world.World;

import javax.swing.*;
import java.util.HashMap;


public class SkillTreeScreen implements Screen {

    public World world;
    private GardenGame app;
    private Stage stage;
    private Table table;
    private Texture textureRightArrow, textureLeftArrow, textureDownArrow, textureEmptyArrow;
    private Image imgRightArrow, imgLeftArrow, imgDownArrow, imgEmptyArrow;
    private TextButton basicPlants, fertilizer, fertilizerPlus, general, moreFruits, moreFlowers,
            construction, communication, water, waterPlus, irrigation, autoHarvest,
            settingsButton, quitButton, playButton, backButton;

    private HashMap<Integer, TextButton> lockedMap;

    private SkillTree skillTree;

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
    public SkillTreeScreen(GardenGame app, SkillTree skillTree) {
        this.app = app;
        this.skillTree = app.gameScreen.world.player.skillTree;
        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport(camera));

        initStage();

        hudColor = Color.BLACK;
        shapeRenderer = new ShapeRenderer();
        //lockedMap = new HashMap<>();


    }

    private void initStage() {


        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();

        world = app.gameScreen.world;
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("uiskin.json"));


        Label title = new Label("Skill Tree", app.assets.largeTextStyle);
        title.setFontScale(5);

        /*

        //ImageButton playButton = new ImageButton(app.assets.goldIcon);
        playButton = new TextButton("Resume",skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resumeGame();
            }
        });

         */



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

                skillTree.setCurrentlyLearning(Constants.BASIC_PLANTS);
                basicPlants.setColor(0,0,0,1);

            }
        });
        //basicPlants.setVisible(false);





        basicPlants.setPosition(basicPlantsX, basicPlantsY);
        stage.addActor(basicPlants);

        fertilizer = new TextButton("Fertilizer",skin);
        fertilizer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.FERTILIZER_1);
                //moreFlowers.setTouchable(Touchable.enabled);
                //moreFruits.setTouchable(Touchable.enabled);
                fertilizer.setColor(0,0,0,1);

            }
        });
        fertilizer.setPosition(fertilizerX, fertilizerY);
        stage.addActor(fertilizer);

        general = new TextButton("General",skin);
        general.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.GENERAL);
                //communication.setTouchable(Touchable.enabled);
                //construction.setTouchable(Touchable.enabled);
                //water.setTouchable(Touchable.enabled);
                general.setColor(0,0,0,1);
            }
        });
        general.setPosition(generalX, generalY);
        stage.addActor(general);

        moreFlowers = new TextButton("More Flowers",skin);
        moreFlowers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //fertilizerPlus.setTouchable(Touchable.enabled);
                skillTree.setCurrentlyLearning(Constants.MORE_FLOWERS);
                moreFlowers.setColor(0,0,0,1);
            }
        });
        moreFlowers.setPosition(moreFlowersX, moreFlowersY);
        stage.addActor(moreFlowers);

        moreFruits = new TextButton("More Fruits",skin);
        moreFruits.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.MORE_FRUITS);
                //fertilizerPlus.setTouchable(Touchable.enabled);
                moreFlowers.setColor(0,0,0,1);
            }
        });
        moreFruits.setPosition(moreFruitsX, moreFruitsY);
        stage.addActor(moreFruits);

        construction = new TextButton("Construction",skin);
        construction.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.CONSTRUCTION);
                construction.setColor(0,0,0,1);
            }
        });
        construction.setPosition(constructionX, constructionY);
        stage.addActor(construction);

        communication = new TextButton("Communication",skin);
        communication.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.COMMUNICATION);
                communication.setColor(0,0,0,1);
            }
        });
        communication.setPosition(communicationX, communicationY);
        stage.addActor(communication);

        water = new TextButton("Water",skin);
        water.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.WATER_1);
                //waterPlus.setTouchable(Touchable.enabled);
                //world.player.skillTree.skills.get(8).skillLearned();
                //water.setTouchable(Touchable.disabled);
                water.setColor(0,0,0,1);
            }
        });
        water.setPosition(waterX, waterY);
        stage.addActor(water);

        fertilizerPlus = new TextButton("Fertilizer++",skin);
        fertilizerPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.FERTILIZER_2);
                fertilizerPlus.setColor(0,0,0,1);

            }
        });

        fertilizerPlus.setPosition(fertilizerPlusX, fertilizerPlusY);
        stage.addActor(fertilizerPlus);

        waterPlus = new TextButton("Water++",skin);
        waterPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.WATER_2);
                //irrigation.setTouchable(Touchable.enabled);
                waterPlus.setColor(0,0,0,1);
            }
        });
        waterPlus.setPosition(waterPlusX, waterPlusY);
        stage.addActor(waterPlus);

        autoHarvest = new TextButton("Auto harvest",skin);
        autoHarvest.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.AUTO_HARVEST);
                autoHarvest.setColor(0,0,0,1);
            }
        });
        autoHarvest.setPosition(autoHarvestX, autoHarvestY);
        stage.addActor(autoHarvest);

        irrigation = new TextButton("Irrigation",skin);
        irrigation.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.IRRIGATION);
                irrigation.setColor(0,0,0,1);
            }
        });
        irrigation.setPosition(irrigationX, irrigationY);
        stage.addActor(irrigation);



        backButton = new TextButton("Back",skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToGame();
            }
        });

        backButton.setPosition(autoHarvestX, autoHarvestY - 100);
        stage.addActor(backButton);

        lockedMap = new HashMap<>();
        lockedMap.put(Constants.BASIC_PLANTS, basicPlants);
        lockedMap.put(Constants.FERTILIZER_1, fertilizer);
        lockedMap.put(Constants.MORE_FLOWERS, moreFlowers);
        lockedMap.put(Constants.MORE_FRUITS, moreFruits);
        lockedMap.put(Constants.FERTILIZER_2, fertilizerPlus);
        lockedMap.put(Constants.GENERAL, general);
        lockedMap.put(Constants.CONSTRUCTION, construction);
        lockedMap.put(Constants.COMMUNICATION, communication);
        lockedMap.put(Constants.WATER_1, water);
        lockedMap.put(Constants.WATER_2, waterPlus);
        lockedMap.put(Constants.IRRIGATION, irrigation);
        lockedMap.put(Constants.AUTO_HARVEST, autoHarvest);


        /*fertilizer.setTouchable(Touchable.disabled);
        moreFlowers.setTouchable(Touchable.disabled);
        moreFruits.setTouchable(Touchable.disabled);
        fertilizerPlus.setTouchable(Touchable.disabled);

        construction.setTouchable(Touchable.disabled);
        communication.setTouchable(Touchable.disabled);
        water.setTouchable(Touchable.disabled);
        waterPlus.setTouchable(Touchable.disabled);
        irrigation.setTouchable(Touchable.disabled);
        autoHarvest.setTouchable(Touchable.disabled);*/






        quitButton = new TextButton("Back to menu",skin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMenue();
            }
        });

    }

    private void backToGame() {
        app.setScreen(app.gameScreen);
    }

    /*
        private void resumeGame(){
            app.sound.buttonMenueSound();
            app.setScreen(app.gameScreen);
        }

     */
    private void backToMenue(){
        //Gdx.app.exit();
        app.sound.SoundButtonClick();
        app.preferencesBool = false;
        app.currentGameBool = true;

        app.sound.Chance_Music();
        app.setScreen(app.titleScreen);
    }





    private void preferences(){
        app.sound.SoundButtonClick();
        if(app.preferencesScreen == null) {
            app.preferencesScreen = new PreferencesScreen(app);
        }
        app.setScreen(app.preferencesScreen);
    }

    @Override
    public void show() {
        manageButtons();
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) backToMenue();
        //if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) resumeGame();

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

    public void manageButtons() {

        if(     skillTree.skills.get(Constants.FERTILIZER_2).learned &&
                skillTree.skills.get(Constants.CONSTRUCTION).learned &&
                skillTree.skills.get(Constants.IRRIGATION).learned ) {
            skillTree.availableToLearn[Constants.AUTO_HARVEST] = true;
        }

        for(int i=0; i < 12; i++) {
            if (skillTree.availableToLearn[i]) {
                lockedMap.get(i).setTouchable(Touchable.enabled);
            } else {
                lockedMap.get(i).setTouchable(Touchable.disabled);
            }
        }


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
