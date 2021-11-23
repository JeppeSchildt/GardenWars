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
import com.garden.game.tools.PlantFactory;
import com.garden.game.tools.Constants;
import com.garden.game.world.plants.Plant;
import com.garden.game.world.World;

import java.util.ArrayList;


// When tile is hovered, show some menu with things you can do to that
// tile. When house is clicked show what you can do there.
// Show stats, gold etc. Provide a button for ending turn.
// This sort of stuff goes in a stage...?
public class GameScreen extends AbstractScreen {
    public World world;
    GardenGame app;
    Stage hud;
    public Label txtGold, txtWater, txtTurnNumber, txtTitle, txtMonthWeekDay, txtResources, txtTileInfo;
    private Texture textureGameBorder, textureBtnBorder, textureNextTurn, textureSettings, textureTalent;
    private Image imgGameBorder, imgBtnBorder, imgNextTurn, imgSettings, imgTalent;
    Table buttonTable,outerTable;
    SpriteBatch batchTest;
    private GlyphLayout dialogGlyphLayout = new GlyphLayout();
    ScrollPane scrollPane;
    Skin skin;
    private final InputMultiplexer mux;
    //private final Color hudColor;
    public Group grp;
    public NinePatch np;
    public BitmapFont font = new BitmapFont();
    private Label txtSelectedTileCoordinates;
    ArrayList<TextButton> buttonList;
    PlantFactory actorFactory;
    private boolean improvementsShown;
    final OrthographicCamera camera;
    //private ShapeRenderer shapeRenderer;
    Sprite spriteHighlight;

    private Table tableResources, tableDay, tableButtons, tableTileInfo,dropOutTable;

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
        actorFactory = new PlantFactory(app.assets);

        //shapeRenderer = new ShapeRenderer();

        world.user.dkk = 200;
        world.dayCount = 1;

        spriteHighlight = app.assets.textureAtlas.createSprite("border_tile");

        initHUD();
    }

    private void initHUD() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        buttonList = new ArrayList<TextButton>();
        grp = new Group();
        textureGameBorder = new Texture(Gdx.files.internal("inGameDesign/GameBorder.png"));
        imgGameBorder = new Image(textureGameBorder);
        imgGameBorder.setPosition(0, 0);
        hud.addActor(imgGameBorder);

        textureBtnBorder = new Texture(Gdx.files.internal("inGameDesign/ButtonBorder.png"));
        imgBtnBorder = new Image(textureBtnBorder);
        imgBtnBorder.setPosition(app.maxWidth - (144 + 10), 35);
        hud.addActor(imgBtnBorder);

        tabelSetup();
        setupTextIcons();
        drawButtons();

        setupTileImprovementBox();

        if (app.debugMode){
            debugButtons();
        }

    }

    private void tabelSetup(){
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
        Plant plant = world.user.getPlantAtPosition(x*32, y*32);

        String improvement = (plant != null) ? plant.getName() + "\nWater: " + plant.getWater() + "\n"+ plant.getState().getStateName() : "Grass";
        return coordinates + improvement;
    }


    public void updateHUD() {
        //txtSelectedTileCoordinates.setText(world.hoveredX + "," + world.hoveredY);
        //txtTurnNumber.setText("Days: " + world.turnNumber);

        String longSpace = "          ";
        String txtWater = "Water: " + world.user.water + "/" + world.user.maxWater + longSpace;
        String txtGold = "Gold: " + world.user.dkk + longSpace;
        String txtPoint= "Point: " + world.user.point + "/" + world.user.maxPoint;

        txtResources.setText(txtWater + txtGold  + txtPoint);
        txtTileInfo.setText(getTileInfo(world.hoveredX, world.hoveredY));
        String totalDays = "Month: " + world.monthCount + ", " + "Week: " + world.weekCount + ", " + "Day: " + world.dayCount;
        txtMonthWeekDay.setText(totalDays);
    }

    void setButton (String text, Skin skin) {
    	buttonList.add(new TextButton(text,skin));
    }
    // Consider: Individual setup button methods and update scrollpane methods.
    // Can also be used when new skills are learned.
    void setupTileImprovementBox() {

    	for (int i=0;i<2;i++) {
            String t = "";
    		if(i == 0) {
                t = "Water tile";
            } else {
                t = "Plant something" + i;
            }
    		setButton(t,skin);
    	}
        buttonTable = new Table(skin);
        outerTable = new Table(skin);
        dropOutTable = new Table(skin);
        for(int i = 0; i < 2; i++) {
            TextButton textButton = buttonList.get(i);
            if(i == 0) {
                textButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(world.user.canWater(world.hoveredX * 32, world.hoveredY * 32)) {
                            world.user.water(world.hoveredX * 32, world.hoveredY * 32, 2);
                        }
                        dropOutTable.clearChildren();
                        dropOutTable.remove();;
                        outerTable.remove();

                    }
                });
            } else {
                textButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (world.user.canPlant(Constants.GRASS, world.hoveredX * 32, world.hoveredY * 32)) {
                            Plant plant = actorFactory.createPlant(Constants.CUCUMBER, world.hoveredX, world.hoveredY);
                            world.user.plant(world.hoveredX * 32, world.hoveredY * 32, plant);
                        }
                        dropOutTable.clearChildren();
                        dropOutTable.remove();
                        outerTable.remove();

                    }
                });
            }
            float highest = 0.0f;
            for (Button b : buttonList) {
                if (b.getWidth() > highest) {
                    highest = b.getWidth();
                }
            }
            for (Button b : buttonList) {
                b.padLeft((highest-b.getWidth())/2);
                b.padRight((highest-b.getWidth())/2);
            }
            buttonTable.add(textButton).expandX().fillY().row();
        }
        for (final TextButton txtButton : buttonList) {
            System.out.println(txtButton.getText());
            txtButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    //Create new menu next to it
                    ArrayList<TextButton> buttonListDropOut = new ArrayList<TextButton>();
                    TextButton button1 = new TextButton("Water", skin);
                    button1.setColor(Color.BLUE);
                    TextButton button2 = new TextButton("Kill", skin);
                    button2.setColor(Color.RED);
                    if (!dropOutTable.hasChildren()) {
                        dropOutTable.add(button1).expandX().fillY().row();
                        dropOutTable.add(button2).expandX().fillY().row();
                        buttonListDropOut.add(button1);
                        buttonListDropOut.add(button2);
                    }
                    dropOutTable.setSize(200, 100);
                    float highest = 0.0f;
                    for (Button b : buttonListDropOut) {
                        if (b.getWidth() > highest) {
                            highest = b.getWidth();
                        }
                    }
                    for (Button b : buttonListDropOut) {
                        b.padLeft((highest - b.getWidth()) / 2);
                        b.padRight((highest - b.getWidth()) / 2);
                    }
                    System.out.println("x:" + txtButton.getX() + "y:" + txtButton.getY());
                    dropOutTable.setPosition(txtButton.localToStageCoordinates(new Vector2(0, 0)).x + 50, txtButton.localToStageCoordinates(new Vector2(0, 0)).y - 50);
                    hud.addActor(dropOutTable);
                }
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    dropOutTable.clearChildren();
                    dropOutTable.remove();
                }
            });


        }
        buttonTable.setSize(200, 100);
        outerTable.setSize(600, 400);
        scrollPane = new ScrollPane(buttonTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        outerTable.add(scrollPane).expandY();

    }


    private void nextTurn(){
        grp.remove();
        app.sound.buttonMenueSound();

        world.nextTurn();

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
        float bx = world.user.unit.getX();//(Gdx.graphics.getWidth() - dw) / 2.0f;
        float by = world.user.unit.getY();//Gdx.graphics.getHeight() - dh * 1.2f;
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

        Label lbl = new Label("Yahooo",skin);
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
        //dropOutTable.setVisible(false);
        //dropOutTable.remove();
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        //Vector3 position = world.worldCamera.project(clickCoordinates);
        Vector3 position = camera.project(clickCoordinates);
        Vector2 test = hud.stageToScreenCoordinates(new Vector2(position.x, position.y));
        //(int) (position.x) / world.tileSize;
        if(button == Input.Buttons.RIGHT) {
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
            dropOutTable.clearChildren();
            dropOutTable.remove();
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
        checkInput(); // Does not seem ideal to check input in render method. But convenient for now...
        world.update(delta);
        world.render();
        //app.batch.begin(); //remove ?
        drawMenu();
        app.batch.setProjectionMatrix(camera.combined);
        hud.act(delta);
        hud.draw();

        camera.update();
        //renderBubble("HEJ");
        app.batch.end(); // End batch here, finishing rendering.

    }



    private void checkInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { pauseScreen(); }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            nextTurn();
            System.out.println("Key ENTER press");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE))
        {
            if (!app.debugMode){
                app.debugMode = true;
            }
            else{
                app.debugMode = false;
                /*
                app.gameScreen = new GameScreen(app);
                app.setScreen(app.gameScreen);
                app.gameScreen.world.init("World.tmx");
                 */

            }
            System.out.println("Key BACKSPACE press");
            debugButtons();

        }





    }

    private void pauseScreen(){
            app.preferencesBool = true;

            app.sound.buttonMenueSound();
            if (app.pauseScreen == null) {
                app.pauseScreen = new PauseScreen(app);
            }
            app.setScreen(app.pauseScreen);
    }

    private void skillTreeScreen(){

        if (app.SkillTreeScreen == null) {
            app.SkillTreeScreen = new SkillTreeScreen(app);
        }
        app.setScreen(app.SkillTreeScreen);
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
                if (!app.drySeason)
                    app.drySeason = true;
                else app.drySeason = false;
            }
        });

        TextButton debugEvenButton = new TextButton("Test Event",skin);
        debugEvenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //app.batch.begin();
                startEvent("magazine");
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


