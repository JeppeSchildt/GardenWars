package com.garden.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garden.game.GardenGame;
import com.garden.game.world.World;


// When tile is hovered, show some menu with things you can do to that
// tile. When house is clicked show what you can do there.
// Show stats, gold etc. Provide a button for ending turn.
// This sort of stuff goes in a stage...?
public class GameScreen implements Screen {
    public World world;
    GardenGame app;
    Stage hud;
    public Label goldText, waterText,  turnNumber;
    private final InputMultiplexer mux;

    public GameScreen(GardenGame app) {
        this.app = app;
        world = new World(app);
        final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud = new Stage(new ScreenViewport(camera));
        //mux = new InputMultiplexer(hud, world.mapInput);
        mux = new InputMultiplexer();
        mux.addProcessor(world.mapInput);
        mux.addProcessor(hud);
        Gdx.input.setInputProcessor(mux);
        initHUD();

        /*
          InputMultiplexer multiplexer = new InputMultiplexer();
            multiplexer.addProcessor(camController);
            multiplexer.addProcessor(stage);

            Gdx.input.setInputProcessor(multiplexer);
         */

    }

    private void initHUD() {

        setUpIcon();

        // ----- NextTurn Setup----- //
        ImageButton btnEndTurn = new ImageButton(app.assets.nextturnIcon);
		btnEndTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.endTurn();
                //Gdx.app.debug("DEBUG", "clicked");
                //System.out.println("clicked");
            }
        });

        // 1/8 not good solution ...
		btnEndTurn.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/6, 0);

        turnNumber = new Label("Turn" + world.turnNumber, app.assets.largeTextStyle);
        turnNumber.setAlignment(Align.bottomLeft);
        turnNumber.setPosition(575, 15);




        hud.addActor(btnEndTurn);
        hud.addActor(turnNumber);
    }

    private void setUpIcon() {
        // ----- Gold Icon Setup----- //
        Image goldIcon = new Image(app.assets.goldIcon);
        goldIcon.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/6,  Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/6 + 10);

        goldText = new Label("" + world.user.dkk, app.assets.largeTextStyle);
        goldText.setAlignment(Align.bottomLeft);

        goldText.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/11,  Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/11-10);

        hud.addActor(goldIcon);
        hud.addActor(goldText);

        // ----- Water Icon Setup----- //

        Image waterIcon = new Image(app.assets.waterIcon);
        waterIcon.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/3,  Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/6 + 10);

        waterText = new Label("" + world.user.dkk, app.assets.largeTextStyle);
        waterText.setAlignment(Align.bottomLeft);

        waterText.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/4,  Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/11-10);

        hud.addActor(waterIcon);
        hud.addActor(waterText);

    }


    public void updateHUD() {
        goldText.setText("" + world.user.dkk);
        turnNumber.setText("" + world.turnNumber);
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

        hud.draw();
        hud.act(delta);
    }

    public void drawMenu(){

        // ----- Color line ----- //
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        // Starting point of menu/hud in right side of screen.
        int rect_x = Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/7;
        int rect_y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/10;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(10,100,50, 0);
        shapeRenderer.rect(0, rect_y, Gdx.graphics.getWidth(), rect_x);
        shapeRenderer.end();
        updateHUD();

        // ----- Main Color ----- //
        ShapeRenderer shapeRenderer2 = new ShapeRenderer();
        // Starting point of menu/hud in right side of screen.
        int rect_x2 = Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/7;
        int rect_y2 = Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/12;
        shapeRenderer2.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer2.setColor(255,184,10, 0);
        shapeRenderer2.rect(0, rect_y2, Gdx.graphics.getWidth(), rect_x2);
        shapeRenderer2.end();
        updateHUD();
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
