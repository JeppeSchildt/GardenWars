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
import com.garden.game.world.plants.Plant;
import com.garden.game.world.World;

import java.util.ArrayList;
import java.util.Random;


// When tile is hovered, show some menu with things you can do to that
// tile. When house is clicked show what you can do there.
// Show stats, gold etc. Provide a button for ending turn.
// This sort of stuff goes in a stage...?
public class GameScreen extends AbstractScreen {
    public World world;
    private GardenGame app;
    private Stage hud;
    public Label txtGold, txtWater, txtTurnNumber, txtTitle, txtMonthWeekDay, txtResources, txtTileInfo, txtNextTurn, txtQuests, lbl, txtGuid;
    private String nextTurnStr = "Day number ";
    private Texture textureGameBorder, textureBtnBorder, textureNextTurn, textureSettings, textureTalent, textureKeyboardControls;
    private Image imgGameBorder, imgBtnBorder, imgNextTurn, imgSettings, imgTalent, imgBlkScreen, imgKeyboardControls;
    private float blkScreenAlpha;
    private Table buttonTable, outerTable;
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
    private boolean showDialouge = false, showQuest = true, questKeyPress, showGuid = true, guidKeyPress;
    private final OrthographicCamera camera;
    private ShapeRenderer shapeRenderer,shapeRendererV2, shapeRendererQuestBox, shapeRendererGuidBox;
    private Sprite spriteHighlight;

    private int dialogStep = 0;


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
        //
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
        // 1024/2 - 75 :)
        txtNextTurn.setPosition(1024/2-75, 768/2);

        // -------- Blank Screen  hud setup -------- //
        imgBlkScreen = new Image(new TextureRegion(app.assets.<Texture>get("black_screen.png")));
        imgBlkScreen.setSize(1024,768);
        //imgBlkScreen.setColor(0,0,0,1);


        imgKeyboardControls = new Image(new TextureRegion(app.assets.<Texture>get("KeyboardControls.png")));
        imgKeyboardControls.setSize(844,284);
        imgKeyboardControls.setPosition(100, 250);



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
        String txtPoint= "Score: " + world.player.points + "/" + world.player.maxPoint;
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



    void startIntroDialogue() {
        showDialouge = true;
        dialogBackground(Dialogue.dia_3);
    }
    void changeDialog(String text) {
        lbl.setText(text);
    }
    void dialogBackground(String text) {

        boolean start = false;
        if (app.batch.isDrawing()) {
            app.batch.end();
            start = true;
        }

        int step = (int) (time*12);
        if (step > text.length()) {
            dialogStep = 0;
            showDialouge = false;
        }
        else {
            String textModified = text.substring(0,step);
            changeDialog(textModified);
        }

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
        lbl.setWidth(500); //500
        lbl.setHeight(150);
        app.batch.begin();
        lbl.draw(app.batch,10f);
        app.batch.end();
        camera.update();
        if (start) {
            app.batch.begin();
        }

        /*

        Group grp = new Group();
        grp.addActor();
        lbl.setColor(Color.RED);
        lbl.setSize(100,100);
        lbl.toFront();
        hud.addActor(lbl); */
        //lbl.draw(app.batch,1f);
        //fnt.draw(app.batch,"Hello",10,10);
    }
    void setButton (String text, Skin skin) {
    	buttonList.add(new TextButton(text,skin));
    }
    // Consider: Individual setup button methods and update scrollpane methods.
    // Can also be used when new skills are learned.
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
        //txtQuests.setText(world.player.quests.get(0).description);
        //System.out.println(world.player.quests.get(0).description);

        updateTxtQuests();
    }

    public void updateTxtQuests() {
        String strQuests = "Quest: \n";
        for(Quest q: world.player.quests) {
            strQuests += (q.description + "\n");
        }
        txtQuests.setText(strQuests);
    }

    private void renderBubble(String text) {
        dialogGlyphLayout.setText(font, text, Color.WHITE, Gdx.graphics.getWidth() * 0.75f, Align.topLeft, true);
        float tw = dialogGlyphLayout.width;
        float th = dialogGlyphLayout.height;

        // 0.05 for 5% margin, dw and dh are the width of the bubble
        // bo the bubble is the size of the text, plus a 5% margin (of the window size)
        float dw = tw + 0.05f * Gdx.graphics.getWidth();
        float dh = th + 0.05f * Gdx.graphics.getHeight();

        // bx, bh is the position of the buggle
        float bx = world.player.unit.getX();//(Gdx.graphics.getWidth() - dw) / 2.0f;
        float by = world.player.unit.getY();//Gdx.graphics.getHeight() - dh * 1.2f;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        app.batch.enableBlending();
        app.batch.begin();
        app.batch.setProjectionMatrix(camera.combined);
        np.draw(app.batch, 100, 100, 100, 100);
        //font.draw(app.batch,  dialogGlyphLayout, bx + (dw-tw) / 2.0f, by + dh - (dh-th) / 2.0f);
        System.out.println("In here yada");
        Color c = app.batch.getColor();
        app.batch.setColor(c.r,c.g,c.b,1f);
        Image image = new Image(np);
        image.setWidth(140);
        Container<Image> container = new Container<Image>(image);
        container.fill();
        container.setSize(140,69);
        container.setOrigin(bx,by);

        Label lbl = new Label("Random TEXT",skin);
        lbl.setOrigin(container.getX()+100,container.getY());
        lbl.setAlignment(Align.center);
        lbl.setColor(Color.RED);
        lbl.setSize(100,100);
        grp.addActor(container);
        grp.addActor(lbl);
        grp.setPosition(bx,by);
        hud.addActor(grp);
        app.batch.end();
    }

    public void magazineEvent() {
        /*
        NinePatch np = new NinePatch(new Texture(Gdx.files.internal("inGameDesign/ButtonNextTurn.png")),10,10,10,10);
        np.draw(app.batch,100,100,100,100);
        spriteHighlight.draw(getBatch()); */

        int textBoxBuffer = 5;
        int textBoxWidth = 80;
        //GlyphLayout layout = new GlyphLayout(font,"hej",200,200,textBoxWidth, Align.left,true)
        //batchTest = new SpriteBatch();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        font = skin.get("default-font", BitmapFont.class);
        //Texture bubble = new Texture(Gdx.files.internal("inGameDesign/ButtonNextTurn.png"));
        Texture bubble = new Texture(Gdx.files.internal("speech_bubble_v3.png"));
        np = new NinePatch(new TextureRegion(bubble,0,0,bubble.getWidth(),bubble.getHeight()),5,5,5,5);
        renderBubble("TEST");
    }
    public void droughtEvent() {

    }

    public void startEvent(String event) {
        switch(event) {
            case "magazine":
                magazineEvent();
                System.out.println("Magazine visit");
                break;
            case "drought":
                droughtEvent();
                System.out.println("Drought event");
                break;
        }
    }

    // https://stackoverflow.com/questions/14700577/drawing-transparent-shaperenderer-in-libgdx
    public void drawMenu(){
        //app.batch.draw(inGameBorder,0,0);
        /*Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        int rect_x = Gdx.graphics.getWidth();
        int rect_y = Gdx.graphics.getHeight() - 100;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(hudColor);
        //shapeRenderer.rect(0, rect_y, Gdx.graphics.getWidth(), rect_x);
        shapeRenderer.rect(0,0, Gdx.graphics.getWidth(), 100);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);*/
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



        if (showDialouge) {
            startIntroDialogue(); //here

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

    private void moveToPorch() {
        world.player.unit.clearActions();
        world.player.unit.setDirec(Constants.DOWN);
        world.player.unit.activeAnimation = world.player.unit.stopAnimations.get(Constants.DOWN);
        world.player.unit.setX(Constants.FRONT_PORCH_X);
        world.player.unit.setY(Constants.FRONT_PORCH_Y);
    }

    private void nextTurnInfo() {
        if(nextTurnClicked) {
            showDialouge = false;
            if(blkScreenAlpha >= 2) {
                nextTurnClicked = false;
                imgBlkScreen.remove();
                txtNextTurn.remove();
                blkScreenAlpha = 0.0f;

                // Move character to front porch instantly.
                moveToPorch();
                app.sound.SoundNewDay();
            }
            if(blkScreenAlpha >= 0.5f) {
                nextTurnStr = "Day " + world.turnNumber;
                txtNextTurn.setText(nextTurnStr);
                hud.addActor(txtNextTurn);
            }
        }
        blkScreenAlpha += 0.015f;
        imgBlkScreen.setColor(0,0, 0, blkScreenAlpha);
    }



    private void checkInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { pauseScreen(); }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            nextTurn();
            System.out.println("Key ENTER pressed");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            System.out.println("Key SPACE pressed");
            showDialouge = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE))
        {
            if (!app.debugMode){
                app.debugMode = true;
            }
            else{
                app.debugMode = false;
            }
            System.out.println("Key BACKSPACE press");
            debugButtons();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            System.out.println("Key Q pressed");

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


        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            System.out.println("Key I pressed");

            if (!showGuid){
                showGuid = true;
                guidKeyPress = false;
                hud.addActor(imgKeyboardControls);



            }
            else{
                showGuid = false;
                imgKeyboardControls.remove();

                txtGuid.remove();
                guidKeyPress = true;
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            System.out.println("Key X pressed");

            if (!showDialouge){
                showDialouge = true;
                dialogBackground(Dialogue.dia_1);
            }
            else{
                showDialouge = false;
            }
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


        txtGuid.setText("Beginner’s Guide: \t'I' hide\n\n" +
                "Lorem Ipsum is simply dummy \ntext of the printing and \n typesetting industry. Lorem \nIpsum has been the industry's \nstandard dummy text ever since \nthe 1500s, when an unknown \nprinter took a galley of type and \nscrambled it t");

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

        Table DebugTable = new Table();

        DebugTable.setFillParent(true);
        DebugTable.setDebug(false);
        DebugTable.setPosition(-450, 100);

        hud.addActor(DebugTable);

        TextButton debugSeasonButton = new TextButton("Test Season",skin);
        debugSeasonButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!world.drySeason)
                    world.drySeason = true;
                else world.drySeason = false;
            }
        });

        TextButton debugEvenButton = new TextButton("Test Event",skin);
        debugEvenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //app.batch.begin();
                startEvent("magazine");
                /*for(Quest q : world.player.quests) {
                    if(q.isCompleted) {
                        q.onCompleted();
                    }
                }*/
                //font.draw(app.batch,"TEST",100,100);
                //app.batch.end();
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


