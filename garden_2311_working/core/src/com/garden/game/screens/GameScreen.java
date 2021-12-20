package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.player.Quest;
import com.garden.game.tools.Dialogue;
import com.garden.game.tools.PlantFactory;
import com.garden.game.tools.Constants;
import com.garden.game.world.Plant;
import com.garden.game.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// When tile is hovered, show some menu with things you can do to that
// tile. When house is clicked show what you can do there.
// Show stats, gold etc. Provide a button for ending turn.
// This sort of stuff goes in a stage...?
public class GameScreen extends AbstractScreen {
    public World world;
    private GardenGame app;
    private Stage hud;
    public Label txtGold, txtWater, txtTurnNumber, txtTitle, txtMonthWeekDay, txtResources, txtTileInfo, txtNextTurn, txtQuests, lbl, txtGuid, txtSkills;
    private String nextTurnStr = "Day number ";
    private Texture textureGameBorder, textureBtnBorder, textureNextTurn, textureSettings, textureTalent, textureKeyboardControls;
    private Image imgGameBorder, imgBtnBorder, imgNextTurn, imgSettings, imgTalent, imgBlkScreen;
    private float blkScreenAlpha;
    private Table buttonTable, outerTable, DebugTable;
    private SpriteBatch batchTest;
    private GlyphLayout dialogGlyphLayout = new GlyphLayout();
    private ScrollPane scrollPane;
    private Skin skin;
    private float time = 0;
    private final InputMultiplexer mux;
    //private final Color hudColor;
    public Group grp;
    public NinePatch np;
    public BitmapFont font = new BitmapFont();
    private Label txtSelectedTileCoordinates;
    private ArrayList<TextButton> buttonList;
    private PlantFactory plantFactory;
    private boolean improvementsShown;
    private boolean showDialog = false, showQuest = true, questKeyPress, showGuid = true, guidKeyPress, justLearned;
    private final OrthographicCamera camera;
    private ShapeRenderer shapeRenderer,shapeRendererV2, shapeRendererQuestBox, shapeRendererGuidBox;
    private Sprite spriteHighlight;
    private String currentDialog = "";
    private int dialogStep = 0, dialogIndex = 0;
    private List<String> currentDialogList;


    private Table tableResources, tableDay, tableButtons, tableTileInfo, dropOutTable;

    private boolean nextTurnClicked;

    public GameScreen(GardenGame app) {
        this.app = app;
        app.currentGameBool = true;
        world = new World(app);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud = new Stage(new ScreenViewport(camera));
        mux = new InputMultiplexer();


        //hud = new Stage(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));

        //hud = new Stage(new ScreenViewport(world.worldCamera));
        //hudColor = new Color(1, 1, 1, 0.5f);

        //app.maxWidth = world.tileSize*world.worldWidth;
        //app.maxHeight = Gdx.graphics.getHeight()-100;
        plantFactory = new PlantFactory(app.assets);

        shapeRenderer = new ShapeRenderer();
        shapeRendererV2 = new ShapeRenderer();

        shapeRendererQuestBox = new ShapeRenderer();
        shapeRendererGuidBox = new ShapeRenderer();

        world.player.money = 200;
        world.dayCount = 1;

        spriteHighlight = app.assets.textureAtlas.createSprite("border_tile");

        initHUD();
        moveToPorch();
        startIntroDialogue();
    }

    private void initHUD() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        buttonList = new ArrayList<TextButton>();
        grp = new Group();

        // -------- GameBorder hud setup -------- //
        textureGameBorder = new Texture(Gdx.files.internal("inGameDesign/GameBorderNew.png"));
        imgGameBorder = new Image(textureGameBorder);
        imgGameBorder.setPosition(0, 0);
        hud.addActor(imgGameBorder);

        // -------- ButtonBorder hud setup -------- //
        textureBtnBorder = new Texture(Gdx.files.internal("inGameDesign/ButtonBorder.png"));
        imgBtnBorder = new Image(textureBtnBorder);
        imgBtnBorder.setPosition(app.maxWidth - (144 + 10), 35);
        hud.addActor(imgBtnBorder);

        buttonTable = new Table(skin);
        outerTable = new Table(skin);
        txtNextTurn = new Label("", skin);
        txtSkills = new Label("", skin);
        txtSkills.setPosition(1024/2-75, 768/2-40);
        // 1024/2 - 75 :)
        txtNextTurn.setPosition(1024/2-75, 768/2);

        // -------- Blank Screen  hud setup -------- //
        imgBlkScreen = new Image(new TextureRegion(app.assets.<Texture>get("black_screen.png")));
        imgBlkScreen.setSize(1024,768);
        //imgBlkScreen.setColor(0,0,0,1);

        txtQuests = new Label("", skin);
        txtGuid = new Label("", skin);
        lbl = new Label("",skin);

        tableSetup();
        setupTextIcons();
        drawButtons();

        setupTileImprovementBox(false);


        // ------ Debug mode -------- //
        if (app.debugMode){
            debugButtons();
        }

    }

    private void tableSetup(){
        // Create a table that fills the screen. Everything else will go inside this table.
        /*tableResources = new Table();
        tableResources.setFillParent(true);
        tableResources.setDebug(false);
        tableResources.setPosition(-320, -366);*/

        /*tableDay = new Table();
        tableDay.setFillParent(true);
        tableDay.setDebug(false);
        tableDay.setPosition(400, -366);*/

        tableButtons = new Table();
        tableButtons.setFillParent(true);
        tableButtons.setDebug(false);
        tableButtons.setPosition(430, -254);
        hud.addActor(tableButtons);

        /*tableTileInfo = new Table();
        tableTileInfo.setFillParent(true);
        tableTileInfo.setDebug(false);
        tableTileInfo.setPosition(200, -366);*/

    }

    /* Size of entire window has been fixed, so we can setup UI using constant values */
    private void setupTextIcons() {

        //hud.addActor(tableResources);
        txtResources = new Label("", skin);
        txtResources.setPosition(30,16);
        hud.addActor(txtResources);
        //tableResources.add(txtResources);

        //hud.addActor(tableDay);
        txtMonthWeekDay = new Label("", skin);
        txtMonthWeekDay.setPosition(824,16);
        hud.addActor(txtMonthWeekDay);
        //tableDay.add(txtMonthWeekDay);

        //hud.addActor(tableTileInfo);
        txtTileInfo = new Label("", skin);
        txtTileInfo.setPosition(30, 700);
        hud.addActor(txtTileInfo);
        //tableTileInfo.add(txtTileInfo);

    }

    private void drawButtons(){
        // ----- NextTurn Icon Setup----- //
        //TextButton btnEndTurn = new TextButton("Next Day", skin);
        textureNextTurn = new Texture(Gdx.files.internal("inGameDesign/ButtonNextTurn.png"));
        imgNextTurn = new Image(textureNextTurn);
        imgNextTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextTurn();
            }
        });
        imgNextTurn.setPosition(app.maxWidth-140, 45);
        hud.addActor(imgNextTurn);
        // ----- Settings Icon Setup----- //
        textureSettings = new Texture(Gdx.files.internal("inGameDesign/ButtonSettings.png"));
        imgSettings = new Image(textureSettings);
        imgSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseScreen();
            }
        });
        // ----- Talent Icon Setup----- //
        textureTalent  = new Texture(Gdx.files.internal("inGameDesign/ButtonTalent.png"));
        imgTalent = new Image(textureTalent);
        imgTalent.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                skillTreeScreen();
            }
        });

        hud.addActor(tableButtons);

        tableButtons.add(imgTalent);
        tableButtons.add(imgSettings);

    }

    // Utility method, get info about hovered tile.
    public String getTileInfo(int x, int y) {
        String coordinates = "[" + x + "," + y + "]\n";

        float plantX = (float) (world.hoveredX + Constants.PLANT_OFFSET_X) * Constants.TILE_WIDTH;
        float plantY = (float) (world.hoveredY + Constants.PLANT_OFFSET_Y) * Constants.TILE_HEIGHT;

        Plant plant = world.player.getPlantAtPosition(plantX, plantY);

        String improvement = (plant != null) ? plant.getName() + "\nWater: " + plant.getWater() + "\n"+ plant.getState().getStateName() : "Grass";
        return coordinates + improvement;
    }


    public void updateHUD() {
        //txtSelectedTileCoordinates.setText(world.hoveredX + "," + world.hoveredY);
        //txtTurnNumber.setText("Days: " + world.turnNumber);
        String longSpace = "          ";
        String txtWater = "Water: " + world.player.water + "/" + world.player.maxWater + longSpace;
        String txtGold = "Gold: " + world.player.money + longSpace;
        String txtPoint= "Score: " + String.format("%.2f", world.player.points)  + "/" + world.player.maxPoint;
        // Chance season string if it is dry season or not
        String txtSeason = "";
        if (world.drySeason)
            txtSeason = longSpace + longSpace + "Days To Next Wet Season: " + (world.WetSeasonCount_RandomNumber - world.lengthForDrySeason + 1);
        else if (!world.drySeason){
            if ((world.DrySeasonCount_RandomNumber - world.lengthForWetSeason) <= 2)
                txtSeason = longSpace + longSpace + "Days To Next Dry Season: " + (world.DrySeasonCount_RandomNumber - world.lengthForWetSeason + 1);
            else
                txtSeason = longSpace + longSpace + "Days To Next Dry Season: " + ("?");
        }
        txtResources.setText(txtWater + txtGold  + txtPoint + txtSeason);
        txtTileInfo.setText(getTileInfo(world.hoveredX, world.hoveredY));
        String totalDays = "Month: " + world.monthCount + ", " + "Week: " + world.weekCount + ", " + "Day: " + world.dayCount;
        txtMonthWeekDay.setText(totalDays);
    }

    void questSetup(){
        // ------ QuestBox draw --------- //
        Gdx.gl.glEnable(GL20.GL_BLEND); //Enable blending
        shapeRendererQuestBox.begin(ShapeRenderer.ShapeType.Filled);
        shapeRendererQuestBox.rect(20,40,505,100);
        shapeRendererQuestBox.setColor(0,0,0,0.2f);
        shapeRendererQuestBox.setProjectionMatrix(camera.combined);
        shapeRendererQuestBox.end();
        // -------- Quests hud setup -------- //
        txtQuests.setPosition(27, 80);
        updateTxtQuests();
        hud.addActor(txtQuests);
    }

    void updateDialog(String text) {
        currentDialog = text; //Updates the dialouge
        time = 0; //Resets timer
    }
    void startIntroDialogue() {
        showDialog = true;
        world.introBoss();

        currentDialogList = Dialogue.DIALOG_INTRO;
        updateDialog(currentDialogList.get(dialogIndex));
        dialogIndex++;
        dialogBackground();

    }
    void changeDialog(String text) {
        lbl.setText(text);
    }
    void dialogBackground() {
        String text = currentDialogList.get(dialogIndex);
        boolean start = false;
        if (app.batch.isDrawing()) {
            app.batch.end();
            start = true;
        }

        int step = (int) (time*12);//Dialogue.readingSpeed);
        if (step > text.length()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            //    dialogStep = 0;
            //    showDialouge = false;
            }
            //dialogStep = 0;
            //showDialouge = false;
        }
        else {
            String textModified = text.substring(0,step);
            changeDialog(textModified);
        }
        Label spaceToSkip = new Label("Press {space} to skip",skin);

        Gdx.gl.glEnable(GL20.GL_BLEND); //Enable blending
        shapeRendererV2.begin(ShapeRenderer.ShapeType.Filled);
        shapeRendererV2.rect(235,35,510,160);
        shapeRendererV2.setColor(1,1,1,0.70f);
        shapeRendererV2.setProjectionMatrix(camera.combined);
        shapeRendererV2.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(240,40,500,150);
        shapeRenderer.setColor(45/255f,45/255f,45/255f,0.70f); //Colors are between 0 and 1.. wtf
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.end();
        lbl.setWrap(true);
        lbl.setPosition(240,85);
        spaceToSkip.setPosition(580,45);
       //spaceToSkip.setColor(210/255f,229/255f,216/255f,0.80f); //green-gray ish
        spaceToSkip.setColor(241/255f,241/255f,208/255f,0.60f); //yellow ish
       //spaceToSkip.setColor(205/255f,253/255f,254/255f,0.90f); //blue ish
        lbl.setWidth(500); //500
        lbl.setHeight(150);
        app.batch.begin();
        lbl.draw(app.batch,10f);
        spaceToSkip.draw(app.batch,10f);
        app.batch.end();
        camera.update();
        if (start) {
            app.batch.begin();
        }

    }

    void setupTileImprovementBox(boolean canHarvest) {
        buttonTable = new Table(skin);
        outerTable = new Table(skin);
        buttonList.clear();
        if(canHarvest) {
            addHarvestButton();
        }
        TextButton waterTile = new TextButton("Water Tile", skin);

        waterTile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float plantX = (float) (world.hoveredX + Constants.PLANT_OFFSET_X) * Constants.TILE_WIDTH;
                float plantY = (float) (world.hoveredY + Constants.PLANT_OFFSET_Y) * Constants.TILE_HEIGHT;
                if(world.player.canWater(plantX, plantY)) {
                    //world.player.unit.setPosition(plantX, plantY);
                    world.player.water(plantX, plantY);
                    //world.player.unit.gotoAndWater(plantX, plantY, );
                }
                outerTable.remove();
            }
        });
        buttonList.add(waterTile);

        TextButton getWater = new TextButton("Get Water From Lake", skin);
        getWater.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.player.getMoreWater();
                outerTable.remove();
            }
        });

        if (!world.drySeason)
            buttonList.add(getWater);


        for(final int i : world.player.getAvailablePlants()) {
            TextButton b = new TextButton("Plant: " + Constants.idNameMap.get(i) + " " + Constants.idPriceMap.get(i) + ",-", skin);
           b.addListener(new ClickListener() {
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   float plantX = (float) (world.hoveredX + Constants.PLANT_OFFSET_X) * Constants.TILE_WIDTH;
                   float plantY = (float) (world.hoveredY + Constants.PLANT_OFFSET_Y) * Constants.TILE_HEIGHT;
                   if (world.player.canPlant(i, world.hoveredX, world.hoveredY)) {
                       Plant plant = plantFactory.createPlant(i, world.hoveredX, world.hoveredY);
                       world.player.plant(plantX, plantY, plant);
                   }
                   outerTable.remove();
               }
           });
            buttonList.add(b);
        }
        ensureWidthButtonTable();
        buttonTable.setSize(200, 100);
        outerTable.setSize(600, 400);
        scrollPane = new ScrollPane(buttonTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        outerTable.add(scrollPane).expandY();
    }

    /**
     * Add harvest button to improvement box
     * */
    public void addHarvestButton() {
        TextButton harvestPlant = new TextButton("Harvest Plant", skin);
        harvestPlant.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float plantX = (float) (world.hoveredX + Constants.PLANT_OFFSET_X) * Constants.TILE_WIDTH;
                float plantY = (float) (world.hoveredY + Constants.PLANT_OFFSET_Y) * Constants.TILE_HEIGHT;
                world.player.harvest(plantX, plantY);
                outerTable.remove();
            }
        });
        buttonList.add(harvestPlant);
    }
    
    /**
     * Ensures all buttons are set to same size as the largest
     */
    private void ensureWidthButtonTable() {
        float highest = 0.00f;
        Table newT = new Table(skin);
        for (TextButton b : buttonList) {
            if (b.getWidth()>highest) {
                highest = b.getWidth();
            }
        }
        buttonTable.clearChildren();
        for (TextButton b : buttonList) {
            float diff = highest - b.getWidth();
            b.setWidth(highest);
            b.padLeft(diff/2);
            b.padRight(diff/2);
            buttonTable.add(b);
            buttonTable.row();
        }
    }

    private void nextTurn(){
        grp.remove();
        app.sound.SoundButtonClick();

        // Set up fade to black stuff
        blkScreenAlpha = 0f;
        imgBlkScreen.setColor(0,0,0,blkScreenAlpha);
        hud.addActor(imgBlkScreen);

        nextTurnClicked = true;

        // Make character go to house
        world.player.unit.setPosition(Constants.FRONT_PORCH_X, Constants.FRONT_PORCH_Y);

        world.nextTurn();

        updateTxtQuests();
        dialogIndex = 0;
    }

    public void updateTxtQuests() {
        String strQuests = "Quests: \n";
        for(Quest q: world.player.quests) {
            strQuests += (q.description + "\n");
        }
        txtQuests.setText(strQuests);
    }

    public void drawMenu(){
        updateHUD();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);

        Vector3 position = camera.project(clickCoordinates);
        Vector2 test = hud.stageToScreenCoordinates(new Vector2(position.x, position.y));

        outerTable.remove(); // Remove the right click table on new click.

        if(button == Input.Buttons.RIGHT) {
            boolean canHarvest = false;
            Plant p = world.player.getPlantAtPosition((world.hoveredX+Constants.PLANT_OFFSET_X)*Constants.TILE_WIDTH, (world.hoveredY+Constants.PLANT_OFFSET_Y)*Constants.TILE_HEIGHT);
            if(p != null) {
                if (p.getState() == Plant.PlantState.HEALTHY) {
                    canHarvest = true;
                }
            }
            setupTileImprovementBox(canHarvest);
        	int posX = (int) (position.x) / world.tileSize;  // / world.tileSize;
        	int posY = (int) (position.y) / world.tileSize; // / world.tileSize;
        	double cat = world.tileSize/2;
        	double dist = Math.sqrt(Math.pow(cat, 2)*2);
        	float goX = (float)(posX * world.tileSize + dist);
        	float goY = (float)(posY * world.tileSize + dist);
            //outerTable.setPosition(goX-200,goY-300); //-200, -300 is found by trial and error


            outerTable.setPosition(test.x-200, test.y-200); //-200, -300 is found by trial and error
            scrollPane.setScrollPercentY(0);
            hud.addActor(outerTable);
            improvementsShown = true;
        } else {
            outerTable.remove();
            improvementsShown = false;
        }
        return false;
    }


    // Scroll improvements menu when shown.
    @Override
    public boolean scrolled(float amountX, float amountY) {
        if(improvementsShown) {
            scrollPane.setScrollPercentY(amountY);
            return true;
        }
        return false;
    }

    @Override
    public void show() {
        mux.addProcessor(hud);
        mux.addProcessor(this);
        mux.addProcessor(world.mapInput);
        Gdx.input.setInputProcessor(mux);
    }

    // https://stackoverflow.com/questions/14700577/drawing-transparent-shaperenderer-in-libgdx
    // Render player things like character and plants in this method.
    // Using camera here maybe.
    @Override
    public void render(float delta) {
        time += delta;
        updateHUD();
        nextTurnInfo();
        checkInput(); // Does not seem ideal to check input in render method. But convenient for now...
        world.update(delta);
        world.render();

        //app.batch.begin(); //remove ?
        drawMenu();

        app.batch.setProjectionMatrix(camera.combined);
        hud.act(delta);
        hud.draw();

        if (showQuest)
            questSetup();
        if (showGuid){
            guidBoxSetup();
        }

        if (showDialog) {
            //startIntroDialogue(); //here
            dialogBackground();
            showQuest = false;
            txtQuests.remove();

            showGuid = false;
            txtGuid.remove();
        }
        else {
            if (!questKeyPress)
                showQuest = true;
            if (!guidKeyPress)
                showGuid = true;
        }
        camera.update();
        app.batch.end(); // End batch here, finishing rendering.

        if (!world.isStartDrySeason){
            world.isStartDrySeason = true;
            world.DrySeasonCount_RandomNumber = new Random().nextInt(Constants.MAX_WET_SEASONS_DAYS) + Constants.MIN_WET_SEASONS_DAYS;
        }
    }

    private void weekBoss(){
        currentDialogList = world.boss.currentDialog;
        showDialog = true;
        updateDialog(currentDialogList.get(dialogIndex));
        dialogBackground();
    }

    private void moveToPorch() {
        world.player.unit.clearActions();
        world.player.unit.setDirec(Constants.DOWN);
        world.player.unit.activeAnimation = world.player.unit.stopAnimations.get(Constants.DOWN);
        world.player.unit.setX(Constants.FRONT_PORCH_X);
        world.player.unit.setY(Constants.FRONT_PORCH_Y);
    }

    private void nextTurnInfo() {
        if(nextTurnClicked) {
            showDialog = false;
            if(blkScreenAlpha >= 2f) {

                nextTurnClicked = false;
                imgBlkScreen.remove();
                txtNextTurn.remove();
                txtSkills.remove();
                blkScreenAlpha = 0.0f;

                if(world.isBossEvent) {
                    weekBoss();
                }

                // Move character to front porch instantly.
                moveToPorch();
                app.sound.SoundNewDay();
            }
            if(blkScreenAlpha >= 0.5f) { // change this to longer
                nextTurnStr = "Day " + world.turnNumber;
                skillInfo();

                hud.addActor(txtSkills);

                txtNextTurn.setText(nextTurnStr);
                hud.addActor(txtNextTurn);
            }
        }
        blkScreenAlpha += 0.015f;
        imgBlkScreen.setColor(0,0, 0, blkScreenAlpha);
    }

    public void skillInfo() {
        String currentlyLearning = "";
        if(world.player.skillTree.currentlyLearning == null) {
            currentlyLearning = "You are not studying anything";
        } else {
            int turns = world.player.skillTree.currentlyLearning.turns;
            String name = world.player.skillTree.currentlyLearning.name;
            if (turns > 0) {
                currentlyLearning = "Currently studying ";
                currentlyLearning += name;
                currentlyLearning += ". Learned in " + turns + " day(s)";
            } else if (turns == 0) {
                currentlyLearning = "You have just learned ";
                currentlyLearning += name;
            } else {
                currentlyLearning = "You are not studying anything";
            }
        }

        txtSkills.setText(currentlyLearning);


    }

    private void checkInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            app.sound.SoundButtonClick();
            pauseScreen();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            app.sound.SoundButtonClick();
            if (!showQuest){
                showQuest = true;
                questKeyPress = false;
            }
            else{
                showQuest = false;
                txtQuests.remove();
                questKeyPress = true;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            app.sound.SoundButtonClick();
            world.player.getMoreWater();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            app.sound.SoundButtonClick();
            skillTreeScreen();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            app.sound.SoundButtonClick();
            if (!showGuid){
                showGuid = true;
                guidKeyPress = false;
            }
            else{
                showGuid = false;

                txtGuid.remove();
                guidKeyPress = true;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            app.sound.SoundButtonClick();
            nextTurn();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            app.sound.SoundButtonClick();
            if(app.keyboardControlsScreen == null) {
                app.keyboardControlsScreen = new KeyboardControlsScreen(app);
            }
            app.setScreen(app.keyboardControlsScreen);
        }

        if(world.isBossEvent) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                app.sound.SoundButtonClick();
                if(currentDialogList == null) {
                    return;
                }
                dialogStep = 0;
                dialogIndex++;
                if (dialogIndex >= currentDialogList.size()) {
                    showDialog = false;
                    dialogIndex = 0;
                    world.leaveBoss();
                } else {
                    updateDialog(currentDialogList.get(dialogIndex));
                    dialogBackground();
                }

            }
        }



        if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_RIGHT))
        {
            app.sound.SoundButtonClick();
            if (!app.debugMode){
                app.debugMode = true;
                debugButtons();
            }
            else{
                app.debugMode = false;
                DebugTable.remove();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.X))
        {
            showDialog = true;
            updateDialog(currentDialogList.get(0));
            dialogBackground();
        }


    }

    void guidBoxSetup(){
        // ------ QuestBox draw --------- //
        Gdx.gl.glEnable(GL20.GL_BLEND); //Enable blending
        shapeRendererGuidBox.begin(ShapeRenderer.ShapeType.Filled);
        shapeRendererGuidBox.rect(20,300,250,300);
        shapeRendererGuidBox.setColor(0,0,0,0.2f);
        shapeRendererGuidBox.setProjectionMatrix(camera.combined);
        shapeRendererGuidBox.end();
        // -------- Quests hud setup -------- //
        txtGuid.setPosition(27, 490);
        updateTxtQuests();
        hud.addActor(txtGuid);

        txtGuid.setText("Beginner’s Guide: \n\n" +
                "More info 'Press key 'K' \n" +
                "To hide me 'Press key 'I'");

    }


    private void pauseScreen(){
            app.preferencesBool = true;

            app.sound.SoundButtonClick();
            if (app.pauseScreen == null) {
                app.pauseScreen = new PauseScreen(app);
            }
            app.setScreen(app.pauseScreen);
    }

    private void skillTreeScreen(){

        app.sound.SoundButtonClick();
        if (app.skillTreeScreen == null) {
            app.skillTreeScreen = new SkillTreeScreen(app, world.player.skillTree);
        }
        app.setScreen(app.skillTreeScreen);
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

    }

    @Override
    public void dispose() {
        app.batch.dispose();
    }

    private void debugButtons(){
        DebugTable = new Table();

        DebugTable.setFillParent(true);
        DebugTable.setDebug(false);
        DebugTable.setPosition(-450, 100);

        hud.addActor(DebugTable);

        TextButton debugSeasonButton = new TextButton("Test Season",skin);
        debugSeasonButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!world.drySeason){
                    world.drySeason = true;
                }
                else {
                    world.drySeason = false;
                }
            }
        });

        TextButton debugEvenButton = new TextButton("GameOver",skin);
        debugEvenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if(app.gameOverScreen == null) {
                    app.gameOverScreen = new GameOverScreen(app, world.player.points);
                }
                app.setScreen(app.gameOverScreen);
            }
        });


        if (app.debugMode){
            System.out.println("DebugMode = " + app.debugMode + ": add");
            DebugTable.add(debugSeasonButton).left();
            DebugTable.row();
            DebugTable.add(debugEvenButton).left();
        }

        if (!app.debugMode){
            System.out.println("DebugMode = " + app.debugMode + ": removeActor");
            DebugTable.removeActor(debugSeasonButton);
            DebugTable.removeActor(debugEvenButton);

        }

    }

}


