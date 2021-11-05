package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.tools.PlantFactory;
import com.garden.game.tools.Constants;
import com.garden.game.world.Plant;
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
    public Label txtGold, txtWater, txtTurnNumber, txtTitle, txtMonthWeekDay, txtResources;
    Table buttonTable,outerTable;
    ScrollPane scrollPane;
    Skin skin;
    private final InputMultiplexer mux;
    int maxWidth, maxHeight;
    private final Color hudColor;
    private Label txtSelectedTileCoordinates;
    ArrayList<TextButton> buttonList;
    PlantFactory actorFactory;
    private boolean improvementsShown;
    final OrthographicCamera camera;

    private Table tableResources, table;

    public GameScreen(GardenGame app) {
        this.app = app;
        world = new World(app);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        hud = new Stage(new ScreenViewport(camera));
        hudColor = new Color(1, 1, 1, 0.5f);
        mux = new InputMultiplexer();

        initHUD();

        maxWidth = world.tileSize*world.worldWidth;
        maxHeight = Gdx.graphics.getHeight()-100;
        actorFactory = new PlantFactory(app.assets);

        world.user.dkk = 200;
        world.dayCount = 1;
    }

    private void initHUD() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        buttonList = new ArrayList<TextButton>();	
        setUpIcon();

        setupTileImprovementBox();
    }


    /* Size of entire window has been fixed, so we can setup UI using constant values */
    private void setUpIcon() {
        // Create a table that fills the screen. Everything else will go inside this table.
        tableResources = new Table();
        tableResources.setFillParent(true);
        tableResources.setDebug(false);
        tableResources.setPosition(-420, -365);

        hud.addActor(tableResources);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        table.setPosition(400, -355);

        hud.addActor(table);

        tableSetup();


        txtTitle = new Label("GardenGame", skin);
        txtTitle.setPosition(10,  Gdx.graphics.getHeight() - 40);
        hud.addActor(txtTitle);

        /*
        txtTurnNumber = new Label("Day: " + world.turnNumber, skin);
        txtTurnNumber.setPosition(Gdx.graphics.getWidth() - 85,  Gdx.graphics.getHeight() - 40);
        hud.addActor(txtTurnNumber);
         */

        // Show coordinates of selected tile.
        txtSelectedTileCoordinates = new Label("", skin);
        txtSelectedTileCoordinates.setPosition(Gdx.graphics.getWidth() - 55,  Gdx.graphics.getHeight() - 40);
        hud.addActor(txtSelectedTileCoordinates);
    }

    private void tableSetup(){

        // ----- NextTurn Icon Setup----- //
        TextButton btnEndTurn = new TextButton("Next Day", skin);
        btnEndTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextTurn();
            }
        });

        txtMonthWeekDay = new Label("", skin);

        table.add(btnEndTurn).center();
        table.row();
        table.add(txtMonthWeekDay);


        txtResources = new Label("", skin);
        tableResources.add(txtResources);
    }


    public void updateHUD() {
        txtSelectedTileCoordinates.setText(world.hoveredX + "," + world.hoveredY);
        //txtTurnNumber.setText("Days: " + world.turnNumber);

        String longSpace = "          ";
        txtResources.setText("Water: 0" + longSpace + "Gold: " + world.user.dkk);

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

        /*for(final TextButton textButton : buttonList){
            System.out.println(textButton.getText());
            textButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(world.user.canBuy(Constants.GRASS)) {
                        Plant plant = actorFactory.createPlant(Constants.GRASS, world.hoveredX, world.hoveredY);
                        world.user.plant(world.hoveredX*32, world.hoveredY*32, plant);
                        //world.user.unit.setPosition(world.hoveredX*32, world.hoveredY*32);
                        //world.improvementLayer.setCell(world.hoveredX, world.hoveredY, plant.getCell());
                    }

                    outerTable.remove();

                }
            });
            buttonTable.add(textButton).expandX().fillY().row();
        }*/
        for(int i = 0; i < 2; i++) {
            TextButton textButton = buttonList.get(i);
            if(i == 0) {
                textButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(world.user.canWater(world.hoveredX * 32, world.hoveredY * 32)) {
                            world.user.water(world.hoveredX * 32, world.hoveredY * 32, 2);
                            outerTable.remove();
                        }

                    }
                });
            } else {
                textButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (world.user.canPlant(Constants.GRASS, world.hoveredX * 32, world.hoveredY * 32)) {
                            Plant plant = actorFactory.createPlant(Constants.GRASS, world.hoveredX, world.hoveredY);
                            world.user.plant(world.hoveredX * 32, world.hoveredY * 32, plant);
                            //world.user.unit.setPosition(world.hoveredX*32, world.hoveredY*32);
                            //world.improvementLayer.setCell(world.hoveredX, world.hoveredY, plant.getCell());
                        }

                        outerTable.remove();

                    }
                });
            }
            buttonTable.add(textButton).expandX().fillY().row();

        }

        buttonTable.setSize(200, 100);
        outerTable.setSize(600, 400);
        scrollPane = new ScrollPane(buttonTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        outerTable.add(scrollPane).expandY();

    }

    private void nextTurn(){
        app.sound.buttonMenueSound();
        world.nextTurn();
        System.out.println("Clicked - Next Turn");
    }


    // https://stackoverflow.com/questions/14700577/drawing-transparent-shaperenderer-in-libgdx
    public void drawMenu(){

        // ----- Color line ----- //
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        int rect_x = Gdx.graphics.getWidth();
        int rect_y = Gdx.graphics.getHeight() - 75;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(hudColor);
        shapeRenderer.rect(0, rect_y, Gdx.graphics.getWidth(), rect_x);
        //shapeRenderer.rect(824,0, 200, Gdx.graphics.getHeight()-100);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);



        updateHUD();
        // ----- Main Color ----- Is this borders?? Commented it out to focus on a simple case //
        /*ShapeRenderer shapeRenderer2 = new ShapeRenderer();
        int rect_x2 = Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/7;
        int rect_y2 = Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/12;
        shapeRenderer2.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer2.setColor(255,184,10, 0);
        shapeRenderer2.rect(0, rect_y2, Gdx.graphics.getWidth(), rect_x2);
        shapeRenderer2.end();
        */
    }



    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = camera.unproject(clickCoordinates);
        //(int) (position.x) / world.tileSize;
        if(button == Input.Buttons.RIGHT) {
        	int posX = (int) (position.x) / world.tileSize;  // / world.tileSize;
        	int posY = (int) (position.y) / world.tileSize; // / world.tileSize;
        	double cat = world.tileSize/2;
        	double dist = Math.sqrt(Math.pow(cat, 2)*2); 
        	float goX = (float)(posX * world.tileSize + dist);
        	float goY = (float)(posY * world.tileSize + dist);
            outerTable.setPosition(goX-200,goY-300); //-200, -300 is found by trial and error
            /*
            System.out.println("Position: " + position.x + " & " + position.y);
            System.out.println("Clicked on: "+posX +" & " + posY);
            System.out.println("Placing box: " + goX + " & " + goY);
            */
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

    // Render player things like character and plants in this method.
    // Using camera here maybe.
    @Override
    public void render(float delta) {
        world.update(delta);
        world.render();
        drawMenu();
        hud.act(delta);
        hud.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            app.preferencesBool = true;

            /*
            if (app.assets.inGameMusic.isPlaying()){
                    app.assets.inGameMusic.setVolume(0.2334f);
                    app.assets.inGameMusic.setVolume(0.2334f);

                    app.assets.ambientSound_Bird.pause();
            }
             */
            app.sound.buttonMenueSound();
            app.setScreen(app.pauseScreen);
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) nextTurn();




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
}


