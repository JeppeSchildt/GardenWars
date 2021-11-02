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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
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
    public Label txtGold, txtWater,  txtTurnNumber, txtSelectedTileX, txtSelectedTileY;
    Table buttonTable,outerTable;
    ScrollPane scrollPane;
    Skin skin;
    private final InputMultiplexer mux;
    int maxWidth, maxHeight;
    boolean ignore;
    private final Color hudColor;
    private Label txtSelectedTileCoordinates;
    ArrayList<TextButton> buttonList;
    PlantFactory actorFactory;
    private boolean improvementsShown;
    final OrthographicCamera camera;

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
        ignore = false;
        actorFactory = new PlantFactory(app.assets);



    }

    private void initHUD() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        buttonList = new ArrayList<TextButton>();	
        setUpIcon();
        setUpButtons();
        setupTileImprovementBox();


    }

    private void setUpButtons() {
        // ----- NextTurn Icon Setup----- //
        ImageButton btnEndTurn = new ImageButton(app.assets.nextturnIcon);
        btnEndTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.sound.buttonMenueSound();
                world.endTurn();
                System.out.println("Clicked - Next Turn");
            }
        });


        btnEndTurn.setPosition(5, Gdx.graphics.getHeight() - 100);
        txtTurnNumber = new Label("Turn" + world.turnNumber, skin);
        txtTurnNumber.setAlignment(Align.bottomLeft);
        txtTurnNumber.setPosition(44, Gdx.graphics.getHeight() - 50);

        hud.addActor(btnEndTurn);
        hud.addActor(txtTurnNumber);
    }


    /* Size of entire window has been fixed, so we can setup UI using constant values */
    private void setUpIcon() {
        // ----- Gold Icon Setup----- //
        Image goldIcon = new Image(app.assets.goldIcon);
        goldIcon.setPosition(Gdx.graphics.getWidth() - 105,  Gdx.graphics.getHeight() - 80);

        txtGold = new Label("Gold: " + world.user.dkk, skin);
        txtGold.setPosition(Gdx.graphics.getWidth() - 85,  Gdx.graphics.getHeight() - 60);
        //hud.addActor(goldIcon);
        hud.addActor(txtGold);

        // ----- Water Icon Setup----- //
        // 220 pixels left of water icon...
        Image waterIcon = new Image(app.assets.waterIcon);
        waterIcon.setPosition(Gdx.graphics.getWidth() - 225,  Gdx.graphics.getHeight() - 80);
        txtWater = new Label("" + world.user.dkk, skin);
        txtWater.setPosition(Gdx.graphics.getWidth() - 165,  Gdx.graphics.getHeight() - 60);

        // Show coordinates of selected tile.
        txtSelectedTileCoordinates = new Label("Water: ", skin);
        txtSelectedTileCoordinates.setPosition(Gdx.graphics.getWidth()- 55, Gdx.graphics.getHeight() - 150);
        //hud.addActor(txtSelectedTileCoordinates);
        //hud.addActor(waterIcon);
        //hud.addActor(txtWater);

    }
    void setButton (String text, Skin skin) {
    	buttonList.add(new TextButton(text,skin));
    }
    // Consider: Individual setup button methods and update scrollpane methods.
    // Can also be used when new skills are learned.
    void setupTileImprovementBox() {

    	for (int i=0;i<8;i++) {
    		String t = "Plant something" + i ;
    		setButton(t,skin);
    	}
        buttonTable = new Table(skin);
        outerTable = new Table(skin);

        for(final TextButton textButton : buttonList){
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
        }

        buttonTable.setSize(200, 100);
        outerTable.setSize(600, 400);
        scrollPane = new ScrollPane(buttonTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        outerTable.add(scrollPane).expandY();

    }


    public void updateHUD() {
        txtSelectedTileCoordinates.setText(world.hoveredX + "," + world.hoveredY);
        txtGold.setText("Gold: " + world.user.dkk);
        txtTurnNumber.setText("" + world.turnNumber);

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

            app.setScreen(app.pauseScreen);
        }

    }

    // https://stackoverflow.com/questions/14700577/drawing-transparent-shaperenderer-in-libgdx
    public void drawMenu(){

        // ----- Color line ----- //
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        int rect_x = Gdx.graphics.getWidth();
        int rect_y = Gdx.graphics.getHeight() - 100;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(hudColor);
        shapeRenderer.rect(0, rect_y, Gdx.graphics.getWidth(), rect_x);
        shapeRenderer.rect(824,0, 200, Gdx.graphics.getHeight()-100);
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
        updateHUD();*/
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
}
