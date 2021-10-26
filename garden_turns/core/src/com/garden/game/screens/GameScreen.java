package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;

import javax.swing.text.Style;
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
    SelectBox<String> sltBoxTileImprovements;
    Dialog dialogTileImprovements;
    Style tableStyle;
    Table buttonTable,outerTable;
    ScrollPane scrollPane;
    Skin skin;
    private final InputMultiplexer mux;
    int maxWidth, maxHeight;
    boolean ignore;
    private final Color hudColor;
    private Label txtSelectedTileCoordinates;

    public GameScreen(GardenGame app) {
        this.app = app;
        world = new World(app);
        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        hud = new Stage(new ScreenViewport(camera));
        hudColor = new Color(1, 1, 1, 0.5f);
        mux = new InputMultiplexer();
        mux.addProcessor(hud);
        mux.addProcessor(this);
        mux.addProcessor(world.mapInput);
        Gdx.input.setInputProcessor(mux);
        initHUD();

        maxWidth = world.tileSize*world.worldWidth;
        maxHeight = Gdx.graphics.getHeight()-100;
        ignore = false;

    }

    private void initHUD() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        setUpIcon();
        setUpButtons();
        setupTileImprovementBox();

    }

    private void setUpButtons() {
        // ----- NextTurn Setup----- //
        ImageButton btnEndTurn = new ImageButton(app.assets.nextturnIcon);
        btnEndTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.endTurn();
                System.out.println("clicked");
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

        txtGold = new Label("" + world.user.dkk, skin);

        txtGold.setPosition(Gdx.graphics.getWidth() - 45,  Gdx.graphics.getHeight() - 60);

        hud.addActor(goldIcon);
        hud.addActor(txtGold);

        // ----- Water Icon Setup----- //
        // 220 pixels left of water icon...
        Image waterIcon = new Image(app.assets.waterIcon);
        waterIcon.setPosition(Gdx.graphics.getWidth() - 225,  Gdx.graphics.getHeight() - 80);
        txtWater = new Label("" + world.user.dkk, skin);
        txtWater.setPosition(Gdx.graphics.getWidth() - 165,  Gdx.graphics.getHeight() - 60);

        // Show coordinates of selected tile.
        txtSelectedTileCoordinates = new Label("", skin);

        txtSelectedTileCoordinates.setPosition(Gdx.graphics.getWidth()- 55, Gdx.graphics.getHeight() - 150);


        hud.addActor(txtSelectedTileCoordinates);
        hud.addActor(waterIcon);
        hud.addActor(txtWater);

    }

    void setupTileImprovementBox() {

        final TextButton button1 = new TextButton("Water tile",skin);
        final TextButton button2 = new TextButton("Plant something",skin);
        final TextButton button3 = new TextButton("Plant something3",skin);
        final TextButton button4 = new TextButton("Plant something4",skin);
        final TextButton button5 = new TextButton("Plant something5",skin);
        ArrayList<TextButton> buttonList = new ArrayList<>();
        buttonList.add(new TextButton("Water tile",skin));
        buttonList.add(new TextButton("Plant something",skin));
        buttonList.add(new TextButton("Plant something3",skin));
        buttonList.add(new TextButton("Plant something4",skin));
        buttonList.add(new TextButton("Plant something5",skin));
        buttonTable = new Table(skin);
        outerTable = new Table(skin);
        for(final TextButton textButton : buttonList){
            textButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("clicked " + textButton.getText());
                }
            });
            buttonTable.add(textButton);
            buttonTable.row();
        }

        buttonTable.setSize(100, 100);
        outerTable.setSize(200, 200);
        scrollPane = new ScrollPane(buttonTable, skin);
        outerTable.add(scrollPane);
        outerTable.setPosition(824, 400);
        hud.addActor(outerTable);

    }


    public void updateHUD() {
        txtSelectedTileCoordinates.setText(world.hoveredX + "," + world.hoveredY);
        txtGold.setText("" + world.user.dkk);
        txtTurnNumber.setText("" + world.turnNumber);

    }

    @Override
    public void show() {
        //Gdx.input.setInputProcessor(mux);

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
}
