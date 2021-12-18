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
import com.garden.game.Skills.Skill;
import com.garden.game.Skills.SkillTree;
import com.garden.game.tools.Constants;
import com.garden.game.world.World;
import com.sun.tools.javac.util.StringUtils;

import javax.swing.*;
import java.util.HashMap;


public class SkillTreeScreen implements Screen {

    public World world;
    private GardenGame app;
    private Stage stage;
    private Table table;
    public String currentLearning = "", strLearned = "";

    private Label curentL, learnedSkills;
    private Texture textureRightArrow, textureLeftArrow, textureDownArrow, textureEmptyArrow;
    private Image imgRightArrow, imgLeftArrow, imgDownArrow, imgEmptyArrow;
    public static TextButton basicPlants, fertilizer, fertilizerPlus, general, moreFruits, moreFlowers,
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
        curentL = new Label("", skin);
        curentL.setColor(0,1,1,1);
        curentL.setPosition(700, 100);
        stage.addActor(curentL);

        learnedSkills = new Label(strLearned, skin);
        learnedSkills.setPosition(25, 700);
        learnedSkills.setColor(0.184f, 0.505f, 0.211f,1);
        stage.addActor(learnedSkills);

    }

    private void initStage() {

        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();

        world = app.gameScreen.world;
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

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


        basicPlants = new TextButton("Basic Plants",skin);

        basicPlants.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                skillTree.setCurrentlyLearning(Constants.BASIC_PLANTS);
                //basicPlants.setColor(0.184f, 0.505f, 0.211f,1);
                //basicPlants.setTouchable(Touchable.disabled);
                manageButtons();



            }
        });
        //basicPlants.setVisible(false);
        basicPlants.setPosition(basicPlantsX, basicPlantsY);
        stage.addActor(basicPlants);


        fertilizer = new TextButton("Fertilizer",skin);
        //fertilizer.setColor(0,0,0,0.5f);
        fertilizer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.FERTILIZER_1);
                //moreFlowers.setTouchable(Touchable.disabled);
                //moreFruits.setTouchable(Touchable.enabled);
                manageButtons();

            }
        });
        fertilizer.setPosition(fertilizerX, fertilizerY);
        stage.addActor(fertilizer);

        general = new TextButton("General",skin);

        general.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.GENERAL);
                //general.setTouchable(Touchable.disabled);
                //construction.setTouchable(Touchable.enabled);
                //water.setTouchable(Touchable.enabled);
               // general.setColor(0.184f, 0.505f, 0.211f,1);
                manageButtons();

            }
        });
        general.setPosition(generalX, generalY);
        stage.addActor(general);

        moreFlowers = new TextButton("More Flowers",skin);
        moreFlowers.setColor(0,0,0,0.5f);
        moreFlowers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               // moreFruits.setTouchable(Touchable.disabled);
                skillTree.setCurrentlyLearning(Constants.MORE_FLOWERS);
                manageButtons();

            }
        });
        moreFlowers.setPosition(moreFlowersX, moreFlowersY);
        stage.addActor(moreFlowers);

        moreFruits = new TextButton("More Fruits",skin);
        //moreFruits.setColor(0,0,0,0.5f);
        moreFruits.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.MORE_FRUITS);
                manageButtons();
                //fertilizerPlus.setTouchable(Touchable.enabled);

            }
        });
        moreFruits.setPosition(moreFruitsX, moreFruitsY);
        stage.addActor(moreFruits);

        construction = new TextButton("Construction",skin);
        //construction.setColor(0,0,0,0.5f);
        construction.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.CONSTRUCTION);
                manageButtons();

            }
        });
        construction.setPosition(constructionX, constructionY);
        stage.addActor(construction);

        communication = new TextButton("Communication",skin);
        //communication.setColor(0,0,0,0.5f);
        communication.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.COMMUNICATION);
                manageButtons();
            }
        });
        communication.setPosition(communicationX, communicationY);
        stage.addActor(communication);

        water = new TextButton("Water",skin);
       // water.setColor(0,0,0,0.5f);
        water.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.WATER_1);
                //waterPlus.setTouchable(Touchable.enabled);
                //world.player.skillTree.skills.get(8).skillLearned();
                //water.setTouchable(Touchable.disabled);
                manageButtons();

            }
        });
        water.setPosition(waterX, waterY);
        stage.addActor(water);

        fertilizerPlus = new TextButton("Fertilizer++",skin);
        //fertilizerPlus.setColor(0,0,0,0.5f);
        fertilizerPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.FERTILIZER_2);
                manageButtons();

            }
        });

        fertilizerPlus.setPosition(fertilizerPlusX, fertilizerPlusY);
        stage.addActor(fertilizerPlus);

        waterPlus = new TextButton("Water++",skin);
        //waterPlus.setColor(0,0,0,0.5f);
        waterPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.WATER_2);
                //irrigation.setTouchable(Touchable.enabled);
                manageButtons();

            }
        });
        waterPlus.setPosition(waterPlusX, waterPlusY);
        stage.addActor(waterPlus);

        autoHarvest = new TextButton("Auto harvest",skin);
        //autoHarvest.setColor(0,0,0,0.5f);
        autoHarvest.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.AUTO_HARVEST);
                manageButtons();

            }
        });
        autoHarvest.setPosition(autoHarvestX, autoHarvestY);
        stage.addActor(autoHarvest);

        irrigation = new TextButton("Irrigation",skin);
        //irrigation.setColor(0,0,0,0.5f);
        irrigation.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillTree.setCurrentlyLearning(Constants.IRRIGATION);
                manageButtons();

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


    private void resumeGame(){
        app.sound.SoundButtonClick();
        app.setScreen(app.gameScreen);
    }

    @Override
    public void show() {
        manageButtons();
        updateText();
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) resumeGame();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) resumeGame();
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) resumeGame();

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
        } else{
            skillTree.availableToLearn[Constants.AUTO_HARVEST] = false;
        }

        for(int i=0; i < 12; i++) {
            if (skillTree.availableToLearn[i]) {
                lockedMap.get(i).setTouchable(Touchable.enabled);
                lockedMap.get(i).setColor(1,1,1,1);
            } else {
                lockedMap.get(i).setColor(0,0,0,0.5f);
                lockedMap.get(i).setTouchable(Touchable.disabled);
            }
            if (skillTree.skills.get(i).learned){
                lockedMap.get(i).setColor(0.184f, 0.505f, 0.211f,1);
                lockedMap.get(i).setTouchable(Touchable.disabled);
               // skillTree.currentlyLearning = null;
            }

        }
        if(skillTree.currentlyLearning != null) {
            lockedMap.get(skillTree.index).setColor(0,1,1,0.5f);
            if(skillTree.skills.get(skillTree.index).learned){
                lockedMap.get(skillTree.index).setColor(0.184f, 0.505f, 0.211f,1);
            }
            lockedMap.get(skillTree.index).setTouchable(Touchable.disabled);
        }

        updateText();



    }

    private void updateText() {
        currentLearning = "Currently Learning: ";
        strLearned = "Learned skills: ";

        if(skillTree.currentlyLearning == null) {
            currentLearning += "None";
        } else if (skillTree.currentlyLearning.learned) {
            currentLearning += "None";
        } else {
            String localLearning = lockedMap.get(skillTree.index).toString();
            currentLearning  += localLearning.substring(localLearning.lastIndexOf(":") + 1);
        }

        curentL.setText(currentLearning);

        for(int i = 0; i < 12; i++) {
            if(skillTree.skills.get(i).learned) {
                String skill = lockedMap.get(i).toString();
                skill = skill.substring(skill.lastIndexOf(":") + 1);
                strLearned += "\n" + skill;
            }
        }
        if(strLearned.compareTo("Learned skills: ") == 0) {
            strLearned += "None";
        }
        learnedSkills.setText(strLearned);

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
